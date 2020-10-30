package parsers

import org.scalatest.flatspec._
import org.scalatest.matchers._
import parsers.Parsertest._

import scala.util.Try


class OperacionesSpec extends AnyFlatSpec with should.Matchers {
  it should "parsear correctamente con satisfies" in {
    val sat = integer.satisfiesComb((element:Try[Any])=> {true})
    parsear("8", sat).get shouldEqual 8
  }

  it should "parsear correctamente con condicion que use resultado" in {
    val sat = integer.satisfiesComb((element:Try[Any])=> element.get == 8)
    parsear("8", sat).get shouldEqual 8
  }

  it should "fallar satisfies si falla condicion" in {
    val sat = integer.satisfiesComb((element:Try[Any])=> {false})
    parsear("8", sat).isFailure shouldEqual true
  }

  it should "fallar satisfies si falla condicion que use resultado" in {
    val sat = integer.satisfiesComb((element:Try[Any])=> {element.get == 9})
    parsear("8", sat).isFailure shouldEqual true

  }

  it should "fallar satisfies si falla parser inicial" in {
    val aob = integer.satisfiesComb((element:Try[Any])=> {element.get == 8})
    parsear("450 450", aob).isFailure shouldEqual true
  }

  it should "parsear correctamente con opt positivo" in {
    val talVezIn = string("in").opt
    val precedencia = talVezIn <> string("fija")
    parsear("infija", precedencia).get shouldEqual ("in","fija")
    //parsear("fija", precedencia).get shouldEqual (" ","fija")
  }
  it should "parsear correctamente con opt negativo" in {
    val talVezIn = string("in").opt
    val precedencia = talVezIn <> string("fija")
    parsear("fija", precedencia).get shouldEqual ("","fija")
  }
}