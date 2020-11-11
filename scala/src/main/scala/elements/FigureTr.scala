package elements

trait FigureTr;

case class TriangleFigure(int1: Position, int2: Position, int3: Position) extends FigureTr {
  //   TODO: ver si es funcion que recibe parametros y devuelve
  //    triangulo o si es un objeto
}

case class RectangleFigure(int1: Position, int2: Position) extends FigureTr {
  //   TODO: ver si es funcion que recibe parametros y devuelve
  //    triangulo o si es un objeto
}

case class CircleFigure(int1: Position, int2: Int) extends FigureTr {
  //   TODO: ver si es funcion que recibe parametros y devuelve
  //    triangulo o si es un objeto
}

//TODO: pasar a ComplexElement y aprovechar con TransformElem
case class GroupFigure(int1: List[FigureTr]) extends  FigureTr {
  //   TODO: ver si es funcion que recibe parametros y devuelve
  //    triangulo o si es un objeto
}

case class Position(posX: Int, posY: Int)