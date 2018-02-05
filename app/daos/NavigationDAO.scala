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
      //val cursor: Future[Cursor[Navigation]] = collection.map {
      //  _.find(Json.obj("name" -> name)).cursor[Navigation] 
      //}
      //val futureNavigationList: Future[Seq[Navigation]] = cursor.flatMap(_.collect[Seq](1))
      //futureNavigationList
      val cursor = collection.map(_.find(Json.obj("name" -> name)).one[Navigation])
      cursor.flatMap(identity)
        
  }
}
