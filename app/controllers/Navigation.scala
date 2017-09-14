package controllers

import play.api._
import play.api.cache._
import play.api.mvc._
import play.api.mvc.Result
import javax.inject.{Inject, Singleton}
import com.github.tototoshi.play2.scalate._
import models._
import daos._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Await}

/** Controller for Navigation
 * 
 */

/*
class Navigation @Inject() (
  scalate: Scalate,
  cache: CacheApi,
  navigationDAO: NavigationDAO
) extends Controller{*/

  /** checks whether a json is validate or not
   *
   *  @param A Reads json datatype and request. 
   *  @return if the json in the request.body is  successful, return JsSuccess Type, else return BadRequest with json errors  
   */
 /* def validateJson[A: Reads] = BodyParsers.parse.json.validate(_.validate[A].asEither.left.map(e => BadRequest(JsError.toJson(e))))
 */
  /** create NavigationRoot entry
   * @param NavigationRootStub json for navigation root,
   * @param request post request. (body == json)
   * the function use validateJson for test json struct. If the json is successfull, it will save in the mongodb
   * 
   */
  /*
  def createNavigationRoot = Action.async(validateJson[NavigationRootStub]) { request =>
    val root = request.body
    navigationDAO.saveRoot(root.toNavigationRoot) 
    Future.successful(Ok("root save"))
  }*/

  /** create navigation entry
   * @param NavigationEntryStub json for navigation entry
   * @param request post request. body == json
   * the function search the entry in db. If the entry already exist, 
   * @return entry exist in DB and 200
   * else: save the entry in db and
   * @return entry save 200
   */
  /*
  def createNavigationEntry = Action.async(validateJson[NavigationEntryStub]) { request =>
    val entry = request.body
    navigationDAO.findEntry(entry.name).map( _ match {
      case Some( _ ) => Ok("entry exist in DB")
      case None => {
        navigationDAO.saveEntry(entry.toNavigationEntry)
        Ok("entry saved")
      }
    })  
  }
  
    
}*/
