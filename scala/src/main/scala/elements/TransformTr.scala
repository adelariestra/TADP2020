package elements

trait TransformTr extends  FigureTr

case class EscalaTr(figuresContained: List[FigureTr], values: Double*) extends TransformTr {
  //   TODO: ver si es funcion que recibe parametros y devuelve
  //    triangulo o si es un objeto
}

case class RotacionTr(figuresContained: List[FigureTr],values: Double*) extends TransformTr {
  //   TODO: ver si es funcion que recibe parametros y devuelve
  //    triangulo o si es un objeto
}

case class TraslacionTr(figuresContained: List[FigureTr],values: Double*) extends TransformTr {
  //   TODO: ver si es funcion que recibe parametros y devuelve
  //    triangulo o si es un objeto
}

case class ColorTr(figuresContained: List[FigureTr],values: Double*) extends  TransformTr {
  //   TODO: ver si es funcion que recibe parametros y devuelve
  //    triangulo o si es un objeto
}
