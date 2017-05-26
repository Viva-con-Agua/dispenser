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

import models.{Micro, ObjectIdWrapper, NavigationEntry}

trait NavigationDAO extends ObjectIdResolver {
  def find(id: UUID): Future[Option[NavigationEntry]]
  def find(name: String): Future[Option[NavigationEntry]]
  def save(navigationEntry: NavigationEntry): Future[NavigationEntry]

}

class NavigationEntryDAO @Inject() (val reactiveMongoApi: ReactiveMongoApi) extends NavigationDAO with MongoController with ReactiveMongoComponents {
 // lazy val reactiveMongoApi = current.injector.instanceOf[ReactiveMongoApi]
  val navigationEntrys = reactiveMongoApi.db.collection[JSONCollection]("navigationEntrys")
  
  override def getObjectId(id: UUID):Future[Option[ObjectIdWrapper]] = 
    navigationEntrys.find(Json.obj("id" -> id), Json.obj("_id" -> 1)).one[ObjectIdWrapper]

  override def getObjectId(name: String):Future[Option[ObjectIdWrapper]] =
    navigationEntrys.find(Json.obj("id" -> UUID.fromString(name)), Json.obj("_id" -> 1)).one[ObjectIdWrapper]

  def find(id: UUID):Future[Option[NavigationEntry]] = 
    navigationEntrys.find(Json.obj("id" -> id)).one[NavigationEntry]

  def find(name: String):Future[Option[NavigationEntry]] = 
    navigationEntrys.find(Json.obj("name" -> name)).one[NavigationEntry]

  def save(navigationEntry: NavigationEntry):Future[NavigationEntry] = 
    navigationEntrys.insert(navigationEntry).map(_ => navigationEntry)

}
