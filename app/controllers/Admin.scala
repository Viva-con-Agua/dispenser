package controllers

import javax.inject.Inject
import play.api.mvc._
import play.api.mvc.Action
import play.api.mvc.Results._


import play.api.i18n.{Messages, MessagesApi, I18nSupport}
import play.api.libs.concurrent.Execution.Implicits.defaultContext

import scala.concurrent.{ExecutionContext, Future, Await}
import scala.concurrent.duration._

class Admin @Inject() (
  val messageApi: MessagesApi
){

  def index = Action.async { implicit request =>
    Future.successful(Ok(views.html.admin.index()))
  }
}
