package parsers

import scala.util.{Failure, Try}


case class ConcatComb[T, U](element1: Parser[T], element2: Parser[U]) extends Parser[(T, U)] {
  override def apply(cadena: String) = {
    val resultado1 = element1.apply(cadena)
    val resultado2 = Try(element2.apply(resultado1.get.cadenaRestante).get)
    Try(ResultadoParseo((resultado1.get.elementoParseado, resultado2.get.elementoParseado), resultado2.get.cadenaRestante))
  }
  // TODO: define if we are going to do this refactor
//  def getFirstElement(resultadoParseo: Try[ResultadoParseo[(T, U)]]): Try[T] ={
//    resultadoParseo.map((elem:ResultadoParseo[(T, U)])=>elem.elementoParseado._1)
//  }
}

case class ORComb[T <: V, U <: V, V](element1: Parser[T], element2: Parser[U]) extends Parser[V] {
  override def apply(cadena: String):Try[ResultadoParseo[V]] = {
    element1.apply(cadena).recoverWith {
      case _ => element2.apply(cadena)
    }
  }
}

case class RightComb[T, U](element1: Parser[T], element2: Parser[U]) extends Parser[U] {
  override def apply(cadena: String): Try[ResultadoParseo[U]] = {
    Try {
      val cadenaRestante = element1.apply(cadena).get.cadenaRestante
      element2.apply(cadenaRestante).get
    }.recoverWith { case e: Exception => return Failure(e) }
  }
}

//Fue idea de Román cambiar esto
case class LeftComb[T, U](element1: Parser[T], element2: Parser[U]) extends Parser[T] {
  override def apply(cadena: String): Try[ResultadoParseo[T]] = {
    Try {
      val result1 = element1.apply(cadena)
      val result2 = element2.apply(result1.get.cadenaRestante).get // TODO: Mejorar
      return Try(ResultadoParseo(result1.get.elementoParseado, result2.cadenaRestante))
    }.recoverWith { case e: Exception => return Failure(e) }
  }
}

case class SepComb[T, U](elementContent: Parser[T], elementSeparator: Parser[U]) extends Parser[List[T]] {
  override def apply(cadena: String): Try[ResultadoParseo[List[T]]] = {
    val secondElement = elementSeparator <> elementContent
    val fullParser = elementContent <> secondElement.*

    fullParser.apply(cadena).map((elem: ResultadoParseo[(T, List[(U, T)])])=>{
      val listaTuplas = elem.elementoParseado._2
      val listaCompleta = List(elem.elementoParseado._1).appendedAll(listaTuplas.map((elem:(U, T))=>{
        elem._2
      }))
      ResultadoParseo(listaCompleta, elem.cadenaRestante)
    })
  }
}
