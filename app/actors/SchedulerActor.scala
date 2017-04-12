package actors
import akka.actor._
import play.api.Logger
import javax.inject._
import controllers._
import actors._
import scala.concurrent.ExecutionContext.Implicits.global
import com.google.inject.assistedinject.Assisted

class SchedulerActor @Inject() (micro: Navigation) extends Actor {
  
  //val microGet = micro.getMicro().getOrElse("None")

  def receive = {
    case "update" => updateDB()
    case "clean" => clean()
  }

  def updateDB(): Unit ={
    Logger.info("updates running")
  }

  def clean(): Unit ={
    Logger.debug("cleanup running")
  }
}
