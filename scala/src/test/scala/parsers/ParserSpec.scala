package parsers

import org.scalatest.flatspec._
import org.scalatest.matchers._

class ParserSpec extends AnyFlatSpec with should.Matchers {
  it should "parsear correctamente cualquier AnyCharP" in {
    anyChar("armenia").get shouldEqual ResultadoParseo('a', "rmenia")
  }

  it should "parseo falla ante un no AnyCharP" in {
    anyChar("").isFailure shouldEqual true
  }

  it should "parsear correctamente CharP" in {
    char('a')("armenia").get shouldEqual ResultadoParseo('a', "rmenia")
  }

  it should "parseo falla ante un no CharP" in {
    char('b')("armenia").isFailure shouldEqual true
  }

  it should "parsear correctamente cualquier DigitP" in {
    digit("1armenia").get shouldEqual ResultadoParseo('1', "armenia")
  }

  it should "parseo falla ante un no DigitP" in {
    digit("armenia").isFailure shouldEqual true
  }

  it should "parsear correctamente StringP" in {
    string("arm")("armenia").get shouldEqual ResultadoParseo("arm", "enia")
  }

  it should "parseo falla ante un no StringP" in {
    string("boca")("armenia").isFailure shouldEqual true
  }

  it should "parsear correctamente IntegerP si cadena es un numero" in {
    integer("88").get shouldEqual ResultadoParseo(88, "")
  }

  it should "parsear correctamente IntegerP si cadena es un numero con letras" in {
    integer("10000AA").get shouldEqual ResultadoParseo(10000, "AA")
  }

  it should "parsear correctamente IntegerP si cadena es un numero negativo " in {
    integer("-10").get shouldEqual ResultadoParseo(-10, "")
  }

  it should "parsear correctamente IntegerP si cadena es un numero negativo con letras" in {
    integer("-10ABCsdfds").get shouldEqual ResultadoParseo(-10, "ABCsdfds")
  }

  it should "parseo falla ante un no IntegerP" in {
    integer("armenia").isFailure shouldEqual true
  }

  it should "parseo falla ante un no IntegerP con numeros por el medio" in {
    integer("armenia123").isFailure shouldEqual true
  }

  it should "parsear correctamente cualquier IntegerP negativo" in {
    integer("-8").get shouldEqual ResultadoParseo(-8, "")
  }
  it should "parsear correctamente DoubleP" in {
    double("8000.15").get shouldEqual ResultadoParseo(8000.15: Double,"")
  }

  it should "parseo falla ante un no DoubleP" in {
    double("armenia").isFailure shouldEqual true
  }
}