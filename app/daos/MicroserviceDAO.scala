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

import models.{Micro, ObjectIdWrapper}
import com.google.inject.ImplementedBy


@ImplementedBy(classOf[MongoMiSeDAO])
trait MicroserviceDAO extends ObjectIdResolver  {
  def find(id: UUID):Future[Option[Micro]]
  def find(name: String):Future[Option[Micro]]
  //def find()
  def save(micserv: Micro):Future[Micro]
}



class MongoMiSeDAO @Inject() (val reactiveMongoApi: ReactiveMongoApi) extends MicroserviceDAO with MongoController with ReactiveMongoComponents {
  val microservices = reactiveMongoApi.db.collection[JSONCollection]("microservices")
  
  override def getObjectId(id: UUID):Future[Option[ObjectIdWrapper]] = 
    microservices.find(Json.obj("id" -> id), Json.obj("_id" -> 1)).one[ObjectIdWrapper]

  override def getObjectId(name: String):Future[Option[ObjectIdWrapper]] =
    microservices.find(Json.obj("id" -> UUID.fromString(name)), Json.obj("_id" -> 1)).one[ObjectIdWrapper]

  def find(id: UUID):Future[Option[Micro]] = 
    microservices.find(Json.obj("id" -> id)).one[Micro]

  def find(name: String):Future[Option[Micro]] = 
    microservices.find(Json.obj("name" -> name)).one[Micro]

  def save(micro: Micro):Future[Micro] = 
    microservices.insert(micro).map(_ => micro)

}
