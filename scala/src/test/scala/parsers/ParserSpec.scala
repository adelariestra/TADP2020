package tadp

import org.scalatest.flatspec._
import org.scalatest.matchers._
import parsers.Parsertest._
import parsers._

class ParserSpec extends AnyFlatSpec with should.Matchers {
  it should "parsear correctamente cualquier AnyCharP" in {
    parsear("armenia",AnyCharP).get shouldEqual( 'a':Char)
  }

  it should "parseo falla ante un no AnyCharP" in {
    parsear("",AnyCharP).isFailure shouldEqual true // TODO: cambiar shoulEqual true por algun assert
  }
  
  it should "parsear correctamente cualquier DigitP" in {
    parsear("1armenia",DigitP).get shouldEqual ('1':Char)
  }
  it should "parseo falla ante un no DigitP" in {
    parsear("armenia",DigitP).isFailure shouldEqual true
  }

  it should "parsear correctamente CharP" in {
    parsear("armenia",CharP('a')).get shouldEqual ('a':Char)
  }

  it should "parseo falla ante un no CharP" in {
    parsear("armenia",CharP('b')).isFailure shouldEqual true
  }

  it should "parsear correctamente StringP" in {
    parsear("armenia",StringP("arm")).get shouldEqual("arm":String)
  }

  it should "parseo falla ante un no StringP" in {
    parsear("armenia",StringP("boca")).isFailure shouldEqual true
  }

  it should "parsear correctamente cualquier IntegerP" in {
    parsear("8",IntegerP).get shouldEqual (8:Int)
  }

  it should "parsear correctamente cualquier IntegerP negativo" in {
    parsear("-8",IntegerP).get shouldEqual (-8:Int)
  }

  it should "parseo falla ante un no IntegerP" in {
    parsear("armenia",IntegerP).isFailure shouldEqual true
  }

  it should "parsear correctamente DoubleP" in {
    println(parsear("8000.15",DoubleP).get)
    parsear("8000.15",DoubleP).get shouldEqual (8000.15:Double)
  }

  it should "parseo falla ante un no DoubleP" in {
    parsear("armenia",DoubleP).isFailure shouldEqual true
  }
}
