package daos

import play.api.mvc._
import javax.inject.Inject
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.Logger
import models._
import models.JsonFormats._
import play.api.data.Form
// Reactive Mongo imports
import reactivemongo.api.Cursor

import play.modules.reactivemongo._ // ReactiveMongo Play2 plugin

// BSON-JSON conversions/collection
import reactivemongo.play.json._, collection._


class UserDAO @Inject() (
  cc: ControllerComponents,
  val reactiveMongoApi: ReactiveMongoApi ) extends AbstractController(cc) with MongoController with ReactiveMongoComponents {
    def collection: Future[JSONCollection] = database.map(
    _.collection[JSONCollection]("users"))

    def create(user: User):Future[User] = 
       collection.flatMap(_.insert(user)).map { lastError =>
        Logger.debug(s"Successfully inserted with LastError: $lastError")
        Created
        user
      }
  }
