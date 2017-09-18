package controllers

import javax.inject.Inject
import play.api.mvc._
import play.api.mvc.Action
import play.api.mvc.Results._
import play.api.mvc.BodyParsers.parse._

import play.api.i18n.{Messages, MessagesApi, I18nSupport}
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.{ExecutionContext, Future, Await}
import scala.concurrent.duration._

/*
 * --controller for admin_webinterface--
 *
 * @param index return the admin mainpage
 * 
 *
 */


class Admin @Inject() (
  val messageApi: MessagesApi
){

  def index = Action.async { implicit request =>
    Future.successful(Ok(views.html.admin.index()))
  }
/*
  def uploadMustache = Action(parse.multipartFromData) { request =>
    request.body.file("mustache").map { mustache =>
      import java.io.File
      val filename = mustache.filename
      val contentType = mustache.contentType
      mustache.ref.moveTo(new File(s".app/views/mustache/$filename"))
      OK("Mustach uploaded")
    }.getOrElse {
      Redirect(routes.Application.index).flashing(
        "error" -> "Missing file")
    }
  }*/
}
