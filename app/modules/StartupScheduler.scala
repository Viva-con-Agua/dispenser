package modules

import play.libs.akka.AkkaGuiceSupport
import com.google.inject.AbstractModule
import akka.actor._
import actors._
class StartupScheduler extends AbstractModule with AkkaGuiceSupport {
  def configure() = {
    bindActor(classOf[SchedulerActor], "scheduler-actor")
    bindActor(classOf[MicroserviceGet], "micro-actor")
    bind(classOf[Scheduler]).asEagerSingleton()
  }
}
