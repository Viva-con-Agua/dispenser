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
      case Some(navigation) => Future.successful(Ok(render.buildSimpleHtml(navigation, template.templateData)))
      case _ => Future.successful(BadRequest("Navigation not found"))
    }
  }
        

  def getTemplate = Action.async(validateJson[Template]) { request =>
    val template = request.body
    val templateName = template.metaData.templateName
    templateName match  {
      case "header" => {
        try {
          Future.successful(Ok(render.build_header_single(template)))
        }catch{
          case ex: Exception => {
            println(ex.toString)
            Future.successful(BadRequest(ex.toString))
          }
        }
      }
      case _ => Future.successful(BadRequest("Template wird nicht supported"))
    }

  }
}


