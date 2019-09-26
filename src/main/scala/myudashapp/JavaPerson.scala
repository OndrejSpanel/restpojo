package myudashapp

import com.avsystem.commons.serialization.GenCodec

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
