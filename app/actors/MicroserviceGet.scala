package actors

import play.api.mvc._
import javax.inject.Inject
import play.api.libs.ws._
import play.api.http.HttpEntity
import play.api.Logger
import akka.actor._
import com.google.inject.name.Named


/*trait MicroserviceGet extends Actor {
  def props = Props[MicroserviceGet]
  case class getMicro()
}*/

class MicroserviceGet @Inject() (ws: WSClient) extends Actor {  
  def receive = {
    case "micro-get" => getMicro()
  }

  def getMicro()  ={
    val req = ws.url("http://localhost:8800/micro")
    Logger.info("req")
  }
}
