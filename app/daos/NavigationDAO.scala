package daos

import java.util.UUID

import javax.inject._
import play.api.libs.json._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future} 
import scala.concurrent.duration._

import play.api.Play.current


import play.modules.reactivemongo.{ // ReactiveMongo Play2 plugin
  MongoController,
  ReactiveMongoApi,
  ReactiveMongoComponents
}

import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._
import play.modules.reactivemongo.json.collection.JSONCollection

import models.{Micro, ObjectIdWrapper, NavigationEntry, NavigationRoot}

trait NavigationDAO extends ObjectIdResolver {
  def findEntry(id: UUID): Future[Option[NavigationEntry]]
  def findEntry(name: String): Future[Option[NavigationEntry]]
  def saveEntry(navigationEntry: NavigationEntry): Future[NavigationEntry]
  def findRoot(id: UUID): Future[Option[NavigationRoot]]
  def findRoot(name: String): Future[Option[NavigationRoot]]
  def saveRoot(navigationEntry: NavigationRoot): Future[NavigationRoot]


}

class NavigationEntryDAO @Inject() (val reactiveMongoApi: ReactiveMongoApi) extends NavigationDAO with MongoController with ReactiveMongoComponents {
 // lazy val reactiveMongoApi = current.injector.instanceOf[ReactiveMongoApi]
  val navigationEntrys = reactiveMongoApi.db.collection[JSONCollection]("navigationEntrys")
  val navigationRoots = reactiveMongoApi.db.collection[JSONCollection]("navigationRoots")

  override def getObjectId(id: UUID):Future[Option[ObjectIdWrapper]] = 
    navigationEntrys.find(Json.obj("id" -> id), Json.obj("_id" -> 1)).one[ObjectIdWrapper]

  override def getObjectId(name: String):Future[Option[ObjectIdWrapper]] =
    navigationEntrys.find(Json.obj("id" -> UUID.fromString(name)), Json.obj("_id" -> 1)).one[ObjectIdWrapper]

  def findEntry(id: UUID):Future[Option[NavigationEntry]] = 
    navigationEntrys.find(Json.obj("id" -> id)).one[NavigationEntry]

  def findEntry(name: String):Future[Option[NavigationEntry]] = 
    navigationEntrys.find(Json.obj("name" -> name)).one[NavigationEntry]

  def saveEntry(navigationEntry: NavigationEntry):Future[NavigationEntry] = 
    navigationEntrys.insert(navigationEntry).map(_ => navigationEntry)
  
  def findRoot(id: UUID):Future[Option[NavigationRoot]] = 
    navigationRoots.find(Json.obj("id" -> id)).one[NavigationRoot]

  def findRoot(name: String):Future[Option[NavigationRoot]] = 
    navigationRoots.find(Json.obj("name" -> name)).one[NavigationRoot]

  def saveRoot(navigationEntry: NavigationRoot):Future[NavigationRoot] = 
    navigationRoots.insert(navigationEntry).map(_ => navigationEntry)
  

}
