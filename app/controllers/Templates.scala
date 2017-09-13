package controllers



import play.api.Play.current
import play.Environment
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

/** Controller for Templates
 *
 */

class Templates @Inject() (
  scalate: Scalate
  ,cache: CacheApi
  ,implicit val env: Environment
) extends Controller{

  
  /** checks whether a json is validate or not
   *
   *  @param A Reads json datatype and request. 
   *  @return if the json in the request.body is  successful, return JsSuccess Type, else return BadRequest with json errors  
   */
  def validateJson[A: Reads] = BodyParsers.parse.json.validate(_.validate[A].asEither.left.map(e => BadRequest(JsError.toJson(e))))
  
  /**create html over mustache templates
   *
   *@param Template template json 
   * 
   * fill mustache via scalate 
   * mustache file is named [templateName + . mustache ]
   * use @function toTemplateString for fill the mustache vars
   * @param templateName name of the wish html 
   * 
   *@return html or 404 
   */
  def getTemplate = Action(validateJson[Template]) { request =>
    val template = request.body
    val templateName = template.metaData.template
    templateName match  {
      case "navigation_top" => {
        val head = scalate.render("mustache/header/header_single.mustache", template.toTemplateString).toString
        val body = scalate.render("mustache/navigate/navigate_top.mustache", template.toTemplateString).toString
        Ok(scalate.render("mustache/main.mustache", Map("head" -> head,
                                                    "body" -> body
                                                         )))
      }
      case _ => BadRequest("Template wird nicht supported")
    }
  }

  
  def get_html_header (header : String, title : String) : String = {
    return(scalate.render("mustache/header/" + header + ".mustache", Map{"title" -> title; "NEXT_BODY" -> ""}).toString)
  }
    
}
