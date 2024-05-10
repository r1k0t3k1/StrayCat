package controller

import burp.api.montoya.MontoyaApi
import burp.api.montoya.http.handler.{HttpHandler, HttpRequestToBeSent, HttpResponseReceived, RequestToBeSentAction, ResponseReceivedAction}
import burp.api.montoya.ui.editor.{HttpRequestEditor, HttpResponseEditor}
import model.{ProxyLogItemModel, ProxyLogTableModel}
import view.ProxyView

import javax.swing.DefaultComboBoxModel

class ProxyController(
  private val api: MontoyaApi,
) extends HttpHandler {

  val proxyLogTableModel: ProxyLogTableModel = new ProxyLogTableModel
  var currentRequestName: String = "" // TODO
  var requestNameHistory: DefaultComboBoxModel[String] = new DefaultComboBoxModel[String]// TODO
  def appendRequestNameHistory(name: String): Unit = requestNameHistory.addElement(name)

  var isProxying: Boolean = false
  def toggleProxying(): Boolean = {
    isProxying = !isProxying
    isProxying
  }

  def createHttpRequestEditor: HttpRequestEditor = api.userInterface().createHttpRequestEditor()
  def createHttpResponseEditor: HttpResponseEditor = api.userInterface().createHttpResponseEditor()
  def getFrameCenter: Int = api.userInterface().swingUtils().suiteFrame().getWidth / 2
  override def handleHttpRequestToBeSent(requestToBeSent: HttpRequestToBeSent): RequestToBeSentAction = RequestToBeSentAction.continueWith(requestToBeSent)

  def logging(s: String): Unit = api.logging().logToOutput(s)
  override def handleHttpResponseReceived(responseReceived: HttpResponseReceived): ResponseReceivedAction = {
    if (isProxying) {
      proxyLogTableModel.add(new ProxyLogItemModel(Some(responseReceived), currentRequestName, proxyLogTableModel.getRowCount + 1))
    }
    ResponseReceivedAction.continueWith(responseReceived)
  }
}
