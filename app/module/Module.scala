package module

import net.ceedubs.ficus.Ficus._
import net.ceedubs.ficus.readers.ArbitraryTypeReader._
import net.codingwell.scalaguice.ScalaModule
import com.google.inject.{AbstractModule, Provides}
import play.api.Configuration
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.mailer.MailerClient
import play.api.libs.ws.WSClient
import daos._
/*
class Module extends AbstractModule with ScalaModule {

  def configure() {
    bind[NavigationDAO].to[NavigationEntryDAO]
  }
}*/
