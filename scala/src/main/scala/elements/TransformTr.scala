package elements
import tadp.internal.TADPDrawingAdapter

trait TransformTr extends  FigureTr

case class EscalaTr(override val figureContained: FigureTr, values:Double*) extends TransformTr {
  override def drawUsing(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter // TODO: imp missing
}

case class RotacionTr(override val figureContained: FigureTr, values:Int) extends TransformTr {
  override def drawUsing(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter // TODO: imp missing
}

case class TraslacionTr(override val figureContained: FigureTr, values:Int*) extends TransformTr {
  override def drawUsing(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter // TODO: imp missing
}

case class ColorTr(override val figureContained: FigureTr, values:Int*) extends  TransformTr {
  override def drawUsing(adapter: TADPDrawingAdapter): TADPDrawingAdapter = adapter // TODO: imp missing
}
