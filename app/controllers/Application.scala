import play.api.i18n.{MessagesApi, Messages, I18nSupport}
import play.api.i18n.Messages.Implicits._

import play.api.libs.json._
import play.api.routing.JavaScriptReverseRouter

import scala.collection.JavaConversions._

import scala.concurrent.Future
import com.typesafe.config.ConfigFactory



class Application @Inject() (
  val messageApi: MessageApi
  ) extends I18nSupport {
  
  def index = SecuredAction.async { implicit request =>
    Future.successful(Redirect(routes.Projects.index()))
  }

  def javascriptRoutes = Action { implicit request =>
    Ok(
      JavaScriptReverseRouter("jsRoutes")(
       // routes.javascript.
      )
    ).as("text/javascript")
  }


  def getMessages() = Action {                                                                                                     
    request => {
      val langs = conf.getStringList("play.i18n.langs")
      val msg = langs.foldLeft[Map[String, Map[String, String]]](Map())((collection, lang) => {                          
      val lines = scala.io.Source.fromFile(play.Play.application().resource("messages." + lang).toURI()).getLines()              
        collection ++ Map(
          lang -> lines.foldLeft[Map[String, String]](Map())(
            (messages, line) => if(line.contains("="))
              messages ++ Map((line.split("=")(0) -> line.split("=")(1)))
            else
              messages
          )
        )
      })
                                
      val acceptedLangs = request.acceptLanguages.map( _.code )
      val defaultLang = acceptedLangs.find(langs.contains(_)).getOrElse(conf.getString("langsDefault"))
      val i18n = Json.obj(
        "default" -> defaultLang,
        "acceptedLanguages" -> Json.toJson(acceptedLangs),
        "translations" -> Json.toJson(msg)
      )
      Ok("var i18n = " + i18n).as("text/javascript")
    }
  }


}
