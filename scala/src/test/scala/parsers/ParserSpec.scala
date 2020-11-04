package tadp

import org.scalatest.flatspec._
import org.scalatest.matchers._
//import parsers.Parsertest._
import parsers._

class ParserSpec extends AnyFlatSpec with should.Matchers {

  it should "parsear correctamente cualquier AnyCharP" in {
    anyChar("armenia") shouldEqual (ParseoExitoso('a', "rmenia"))
  }

  it should "parseo falla ante un no AnyCharP" in {
    anyChar("").isErrorParseo shouldEqual true // TODO: cambiar shoulEqual true por algun assert
  }

  it should "parsear correctamente CharP" in {
    char('a')("armenia") shouldEqual (ParseoExitoso('a', "rmenia"))
  }

  it should "parseo falla ante un no CharP" in {
    char('b')("armenia").isErrorParseo shouldEqual true
  }

  it should "parsear correctamente cualquier DigitP" in {
    digit("1armenia") shouldEqual (ParseoExitoso('1',"armenia"))
  }

  it should "parseo falla ante un no DigitP" in {
    digit("armenia").isErrorParseo shouldEqual true
  }

  it should "parsear correctamente StringP" in {
    string("arm")("armenia") shouldEqual(ParseoExitoso("arm", "enia"))
  }

  it should "parseo falla ante un no StringP" in {
    string("boca")("armenia").isErrorParseo shouldEqual true
  }
}
 /*


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

  it should "parsear correctamente cualquier IntegerP negativo" in {
    IntegerP.getResultado("-8").get shouldEqual (-8:Int)
  }

  it should "parsear correctamente DoubleP" in {
    DoubleP.getResultado("8000.15").get shouldEqual (8000.15:Double)
  }

  it should "parseo falla ante un no DoubleP" in {
    DoubleP.getResultado("armenia").isFailure shouldEqual true
  }
}*/
