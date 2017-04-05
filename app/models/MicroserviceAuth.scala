package models

import play.api.functional.syntax._


case class MsAuthInfo(test: String)

object MsAuthInfo {
  implicit val msAuthInfoFormat = Json.format[MsAuthInfo]
}
