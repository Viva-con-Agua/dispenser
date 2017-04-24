/**package controllers

import play.api.mvc._
import akka.actor._
import akka.pattern.ask
import akka.util.Timeout
import javax.inject._
import actors.ConfiguredActor._
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

@Singleton 

class HandleMicro @Inject() (@Name("micro-handler") configuredActor: ActorRef) (implicit ex: ExecutionContext) extends Controller {
  
  def getMicro = Action.async {
    println("test")
  }

}**/
