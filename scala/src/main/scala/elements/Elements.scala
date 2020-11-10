package elements

import parsers._

import scala.util.Try

case object figure extends Parser[FigureTr] {
  override def apply(cadena: String): Try[ResultadoParseo[FigureTr]] = {
    val parsers1 = triangle <|> rectangle
    val parseadorGeneral = parsers1 <|> circle
    parseadorGeneral.apply(cadena)
  }
}