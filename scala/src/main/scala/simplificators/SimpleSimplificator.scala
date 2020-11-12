package simplificators

import elements._

trait SimpleSimplificator extends (FigureTr => FigureTr)

case object generalSimplificator extends SimpleSimplificator {
  override def apply(mainFigure: FigureTr): FigureTr = {
    // TODO: convert to superior order
    mainFigure.applyFunction(nestedTrSimp)
      .applyFunction(commonTrSimp)
    // TODO: implement the rest of functions
  }
}

case object nestedTrSimp extends SimpleSimplificator {
  override def apply(mainFigure: FigureTr): FigureTr = simplify(mainFigure)

  def simplify(mainFigure: FigureTr): FigureTr =
    mainFigure match {
      case ColorTr(ColorTr(figureContained, a1, a2, a3), _, _, _) => ColorTr(figureContained, a1, a2, a3)
      case RotacionTr(RotacionTr(figureContained, a1), b1) => RotacionTr(figureContained, a1+b1)
      case TraslacionTr(TraslacionTr(figureContained, a1, a2), b1, b2) => TraslacionTr(figureContained,a1+b1,a2+b2)
      case EscalaTr(EscalaTr(figureContained, a1, a2), b1, b2) => EscalaTr(figureContained, a1*b1,a2*b2)
      case GroupFigure(elementList) => GroupFigure(elementList.map(element => simplify(element)))
      case anotherFigure: SimpleFigureTr => anotherFigure

      case EscalaTr(figure, val1, val2) => EscalaTr(simplify(figure), val1, val2)
      case RotacionTr(figure, val1) => RotacionTr(simplify(figure), val1)
      case TraslacionTr(figure, val1, val2) => TraslacionTr(simplify(figure), val1, val2)
      case ColorTr(figure, val1, val2, val3) => ColorTr(simplify(figure), val1, val2, val3)
    }

}

case object commonTrSimp extends SimpleSimplificator {
  override def apply(mainFigure: FigureTr): FigureTr = simplify(mainFigure)

  def simplify(mainFigure: FigureTr): FigureTr = {
    mainFigure match {
      case GroupFigure(elementList) =>
        elementList.head match {
          case EscalaTr(_, values) =>
            if (elementList.forall(element => element.equals(EscalaTr(element.figureContained, values))))
              EscalaTr(GroupFigure(elementList.map(element => element.figureContained)), values)
            else
              GroupFigure(elementList.map(element => simplify(element)))

          case RotacionTr(_, val1) =>
            if (elementList.forall(element => element.equals(RotacionTr(element.figureContained, val1))))
              RotacionTr(GroupFigure(elementList.map(element => element.figureContained)), val1)
            else
              GroupFigure(elementList.map(element => simplify(element)))

          case TraslacionTr(_, val1, val2) =>
            if (elementList.forall(element => element.equals(TraslacionTr(element.figureContained, val1, val2))))
              TraslacionTr(GroupFigure(elementList.map(element => element.figureContained)), val1, val2)
            else
              GroupFigure(elementList.map(element => simplify(element)))

          case ColorTr(_, val1, val2, val3) =>
            if (elementList.forall(element => element.equals(ColorTr(element.figureContained, val1, val2, val3))))
              ColorTr(GroupFigure(elementList.map(element => element.figureContained)), val1, val2, val3)
            else
              GroupFigure(elementList.map(element => simplify(element)))

          case GroupFigure(elementList) => GroupFigure(elementList.map(element => simplify(element)))
          case anotherFigure: SimpleFigureTr => anotherFigure
        }
      case anotherFigure: SimpleFigureTr => anotherFigure
      case anotherFigure: TransformTr => anotherFigure
    }
  }
}