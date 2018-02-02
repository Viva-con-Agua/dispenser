package controllers

import javax.inject._

import play.api._
import play.api.mvc._
import play.api.libs.json._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import com.github.tototoshi.play2.scalate._
import models.JsonFormatsNavigation._
import daos._
import models._
import utils._


class NavigationController @Inject() (
  cc:ControllerComponents,
  //render: RenderHtml,
  navigationDAO: NavigationDAO
)extends AbstractController(cc) {

  def validateJson[A: Reads] = BodyParsers.parse.json.validate(_.validate[A].asEither.left.map(e => BadRequest(JsError.toJson(e))))
  
  def insert = Action.async(validateJson[Navigation]) { request =>
    navigationDAO.insert(request.body)
    Future.successful(Ok)
  }
}
    

    
