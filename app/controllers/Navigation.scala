package controllers

import play.api.cache._

import play.api.mvc._

import javax.inject.Inject

import play.api.libs.json._

import play.api.Logger
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.libs.ws._
import play.api.http.HttpEntity

import akka.actor._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import akka.util.ByteString
import models._
import scala.concurrent.ExecutionContext
import play.api.libs.functional.syntax._
import scala.concurrent.duration._

import daos.{MicroserviceDAO}
import com.google.inject.ImplementedBy
/*
@ImplementedBy(classOf[Navi])
trait Navigation extends Actor {

  def getMicro(): Unit
}
*/
class Navigation @Inject() (
  cache: CacheApi,
  ws: WSClient,
  microDAO: MicroserviceDAO
) extends Controller {
  def getMicro() = Action.async {
    Logger.info("WHAAT")
    ws.url("http://localhost:8800/micro").get().map {
      response => ( response.json \ "microservices").validate[List[MicroStub]] match {
        case s: JsSuccess[List[MicroStub]] => s.get.foreach((stub: MicroStub) => microDAO.save(stub.toMicro))
        case e: JsError => Logger.info("not validate Json")
      }
      Results.Ok("The response code was" + response.status)
    }
  }
  
  
}
