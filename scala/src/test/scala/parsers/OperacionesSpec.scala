package parsers

import org.scalatest.flatspec._
import org.scalatest.matchers._
import parsers.Parsertest._

import scala.util.Try


class OperacionesSpec extends AnyFlatSpec with should.Matchers {
  it should "parsear correctamente con satisfies" in {
    val sat = integer.satisfies((element:Try[Any])=> {true})
    sat.parsear("8").get shouldEqual 8
  }

  it should "parsear correctamente con condicion que use resultado" in {
    val sat = integer.satisfies((element:Try[Any])=> element.get == 8)
    sat.parsear("8").get shouldEqual 8
  }

  it should "fallar satisfies si falla condicion" in {
    val sat = integer.satisfies((element:Try[Any])=> {false})
    sat.parsear("8").isFailure shouldEqual true
  }

  it should "fallar satisfies si falla condicion que use resultado" in {
    val sat = integer.satisfies((element:Try[Any])=> {element.get == 9})
    sat.parsear("8").isFailure shouldEqual true

  }

  it should "fallar satisfies si falla parser inicial" in {
    val aob = integer.satisfies((element:Try[Any])=> {element.get == 8})
    aob.parsear("450 450").isFailure shouldEqual true
  }

  it should "parsear correctamente con opt positivo" in {
    val talVezIn = string("in").opt
    val precedencia = talVezIn <> string("fija")
    precedencia.parsear("infija").get shouldEqual ("in","fija")
  }
  it should "parsear correctamente con opt negativo" in {
    val talVezIn = string("in").opt
    val precedencia = talVezIn <> string("fija")
    precedencia.parsear("fija").get shouldEqual ("","fija")
  }
}