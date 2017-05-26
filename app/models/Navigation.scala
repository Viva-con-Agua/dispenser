package models
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.json.Json
import java.util.{Date, UUID}

case class NavigationEntryStub (
  name: String,
  title: String,
  url: String,
  root: UUID,
  sub: Option[List[Id]],
  degree: Int,
 ){
  def toNavigationEntry: NavigationEntry = NavigationEntry(UUID.randomUUID(), NavigationEntryStub)
 }

object NavigationEntryStub {
  def apply(tuple: (String, String, String, Option[List[String]])): NavigationEntryStub =
    NavigationEntryStub(tuple._1, tuple._2, tuple._3, tuple._4, tuble._5)

  implicit val navigationEntryStubWrites: OWrites[NavigationEntryStub] = (
    (JsPath \ "name").write[String] and
    (JsPath \ "title").write[String] and
    (JsPath \ "url").write[String] and
    (JsPath \ "root").write[UUID] and
    (JsPath \ "sub").writeNullable[List[UUID]]
  )(unlift(NavigationEntryStub.unapply))

  implicit val navigationEntryStubReads: Reads[NavigationEntryStub] = (
    (JsPath \ "nane").read[String] and
    (JsPath \ "title").read[String] and
    (JsPath \ "url").read[String] and
    (JsPath \ "root").read[UUID] and
    (JsPath \ "sub").readNullable[List[Id]] 
  ).tupled.map(NavigationEntryStub)

  

}

case class NavigationEntry (
  _id: UUID,
  name: String,
  title: String,
  url: String,
  root: UUID,
  sub: Option[List[UUID]],
  degree: Int,
 )

object NavigationEntry {
  def apply(tuple: (UUID, String, String, UUID, Option[List[UUID]])): NavigationEntry =
    NavigationEntry(tuple._1, tuple._2, tuple._3, tuple._4, tuble._5)
  def apply(_id: UUID, stub: NavigationEntryStub): NavigationEntry =
    NavigationEntry(_id, stub.name, stub.title, stub.url, stub.root, stub.sub, stub.degree)

  implicit val navigationEntryWrites: OWrites[NavigationEntry] = (
    (JsPath \ "_id").write[UUID] and
    (JsPath \ "name").write[String] and
    (JsPath \ "title").write[String] and
    (JsPath \ "url").write[String] and
    (JsPath \ "root").write[UUID] and
    (JsPath \ "sub").writeNullable[List[UUID]]
  )(unlift(NavigationEntry.unapply))

  implicit val navigationEntryReads: Reads[NavigationEntry] = (
    (JsPath \ "_id").read[UUID] and
    (JsPath \ "name").read[String] and
    (JsPath \ "title").read[String] and
    (JsPath \ "url").read[String] and
    (JsPath \ "root").read[UUID] and
    (JsPath \ "sub").readNullable[List[UUID]] 
  ).tupled.map(NavigationEntry)

  

}


/**
case class rootMenu(name: String, subs: List[String]) extends MenuBase 

case class SubMenu(name: String, root: String, sub: List[String], link: String, version: String) extends MenuBase

case class entryMenu(name: String, link: String)
**/

