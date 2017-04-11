package modules

import akka.actor._
import javax.inject._
import com.google.inject.AbstractModule
import com.google.inject.name.Names
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.concurrent.Akka
import play.api.libs.concurrent.ExecutionContextProvider
import scala.concurrent.duration._
import play.api.Play.current
import scala.concurrent.ExecutionContext
import scala.concurrent.ExecutionContext.Implicits._

import scala.concurrent.forkjoin._
// the following is equivalent to `implicit val ec = ExecutionContext.global`

class Scheduler @Inject() (val system: ActorSystem, @Named("scheduler-actor") val schedulerActor: ActorRef) (implicit ec: ExecutionContext) {
  system.scheduler.schedule(0.microseconds, 5.seconds, schedulerActor, "update")
}

