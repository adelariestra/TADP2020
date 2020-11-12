package simplificators

import elements._

trait SimpleSimplificator extends (FigureTr => FigureTr)

case object generalSimplificator extends SimpleSimplificator {
  override def apply(mainFigure: FigureTr): FigureTr = {
    // TODO: convert to superior order
    mainFigure.applyFunction(nestedColorSimp)
      .applyFunction(commonTrSimp)
    // TODO: implement the rest of functions
  }
}

case object nestedColorSimp extends SimpleSimplificator {
  override def apply(mainFigure: FigureTr): FigureTr = simplify(mainFigure)

  def simplify(mainFigure: FigureTr): FigureTr =
    mainFigure match {
      case ColorTr(ColorTr(figureContained, a, b, c), _, _, _) => ColorTr(figureContained, a, b, c)
      case GroupFigure(elementList) => GroupFigure(elementList.map(element => simplify(element)))
      case anotherFigure: SimpleFigureTr => anotherFigure
      case anotherFigure: TransformTr => anotherFigure
    }

}

case object commonTrSimp extends SimpleSimplificator {
  override def apply(mainFigure: FigureTr): FigureTr = simplify(mainFigure)

  def simplify(mainFigure: FigureTr): FigureTr = {
    mainFigure match {
      case GroupFigure(elementList) =>
        elementList.head match {
          case EscalaTr(figure, values) =>
            if (elementList.forall(element => element.equals(EscalaTr(element.figureContained, values))))
              EscalaTr(GroupFigure(elementList.map(element => element.figureContained)), values)
            else
              EscalaTr(simplify(figure), values)
          case RotacionTr(figure, val1) =>
            if (elementList.forall(element => element.equals(RotacionTr(element.figureContained, val1))))
              RotacionTr(GroupFigure(elementList.map(element => element.figureContained)), val1)
            else
              RotacionTr(simplify(figure), val1)
          case TraslacionTr(figure, val1, val2) =>
            if (elementList.forall(element => element.equals(TraslacionTr(element.figureContained, val1, val2))))
              TraslacionTr(GroupFigure(elementList.map(element => element.figureContained)), val1, val2)
            else
              TraslacionTr(simplify(figure), val1, val2)
          case ColorTr(figure, val1, val2, val3) =>
            if (elementList.forall(element => element.equals(ColorTr(element.figureContained, val1, val2, val3))))
              ColorTr(GroupFigure(elementList.map(element => element.figureContained)), val1, val2, val3)
            else
              ColorTr(simplify(figure), val1, val2, val3)
          case GroupFigure(elementList) => GroupFigure(elementList.map(element => simplify(element)))
          case anotherFigure: SimpleFigureTr => anotherFigure
        }
      case anotherFigure: SimpleFigureTr => anotherFigure
      case anotherFigure: TransformTr => anotherFigure
    }
  }
}