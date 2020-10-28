package parsers

sealed trait ParserBasico

case object AnyCharP extends ParserBasico
case object IntegerP  extends ParserBasico
