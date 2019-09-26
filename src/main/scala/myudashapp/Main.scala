package myudashapp

import com.avsystem.commons.jiop.JavaInterop._
import com.avsystem.commons.serialization.GenCodec
import com.avsystem.commons.serialization.json.JsonStringOutput

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
