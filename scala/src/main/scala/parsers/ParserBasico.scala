package parsers

import scala.util.Try

trait Parser[T] extends (String => Try[ResultadoParseo[T]]) {
  // combinator
  def <>[U](parser2: Parser[U]): ConcatComb[T, U] = ConcatComb[T, U](this, parser2)

  def <|>[U<:V,V >:T](parser2: Parser[U]): ORComb[T, U, V] = ORComb[T, U,V](this, parser2)

  def <~[U](parser2: Parser[U]): LeftComb[T, U] = LeftComb[T, U](this, parser2)

  def ~>[U](parser2: Parser[U]): RightComb[T, U] = RightComb[T, U](this, parser2)

  def sepBy[U](parser2: Parser[U]): SepComb[T, U] = SepComb[T, U](this, parser2)

  // operators
  def satisfies(funcion: T => Boolean): SatisfiesOp[T] = SatisfiesOp(this, funcion)

  def opt: OptOp[T] = OptOp(this)

  def * : KleeneOp[T] = KleeneOp(this)

  def + : ClauPoseOp[T] = ClauPoseOp(this)

  def map[U](funcion: T => U): MapOp[T, U] = MapOp[T, U](this, funcion)
}

case object anyChar extends Parser[Char] {
  override def apply(cadena: String): Try[ResultadoParseo[Char]] =
    Try(cadena).map(elemento => ResultadoParseo(elemento.head, elemento.tail))
}

case class char(charAMatchear: Char) extends Parser[Char] {
  override def apply(cadena: String): Try[ResultadoParseo[Char]] = {
    Try(cadena).filter(elemento => elemento.head.equals(charAMatchear))
      .map(elemento => ResultadoParseo(elemento.head, elemento.tail))
  }
}

case object digit extends Parser[Char] {
  override def apply(cadena: String): Try[ResultadoParseo[Char]] = {
    Try(cadena).filter(elemento => elemento.head.isDigit)
      .map(elemento => ResultadoParseo(elemento.head, elemento.tail))
  }
}

case class string(stringName: String) extends Parser[String] {
  override def apply(cadena: String): Try[ResultadoParseo[String]] = {
    Try(cadena).filter(elemento => elemento.take(stringName.length).equals(stringName))
      .map(elemento => ResultadoParseo(elemento.take(stringName.length), elemento.drop(stringName.length)))
  }
}

case object integer extends Parser[Int] {
  override def apply(cadena: String): Try[ResultadoParseo[Int]] = {
    val parserInteger = char('-').opt <> digit.+
    val parserMap = parserInteger.map(tupla => (tupla._1.getOrElse("") + tupla._2.mkString("")).toInt)
    parserMap.apply(cadena)
  }
}

case object double extends Parser[Double] {
  override def apply(cadena: String): Try[ResultadoParseo[Double]] = {
    val decimals = char('.') <> digit.+
    val parserDouble = integer <> decimals.opt
    val parserMap = parserDouble.map(
      tupla => {
        val decimales = tupla._2.map(tupla2 => tupla2._1 + tupla2._2.mkString(""))
        (tupla._1.toString + decimales.getOrElse("")).toDouble
      }
    )
    parserMap.apply(cadena)
  }
}

