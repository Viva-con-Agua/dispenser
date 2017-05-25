package controllers


import play.api._
import play.api.cache._
import play.api.mvc._
import play.api.mvc.Result
import javax.inject.{Inject, Singleton}
import com.github.tototoshi.play2.scalate._
import models._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import scala.concurrent.ExecutionContext.Implicits.global


class Templates @Inject() (
  scalate: Scalate
  ,cache: CacheApi
) extends Controller{


  def validateJson[A: Reads] = BodyParsers.parse.json.validate(_.validate[A].asEither.left.map(e => BadRequest(JsError.toJson(e))))
  
  def getTemplate = Action(validateJson[Template]) { request =>
    val template = request.body
    val templateName = template.metaData.template 
    
    //templateName match {
      //case "simpleTemplate" => 
        
    Ok(scalate.render("mustache/" + templateName +".mustache" , template.toTemplateString))
    //}
  }
  def getMenu = Action(validateJson[Menu]) { request =>
    
}
