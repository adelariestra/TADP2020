package tadp

import elements.FigureTr
import scalafx.scene.paint.Color
import tadp.internal.TADPDrawingAdapter

object TADPDrawingApp extends App {
  def drawTree(figure: FigureTr) =
    TADPDrawingAdapter.forScreen {
      adapter => figure.drawUsing(adapter)
    }
}
