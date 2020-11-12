package elements

trait FigureTr {
  def applyFunction(f: FigureTr => FigureTr): FigureTr ={
    f(this)
  }

  // TODO: para pattern matching, fix (puede ser Option o cambiar jerarquia)
  val figureContained:FigureTr = null
}

case class TriangleFigure(int1: Position, int2: Position, int3: Position) extends FigureTr

case class RectangleFigure(int1: Position, int2: Position) extends FigureTr

case class CircleFigure(int1: Position, int2: Int) extends FigureTr

case class GroupFigure(figuresContained: List[FigureTr]) extends FigureTr

case class Position(posX: Int, posY: Int)