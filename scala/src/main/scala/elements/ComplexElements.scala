package elements

import parsers._

import scala.util.Try

//case object group extends Parser[TriangleFigure] {
//  override def apply(cadena: String): Try[ResultadoParseo[TriangleFigure]] = {
//    val parserPosiciones = integer.sepBy(string(" @ ")).sepBy(string(", "))
//    val parseadorGeneral = string("triangulo[") <> parserPosiciones <> char(']');
//
//    parseadorGeneral.apply(cadena).map((element) => obtainFigure(element))
//  }
//
//  // TODO: refactor to common function betweem figures
//  def obtainFigure(result: ResultadoParseo[((String, List[List[Int]]), Char)]): ResultadoParseo[TriangleFigure] = {
//    val positionsList = result.elementoParseado._1._2
//    if (positionsList.size != 3) throw new Exception("Invalid amount of elements") //TODO: change exception type
//    ResultadoParseo( //TODO: des-algoritmizar
//      TriangleFigure(
//        (positionsList(0)(0), positionsList(0)(1)),
//        (positionsList(1)(0), positionsList(1)(1)),
//        (positionsList(2)(0), positionsList(2)(1)))
//      , result.cadenaRestante)
//  }
//}

//TODO: pasar a ComplexElement y aprovechar con TransformElem
case class GroupFigure(int1: List[FigureTr]) extends  FigureTr {
  //   TODO: ver si es funcion que recibe parametros y devuelve
  //    triangulo o si es un objeto
}