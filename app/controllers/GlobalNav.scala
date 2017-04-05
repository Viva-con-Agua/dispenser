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




class GlobalNav @Inject() (ws: WSClient) extends Controller {
  
  
  var url: 
  
  var navRequest: WSRequest = ws.url(url)
  def analyseEntry(entry: globalEntry) {
  
  }
  
  //entry Microservice into the Navigation
  def entryNav(nav: globalNav, ms: Microservice) {
    
  }
  //request for global navigation
  def requestNav(url: String) {
  
  }

}
