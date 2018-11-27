package models
import reactivemongo.play.json._


case class Permission(
  role: String,
  crewNames: Option[List[String]],
  pillars: Option[List[String]]
  )

case class NavigationEntry(
  lable: String,
  url: String,
  entrys: Option[List[NavigationEntry]],
  permission: Option[List[Permission]]
  )
case class Navigation (
  name: String,
  involved: List[String],
  entrys: List[NavigationEntry]

)

object JsonFormatsNavigation {
  import play.api.libs.json.Json
  
  implicit val permissionFormat = Json.format[Permission]
  implicit val navigationEntryFormat = Json.format[NavigationEntry]
  implicit val navigationFormat = Json.format[Navigation]
}
