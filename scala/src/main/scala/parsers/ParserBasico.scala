package parsers

sealed trait ParserBasico{
  def <|>(parserBasico2: ParserBasico): ParserBasico = {
    ORComb(this,parserBasico2)
  }
  def <>(parserBasico2: ParserBasico): ParserBasico = {
    ANDComb(this,parserBasico2)
  }


}

case object AnyCharP extends ParserBasico
case object IntegerP  extends ParserBasico
case object DigitP extends ParserBasico
case class CharP(charName:Char) extends ParserBasico
case class StringP(stringName:String) extends ParserBasico
case object DoubleP extends ParserBasico
case class ORComb(element1:ParserBasico,element2:ParserBasico) extends ParserBasico
case class ANDComb(element1:ParserBasico,element2:ParserBasico) extends ParserBasico