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
import play.utils.UriEncoding

import models._

class RegistMS @Inject() (ws: WSClienti,
                          msDAO: MicroserviceDAO
                        ) extends Controller {
  
  def registrate(name: String, url: String, version: String) = Action.async {
    val auth = MsAuthInfo("Test")
    val msData = msDAO.insert(MicroserviceData(name, url, version, auth, None))
  }  
}
