package elements

trait TransformTr extends  FigureTr

case class EscalaTr(figuresContained: List[FigureTr],val1: Double, val2: Double) extends TransformTr {
  //   TODO: ver si es funcion que recibe parametros y devuelve
  //    triangulo o si es un objeto
}

case class RotacionTr(figuresContained: List[FigureTr],val1 : Int) extends TransformTr {
  //   TODO: ver si es funcion que recibe parametros y devuelve
  //    triangulo o si es un objeto
}

case class TraslacionTr(figuresContained: List[FigureTr],val1 : Int, val2 : Int) extends TransformTr {
  //   TODO: ver si es funcion que recibe parametros y devuelve
  //    triangulo o si es un objeto
}

case class ColorTr(figuresContained: List[FigureTr],val1 : Int,val2 : Int,val3 : Int) extends  TransformTr {
  //   TODO: ver si es funcion que recibe parametros y devuelve
  //    triangulo o si es un objeto
}
