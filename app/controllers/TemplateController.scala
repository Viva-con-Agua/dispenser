package controllers

import javax.inject._

import play.api._
import play.api.mvc._
import play.api.libs.json._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import com.github.tototoshi.play2.scalate._
import models.JsonFormatsTemplate._
import models._
import daos._
import services.RenderService


class TemplateController @Inject() (
  cc: ControllerComponents,
  render: RenderService,
  navigationDAO: NavigationDAO
)extends AbstractController(cc) {
  
  def validateJson[A: Reads] = BodyParsers.parse.json.validate(_.validate[A].asEither.left.map(e => BadRequest(JsError.toJson(e))))

  def getSimpleTemplate = Action.async(validateJson[Template]) { request =>
    val template = request.body
    navigationDAO.find(template.navigationData.navigationName).flatMap {
      case Some(navigation) => Future.successful(Ok(render.buildSimpleHtml(navigation, template.templateData, template.navigationData.active)))
      case _ => Future.successful(BadRequest("Navigation not found"))
    }
  }
  
  def getErrorTemplate = Action.async(validateJson[TemplateData]) { request =>
    val templateData = request.body
    Future.successful(Ok(render.buildErrorHtml(templateData)))
  }

  def getTemplate = Action.async(validateJson[Template]) { request =>
    val template = request.body
    val path = template.navigationData.active
    Logger.debug(path)
    navigationDAO.findByPath(path).flatMap {
      case Some(navigation) => Future.successful(Ok(render.buildSimpleHtml(navigation, template.templateData, template.navigationData.active)))
      case _ => Future.successful(BadRequest("Navigation not found"))
    }
  }
}


