/**package daos

import java.util.UUID
import java.inject._
import play.api.libs.json._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import play.api.Play.current

import play.modules.reactivemongo.{
  MongoController,
  ReactiveMongoApi,
  ReactiveMongoComponents
}

import play.modules.reactivemongo.ReactiveMongoApi
import play.modules.reactivemongo.json._
import play.modules.reactivemongo.json.collection.JSONCollection

import models.{Template}


trait TemplateDAO extends ObjectIdResolver {
  def find(id: UUID):Future[Option[Template]]
  def find(name: String):Future[Option[Template]]
  def save(template: Template):Future[Template]
}

class TemplateDAOHandler @Inject() (val reactiveMongoApi: ReactiveMongoApi) extends TemplateDAO with MongoController with ReactiveMongoComponents  {

  val templates = reactiveMongoApi.db.collection[JSONCollection]("templates")
  
  override def getObjectId(id: UUID):Future[Option[ObjectIdWrapper]] =
    templates.find(Json.obj("id" -> id), Json.obj("_id" -> 1)).one[ObjectIdWrapper]

  override def getObjectId(name: String):Future[Option[ObjectIdWrapper]] = 
    templates.find(Json.obj("id" -> UUID.formString(name)), Json.obj("_id" -> 1)).one[ObjectIdWrapper]
  
  def find(id: UUID):Future[Option[Micro]] = templates.find(Json.obj("id" -> id)).one[Micro]
    templates.find(Json.obj("id" -> id)).one[Micro]
  def find(name: String):Future[Option[Micro]] = 
    templates.find(Json.obj("name" -> name)).one[Micro]

  def save(micro: Micro):Future[Micro] = 
    templates.insert(micro).map(_ => micro)
}**/


