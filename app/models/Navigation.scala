package models
import reactivemongo.play.json._


case class Label(
  de_DE: String,
  en_EN: String
  )

case class Permission(
  role: String,
  crewNames: Option[List[String]],
  pillars: Option[List[String]]
  )

case class NavigationEntry(
  label: Label,
  url: String,
  entrys: Option[List[NavigationEntry]],
  permission: Option[List[Permission]],
  style: Option[String]
  )
case class Navigation (
  name: String,
  entrys: List[NavigationEntry]
)

object JsonFormatsNavigation {
  import play.api.libs.json.Json
  implicit val labelFormat = Json.format[Label]
  implicit val permissionFormat = Json.format[Permission]
  implicit val navigationEntryFormat = Json.format[NavigationEntry]
  implicit val navigationFormat = Json.format[Navigation]
}
