package daos

import play.api.mvc._
import javax.inject.Inject
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.Logger
import models._
import models.JsonFormatsNavigation._
import play.api.data.Form
import play.api.libs.json._
// Reactive Mongo imports
import reactivemongo.api.Cursor

import play.modules.reactivemongo._ // ReactiveMongo Play2 plugin

// BSON-JSON conversions/collection
import reactivemongo.play.json._, collection._

class NavigationDAO @Inject() (
  cc:ControllerComponents,
  val reactiveMongoApi: ReactiveMongoApi ) extends AbstractController(cc) with MongoController with ReactiveMongoComponents {
    def collection: Future[JSONCollection] = database.map(
      _.collection[JSONCollection]("navigations"))

    def insert(navigation: Navigation):Future[Navigation] = 
      collection.flatMap(_.insert(navigation)).map { lastError => 
        Logger.debug(s"Successfully inserted Navigation with : $lastError")
        navigation
      }

    def find(name: String):Future[Option[Navigation]] = {
      val cursor = collection.map(_.find(Json.obj("name" -> name)).one[Navigation])
      cursor.flatMap(identity)
    }

    def update(navigation: Navigation):Future[Navigation] = {
      find(navigation.name).map {
        case Some(a) => {
          collection.flatMap(_.update(Json.obj("name" -> navigation.name), Json.obj("$set" -> navigation)))
          navigation
        }
        case None => { 
          insert(navigation)
          navigation
        }
      }
    }
}
