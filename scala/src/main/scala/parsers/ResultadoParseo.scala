package parsers

import scala.util.Try
import scala.util.control.NonFatal


object ResultadoParseo {
  def apply[T](r: => (T,String)) :ResultadoParseo[T] = {
    try ParseoExitoso(r._1, r._2) catch {
      case error :Exception => ErrorParseo(r._2,error)
    }
  }
}

sealed abstract class ResultadoParseo[T] extends Product with Serializable {
  def isErrorParseo :Boolean
  def isParseoExitoso :Boolean

  def map(f: String => (T, String)): ResultadoParseo[T]
  def filter(f: String => Boolean): ResultadoParseo[T]
  def flatMap(f: String => ResultadoParseo[T]): ResultadoParseo[T]
}

case class ParseoExitoso[T](resultado :T, cadena :String) extends ResultadoParseo[T] {
  def isErrorParseo = false
  def isParseoExitoso = true

  override def map(f: String => (T, String)) :ResultadoParseo[T] = ResultadoParseo(f(cadena))
  override def filter(f: String => Boolean) :ResultadoParseo[T] =
    try {
      if (f(cadena)) this else ErrorParseo(cadena, new NoSuchElementException("Predicate does not hold for " + cadena))
    } catch { case error :Exception => ErrorParseo(cadena, error) }

  override def flatMap(f: String => ResultadoParseo[T]): ResultadoParseo[T] =
    try f(cadena) catch { case error :Exception => ErrorParseo(cadena, error) }


}

case class ErrorParseo[T](cadena :String,exception: Exception) extends ResultadoParseo[T] {
  def isErrorParseo = true
  def isParseoExitoso = false

  override def map(f: String => (T,String)): ResultadoParseo[T] = this
  override def filter(f: String => Boolean): ResultadoParseo[T] = this
  override def flatMap(f: String => ResultadoParseo[T]): ResultadoParseo[T] = this.asInstanceOf[ResultadoParseo[T]]
}



