package utils

import models._
import javax.inject.Inject
import com.github.tototoshi.play2.scalate._

class RenderHtml @Inject() (
  scalate: Scalate
) {
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
  def get_html_header (header : String, title : String) : String = {
    return(scalate.render("mustache/header/" + header + ".mustache", Map{"title" -> title; "NEXT_BODY" -> ""}).toString)
  }
 /**create a html header
  *@param template Template
  *@return html_header String
  */

  def build_header_single (template : Template) : String = {
   scalate.render("mustache/header/header_single.mustache", Map("title" -> template.templateData.title, "hostURL" -> "http://localhost:4000")).toString
  }
}
