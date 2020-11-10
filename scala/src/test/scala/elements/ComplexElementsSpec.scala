package elements

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import parsers.ResultadoParseo

class ComplexElementsSpec extends AnyFlatSpec with should.Matchers {
  it should "parsear correctamente un grupo" in {
    group("grupo(circulo[200 @ 350, 100], circulo[200 @ 350, 100])").get shouldEqual (ResultadoParseo(GroupFigure(List[FigureTr](CircleFigure(Position(200,350),100),CircleFigure(Position(200,350),100))), ""))
  }
}
