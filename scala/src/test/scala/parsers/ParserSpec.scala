package tadp

import org.scalatest.flatspec._
import org.scalatest.matchers._
import parsers._
import scala.util.Try

class ParserSpec extends AnyFlatSpec with should.Matchers {
  it should "parsear correctamente cualquier char" in {
    Parsertest.algo("armenia",AnyCharP) shouldEqual Try("a")
  }

  it should "parsear correctamente cualquier integer" in {
    Parsertest.algo("1armenia",IntegerP) shouldEqual Try("1")
  }

  it should "fallar ante un no integer" in {
    Parsertest.algo("armenia",IntegerP).isFailure shouldEqual true
  }

}
