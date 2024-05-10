import swing._
import java.awt.Component
import burp.api.montoya.BurpExtension
import burp.api.montoya.MontoyaApi
import controller.RootController
import view.RootView

class StrayCat extends BurpExtension {
  override def initialize(api: MontoyaApi): Unit = {
    val rootController = new RootController(api)
    val c = new RootView(rootController)
    api.userInterface().registerSuiteTab("StrayCat", c.getComponent)
  }
}