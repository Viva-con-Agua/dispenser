package models

import play.api.libs.json.{JsPath, Json, OWrites, Reads}
import java.util.{Date, UUID}
import play.api.libs.functional.syntax._

/*
 * Datastruct for Menus:
 * Json for menuEntry:
 *  menuEntry {
 *    name: "String" //microservice name 
 *    titel: "String" //entry titel 
 *    url: "String" //link to the miroservice content
 *    root: "String" //name of the root menuEntry
 *    sub: List[String] //optional list of sub menuEntryReads
 *  }
 *
 */


case class MenuEntry {
  name: String,
  titel: String,
  url: String,
  root: String,
  sub: Option[List[String]]
}

object MenuEntry {

  def apply(tuple: (String, String, String, Option[List[String]]): MenuEntry =
    MenuEntry(tuple._1, tuple._2, tuple._3, tuple._4)

  implicit val menuEntryWrites: OWrites[MenuEntry] = (
    (JsPath \ "name").write[String] and
    (JsPath \ "titel").write[String] and
    (JsPath \ "url").write[String] and
    (JsPath \ "root").write[String] and
    (JsPath \ "sub").writeNullable[List[String]]
  )(unlift(MenuEntry.unapply))

  implicit val menuEntryReads: Reads[MenuEntry] = (
    (JsPath \ "nane").read[String] and
    (JsPath \ "titel").read[String] and
    (JsPath \ "url").read[String] and
    (JsPath \ "root").read[String] and
    (JsPath \ "sub").readNullable[List[String]] 
  ).tupled.map(MenuEntry)
}
   

