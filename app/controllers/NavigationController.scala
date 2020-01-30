package controllers

import javax.inject._

import play.api._
import play.api.mvc._
import play.api.libs.json._
import play.Play

import com.github.tototoshi.play2.scalate._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import com.mohiva.play.silhouette.api.Silhouette
import org.vivaconagua.play2OauthClient.silhouette.CookieEnv
import org.vivaconagua.play2OauthClient.silhouette.UserService


import play.api.mvc.AnyContent
import com.mohiva.play.silhouette.api.actions.SecuredErrorHandler

import models.JsonFormatsNavigation._
import daos._
import models._
import services.RenderService

import scala.io.Source

class NavigationController @Inject() (
  cc:ControllerComponents,
  scalate: Scalate,
  navigationDAO: NavigationDAO,
  config: Configuration,
  render: RenderService,
  silhouette: Silhouette[CookieEnv],
  userService: UserService,
  val env: Environment
)extends AbstractController(cc) {

  def validateJson[A: Reads] = BodyParsers.parse.json.validate(_.validate[A].asEither.left.map(e => BadRequest(JsError.toJson(e))))
 
  //def index = Action.async { implicit request =>
  //  Future.successful(Ok(views.html.navigation.index()))
  //}

  val logger: Logger = Logger(this.getClass())

  def init = Action.async { implicit request =>
   // getNavigationFromFile("noSignInDE") match {
   //   case s: JsSuccess[Navigation] => navigationDAO.update(s.get)
   //   case e: JsError => BadRequest(JsError.toJson(e).toString)
   // }
    getNavigationFromFile("noSignIn") match {
      case s: JsSuccess[Navigation] => navigationDAO.update(s.get)
      case e: JsError => BadRequest(JsError.toJson(e).toString)
    }
    getNavigationFromFile("GlobalNav") match {
      case s: JsSuccess[Navigation] => navigationDAO.update(s.get)
      case e: JsError => BadRequest(JsError.toJson(e).toString)
    } 
   // getNavigationFromFile("GlobalNavEN") match {
   //   case s: JsSuccess[Navigation] => navigationDAO.update(s.get)
   //   case e: JsError => BadRequest(JsError.toJson(e).toString)
   // } 
    Future.successful(Ok)
  }

  def insertNavigation = Action.async(validateJson[Navigation]) { request =>
    navigationDAO.update(request.body)
    Future.successful(Ok)
  }
  
  def getNavigationAsJson(name: String) = Action.async { request =>
    navigationDAO.find(name).map{
      case Some(a) => Ok(Json.toJson(a.entrys))
      case _ => BadRequest("Navigation" + name + " not found")
    }
  }
  
  val defaultHandler = new SecuredErrorHandler {
    override def onNotAuthenticated(implicit request: RequestHeader) = {
      navigationDAO.find("no-SignInDE").map{
          case Some(a) => Ok(Json.toJson(a.entrys))
          case _ => BadRequest("Navigation not found")
      }
    }
    override def onNotAuthorized(implicit request: RequestHeader) = {
      Future.successful(BadRequest("No Navigation access"))
    }
  }
  
  def navigationNoSignIn(locale: String) = {
      if(locale ==  "de" ) { 
        navigationDAO.find("no-SignInDE").map{
          case Some(a) => Ok(Json.toJson(a.entrys))
          case _ => BadRequest("Navigation not found")
        }
      } else {
        navigationDAO.find("no-SignInEN").map{
          case Some(a) => Ok(Json.toJson(a.entrys))
          case _ => BadRequest("Navigation not found")
        }
      }
 
  }
  def navigationGlobal(locale: String) = {
      if ( locale == "de") { 
        navigationDAO.find("GlobalNavDE").map{
          case Some(a) => Ok(Json.toJson(a.entrys))
          case _ => BadRequest("Navigation not found")
        }
      } else {
        navigationDAO.find("GlobalNavEN").map{
          case Some(a) => Ok(Json.toJson(a.entrys))
          case _ => BadRequest("Navigation not found")
        }
      }
  }
  def getGlobalNavigationAsJson(name: String, locale: String) = Action.async { implicit request =>
    Logger.debug(name)
    if( name == "default") {
      navigationNoSignIn(locale)
    } else {
      navigationGlobal(locale)
    }
  }

  /**
   */
  def globalNavigationAsJson(id: String, locale: String) = Action.async { request => 
    if (id == "default") {
      navigationDAO.find("no-SignIn").map{
        case Some(navigation) => Ok(Json.toJson(navigation.entrys))
        case _ => BadRequest("Navigation not found")
      }
    }else{
      navigationDAO.find("GlobalNav").map{
        case Some(navigation) => Ok(Json.toJson(navigation.entrys))
        case _ => BadRequest("Navigation not found")
      }
    }
  }

  def getNavigation(name: String) = Action.async { request =>
    navigationDAO.find(name).map{
      case Some(navigation) => Ok(Json.toJson(navigation.entrys))
      case None => BadRequest("Navigation " + name + " not found")
    }
  }
  def userTest = silhouette.SecuredAction.async { implicit request => {
    Future.successful(Ok("User: " + request.identity))
  }}

  private def getNavigationFromFile(name: String): JsResult[Navigation] = {
    Logger.debug(Play.application.path.toString)
    val source: String = Source.fromFile(env.getFile("/conf/navigation/jsons/" + name + ".json"))("UTF-8").getLines.mkString
    Json.parse(source).validate[Navigation]
  }
}

    

    
