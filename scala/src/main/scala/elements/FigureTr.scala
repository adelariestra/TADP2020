package elements

import tadp.internal.TADPDrawingAdapter

trait FigureTr {
  def applyFunction(f: FigureTr => FigureTr): FigureTr ={
    f(this)
  }

  def drawUsing(adapter:TADPDrawingAdapter): TADPDrawingAdapter;

  // TODO: para pattern matching, fix (puede ser Option o cambiar jerarquia)
  val figureContained:FigureTr = null
}

trait SimpleFigureTr extends  FigureTr;

case class TriangleFigure(int1: (Double,Double), int2: (Double,Double), int3: (Double,Double)) extends SimpleFigureTr {
  override def drawUsing(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter.triangle(int1,int2,int3)
}

case class RectangleFigure(int1: (Double,Double), int2: (Double,Double)) extends SimpleFigureTr {
  override def drawUsing(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter.rectangle(int1,int2)
}

case class CircleFigure(int1: (Double,Double), int2: Int) extends SimpleFigureTr {
  override def drawUsing(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter.circle(int1,int2)
}

case class GroupFigure(figuresContained: List[FigureTr]) extends FigureTr {
  override def drawUsing(adapter: TADPDrawingAdapter): TADPDrawingAdapter = {
    figuresContained.foldLeft(adapter){
      (adapterin: TADPDrawingAdapter,fig:FigureTr) => fig.drawUsing(adapterin)}
  }
}

