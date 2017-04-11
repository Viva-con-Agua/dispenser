package modules
import akka.actor._
import play.api.Logger
import javax.inject._
import controller._

class SchedulerActor @Inject() (micro: Navigation) extends Actor {
  def receive = {
    case "update" => updateDB()
    case "clean" => clean()
  }

  def updateDB(): Unit ={
    micro.getMicro()
    Logger.info("updates running")
  }

  def clean(): Unit ={
    Logger.debug("cleanup running")
  }
}
