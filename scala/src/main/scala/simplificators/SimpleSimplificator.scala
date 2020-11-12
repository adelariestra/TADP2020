package simplificators

import elements._
import parsers.ResultadoParseo

import scala.util.Try

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
  override def apply(mainFigure: FigureTr): FigureTr = {
    simplify(mainFigure)
  }

  def simplify(mainFigure: FigureTr): FigureTr = {
    mainFigure match {
      case ColorTr((ColorTr(figureContained, a, b, c)), _, _, _) => ColorTr(figureContained, a, b, c)
      case GroupFigure(elementList) => GroupFigure(elementList.map((element) => simplify(element)))
      case anotherFigure => if(anotherFigure.figureContained!=null) simplify(anotherFigure.figureContained) else anotherFigure
    }
  }
}

case object commonTrSimp extends SimpleSimplificator {
  override def apply(mainFigure: FigureTr): FigureTr = {
    simplify(mainFigure)
  }

  def simplify(mainFigure: FigureTr): FigureTr = {
    mainFigure match {
      case GroupFigure(elementList) => {
        if (elementList.forall(_ == elementList.head)) // si hay hijos repes
          elementList.head match{
            case EscalaTr(figures,values)=>EscalaTr(GroupFigure(elementList.map(element=>element.figureContained)),values)
            case RotacionTr(figures,values)=>RotacionTr(GroupFigure(elementList.map(element=>element.figureContained)),values)
            case TraslacionTr(figures,values)=>TraslacionTr(GroupFigure(elementList.map(element=>element.figureContained)),values)
            case ColorTr(figures,values)=> ColorTr(GroupFigure(elementList.map(element=>element.figureContained)),values)

            case GroupFigure(elementList) => GroupFigure(elementList.map((element) => simplify(element)))
//            case _ => GroupFigure(elementList)
          }
        else {
          Console.println("NO MATCHEO")
          GroupFigure(elementList.map((element) => simplify(element)))
        }
      }
      case anotherFigure =>  anotherFigure
    }
  }
}