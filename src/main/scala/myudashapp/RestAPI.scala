package myudashapp

import com.avsystem.commons.serialization.GenCodec
import io.udash.rest.{DefaultRestImplicits, GET, RestApiCompanion, RestDataCompanion}

import scala.concurrent.Future

import JavaPersonFakeCompanion._

case class Team(leader: JavaPerson)
object Team extends RestDataCompanion[Team]

trait EnhancedRestImplicits extends DefaultRestImplicits {
  implicit val javaPersonCodec: GenCodec[JavaPerson] = JavaPersonFakeCompanion.javaPersonCodec
}

object EnhancedRestImplicits extends EnhancedRestImplicits

trait RestAPI {
  @GET
  def identity: Future[JavaPerson]
}

object RestAPI extends RestApiCompanion[EnhancedRestImplicits,RestAPI](EnhancedRestImplicits)
