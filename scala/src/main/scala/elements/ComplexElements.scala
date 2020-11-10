package elements

import elements.circle.obtainFigure
import parsers._

import scala.util.Try

case object espacios extends Parser[String]{
  override def apply(cadena :String) ={
    val parserAux = string("\n") <|> string("\t") <|> string(" ")
    parserAux.*(cadena).map(elem => ResultadoParseo(elem.elementoParseado.toString(),elem.cadenaRestante))
  }
}

case object group extends Parser[GroupFigure] {
  override def apply(cadena: String): Try[ResultadoParseo[GroupFigure]] = {
    // TODO: faltan los /n y /t dentro del sepBy
    val parseadorGeneral = string("grupo(") <~ espacios <> figure.sepBy(string(", ")) <~ espacios <> char(')');
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

