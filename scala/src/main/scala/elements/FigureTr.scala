package elements

trait FigureTr {
  def applyFunction(f: FigureTr => FigureTr): FigureTr ={
    f(this)
  }

  // TODO: para pattern matching, fix (puede ser Option o cambiar jerarquia)
  val figureContained:FigureTr = null
}

trait SimpleFigureTr extends  FigureTr;

case class TriangleFigure(int1: Position, int2: Position, int3: Position) extends SimpleFigureTr

case class RectangleFigure(int1: Position, int2: Position) extends SimpleFigureTr

case class CircleFigure(int1: Position, int2: Int) extends SimpleFigureTr

case class GroupFigure(figuresContained: List[FigureTr]) extends FigureTr

case class Position(posX: Int, posY: Int)