package tadp

import org.scalatest.flatspec._
import org.scalatest.matchers._
import parsers.Parsertest._
import parsers._

class ParserSpec extends AnyFlatSpec with should.Matchers {
  /*it should "parsear correctamente cualquier AnyCharP" in {
    AnyCharP.parsear("armenia").get shouldEqual( 'a':Char)
  }

  it should "parseo falla ante un no AnyCharP" in {
    AnyCharP.parsear("").isFailure shouldEqual true // TODO: cambiar shoulEqual true por algun assert
  }
  
  it should "parsear correctamente cualquier DigitP" in {
    DigitP.parsear("1armenia").get shouldEqual ('1':Char)
  }
  it should "parseo falla ante un no DigitP" in {
    DigitP.parsear("armenia").isFailure shouldEqual true
  }

  it should "parsear correctamente CharP" in {
    CharP('a').parsear("armenia").get shouldEqual ('a':Char)
  }

  it should "parseo falla ante un no CharP" in {
    CharP('b').parsear("armenia").isFailure shouldEqual true
  }

  it should "parsear correctamente StringP" in {
    StringP("arm").parsear("armenia").get shouldEqual("arm":String)
  }

  it should "parseo falla ante un no StringP" in {
    StringP("boca").parsear("armenia").isFailure shouldEqual true
  }

  it should "parsear correctamente cualquier IntegerP" in {
    IntegerP.parsear("8").get shouldEqual (8:Int)
  }

  it should "parsear correctamente cualquier IntegerP negativo" in {
    IntegerP.parsear("-8").get shouldEqual (-8:Int)
  }

  it should "parseo falla ante un no IntegerP" in {
    IntegerP.parsear("armenia").isFailure shouldEqual true
  }

  it should "parsear correctamente DoubleP" in {
    DoubleP.parsear("8000.15").get shouldEqual (8000.15:Double)
  }

  it should "parseo falla ante un no DoubleP" in {
    DoubleP.parsear("armenia").isFailure shouldEqual true
  }
*/
  it should "parsear correctamente cualquier AnyCharP2" in {
    AnyCharP.getResultado("hola").get.getTextoRestante() shouldEqual ("ola":String)
  }

  it should "parsear cualquier AnyCharP con error" in {
    AnyCharP.getResultado("").isFailure shouldEqual true
  }

  it should "parsear correctamente cualquier DigitP2" in {
    DigitP.getResultado("7-1").get.getResultado() shouldEqual ('7':Char)
  }

  it should "parsear cualquier DigitP2 con error" in {
    DigitP.getResultado("a71").isFailure shouldEqual true
  }

  it should "parsear cualquier CharP con error" in {
    new CharP('b').getResultado("boca").get.getResultado() shouldEqual ('b':Char)
  }

  it should "parsear cualquier StringP con error" in {
    new StringP("boca").getResultado("bocaCampeon").get.getTextoRestante() shouldEqual ("Campeon":String)
  }
}
