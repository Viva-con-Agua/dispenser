package modules

/**import play.libs.akka.AkkaGuiceSupport
import com.google.inject.AbstractModule
import akka.actor._
import actors._**/

/**
 * ActionBindings Class bind the Actors for Guice-Support
 *
 **/

/**
class ActorBindings extends AbstractModule with AkkaGuiceSupport {
  def configure() = {
    bindActor(classOf[MicroGet], "micro-get")

    //bind the Scheduler and Initial at startup via asEagerSingleton
    bind(classOf[Scheduler]).asEagerSingleton()
  }
}**/
