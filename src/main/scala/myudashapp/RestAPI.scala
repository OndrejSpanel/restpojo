package myudashapp

import com.avsystem.commons.serialization.GenCodec
import io.udash.rest.{DefaultRestImplicits, GET, RestApiCompanion, RestDataCompanion}

import scala.concurrent.Future

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

case class Team(leader: JavaPerson)
object Team extends RestDataCompanion[Team]

trait EnhancedRestImplicits extends DefaultRestImplicits {
  implicit val javaPersonCodec = JavaPersonFakeCompanion.javaPersonCodec
}

object EnhancedRestImplicits extends EnhancedRestImplicits

trait RestAPI {
  @GET
  def identity: Future[JavaPerson]
}

object RestAPI extends RestApiCompanion[EnhancedRestImplicits,RestAPI](EnhancedRestImplicits)
