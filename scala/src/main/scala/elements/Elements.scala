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
  override def apply(cadena :String) ={
    val parserAux = string("\n") <|> string("\t") <|> string(" ")
    parserAux.*(cadena).map(elem => ResultadoParseo(elem.elementoParseado.toString(),elem.cadenaRestante))
  }
}

case object positions extends Parser[List[Position]] {
  override def apply(cadena: String): Try[ResultadoParseo[List[Position]]] = {
    val parserPosicion = integer <~ string(" @ ") <> integer
    val parseadorGeneral = parserPosicion.sepBy(string(", "))

    parseadorGeneral.apply(cadena).map((element) => obtainFigure(element))
  }

  def obtainFigure(result: ResultadoParseo[List[(Int, Int)]]): ResultadoParseo[List[Position]] = {
    val positionsList = result.elementoParseado
    ResultadoParseo(positionsList.map((element) => Position(element._1, element._2)), result.cadenaRestante)
  }
}

