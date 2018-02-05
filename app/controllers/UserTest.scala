package controllers

import javax.inject._
import play.api._
import play.api.mvc._

import scala.concurrent.Future

import play.api.Logger
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._

// Reactive Mongo imports
import reactivemongo.api.Cursor

import play.modules.reactivemongo._ // ReactiveMongo Play2 plugin

// BSON-JSON conversions/collection
import reactivemongo.play.json._, collection._

/*
 * Example using ReactiveMongo + Play JSON library.
 *
 * There are two approaches demonstrated in this controller:
 * - using JsObjects directly
 * - using case classes that can be turned into JSON using Reads and Writes.
 *
 * This controller uses case classes and their associated Reads/Writes
 * to read or write JSON structures.
 *
 * Instead of using the default Collection implementation (which interacts with
 * BSON structures + BSONReader/BSONWriter), we use a specialized
 * implementation that works with JsObject + Reads/Writes.
 *
 * Of course, you can still use the default Collection implementation
 * (BSONCollection.) See ReactiveMongo examples to learn how to use it.
 */
class ApplicationUsingJsonReadersWriters @Inject() (
  cc: ControllerComponents,
  val reactiveMongoApi: ReactiveMongoApi) extends AbstractController(cc)
    with MongoController with ReactiveMongoComponents {
  /*
   * Get a JSONCollection (a Collection implementation that is designed to work
   * with JsObject, Reads and Writes.)
   * Note that the `collection` is not a `val`, but a `def`. We do _not_ store
   * the collection reference to avoid potential problems in development with
   * Play hot-reloading.
   */
  def collection: Future[JSONCollection] = database.map(
    _.collection[JSONCollection]("persons"))

  // ------------------------------------------ //
  // Using case classes + JSON Writes and Reads //
  // ------------------------------------------ //
  import play.api.data.Form
  import models._
  import models.JsonFormats._

  def create = Action.async {
    val user = User(29, "John", "Smith", List(
      Feed("Slashdot news", "http://slashdot.org/slashdot.rdf")))

    // insert the user
    val futureResult = collection.flatMap(_.insert(user))

    // when the insert is performed, send a OK 200 result
    futureResult.map(_ => Ok)
  }

  def createFromJson = Action.async(parse.json) { request =>
    /*
     * request.body is a JsValue.
     * There is an implicit Writes that turns this JsValue as a JsObject,
     * so you can call insert() with this JsValue.
     * (insert() takes a JsObject as parameter, or anything that can be
     * turned into a JsObject using a Writes.)
     */
    request.body.validate[User].map { user =>
      // `user` is an instance of the case class `models.User`
      collection.flatMap(_.insert(user)).map { lastError =>
        Logger.debug(s"Successfully inserted with LastError: $lastError")
        Created
      }
    }.getOrElse(Future.successful(BadRequest("invalid json")))
  }

  def findByName(lastName: String) = Action.async {
    // let's do our query
    val cursor: Future[Cursor[User]] = collection.map {
      // find all people with name `name`
      _.find(Json.obj("lastName" -> lastName)).
        // sort them by creation date
        sort(Json.obj("created" -> -1)).
        // perform the query and get a cursor of JsObject
        cursor[User]
    }

    // gather all the JsObjects in a list
    val futureUsersList: Future[List[User]] = cursor.flatMap(_.collect[List]())

    // everything's ok! Let's reply with the array
    futureUsersList.map { persons =>
      Ok(persons.toString)
    }
  }
}
