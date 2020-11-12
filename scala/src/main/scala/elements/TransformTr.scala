package elements

trait TransformTr extends  FigureTr

case class EscalaTr(override val figureContained: FigureTr, values:Double*) extends TransformTr {
  //   TODO: ver si es funcion que recibe parametros y devuelve
  //    triangulo o si es un objeto
}

case class RotacionTr(override val figureContained: FigureTr, values:Int) extends TransformTr {

  //   TODO: ver si es funcion que recibe parametros y devuelve
  //    triangulo o si es un objeto
}

case class TraslacionTr(override val figureContained: FigureTr, values:Int*) extends TransformTr {

  //   TODO: ver si es funcion que recibe parametros y devuelve
  //    triangulo o si es un objeto
}

case class ColorTr(override val figureContained: FigureTr, values:Int*) extends  TransformTr {

  //   TODO: ver si es funcion que recibe parametros y devuelve
  //    triangulo o si es un objeto
}
