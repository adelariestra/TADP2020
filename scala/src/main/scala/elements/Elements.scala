package elements

import parsers._

import scala.util.Try

case object figure extends Parser[FigureTr] {
  override def apply(cadena: String): Try[ResultadoParseo[FigureTr]] = {
    val parseadorGeneral = triangle <|> rectangle <|> circle <|> group <|> transformation
    parseadorGeneral.apply(cadena)
  }
}

case object espacios extends Parser[String]{
  override def apply(cadena :String): Try[ResultadoParseo[String]] ={
    val parserAux = string("\n") <|> string("\t") <|> string(" ")
    parserAux.*(cadena).map(elem => ResultadoParseo(elem.elementoParseado.toString(),elem.cadenaRestante))
  }
}

case object positions extends Parser[List[(Double,Double)]] {
  override def apply(cadena: String): Try[ResultadoParseo[List[(Double,Double)]]] = {
    val posSeparator = string(" @ ") <|> string("@")
    val elemSeparator = string(", ") <|> string(",")
    val parserPosicion = double <~ posSeparator <> double
    val parseadorGeneral = parserPosicion.sepBy(elemSeparator)

    parseadorGeneral.apply(cadena)
  }
}

