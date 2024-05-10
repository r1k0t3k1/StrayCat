package model

import burp.api.montoya.http.handler.HttpResponseReceived

import java.time.LocalDateTime

class ProxyLogItemModel (
  res: Option[HttpResponseReceived] = None,
  name: String = "",
  id: Int = 0,
) {
  private val response: Option[HttpResponseReceived] = res

  private val requestName: String = name
  private val host: String = response match {
    case Some(r) => r.initiatingRequest().httpService().host()
    case None => ""
  }
  private val method: String = response match {
    case Some(r) => r.initiatingRequest().method()
    case None => ""
  }
  private val path: String = response match {
    case Some(r) => r.initiatingRequest().path()
    case None => ""
  }
  private val params: Int = response match {
    case Some(r) => r.initiatingRequest().parameters().size()
    case None => 0
  }
  private val statusCode: String = response match {
    case Some(r) => r.statusCode().toString
    case None => ""
  }
  private val isTarget = false // TODO
  private val isCommit: Boolean = false // TODO
  private val isDuplicate: Boolean = false // TODO
  private val isSimilar: Boolean = false // TODO
  private val size: Int = response match {
    case Some(r) => r.body().length()
    case None => 0
  }
  private val mimeType: Any = response match {
    case Some(r) => r.mimeType()
    case None => ""
  }
  private val extension: String = response match {
    case Some(r) => r.initiatingRequest().fileExtension()
    case None => ""
  }
  private val note: String = "" // TODO
  private val isTLS: Boolean = response match {
    case Some(r) => r.initiatingRequest().httpService().secure()
    case None => false
  }
  private val cookies: Int = response match {
    case Some(r) => r.cookies().size()
    case None => 0
  }
  private val time: String = LocalDateTime.now().toString

  def requestResponse: Option[HttpResponseReceived] = response
  def getFieldValueAt(i: Int): AnyRef = {
    i match {
      case 0 => id.asInstanceOf[AnyRef]
      case 1 => requestName.asInstanceOf[AnyRef]
      case 2 => host.asInstanceOf[AnyRef]
      case 3 => method.asInstanceOf[AnyRef]
      case 4 => path.asInstanceOf[AnyRef]
      case 5 => params.asInstanceOf[AnyRef]
      case 6 => statusCode.asInstanceOf[AnyRef]
      case 7 => isTarget.asInstanceOf[AnyRef]
      case 8 => isCommit.asInstanceOf[AnyRef]
      case 9 => isDuplicate.asInstanceOf[AnyRef]
      case 10 => isSimilar.asInstanceOf[AnyRef]
      case 11 => size.asInstanceOf[AnyRef]
      case 12 => mimeType.asInstanceOf[AnyRef]
      case 13 => extension.asInstanceOf[AnyRef]
      case 14 => note.asInstanceOf[AnyRef]
      case 15 => isTLS.asInstanceOf[AnyRef]
      case 16 => cookies.asInstanceOf[AnyRef]
      case 17 => time.asInstanceOf[AnyRef]
      case _ => "Not implemented"
    }
  }
}
object ProxyLogItemModel {
  private val fields = List(
    "#",
    "Name",
    "Host",
    "Method",
    "Path",
    "Params",
    "Status",
    "Target?",
    "Commit?",
    "Dup?",
    "Sim?",
    "Size",
    "Mime",
    "ext",
    "note",
    "TLS",
    "Cookies",
    "Time"
  )
  def getFieldCount: Int = fields.length
  def getFieldName(i: Int): String = fields(i)
}