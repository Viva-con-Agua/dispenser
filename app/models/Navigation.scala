package models
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json.{JsPath, Json, OWrites, Reads}
import play.api.libs.functional.syntax._
import java.util.{Date, UUID}



case class NavigationEntryStub (
  name: String,
  title: String,
  url: String,
  root: UUID,
  sub: Option[List[UUID]],
  degree: Int
 ){
  def toNavigationEntry: NavigationEntry = NavigationEntry(UUID.randomUUID(), name, title, url, root, sub, degree)
 }

object NavigationEntryStub {
  def apply(tuple: (String, String, String, UUID, Option[List[UUID]], Int)): NavigationEntryStub =
    NavigationEntryStub(tuple._1, tuple._2, tuple._3, tuple._4, tuple._5, tuple._6)

  implicit val navigationEntryStubWrites: OWrites[NavigationEntryStub] = (
    (JsPath \ "name").write[String] and
    (JsPath \ "title").write[String] and
    (JsPath \ "url").write[String] and
    (JsPath \ "root").write[UUID] and
    (JsPath \ "sub").writeNullable[List[UUID]] and
    (JsPath \ "degree").write[Int]
  )(unlift(NavigationEntryStub.unapply))

  implicit val navigationEntryStubReads: Reads[NavigationEntryStub] = (
    (JsPath \ "nane").read[String] and
    (JsPath \ "title").read[String] and
    (JsPath \ "url").read[String] and
    (JsPath \ "root").read[UUID] and
    (JsPath \ "sub").readNullable[List[UUID]] and
    (JsPath \ "degree").read[Int]
  ).tupled.map(NavigationEntryStub( _ ))

  

}

case class NavigationEntry (
  _id: UUID,
  name: String,
  title: String,
  url: String,
  root: UUID,
  sub: Option[List[UUID]],
  degree: Int
 )

object NavigationEntry {
  def apply(tuple: (UUID, String, String, String, UUID, Option[List[UUID]], Int)): NavigationEntry =
    NavigationEntry(tuple._1, tuple._2, tuple._3, tuple._4, tuple._5, tuple._6, tuple._7)
  //def apply(_id: UUID, name: String, title: String, url: String, root: UUID, sub: Option[List[UUID]], degree: Int ): NavigationEntry =
  //  NavigationEntry(_id, name, title, url, root, sub, degree)

  implicit val navigationEntryWrites: OWrites[NavigationEntry] = (
    (JsPath \ "_id").write[UUID] and
    (JsPath \ "name").write[String] and
    (JsPath \ "title").write[String] and
    (JsPath \ "url").write[String] and
    (JsPath \ "root").write[UUID] and
    (JsPath \ "sub").writeNullable[List[UUID]] and
    (JsPath \ "degree").write[Int]
  )(unlift(NavigationEntry.unapply))

  implicit val navigationEntryReads: Reads[NavigationEntry] = (
    (JsPath \ "_id").read[UUID] and
    (JsPath \ "name").read[String] and
    (JsPath \ "title").read[String] and
    (JsPath \ "url").read[String] and
    (JsPath \ "root").read[UUID] and
    (JsPath \ "sub").readNullable[List[UUID]] and
    (JsPath \ "degree").read[Int]
  ).tupled.map(NavigationEntry( _ ))

  

}

case class NavigationEntryJson (
  name: String,
  titel: String,
  url: String,
  root: String,
  sub: Option[List[String]]
)

object NavigationEntryJson {

  def apply(tuple: (String, String, String, String, Option[List[String]])): NavigationEntryJson =
    NavigationEntryJson(tuple._1, tuple._2, tuple._3, tuple._4, tuple._5)

  implicit val navigationEntryJsonWrites: OWrites[NavigationEntryJson] = (
    (JsPath \ "name").write[String] and
    (JsPath \ "titel").write[String] and
    (JsPath \ "url").write[String] and
    (JsPath \ "root").write[String] and
    (JsPath \ "sub").writeNullable[List[String]]
  )(unlift(NavigationEntryJson.unapply))

  implicit val navigationEntryJsonReads: Reads[NavigationEntryJson] = (
    (JsPath \ "name").read[String] and
    (JsPath \ "titel").read[String] and
    (JsPath \ "url").read[String] and
    (JsPath \ "root").read[String] and
    (JsPath \ "sub").readNullable[List[String]] 
  ).tupled.map(NavigationEntryJson( _ ))
}
   

/**
case class rootMenu(name: String, subs: List[String]) extends MenuBase 

case class SubMenu(name: String, root: String, sub: List[String], link: String, version: String) extends MenuBase

case class entryMenu(name: String, link: String)
**/

