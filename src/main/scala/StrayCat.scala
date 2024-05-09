import swing._
import java.awt.Component
import burp.api.montoya.BurpExtension
import burp.api.montoya.MontoyaApi
import view.RootComponent

class StrayCat extends BurpExtension {
  override def initialize(api: MontoyaApi): Unit = {
    val c = new RootComponent(api)
    api.userInterface().registerSuiteTab("StrayCat", c.getComponent)
  }
}