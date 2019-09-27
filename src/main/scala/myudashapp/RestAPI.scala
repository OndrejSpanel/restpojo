package myudashapp

import com.avsystem.commons.serialization.{GenCodec, HasGenCodecWithDeps}
import io.udash.rest.{CodecWithStructure, DefaultRestImplicits, GET, RestApiCompanion, RestDataCompanion}

import scala.concurrent.Future
import com.avsystem.commons.meta.MacroInstances
import io.udash.rest.openapi.{RestSchema, RestStructure}

trait EnhancedRestImplicits extends DefaultRestImplicits {
  implicit val javaPersonCodec: GenCodec[JavaPerson] = JavaPersonFakeCompanion.javaPersonCodec
}

object EnhancedRestImplicits extends EnhancedRestImplicits

abstract class EnhancedRestApiCompanion[T](
  implicit macroCodec: MacroInstances[EnhancedRestImplicits.type, () => GenCodec[T]]
) extends HasGenCodecWithDeps[EnhancedRestImplicits.type, T] {
  implicit val instances: MacroInstances[DefaultRestImplicits, CodecWithStructure[T]] = implicitly[MacroInstances[DefaultRestImplicits, CodecWithStructure[T]]]
  implicit lazy val restStructure: RestStructure[T] = instances(DefaultRestImplicits, this).structure
  implicit lazy val restSchema: RestSchema[T] = RestSchema.lazySchema(restStructure.standaloneSchema)
}

case class Team(leader: JavaPerson)

object Team extends EnhancedRestApiCompanion[Team]

trait RestAPI {
  @GET
  def identity: Future[JavaPerson]
}

object RestAPI extends RestApiCompanion[EnhancedRestImplicits,RestAPI](EnhancedRestImplicits)
