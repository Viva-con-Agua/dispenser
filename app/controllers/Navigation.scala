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


class Navigation @Inject() (
  scalate: Scalate,
  cache: CacheApi,
  navigationDAO: NavigationDAO
) extends Controller{


  def validateJson[A: Reads] = BodyParsers.parse.json.validate(_.validate[A].asEither.left.map(e => BadRequest(JsError.toJson(e))))
 
  def createNavigationRoot = Action.async(validateJson[NavigationRootStub]) { request =>
    val root = request.body
    navigationDAO.saveRoot(root.toNavigationRoot) 
    Future.successful(Ok("root save"))
  }
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
  
    
}
