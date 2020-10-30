package parsers
import scala.util.Try

// TODO: renombrar de parser basico a parser
sealed trait ParserBasico{
  def <|>(parserBasico2: ParserBasico): ParserBasico = {
    ORComb(this,parserBasico2)
  }
  def <>(parserBasico2: ParserBasico): ParserBasico = {
    ConcatComb(this,parserBasico2)
  }
  def <~(parserBasico2: ParserBasico): ParserBasico = {
    LeftComb(this,parserBasico2)
  }
  def ~>(parserBasico2: ParserBasico): ParserBasico = {
    RightComb(this,parserBasico2)
  }
  def sepBy(parserBasico2: ParserBasico)= {
    SepByComb(this,parserBasico2)
  }
  def satisfiesComb(condicion:Try[Any] => Boolean): ParserBasico ={
    SatisfiesOp(this,condicion)
  }
  def opt : ParserBasico ={
    OptionalOp(this)
  }
  def * : ParserBasico ={
    KleeneOp(this)
  }
}

case object AnyCharP extends ParserBasico
case object IntegerP  extends ParserBasico
case object DigitP extends ParserBasico
case class CharP(charName:Char) extends ParserBasico
case class StringP(stringName:String) extends ParserBasico
case object DoubleP extends ParserBasico

case class ORComb(element1:ParserBasico,element2:ParserBasico) extends ParserBasico
case class ConcatComb(element1:ParserBasico, element2:ParserBasico) extends ParserBasico
case class LeftComb(element1:ParserBasico,element2:ParserBasico) extends ParserBasico
case class RightComb(element1:ParserBasico,element2:ParserBasico) extends ParserBasico
case class SepByComb(element1:ParserBasico,element2:ParserBasico) extends ParserBasico

case class SatisfiesOp(element:ParserBasico, condicion:Try[Any] => Boolean) extends ParserBasico
case class OptionalOp(parserBasico: ParserBasico) extends ParserBasico
case class KleeneOp(parserBasico: ParserBasico) extends ParserBasico