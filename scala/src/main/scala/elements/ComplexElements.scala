package elements

import elements.circle.obtainFigure
import parsers._

import scala.util.Try

case object group extends Parser[GroupFigure] {
  override def apply(cadena: String): Try[ResultadoParseo[GroupFigure]] = {
    val parseadorAux = string(",") <~ espacios
    val parseadorGeneral = string("grupo(") <~ espacios <> figure.sepBy(parseadorAux) <~ espacios <~ char(')');
    parseadorGeneral.apply(cadena).map((element)=>obtainFigure(element))
  }

  def obtainFigure(result: ResultadoParseo[(String, List[FigureTr])])={
    ResultadoParseo(GroupFigure(result.elementoParseado._2),result.cadenaRestante)
  }
}



