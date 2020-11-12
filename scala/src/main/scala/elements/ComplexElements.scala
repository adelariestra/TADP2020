package elements

import parsers._

import scala.util.Try

case object group extends Parser[GroupFigure] {
  override def apply(cadena: String): Try[ResultadoParseo[GroupFigure]] = {
    val parseadorGeneral = string("grupo") ~> groupContent
    parseadorGeneral.apply(cadena).map(element => obtainFigure(element))
  }

  def obtainFigure(result: ResultadoParseo[List[FigureTr]]): ResultadoParseo[GroupFigure] = {
    ResultadoParseo(GroupFigure(result.elementoParseado), result.cadenaRestante)
  }
}

case object groupContent extends Parser[List[FigureTr]] {
  override def apply(cadena: String): Try[ResultadoParseo[List[FigureTr]]] = {
    val parseadorAux = string(",") <~ espacios
    val parseadorGeneral = char('(') ~> espacios ~> figure.sepBy(parseadorAux) <~ espacios <~ char(')')
    parseadorGeneral.apply(cadena)
  }
}

case object figureContained extends Parser[FigureTr] {
  override def apply(cadena: String): Try[ResultadoParseo[FigureTr]] = {
    val parseadorGeneral = char('(') ~> espacios ~> figure <~ espacios <~ char(')')
    parseadorGeneral.apply(cadena)
  }
}

case object transformation extends Parser[TransformTr] {
  override def apply(cadena: String): Try[ResultadoParseo[TransformTr]] = {
    val parseadorGeneral = escala <|> rotacion <|> traslacion <|> color
    parseadorGeneral.apply(cadena)
  }
}

case object escala extends Parser[EscalaTr] {
  override def apply(cadena: String): Try[ResultadoParseo[EscalaTr]] = {
    val parseadorGeneral = string("escala[") ~> double <~ string(", ") <> double <~ string("]") <> figureContained
    parseadorGeneral.apply(cadena).map(element => obtainFigure(element))
  }

  def obtainFigure(result: ResultadoParseo[((Double,Double), FigureTr)]): ResultadoParseo[EscalaTr] = {
    ResultadoParseo(EscalaTr(result.elementoParseado._2, result.elementoParseado._1._1, result.elementoParseado._1._2), result.cadenaRestante)
  }
}

case object rotacion extends Parser[RotacionTr] {
  override def apply(cadena: String): Try[ResultadoParseo[RotacionTr]] = {
    val parseadorGeneral = string("rotacion[") ~> integer <~ string("]") <> figureContained
    parseadorGeneral.apply(cadena).map(element => obtainFigure(element))
  }

  def obtainFigure(result: ResultadoParseo[(Int, FigureTr)]): ResultadoParseo[RotacionTr] = {
    ResultadoParseo(RotacionTr(result.elementoParseado._2,result.elementoParseado._1), result.cadenaRestante)
  }
}

case object traslacion extends Parser[TraslacionTr] {
  override def apply(cadena: String): Try[ResultadoParseo[TraslacionTr]] = {
    val parseadorGeneral = string("traslacion[") ~> integer <~ string(", ") <> integer <~ string("]") <> figureContained
    parseadorGeneral.apply(cadena).map(element => obtainFigure(element))
  }

  def obtainFigure(result: ResultadoParseo[((Int,Int), FigureTr)]): ResultadoParseo[TraslacionTr] = {
    ResultadoParseo(TraslacionTr(result.elementoParseado._2,result.elementoParseado._1._1, result.elementoParseado._1._2), result.cadenaRestante)
  }
}
case object color extends Parser[ColorTr] {
  override def apply(cadena: String): Try[ResultadoParseo[ColorTr]] = {
    val parseadorGeneral = string("color[") ~> integer <~ string(", ") <> integer <~ string(", ") <> integer <~ string("]") <> figureContained
    parseadorGeneral.apply(cadena).map(element => obtainFigure(element))
  }

  def obtainFigure(result: ResultadoParseo[(((Int,Int),Int), FigureTr)]): ResultadoParseo[ColorTr] = {
    ResultadoParseo(ColorTr(result.elementoParseado._2,result.elementoParseado._1._1._1, result.elementoParseado._1._1._2,result.elementoParseado._1._2), result.cadenaRestante)
  }
}

