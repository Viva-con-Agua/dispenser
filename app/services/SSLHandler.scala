package server

import javax.net.ssl._
import play.core.ApplicationProvider
import play.server.api._

class SSLHandler(appProvider: ApplicationProvider) extends SSLEngineProvider {

  override def createSSLEngine(): SSLEngine = {
    // change it to your custom implementation
    SSLContext.getDefault.createSSLEngine
  }

}
