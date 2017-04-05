package models

import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.Play.currend
import scala.concurrent.ExecutionContext.Implicits.global
import java.util.{Date}




case class Microservice(_id: Id, name: String, url: String, version: Long, auth: MsAuthInfo, timestamp: DataTime)

case class MicroserviceData(name: String, url: String, version: Long, auth: MsAuthInfo, timestamp: DataTime)


object MicroserviceData {
  implicit val microserviceDataFormat = Json.format[MicroserviceData]
}  

object Microservice {
  implicit val microserviceFormat = Json.format[Microservice]
}
