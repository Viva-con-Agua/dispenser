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
import utils._


class NavigationController @Inject() (
  cc:ControllerComponents,
  scalate: Scalate,
  navigationDAO: NavigationDAO
)extends AbstractController(cc) {

  def validateJson[A: Reads] = BodyParsers.parse.json.validate(_.validate[A].asEither.left.map(e => BadRequest(JsError.toJson(e))))
  
  def insertNavigation = Action.async(validateJson[Navigation]) { request =>
    navigationDAO.insert(request.body)
    Future.successful(Ok)
  }
  
  def getNavigation(name: String) = Action.async { request =>
    navigationDAO.find(name).map{
      case Some(a) => Ok(build_navigation(a))
      case None => BadRequest("Navigation " + name + " not found")
    }
  }

  def build_navigation (navigationJson : Navigation) : String = {
    var navigation: String = ""
    navigationJson.entrys.foreach{ entry =>
      navigation = navigation + build_navigation_entry(entry) + "\n"
    }
    scalate.render("mustache/navigate/navigate_top.mustache", Map{"navbarContent" -> navigation}).toString
  }

  def build_navigation_entry (entry : NavigationEntry) : String = {
    scalate.render("mustache/navigate/navigate_entry.mustache", Map{"entryLable" -> entry.lable; "entryURL" -> entry.url}).toString
  }
}
    

    
