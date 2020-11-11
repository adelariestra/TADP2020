package elements

import elements.circle.obtainFigure
import parsers._

import scala.util.Try

case object group extends Parser[GroupFigure] {
  override def apply(cadena: String): Try[ResultadoParseo[GroupFigure]] = {
    val parseadorAux = string(",") <~ espacios
    val parseadorGeneral = string("grupo(") <~ espacios <> figure.sepBy(parseadorAux) <~ espacios <~ char(')');
    parseadorGeneral.apply(cadena).map((element) => obtainFigure(element))
  }

  def obtainFigure(result: ResultadoParseo[(String, List[FigureTr])]) = {
    ResultadoParseo(GroupFigure(result.elementoParseado._2), result.cadenaRestante)
  }
}

case object transformation extends Parser[TransformTr] {
  override def apply(cadena: String): Try[ResultadoParseo[TransformTr]] = {
    val parseadorGeneral = escala
//    <|> rotacion <|> traslacion <|> color
    parseadorGeneral.apply(cadena)
  }
}

case object escala extends Parser[EscalaTr] {
  override def apply(cadena: String): Try[ResultadoParseo[EscalaTr]] = {
    val parseadorAux = string(",") <~ espacios
    val parseadorGeneral = string("escala[") ~> double <~ string(", ") <> double <~ string("](") <~ espacios <> figure.sepBy(parseadorAux) <~ espacios <~ char(')');
    parseadorGeneral.apply(cadena).map((element) => obtainFigure(element))
  }

  def obtainFigure(result: ResultadoParseo[((Double,Double), List[FigureTr])]) = {
    ResultadoParseo(EscalaTr(result.elementoParseado._2,result.elementoParseado._1._1, result.elementoParseado._1._2), result.cadenaRestante)
  }
}

//
//case object rotacion extends Parser[TransformTr] {
//  override def apply(cadena: String): Try[ResultadoParseo[GroupFigure]] = {
//    val parseadorAux = string(",") <~ espacios
//    val parseadorGeneral = string("grupo(") <~ espacios <> figure.sepBy(parseadorAux) <~ espacios <~ char(')');
//    parseadorGeneral.apply(cadena).map((element) => obtainFigure(element))
//  }
//
//  def obtainFigure(result: ResultadoParseo[(String, List[FigureTr])]) = {
//    ResultadoParseo(GroupFigure(result.elementoParseado._2), result.cadenaRestante)
//  }
//}
//case object traslacion extends Parser[TransformTr] {
//  override def apply(cadena: String): Try[ResultadoParseo[GroupFigure]] = {
//    val parseadorAux = string(",") <~ espacios
//    val parseadorGeneral = string("grupo(") <~ espacios <> figure.sepBy(parseadorAux) <~ espacios <~ char(')');
//    parseadorGeneral.apply(cadena).map((element) => obtainFigure(element))
//  }
//
//  def obtainFigure(result: ResultadoParseo[(String, List[FigureTr])]) = {
//    ResultadoParseo(GroupFigure(result.elementoParseado._2), result.cadenaRestante)
//  }
//}
//case object color extends Parser[TransformTr] {
//  override def apply(cadena: String): Try[ResultadoParseo[GroupFigure]] = {
//    val parseadorAux = string(",") <~ espacios
//    val parseadorGeneral = string("grupo(") <~ espacios <> figure.sepBy(parseadorAux) <~ espacios <~ char(')');
//    parseadorGeneral.apply(cadena).map((element) => obtainFigure(element))
//  }
//
//  def obtainFigure(result: ResultadoParseo[(String, List[FigureTr])]) = {
//    ResultadoParseo(GroupFigure(result.elementoParseado._2), result.cadenaRestante)
//  }
//}

