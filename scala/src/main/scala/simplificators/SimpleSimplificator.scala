package simplificators

import elements._
import parsers.ResultadoParseo

import scala.util.Try

trait SimpleSimplificator extends (FigureTr => FigureTr)

case object generalSimplificator extends SimpleSimplificator{
  override def apply(mainFigure:FigureTr):FigureTr={
    // TODO: convert to superior order
      mainFigure.applyFunction(nestedColorSimp)
      // TODO: implement the rest of functions
  }
}


case object nestedColorSimp extends SimpleSimplificator{
  override def apply(mainFigure:FigureTr):FigureTr={
    simplify(mainFigure)
  }

  def simplify(mainFigure:FigureTr): FigureTr ={
    mainFigure match {
      case ColorTr(List(ColorTr(contentList,a,b,c)),_,_,_) => ColorTr(contentList,a,b,c)
      // TODO: fix pattern matching
      case ColorTr(elementList,values) =>{ColorTr(elementList.map((element)=>simplify(element)),values)}
      case EscalaTr(elementList,values) =>{EscalaTr(elementList.map((element)=>simplify(element)),values)}
      case RotacionTr(elementList,values) =>{RotacionTr(elementList.map((element)=>simplify(element)),values)}
      case TraslacionTr(elementList,values) =>{TraslacionTr(elementList.map((element)=>simplify(element)),values)}
      case GroupFigure(elementList) =>{GroupFigure(elementList.map((element)=>simplify(element)))}
    }
  }
}
