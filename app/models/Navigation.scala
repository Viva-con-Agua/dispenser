package models
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json.{JsPath, Json, OWrites, Reads}
import play.api.libs.functional.syntax._
import java.util.{Date, UUID}


/** models for navigaton function
 *  not final
 */



case class NavigationEntry(
  entry_Lable: String,
  entry_URL: String
  )

object NavigationEntry {

  def apply(tuple: (String, String)): NavigationEntry = 
    NavigationEntry(tuple._1, tuple._2)

  implicit val navigationEntryWrites: OWrites[NavigationEntry] = (
    (JsPath \ "entry_Lable").write[String] and
    (JsPath \ "entry_URL").write[String]
  )(unlift(NavigationEntry.unapply))

  implicit val navigationEntryReads: Reads[NavigationEntry] = (
    (JsPath \ "entry_Lable").read[String] and
    (JsPath \ "entry_URL").read[String]
  ).tupled.map(NavigationEntry( _ ))
}


case class Navigation (
  navigation_Name: String,
  navigation_Entrys: List[NavigationEntry]
)

object Navigation {

  def apply(tuple: (String, List[NavigationEntry])): Navigation = 
    Navigation(tuple._1, tuple._2)

  implicit val navigationWrites: OWrites[Navigation] = (
    (JsPath \ "navigation_Name").write[String] and
    (JsPath \ "navigation_Entrys").write[List[NavigationEntry]]
  )(unlift(Navigation.unapply))

  implicit val navigationReads: Reads[Navigation] = (
    (JsPath \ "navigation_Name").read[String] and
    (JsPath \ "navigation_Entrys").read[List[NavigationEntry]]
  ).tupled.map(Navigation( _ ))
}



