package parsers

import scala.util.{Failure, Success, Try}

trait Parser[T] extends (String => Try[ResultadoParseo[T]]) {
  // combinator
  def <>[U](parser2: Parser[U]) = ConcatComb[T, U](this, parser2)

  def <|>[U](parser2: Parser[U]) = ORComb[T, U](this, parser2)

  def <~[U](parser2: Parser[U]) = LeftComb[T, U](this, parser2)

  def ~>[U](parser2: Parser[U]) = RightComb[T, U](this, parser2)

  //TODO: sepBy pending

  // operators
  def satisfies(funcion: T => Boolean) = SatisfiesOp(this, funcion)

  def opt = OptOp(this)

  def * = KleeneOp(this)

  def + = ClauPoseOp(this)
}

case object anyChar extends Parser[Char] {
  override def apply(cadena: String) = {
    Try(cadena).map(elemento => ResultadoParseo(elemento.head, elemento.tail))
  }
}

case class char(charAMatchear: Char) extends Parser[Char] {
  override def apply(cadena: String) = {
    Try(cadena).filter(elemento => elemento.head.equals(charAMatchear))
      .map(elemento => ResultadoParseo(elemento.head, elemento.tail))
  }
}

case object digit extends Parser[Char] {
  override def apply(cadena: String) = {
    Try(cadena).filter(elemento => elemento.head.isDigit)
      .map(elemento => ResultadoParseo(elemento.head, elemento.tail))
  }
}

case class string(stringName: String) extends Parser[String] {
  override def apply(cadena: String) = {
    Try(cadena).filter(elemento => elemento.take(stringName.length).equals(stringName))
      .map(elemento => ResultadoParseo(elemento.take(stringName.length), elemento.drop(stringName.length)))
  }
}

case object integer extends Parser[Int] {
  override def apply(cadena: String): Try[ResultadoParseo[Int]] = {
    char('-')(cadena).transform(
      elem => getConcatParseado(elem.cadenaRestante, "", true),
      elem => getConcatParseado(cadena, "", false)
    )
  }

  def getConcatParseado(cadena: String, valorParseado: String, esNegativo: Boolean): Try[ResultadoParseo[Int]] = {
    digit(cadena).transform(
      elem => Success(
        getConcatParseado(
          elem.cadenaRestante,
          concatInt(valorParseado, elem.elementoParseado, esNegativo),
          false).get
      ),
      _ => Success(ResultadoParseo(valorParseado.toInt, cadena))
    )
  }

  def concatInt(valorParseado: String, aConcatenar: Any, esNegativo: Boolean): String = {
    var stringBase: String = if (esNegativo) "-" else valorParseado
    val valorAConcatenar = aConcatenar

    (stringBase + valorAConcatenar)
  }
}

case object double extends Parser[Double] {
  override def apply(cadena: String) = {
    // TODO: Implementar
    Try(ResultadoParseo(8, ""))
  }
}

case class ConcatComb[T, U](element1: Parser[T], element2: Parser[U]) extends Parser[(T, U)] {
  override def apply(cadena: String) = {
    val resultado1 = element1.apply(cadena)
    val resultado2 = Try(element2.apply(resultado1.get.cadenaRestante).get)
    Try(ResultadoParseo((resultado1.get.elementoParseado, resultado2.get.elementoParseado), resultado2.get.cadenaRestante))
  }
}

case class ORComb[T, U](element1: Parser[T], element2: Parser[U]) extends Parser[Any] {
  override def apply(cadena: String) = {
    element1.apply(cadena).recoverWith {
      case _ => element2.apply(cadena)
    }
  }
}

case class RightComb[T, U](element1: Parser[T], element2: Parser[U]) extends Parser[Any] {
  override def apply(cadena: String): Try[ResultadoParseo[Any]] = {
    Try {
      val cadenaRestante = element1.apply(cadena).get.cadenaRestante
      element2.apply(cadenaRestante).get
    }.recoverWith { case e: Exception => return Failure(e) }
  }
}

case class LeftComb[T, U](element1: Parser[T], element2: Parser[U]) extends Parser[T] {
  override def apply(cadena: String): Try[ResultadoParseo[T]] = {
    Try {
      val result1 = element1.apply(cadena)
      element2.apply(result1.get.cadenaRestante).get // TODO: Mejorar
      return result1
    }.recoverWith { case e: Exception => return Failure(e) }
  }
}

case class SatisfiesOp[T](element1: Parser[T], f: T => Boolean) extends Parser[T] {
  override def apply(cadena: String): Try[ResultadoParseo[T]] = {
    element1.apply(cadena).filter(elem => f(elem.elementoParseado))
  }
}

case class OptOp[T](element1: Parser[T]) extends Parser[Option[T]] {
  override def apply(cadena: String):Try[ResultadoParseo[Option[T]]] = {
    //Success(element1.apply(cadena).getOrElse(ResultadoParseo(none[T].get,cadena))) TODO: fix
    Success(ResultadoParseo(Try(element1.apply(cadena).get.elementoParseado).toOption, element1.apply(cadena).getOrElse(ResultadoParseo("", cadena)).cadenaRestante))
  }

  def none[A]: Option[A] = None
}

case class KleeneOp[T](element1: Parser[T]) extends Parser[List[T]] {
  override def apply(cadena: String):Try[ResultadoParseo[List[T]]] = {
    element1.+.apply(cadena).recoverWith {case _ => Success(ResultadoParseo(List[T](),cadena))}
  }
}

case class ClauPoseOp[T](element1: Parser[T]) extends Parser[List[T]] {


  override def apply(cadena: String):Try[ResultadoParseo[List[T]]] = {
    val listaCreada = buildList(element1,cadena)
    if (listaCreada._1 == List())
      return Failure(new Exception)
    else
      return Try(ResultadoParseo(listaCreada._1,listaCreada._2))
  }

  def buildList(element1: Parser[T],cadena:String): (List[T],String) ={
    val resultadoParseo = element1.apply(cadena)
    resultadoParseo match {
      case Success(_) => {
        val resultadoSig = buildList(element1,resultadoParseo.get.cadenaRestante)
        return (List[T](resultadoParseo.get.elementoParseado).appendedAll(resultadoSig._1),resultadoSig._2)
      }
      case Failure(_) =>{
        return (List[T](),cadena);
      }
    }
  }
}