package elements

import elements.circle.obtainFigure
import parsers._

import scala.util.Try

case object group extends Parser[GroupFigure] {
  override def apply(cadena: String): Try[ResultadoParseo[GroupFigure]] = {
    val parseadorGeneral = string("grupo(") <> figure.sepBy(string(", ")) <> char(')');
    parseadorGeneral.apply(cadena).map((element)=>obtainFigure(element))
  }

  def obtainFigure(result: ResultadoParseo[((String, List[FigureTr]), Char)])={

    ResultadoParseo(GroupFigure(result.elementoParseado._1._2),result.cadenaRestante)
  }
}

//TODO: pasar a ComplexElement y aprovechar con TransformElem
case class GroupFigure(int1: List[FigureTr]) extends  FigureTr {
  //   TODO: ver si es funcion que recibe parametros y devuelve
  //    triangulo o si es un objeto
}