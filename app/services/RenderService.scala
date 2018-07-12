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
  val host = config.get[String]("dispenser.hostURL")
  val context = config.get[String]("play.http.context")
  val hostURL = if(context == "/"){
    host 
  }else{
    host + context
  }
  val indexURL = config.get[String]("dispenser.indexURL")
  val imprintURL = config.get[String]("dispenser.imprintURL")

  def buildSimpleHtml (navigation: Option[Navigation], templateData: TemplateData, active: String): String = {
    val navbarContent:String = navigation match {
      case Some(nav) => build_navigation(nav, active)
      case None => "ERROR: navigation not found"
    }
    val host = templateData.head match {
      case Some(head) =>
        new String(java.util.Base64.getDecoder.decode(head), "UTF-8")
      case None =>
        ""
    }
    scalate.render("mustache/main/main_simple.mustache", Map(
      "title" -> templateData.title,
      "hostURL" -> hostURL,
      "navbarContent" -> navbarContent,
      "host" -> host,
      "imprintURL" -> imprintURL,
      "content" -> new String(java.util.Base64.getDecoder.decode(templateData.content), "UTF-8")
    )).toString
  }
  
  def buildWithNavigationWidget(templateData:TemplateData): String = {
    scalate.render("mustache/main/main_navigation_widget.mustache", Map(
      "title" -> templateData.title,
      "content" -> new String(java.util.Base64.getDecoder.decode(templateData.content), "UTF-8"),
      "footer" -> scalate.render("mustache/footer/footer.mustache", Map("imprintURL" -> imprintURL)).toString
      )).toString   
  }

  def buildErrorHtml(templateData: TemplateData): String = {
    val navbarContent: String = scalate.render("mustache/navigation/navigate_error.mustache").toString
    scalate.render("mustache/main/main_simple.mustache", Map(
      "title" -> templateData.title,
      "hostURL" -> hostURL,
      "navbarContent" -> navbarContent,
      "content" -> new String(java.util.Base64.getDecoder.decode(templateData.content), "UTF-8")
    )).toString
  }

  def build_navigation (navigationJson : Navigation, active: String) : String = {
    var navigation: String = ""
    navigationJson.entrys.foreach{ entry =>
      if (active == entry.url) {
        navigation = navigation + build_navigation_entry_active(entry) + "\n"
      }else{
        navigation = navigation + build_navigation_entry(entry) + "\n"
      }
    }
    scalate.render("mustache/navigation/navigate_content.mustache", Map(
      "navbarEntrys" -> navigation)).toString
  }
  
  def build_navigation_entry_active (entry : NavigationEntry) : String = {
    val entryUrl = host + entry.url
    scalate.render("mustache/navigation/navigate_entry_active.mustache", Map(
      "entryLable" -> entry.lable, 
      "entryURL" -> entryUrl
    )).toString
  }

  
  def build_navigation_entry (entry : NavigationEntry) : String = {
    val entryUrl = host + entry.url
    scalate.render("mustache/navigation/navigate_entry.mustache", Map(
      "entryLable" -> entry.lable, 
      "entryURL" -> entryUrl
    )).toString
  }
  
  def get_html_header (header : String, title : String) : String = {
    scalate.render("mustache/header/" + header + ".mustache", Map(
      "title" -> title, 
      "NEXT_BODY" -> ""
      )).toString
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
