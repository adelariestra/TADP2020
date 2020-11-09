package parsers

import scala.util.Try
import scala.util.control.NonFatal

case class ResultadoParseo[T] (elementoParseado :T, cadenaRestante :String)

// TODO: modificar para que en caso de falla guardemos la cadena que falló

