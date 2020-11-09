package parsers

import com.sun.net.httpserver.Authenticator.Failure

import scala.util
import scala.util.{Failure, Success, Try}

trait Parser[T] extends (String => Try[ResultadoParseo[T]]){
  def <>[U](parser2: Parser[U]) = ConcatComb[T, U](this, parser2)
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
  override def apply(cadena: String) :Try[ResultadoParseo[Int]] = {
    char('-')(cadena).transform(
      elem => getConcatParseado(elem.cadenaRestante, "", true),
      elem => getConcatParseado(cadena, "", false)
    )
  }

  def getConcatParseado(cadena: String, valorParseado: String, esNegativo: Boolean) :Try[ResultadoParseo[Int]] = {
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
    Try(ResultadoParseo(8,""))
  }
}

case class ConcatComb[T, U](element1: Parser[T], element2: Parser[U]) extends Parser[(T,U)] {
  override def apply(cadena: String) = {
    val resultado1 = element1.apply(cadena)
    val resultado2 = Try(element2.apply(resultado1.get.cadenaRestante).get)
    Try(ResultadoParseo((resultado1.get.elementoParseado, resultado2.get.elementoParseado), resultado2.get.cadenaRestante))
  }
}

/*
TODO: Implementar
case class ORComb[T, U, V >: T with U](element1: Parser[T], element2: Parser[U]) extends Parser[V] {
  override def apply(cadena: String) = {
    if (element1.apply(cadena).isSuccess) element1.apply(cadena)
    else element2.apply(cadena)
  }
}

case class LeftComb[T,U](element1: Parser[T], element2: Parser[U]) extends Parser[T]{
  override def apply(cadena :String): Try[ResultadoParseo[T]] = {
    val resultado1 :Try[ResultadoParseo[T]] = element1.apply(cadena)
    val resultado2 :Try[ResultadoParseo[U]] = Try(element2.apply(resultado1.get.cadenaRestante).get)
    if(resultado2.isSuccess) resultado1
    else ???
  }
}
*/