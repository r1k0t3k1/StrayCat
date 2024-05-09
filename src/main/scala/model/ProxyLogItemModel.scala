package model

import burp.api.montoya.http.handler.HttpResponseReceived

import java.time.LocalDateTime

class ProxyLogItemModel (
  val id: Int = 0,
  val requestName: String = "",
  val host: String = "",
  val method: String = "",
  val path: String = "",
  val params: Int = 0,
  val isTarget: Boolean = false,
  val isCommit: Boolean = false,
  val isDuplicate: Boolean = false,
  val isSimilar: Boolean = false,
  val size: Int = 0,
  val mimeType: String = "",
  val extension: String = "",
  val note: String = "",
  val isTLS: Boolean = false,
  val cookies: Int = 0,
  val time: String = "",
) {
  def this(httpResponse: HttpResponseReceived) = {
    this(
      id = 0,
      requestName = "Not implemented",
      host = httpResponse.initiatingRequest().httpService().host(),
      method = httpResponse.initiatingRequest().method(),
      path = httpResponse.initiatingRequest().path(),
      params = httpResponse.initiatingRequest().parameters().size(), // TODO ignore cookies
      size = httpResponse.body().length(),
      mimeType = httpResponse.mimeType().name(),
      extension = httpResponse.initiatingRequest().fileExtension(),
      isTLS = httpResponse.initiatingRequest().httpService().secure(),
      cookies = httpResponse.cookies().size(),
      time = LocalDateTime.now().toString
    )
  }

  def getFieldValueAt(i: Int): AnyRef = {
    i match {
      case 0 => id.asInstanceOf[AnyRef]
      case 1 => requestName.asInstanceOf[AnyRef]
      case 2 => host.asInstanceOf[AnyRef]
      case 3 => method.asInstanceOf[AnyRef]
      case 4 => path.asInstanceOf[AnyRef]
      case 5 => params.asInstanceOf[AnyRef]
      case 6 => isTarget.asInstanceOf[AnyRef]
      case 7 => isCommit.asInstanceOf[AnyRef]
      case 8 => isDuplicate.asInstanceOf[AnyRef]
      case 9 => isSimilar.asInstanceOf[AnyRef]
      case 10 => size.asInstanceOf[AnyRef]
      case 11 => mimeType.asInstanceOf[AnyRef]
      case 12 => extension.asInstanceOf[AnyRef]
      case 13 => note.asInstanceOf[AnyRef]
      case 14 => isTLS.asInstanceOf[AnyRef]
      case 15 => cookies.asInstanceOf[AnyRef]
      case 16 => time.asInstanceOf[AnyRef]
      case _  => "Not implemented"
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