package controllers

import javax.inject._

import play.api._
import play.api.mvc._
import play.api.libs.json._


import com.github.tototoshi.play2.scalate._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

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
  render: RenderService
)extends AbstractController(cc) {

  def validateJson[A: Reads] = BodyParsers.parse.json.validate(_.validate[A].asEither.left.map(e => BadRequest(JsError.toJson(e))))
 
  def index = Action.async { implicit request =>
    Future.successful(Ok(views.html.navigation.index()))
  }
  
  def init = Action.async { implicit request =>
    getNavigationFromFile("noSignIn") match {
      case s: JsSuccess[Navigation] => navigationDAO.insert(s.get)
      case e: JsError => BadRequest(JsError.toJson(e).toString)
    }
    getNavigationFromFile("drops") match {
      case s: JsSuccess[Navigation] => navigationDAO.insert(s.get)
      case e: JsError => BadRequest(JsError.toJson(e).toString)
    } 
    Future.successful(Ok)
  }

  def insertNavigation = Action.async(validateJson[Navigation]) { request =>
    navigationDAO.insert(request.body)
    Future.successful(Ok)
  }
  
  
  def getNavigation(name: String) = Action.async { request =>
    navigationDAO.find(name).map{
      case Some(a) => Ok(render.build_navigation(a, ""))
      case None => BadRequest("Navigation " + name + " not found")
    }
  }

  private def getNavigationFromFile(name: String): JsResult[Navigation] = {
    val source: String = Source.fromFile("app/assets/navigations/" + name + ".json").getLines.mkString
    Json.parse(source).validate[Navigation]
  }
}

    

    
