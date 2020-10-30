package parsers

import scala.util.{Success, Try}

// TODO: cambiar nombre de package
package object Parsertest {

  // TODO: Terminar de poner todas las funciones de los parsers basicos
  def char(charAMatchear: Char): ParserBasico = {
    CharP(charAMatchear)
  }

  def integer: ParserBasico = {
    IntegerP
  }

  def string(stringAMatchear: String): ParserBasico = {
    StringP(stringAMatchear)
  }


}

// sepby = contenido <> (separador <> contenido).+
// integer = char('-').opt <> digit.+ =  results.flatmap(...).toInteger
