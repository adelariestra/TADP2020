package elements

import parsers._

import scala.util.Try

trait ParserElements[T] extends (String => Try[ResultadoParseo[T]])

case object triangle extends ParserElements[TriangleFigure] {
  override def apply(cadena: String): Try[ResultadoParseo[TriangleFigure]] = {
    val parserPosiciones = integer.sepBy(string(" @ ")).sepBy(string(", "))
    val parseadorGeneral = string("triangulo[") <> parserPosiciones <> char(']');

    parseadorGeneral.apply(cadena).map((element) => obtainFigure(element))
  }

  // TODO: refactor to common function betweem figures
  def obtainFigure(result: ResultadoParseo[((String, List[List[Int]]), Char)]): ResultadoParseo[TriangleFigure] = {
    val positionsList = result.elementoParseado._1._2
    if (positionsList.size != 3) throw new Exception("Invalid amount of elements") //TODO: change exception type
    ResultadoParseo( //TODO: des-algoritmizar
      TriangleFigure(
        (positionsList(0)(0), positionsList(0)(1)),
        (positionsList(1)(0), positionsList(1)(1)),
        (positionsList(2)(0), positionsList(2)(1)))
      , result.cadenaRestante)
  }
}

case object rectangle extends ParserElements[RectangleFigure] {
  override def apply(cadena: String): Try[ResultadoParseo[RectangleFigure]] = {
    val parserPosiciones = integer.sepBy(string(" @ ")).sepBy(string(", "))
    val parseadorGeneral = string("rectangulo[") <> parserPosiciones <> char(']');

    parseadorGeneral.apply(cadena).map((element) => obtainFigure(element))
  }

  // TODO: refactor to common function betweem figures
  def obtainFigure(result: ResultadoParseo[((String, List[List[Int]]), Char)]): ResultadoParseo[RectangleFigure] = {
    val positionsList = result.elementoParseado._1._2
    if (positionsList.size != 2) throw new Exception("Invalid amount of elements") //TODO: change exception type

    ResultadoParseo( //TODO: des-algoritmizar
      RectangleFigure(
        (positionsList(0)(0), positionsList(0)(1)),
        (positionsList(1)(0), positionsList(1)(1)))
      , result.cadenaRestante)
  }
}

case class TriangleFigure(int1: (Int, Int), int2: (Int, Int), int3: (Int, Int)) {
  //   TODO: ver si es funcion que recibe parametros y devuelve
  //    triangulo o si es un objeto
}

case class RectangleFigure(int1: (Int, Int), int2: (Int, Int)) {
  //   TODO: ver si es funcion que recibe parametros y devuelve
  //    triangulo o si es un objeto
}