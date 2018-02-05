package models
import reactivemongo.play.json._


case class Permission(
  role: Option[List[String]],
  task: Option[List[String]]
  )

case class NavigationEntry(
  lable: String,
  url: String,
  permission: Option[Permission]
  )

case class Navigation (
  name: String,
  entrys: List[NavigationEntry]
)

object JsonFormatsNavigation {
  import play.api.libs.json.Json
  
  implicit val permissionFormat = Json.format[Permission]
  implicit val navigationEntryFormat = Json.format[NavigationEntry]
  implicit val navigationFormat = Json.format[Navigation]
}
