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
  root: String,
  sub: Option[List[String]]
 ){
  def toNavigationEntry: NavigationEntry = NavigationEntry(UUID.randomUUID(), name, title, url, root, sub)
 }

object NavigationEntryStub {
  def apply(tuple: (String, String, String, String, Option[List[String]])): NavigationEntryStub =
    NavigationEntryStub(tuple._1, tuple._2, tuple._3, tuple._4, tuple._5)

  implicit val navigationEntryStubWrites: OWrites[NavigationEntryStub] = (
    (JsPath \ "name").write[String] and
    (JsPath \ "title").write[String] and
    (JsPath \ "url").write[String] and
    (JsPath \ "root").write[String] and
    (JsPath \ "sub").writeNullable[List[String]] 
  )(unlift(NavigationEntryStub.unapply))

  implicit val navigationEntryStubReads: Reads[NavigationEntryStub] = (
    (JsPath \ "nane").read[String] and
    (JsPath \ "title").read[String] and
    (JsPath \ "url").read[String] and
    (JsPath \ "root").read[String] and
    (JsPath \ "sub").readNullable[List[String]] 
  ).tupled.map(NavigationEntryStub( _ ))

  

}

case class NavigationEntry (
  _id: UUID,
  name: String,
  title: String,
  url: String,
  root: String,
  sub: Option[List[String]]
 )

object NavigationEntry {
  def apply(tuple: (UUID, String, String, String, String, Option[List[String]])): NavigationEntry =
    NavigationEntry(tuple._1, tuple._2, tuple._3, tuple._4, tuple._5, tuple._6)
  //def apply(_id: UUID, name: String, title: String, url: String, root: UUID, sub: Option[List[UUID]], degree: Int ): NavigationEntry =
  //  NavigationEntry(_id, name, title, url, root, sub, degree)

  implicit val navigationEntryWrites: OWrites[NavigationEntry] = (
    (JsPath \ "_id").write[UUID] and
    (JsPath \ "name").write[String] and
    (JsPath \ "title").write[String] and
    (JsPath \ "url").write[String] and
    (JsPath \ "root").write[String] and
    (JsPath \ "sub").writeNullable[List[String]] 
  )(unlift(NavigationEntry.unapply))

  implicit val navigationEntryReads: Reads[NavigationEntry] = (
    (JsPath \ "_id").read[UUID] and
    (JsPath \ "name").read[String] and
    (JsPath \ "title").read[String] and
    (JsPath \ "url").read[String] and
    (JsPath \ "root").read[String] and
    (JsPath \ "sub").readNullable[List[String]] 
  ).tupled.map(NavigationEntry( _ ))

  

}

case class NavigationRootStub (
  name: String,
  sub: Option[List[String]]
){
  def toNavigationRoot: NavigationRoot = NavigationRoot(UUID.randomUUID(), name, sub)
}

object NavigationRootStub {

  def apply(tuple: (String, Option[List[String]])): NavigationRootStub =
    NavigationRootStub(tuple._1, tuple._2)

  implicit val navigationRootStubWrites: OWrites[NavigationRootStub] = (
    (JsPath \ "name").write[String] and
    (JsPath \ "sub").writeNullable[List[String]]
  )(unlift(NavigationRootStub.unapply))

  implicit val navigationRootStubReads: Reads[NavigationRootStub] = (
    (JsPath \ "name").read[String] and
    (JsPath \ "sub").readNullable[List[String]] 
  ).tupled.map(NavigationRootStub( _ ))
}
   
case class NavigationRoot (
  _id: UUID,
  name: String,
  sub: Option[List[String]]
)

object NavigationRoot {

  def apply(tuple: (UUID, String, Option[List[String]])): NavigationRoot =
    NavigationRoot(tuple._1, tuple._2, tuple._3)

  implicit val navigationRootWrites: OWrites[NavigationRoot] = (
    (JsPath \ "_id").write[UUID] and
    (JsPath \ "name").write[String] and
    (JsPath \ "sub").writeNullable[List[String]]
  )(unlift(NavigationRoot.unapply))

  implicit val navigationRootReads: Reads[NavigationRoot] = (
    (JsPath \ "_id").read[UUID] and
    (JsPath \ "name").read[String] and
    (JsPath \ "sub").readNullable[List[String]] 
  ).tupled.map(NavigationRoot( _ ))
}
 
