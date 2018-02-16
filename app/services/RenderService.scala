package services

import java.util.Base64
import models._
import play.api.Configuration
import javax.inject.Inject
import com.github.tototoshi.play2.scalate._

class RenderService @Inject() (
  config: Configuration,
  scalate: Scalate
) {
  val hostURL = config.get[String]("dispenser.hostURL")

  def buildSimpleHtml (navigation: Navigation, templateData: TemplateData): String = {
    val navbarContent:String = build_navigation(navigation)

    scalate.render("mustache/simple/main.mustache", Map{
      "title" -> templateData.title;
      "hostURL" -> hostURL;
      "navbarContent" -> navbarContent;
      "content" -> new String(java.util.Base64.getDecoder.decode(templateData.content), "UTF-8")
    }).toString
  }

  
  def build_navigation (navigationJson : Navigation) : String = {
    var navigation: String = ""
    navigationJson.entrys.foreach{ entry =>
      navigation = navigation + build_navigation_entry(entry) + "\n"
    }
    scalate.render("mustache/navigate/navigate_top.mustache", Map{
      "navbarContent" -> navigation
    }).toString
  }

  def build_navigation_entry (entry : NavigationEntry) : String = {
    scalate.render("mustache/navigate/navigate_entry.mustache", Map{
      "entryLable" -> entry.lable; 
      "entryURL" -> entry.url
    }).toString
  }
  
  def get_html_header (header : String, title : String) : String = {
    scalate.render("mustache/header/" + header + ".mustache", Map{
      "title" -> title; 
      "NEXT_BODY" -> ""
    }).toString
  }
 /**create a html header
  *@param template Template
  *@return html_header String
  */

  def build_header_single (template : Template) : String = {
    scalate.render("mustache/header/header_single.mustache", Map(
      "title" -> template.templateData.title, 
      "hostURL" -> hostURL)
    ).toString
  }
}
