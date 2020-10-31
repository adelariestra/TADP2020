package tadp

import org.scalatest.flatspec._
import org.scalatest.matchers._
import parsers.Parsertest._
import parsers._

class ParserSpec extends AnyFlatSpec with should.Matchers {

  it should "parsear correctamente cualquier AnyCharP" in {
    AnyCharP.getResultado("armenia").get.getResultado() shouldEqual( 'a':Char)
  }

  it should "parseo falla ante un no AnyCharP" in {
    AnyCharP.getResultado("").isFailure shouldEqual true // TODO: cambiar shoulEqual true por algun assert
  }

  it should "parsear correctamente cualquier DigitP" in {
    DigitP.getResultado("1armenia").get.getResultado() shouldEqual ('1':Char)
  }

  it should "parseo falla ante un no DigitP" in {
    DigitP.getResultado("armenia").isFailure shouldEqual true
  }

  it should "parsear correctamente CharP" in {
    CharP('a').getResultado("armenia").get.getResultado() shouldEqual ('a':Char)
  }

  it should "parseo falla ante un no CharP" in {
    CharP('b').getResultado("armenia").isFailure shouldEqual true
  }

  it should "parsear correctamente StringP" in {
    StringP("arm").getResultado("armenia").get.getResultado() shouldEqual("arm":String)
  }

  it should "parseo falla ante un no StringP" in {
    StringP("boca").getResultado("armenia").isFailure shouldEqual true
  }

  it should "parsear correctamente IntegerP si cadena es un numero" in {
    IntegerP.getResultado("88").get.getResultado() shouldEqual (88:Int)
  }

  it should "parsear correctamente IntegerP si cadena es un numero con letras" in {
    IntegerP.getResultado("10000AA").get.getResultado() shouldEqual (10000:Int)
  }

  it should "parsear correctamente IntegerP si cadena es un numero negativo " in {
    IntegerP.getResultado("-10").get.getResultado() shouldEqual (-10:Int)
  }

  it should "parsear correctamente IntegerP si cadena es un numero negativo con letras" in {
    IntegerP.getResultado("-10ABCsdfds").get.getResultado() shouldEqual (-10:Int)
  }

  it should "parseo falla ante un no IntegerP" in {
    IntegerP.getResultado("armenia" ).isFailure shouldEqual true
  }

  it should "parseo falla ante un no IntegerP con numeros por el medio" in {
    IntegerP.getResultado("armenia123" ).isFailure shouldEqual true
  }

/*
  it should "parsear correctamente cualquier IntegerP negativo" in {
    IntegerP.getResultado("-8").get shouldEqual (-8:Int)
  }



  it should "parsear correctamente DoubleP" in {
    DoubleP.getResultado("8000.15").get shouldEqual (8000.15:Double)
  }

  it should "parseo falla ante un no DoubleP" in {
    DoubleP.getResultado("armenia").isFailure shouldEqual true
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
