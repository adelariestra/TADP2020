package elements

import parsers.{Parser, ResultadoParseo, char, integer, string}

import scala.util.Try

case object positions extends Parser[List[Position]] {
  override def apply(cadena: String): Try[ResultadoParseo[List[Position]]] = {
      val parserPosicion = integer <~ string(" @ ") <> integer
      val parseadorGeneral = parserPosicion.sepBy(string(", "))

    parseadorGeneral.apply(cadena).map((element) => obtainFigure(element))
  }

  //TODO: refactor to common function betweem figures
  def obtainFigure(result: ResultadoParseo[List[(Int, Int)]]): ResultadoParseo[List[Position]] = {
    val positionsList = result.elementoParseado
    ResultadoParseo(positionsList.map((element)=>Position(element._1,element._2)),result.cadenaRestante)
  }
}

case object triangle extends Parser[TriangleFigure] {
  override def apply(cadena: String): Try[ResultadoParseo[TriangleFigure]] = {
    val parseadorGeneral = string("triangulo[") ~> positions <~ char(']');

    parseadorGeneral.apply(cadena).map((element) => obtainFigure(element))
  }

  // TODO: refactor to common function betweem figures
  def obtainFigure(result: ResultadoParseo[List[Position]]): ResultadoParseo[TriangleFigure] = {
    val positionsList = result.elementoParseado
    if (positionsList.size != 3) throw new Exception("Invalid amount of elements") //TODO: change exception type
    ResultadoParseo(
      TriangleFigure(positionsList(0),positionsList(1),positionsList(2))
      , result.cadenaRestante)
  }
}

case object rectangle extends Parser[RectangleFigure] {
  override def apply(cadena: String): Try[ResultadoParseo[RectangleFigure]] = {
    val parseadorGeneral = string("rectangulo[") ~> positions <~ char(']');

    parseadorGeneral.apply(cadena).map((element) => obtainFigure(element))
  }

  // TODO: refactor to common function betweem figures
  def obtainFigure(result: ResultadoParseo[List[Position]]): ResultadoParseo[RectangleFigure] = {
    val positionsList = result.elementoParseado
    if (positionsList.size != 2) throw new Exception("Invalid amount of elements") //TODO: change exception type

    ResultadoParseo(
      RectangleFigure(positionsList(0),positionsList(1))
      , result.cadenaRestante)
  }
}

case object circle extends Parser[CircleFigure] {
  override def apply(cadena: String): Try[ResultadoParseo[CircleFigure]] = {
    val parseadorGeneral = string("circulo[") ~> positions <~ string(", ") <> integer <~ char(']');

    parseadorGeneral.apply(cadena).map((element) => obtainFigure(element))
  }

  def obtainFigure(result: ResultadoParseo[(List[Position], Int)]): ResultadoParseo[CircleFigure] = {
    val positionsList = result.elementoParseado._1
    if (positionsList.size != 1) throw new Exception("Invalid amount of elements:".concat(positionsList.size.toString)) //TODO: change exception type

    ResultadoParseo(
      CircleFigure(positionsList(0),result.elementoParseado._2)
      , result.cadenaRestante)
  }
}

trait FigureTr ;

case class TriangleFigure(int1: Position, int2: Position, int3: Position) extends  FigureTr {
  //   TODO: ver si es funcion que recibe parametros y devuelve
  //    triangulo o si es un objeto
}

case class RectangleFigure(int1: Position, int2: Position) extends  FigureTr{
  //   TODO: ver si es funcion que recibe parametros y devuelve
  //    triangulo o si es un objeto
}

case class CircleFigure(int1: Position,int2:Int) extends  FigureTr{
  //   TODO: ver si es funcion que recibe parametros y devuelve
  //    triangulo o si es un objeto
}

case class Position(posX: Int, posY: Int)