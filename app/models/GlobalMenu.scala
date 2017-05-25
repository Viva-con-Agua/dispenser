package models

import play.api.libs.json.{JsPath, Json, OWrites, Reads}
import java.util.{Date, UUID}
import play.api.libs.functional.syntax._

/**
 *Global Menu Json for construct Menu entrys
 *
 */




trait GlobalMenuEntryBase {
  val globalMenuEntryLink: String
  val globalMenuEntryName: String
}


case class GlobalMenuEntry (
  globalMenuEntryLink: String,
  globalMenuEntryName: String
)extends GlobalMenuEntryBase

object GlobalMenuEntry {

  def apply(tuple: (String, String)): GlobalMenuEntry = 
    GlobalMenuEntry(tuple._1, tuple._2)
    
  implicit val globalMenuEntryWrites: OWrites[GlobalMenuEntry] = (
    (JsPath \ "globalMenuEntryLink").write[String] and
    (JsPath \ "globalMenuEntryName").write[String] 
  )(unlift(GlobalMenuEntry.unapply))

   implicit val globalMenuEntryReads: Reads[GlobalMenuEntry] = (
    (JsPath \ "globalMenuEntryLink").read[String] and
    (JsPath \ "globalMenuEntryName").read[String] 
  ).tupled.map(GlobalMenuEntry( _ ))
}

trait GlobalMenuBase {
  val globalMenuEntryList: List[GlobalMenuEntry]
}

case class GlobalMenu (
  globalMenuEntryList: List[GlobalMenuEntry]
)extends GlobalMenuBase 

object GlobalMenu {

  def apply(tuple: (List[GlobalMenuEntry])) : GlobalMenu = 
    GlobalMenu(tuple._1)

  implicit val globalMenuWrites: OWrites[GlobalMenu] = (
    (JsPath \ "globalMenuEntryList").writes[List[GlobalMenuEntry]]
  )(unlift(GlobalMenu.unapply))

  implicit val globalMenuReads: Reads[GlobalMenu] = (
    (JsPath \ "globalMenuEntryList").read[List[GlobalMenuEntry]]
  ).tupled.map(GlobalMenu( _ ))
}
