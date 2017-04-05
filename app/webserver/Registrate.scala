import javax.inject.Inject
import scala.concurrent.Future
import scala.concurrent.duration._

import play.api.mvc._
import play.api.libs.ws._
import play.api.http.HttpEntity

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._
import akka.util.ByteString

import scala.concurrent.ExecutionContext


class Registrate @Inject() (implicit context: ExecutionContext) {
  val futureResult: Future[String] = ws.url("localhost").get().map {
    response =>
      (response.json \ "person" \ "name").as[String]
  }
}
