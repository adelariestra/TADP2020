package tadp

import org.scalatest.flatspec._
import org.scalatest.matchers._
import parsers._

class ParserSpec extends AnyFlatSpec with should.Matchers {
  it should "parsear correctamente cualquier char" in {
    Parsertest.algo("armenia",AnyCharP) shouldEqual "a"
  }

  it should "parsear correctamente cualquier integer" in {
    Parsertest.algo("1armenia",IntegerP) shouldEqual "1"
  }
}
