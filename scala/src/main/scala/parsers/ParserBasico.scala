package parsers

sealed trait ParserBasico

case object AnyCharP extends ParserBasico
case object IntegerP  extends ParserBasico
case object DigitP extends ParserBasico
case class CharP(charName:Char) extends ParserBasico
case class StringP(stringName:String) extends ParserBasico
case object DoubleP extends ParserBasico
