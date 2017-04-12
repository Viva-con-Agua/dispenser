package models

import play.api.libs.json.Json
import java.util.{Date, UUID}
/**
 * Created by dennis on 08.04.2017
 */


trait MicroBase {
  val name: String
  val link: String
}

case class MicroStub(
  name: String, 
  link: String
) extends MicroBase  {
  val version : Option[String] = None
  val lastcheck: Option[Date] = None
  def toMicro : Micro = Micro(UUID.randomUUID(), name, link, version, lastcheck)
}

object MicroStub{
  implicit val microStubJsonFormat = Json.format[MicroStub]
}


case class Micro(
  id: UUID,
  override val name: String,
  override val link: String,
  val version: Option[String],
  val lastcheck: Option[Date]
) extends MicroBase


object Micro {
  implicit val microJsonFormat = Json.format[Micro]
}

/**case class MicroStubList(
  micros: List[MicroStub]
) extends MicroBase

object MicroStubList {
  implicit val microStubListJsonFormat = Json.format[MicroStubList]
}


case class MicroList(
  number: Long,
  micros: List[MicroStub]
)extends MicroBase


object MicroList {
  implicit val microListJsonFormat = Json.format[MicroList]
}**/  







