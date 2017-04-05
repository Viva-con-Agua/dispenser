package models.daos

import javax.inject.Inject
import play.api.libs.json._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, Future} 
import scala.concurrent.duration._
import reactivemongo.api._

import play.modules.reactivemongo.json._
import play.modules.reactivemongo.json.collection._

class MicroserviceDAO @Inject() (db: DB) {
  def microservices: JSONCollection = db.collection[JSONCollection]("microservices")
  
  //insert Microservice Data into microservices Collection
  def insert(ms: MicroserviceData) : Future[Option[Microservice]] = {
    //check that MS is not in Collection
    this.find(ms).flatmap(_ match {
      //if MS is in Collection return MS
      case Some(ms) => Future.successful(Some(ms))
      //else MS Not in Collection insert
      case None => {
        microservices.insert(Microservice(id, ms)).flatMap( _.ok match {
          case true => this.find(ms)
          case false => Future(None)
        })
      }
    })
  }  
  //find MS via MicroserviceData in the Collection microservices
  def find(ms: MicroserviceData) : Future[Option[Microservice]] = {
    microservices.find(this.getQuery(Json.toJson(ms))).one[Microservice]  
  }
}
