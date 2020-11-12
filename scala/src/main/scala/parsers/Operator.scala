package parsers

import scala.util.{Failure, Success, Try}


case class SatisfiesOp[T](element1: Parser[T], f: T => Boolean) extends Parser[T] {
  override def apply(cadena: String): Try[ResultadoParseo[T]] = {
    element1.apply(cadena).filter(elem => f(elem.elementoParseado))
  }
}

case class OptOp[T](element1: Parser[T]) extends Parser[Option[T]] {
  override def apply(cadena: String): Try[ResultadoParseo[Option[T]]] = {
    Success(ResultadoParseo(Try(element1.apply(cadena).get.elementoParseado).toOption, element1.apply(cadena).getOrElse(ResultadoParseo("", cadena)).cadenaRestante))
  }

  def none[A]: Option[A] = None
}

case class KleeneOp[T](element1: Parser[T]) extends Parser[List[T]] {
  override def apply(cadena: String): Try[ResultadoParseo[List[T]]] = {
    element1.+.apply(cadena).recoverWith { case _ => Success(ResultadoParseo(List[T](), cadena)) }
  }
}

case class ClauPoseOp[T](element1: Parser[T]) extends Parser[List[T]] {

  override def apply(cadena: String): Try[ResultadoParseo[List[T]]] = {
    val listaCreada = buildList(element1, cadena)
    if (listaCreada._1 == List())
      Failure(new Exception) // TODO: cambiar nombre de excepción
    else
      Try(ResultadoParseo(listaCreada._1, listaCreada._2))
  }

  def buildList(element1: Parser[T], cadena: String): (List[T], String) = {
    val resultado1 = element1.apply(cadena)
    resultado1 match {
      case Success(_) =>
        val resultadoSig = buildList(element1, resultado1.get.cadenaRestante)
        (List[T](resultado1.get.elementoParseado).appendedAll(resultadoSig._1), resultadoSig._2)
      case Failure(_) =>
        (List[T](), cadena)
    }
  }
}

case class MapOp[T, U](element1: Parser[T], f: T => U) extends Parser[U] {
  override def apply(cadena: String): Try[ResultadoParseo[U]] = {
    element1.apply(cadena).map((elem: ResultadoParseo[T]) => {
      ResultadoParseo(f(elem.elementoParseado), elem.cadenaRestante)
    })
  }
}