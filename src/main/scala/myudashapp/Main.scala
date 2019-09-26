package myudashapp

import com.avsystem.commons.serialization.GenCodec
import com.avsystem.commons.serialization.json.JsonStringOutput

class JavaPerson {
  private var name: String = _
  private var birthYear = 0
  def getName = name
  def setName(name: String) = {
    this.name = name
  }
  def getBirthYear = birthYear
  def setBirthYear(birthYear: Int) = {
    this.birthYear = birthYear
  }
}

object JavaPersonFakeCompanion {
  def apply(name: String, birthYear: Int): JavaPerson = {
    val result = new JavaPerson
    result.setName(name)
    result.setBirthYear(birthYear)
    result
  }
  def unapply(javaPerson: JavaPerson): Option[(String, Int)] =
    Some((javaPerson.getName, javaPerson.getBirthYear))

  implicit val javaPersonCodec: GenCodec[JavaPerson] =
    GenCodec.fromApplyUnapplyProvider[JavaPerson](JavaPersonFakeCompanion)
}

import com.avsystem.commons.jiop.JavaInterop._

object Main extends App {
  import JavaPersonFakeCompanion._

  val person = new JavaPerson
  person.setName("Me First")
  person.setBirthYear(1970)
  val codec = implicitly[GenCodec[JavaPerson]]
  val sb = new JStringBuilder
  val output = new JsonStringOutput(sb)
  codec.write(output, person)
  println(sb.toString)
}
