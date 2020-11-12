package parsers

import scala.util.Try
import scala.util.control.NonFatal

case class ResultadoParseo[+T] (elementoParseado :T, cadenaRestante :String)
