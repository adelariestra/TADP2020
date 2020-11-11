package elements

trait TransformTr extends  FigureTr

case class EscalaTr(figureContained: FigureTr, values: Double*) extends TransformTr {
  //   TODO: ver si es funcion que recibe parametros y devuelve
  //    triangulo o si es un objeto
}

case class RotacionTr(figureContained: FigureTr,values: Double*) extends TransformTr {
  //   TODO: ver si es funcion que recibe parametros y devuelve
  //    triangulo o si es un objeto
}

case class TraslacionTr(figureContained: FigureTr,values: Double*) extends TransformTr {
  //   TODO: ver si es funcion que recibe parametros y devuelve
  //    triangulo o si es un objeto
}

case class ColorTr(figureContained: FigureTr,values: Double*) extends  TransformTr {
  //   TODO: ver si es funcion que recibe parametros y devuelve
  //    triangulo o si es un objeto
}
