package elements

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import parsers.ResultadoParseo

class SimpleElementsSpec extends AnyFlatSpec with should.Matchers {
  it should "parsear correctamente un triángulo" in {
    triangle("triangulo[0 @ 100, 200 @ 300, 150 @ 500]").get shouldEqual (ResultadoParseo((TriangleFigure((0, 100), (200, 300), (150, 500))), ""))
  }
  it should "parsear incorrectamente un triángulo con 2 posiciones" in {
    triangle("triangulo[0 @ 100, 200 @ 300]").isFailure shouldEqual true
  }
  it should "parsear incorrectamente un triángulo con mal tag" in {
    triangle("triangALO[0 @ 100, 200 @ 300, 150 @ 500]").isFailure shouldEqual true
  }
  it should "parsear correctamente un rectángulo" in {
    rectangle("rectangulo[0 @ 100, 200 @ 300]").get shouldEqual (ResultadoParseo((RectangleFigure((0, 100), (200, 300))), ""))
  }
  it should "parsear incorrectamente un rectángulo con 3 posiciones" in {
    rectangle("rectangulo[186 @ 0, 400 @ 150, 400 @ 150]").isFailure shouldEqual true
  }
  it should "parsear incorrectamente un rectángulo con mal tag" in {
    rectangle("triangALO[0 @ 100, 200 @ 300]").isFailure shouldEqual true
  }
  it should "parsear correctamente un circulo" in {
    circle("circulo[100 @ 100, 50]").get shouldEqual (ResultadoParseo((CircleFigure((100, 100),50)), ""))
  }
  it should "parsear incorrectamente un circulo con 2 posiciones pero 2 elementos en la segunda" in {
    circle("circulo[186 @ 0, 400 @ 150]").isFailure shouldEqual true
  }
  it should "parsear incorrectamente un circulo con mal tag" in {
    circle("triangALO[0 @ 100, 200]").isFailure shouldEqual true
  }
}
