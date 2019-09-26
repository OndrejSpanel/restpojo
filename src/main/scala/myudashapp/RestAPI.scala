package myudashapp

import com.avsystem.commons.serialization.GenCodec
import io.udash.rest.{CodecWithStructure, DefaultRestImplicits, GET, RestApiCompanion, RestDataCompanion}

import scala.concurrent.Future
import JavaPersonFakeCompanion._
import com.avsystem.commons.meta.MacroInstances
import io.udash.rest.openapi.{RestSchema, RestStructure}

case class Team(leader: JavaPerson)

abstract class EnhancedRestDataCompanion {
  type T = Team
  implicit val javaPersonCodec = JavaPersonFakeCompanion.javaPersonCodec
  //implicit val instances: MacroInstances[DefaultRestImplicits, CodecWithStructure[T]] = MacroInstances.materialize[DefaultRestImplicits, CodecWithStructure[T]]
  implicit val instances: MacroInstances[DefaultRestImplicits, CodecWithStructure[T]] = implicitly[MacroInstances[DefaultRestImplicits, CodecWithStructure[T]]]
  implicit lazy val codec: GenCodec[T] = instances(DefaultRestImplicits, this).codec
  implicit lazy val restStructure: RestStructure[T] = instances(DefaultRestImplicits, this).structure
  implicit lazy val restSchema: RestSchema[T] = RestSchema.lazySchema(restStructure.standaloneSchema)
}

object Team extends EnhancedRestDataCompanion

trait EnhancedRestImplicits extends DefaultRestImplicits {
  implicit val javaPersonCodec: GenCodec[JavaPerson] = JavaPersonFakeCompanion.javaPersonCodec
}

object EnhancedRestImplicits extends EnhancedRestImplicits

trait RestAPI {
  @GET
  def identity: Future[JavaPerson]
}

object RestAPI extends RestApiCompanion[EnhancedRestImplicits,RestAPI](EnhancedRestImplicits)
