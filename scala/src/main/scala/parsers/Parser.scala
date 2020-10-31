package parsers

import scala.util.{Success, Try}

// TODO: cambiar nombre de package
package object Parsertest {

  def anyChar(): ParserBasico ={
    AnyCharP
  }

  def char(charAMatchear: Char): ParserBasico = {
    CharP(charAMatchear)
  }
  def digitP: ParserBasico = {
    DigitP
  }

  def string(stringAMatchear: String): ParserBasico = {
    StringP(stringAMatchear)
  }

  def integer: ParserBasico = {
    IntegerP
  }

  def double: ParserBasico = {
    DoubleP
  }

}

// sepby = contenido <> (separador <> contenido).+
// integer = char('-').opt <> digit.+ =  results.flatmap(...).toInteger
