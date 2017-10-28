package controllers

import java.io.IOException

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
      case "navigation_top_full" => {
        try {
          Ok(build_navigation_top_full(template))
        }catch{
          case ex: Exception => {
            BadRequest(ex.toString)
          }
        }
      }
      case "navigation_top" => {
        try {
          Ok(build_navigation_top(template))
        }catch{
          case ex: Exception => {
            BadRequest(ex.toString)
          }
        }
      }
      case "header" => {
        try {
          Ok(build_header_single(template))
        }catch{
          case ex: Exception => {
            println(ex.toString)
            BadRequest(ex.toString)
          }
        }
      }
      case _ => BadRequest("Template wird nicht supported")
    }
  }



  def get_html_header (header : String, title : String) : String = {
    return(scalate.render("mustache/header/" + header + ".mustache", Map{"title" -> title; "NEXT_BODY" -> ""}).toString)
  }
 /**create a html header
  *@param template Template
  *@return html_header String
  */
  def build_header_single (template : Template) : String = {
    val body = scalate.render("mustache/header/header_single.mustache", Map("title" -> template.templateData.title, "hostURL" -> "http://localhost:4000")).toString
    return(body)
  }
  /** create a navigation with full header
   *  navigationData need to be set, else return a BadRequestd
   *  @param template Template
   *  @return html_page String
   */

  def build_navigation_top_full (template : Template) : String = {
    var navigation = ""
    val head = scalate.render("mustache/header/header_single.mustache", template.toTemplateString).toString
        template.navigationData match {
          case Some(nav) => navigation = build_navigation_content(nav)
          case None => throw new invalidNavigationException("no Navigation set")
        }
        val body = scalate.render("mustache/navigate/navigate_top.mustache", Map(
          "navbarContent" -> navigation,
          "hostURL" -> "http//localhost:4000")).toString
        return(scalate.render("mustache/main_full.mustache", Map("HEAD" -> head,
                                                    "BODY" -> body
                                                         )).toString)
      }
  /** create a navigation for the top of a page
   *  navigationData need to be set, else return BadRequest
   *  @param template Template
   *  @return html_navigation String
   */
  def build_navigation_top (template : Template) : String = {
    var navigation = ""
        template.navigationData match {
          case Some(nav) => navigation = build_navigation_content(nav)
          case None => throw new invalidNavigationException("no Navigation set")
        }
        return(scalate.render("mustache/navigate/navigate_top.mustache", Map(
          "navbarContent" -> navigation,
          "hostURL" -> "http//0.0.0.0:4000")).toString)
  }
  /**create a navigation content over the navigationData entrys
   * @param navigationData Navigation
   * @return navigation_Entrys String sorted by default sequence
   */
  def build_navigation_content (navigationData : Navigation) : String = {
    var navigation = ""
    navigationData.navigation_Entrys.foreach((nav: NavigationEntry) => { 
      val nav_entry = scalate.render("mustache/navigate/navigate_entry.mustache", nav.toStringMap).toString
      navigation = navigation + nav_entry + "\n"
    })
    return(scalate.render("mustache/navigate/navigate_content.mustache", Map("navbarEntrys" -> navigation)).toString)
  }

    
}
