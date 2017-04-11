package modules

import play.libs.akka.AkkaGuiceSupport
import com.google.inject.AbstractModule
import akka.actor._
class StartupScheduler extends AbstractModule with AkkaGuiceSupport {
  def configure() = {
    bindActor(classOf[SchedulerActor], "scheduler-actor")
    bind(classOf[Scheduler]).asEagerSingleton()
  }
}
