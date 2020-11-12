package elements

import tadp.internal.TADPDrawingAdapter
import scalafx.scene.paint.Color

trait TransformTr extends  FigureTr

case class EscalaTr(override val figureContained: FigureTr, val1 :Double,val2 :Double) extends TransformTr {
  override def drawUsing(adapter: TADPDrawingAdapter): TADPDrawingAdapter ={
      figureContained.drawUsing(adapter.beginScale(val1,val2))
      .end()
  }
}

case class RotacionTr(override val figureContained: FigureTr, val1:Int) extends TransformTr {
  override def drawUsing(adapter: TADPDrawingAdapter): TADPDrawingAdapter = {
    figureContained.drawUsing(adapter.beginRotate(val1))
      .end()
  }
}
case class TraslacionTr(override val figureContained: FigureTr, val1:Int, val2:Int) extends TransformTr {
  override def drawUsing(adapter: TADPDrawingAdapter): TADPDrawingAdapter = {
    figureContained.drawUsing(adapter.beginTranslate(val1,val2))
      .end()
  }
}

case class ColorTr(override val figureContained: FigureTr, val1:Int, val2:Int, val3:Int) extends  TransformTr {
  override def drawUsing(adapter: TADPDrawingAdapter): TADPDrawingAdapter = {
    figureContained.drawUsing(adapter.beginColor(Color.rgb(val1,val2,val3)))
      .end()
  }
}
