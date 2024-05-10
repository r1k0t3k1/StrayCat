package controller

import burp.api.montoya.MontoyaApi
import model.ProxyLogTableModel
import view.ProxyView

import scala.swing.Component

class RootController(val api: MontoyaApi) {

  val proxyController = new ProxyController(api)

  api.http().registerHttpHandler(proxyController)
  def getProxyView: Component = new ProxyView(proxyController)
}
