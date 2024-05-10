package view

import burp.api.montoya.MontoyaApi
import burp.api.montoya.http.handler.{HttpHandler, HttpRequestToBeSent, HttpResponseReceived, RequestToBeSentAction, ResponseReceivedAction}
import model.{ProxyLogItemModel, ProxyLogTableModel}

import java.awt.{Color, Rectangle}
import javax.swing.{BoxLayout, JPanel}
import javax.swing.border.EmptyBorder
import scala.swing.GridBagPanel.Fill
import scala.swing.Table.AutoResizeMode
import scala.swing.event.{ButtonClicked, KeyPressed, ListSelectionChanged, MouseClicked, SelectionChanged, TableColumnsSelected, TableRowsSelected}
import scala.swing.{BoxPanel, Button, ComboBox, Component, FlowPanel, GridBagPanel, Label, Orientation, Panel, ScrollPane, SplitPane, Table, TextField}

class ProxyComponent(
  val api: MontoyaApi
) extends GridBagPanel with HttpHandler {
  // パネルの外側マージン
  border = new EmptyBorder(10,10,10,10)

  var isProxying: Boolean = false

  val c00 = pair2Constraints(0,0)
  c00.fill = Fill.Both
  c00.weightx = 1
  c00.weighty = 0.05
  layout += new BoxPanel(Orientation.Horizontal) {
    border = new EmptyBorder(5,5,5,5)
    contents += new Label("Request name: ")
    contents += new ComboBox[String](List()) { makeEditable() }
    contents += new Button("Through...") {
      foreground = Color.BLACK
      background = Color.GREEN
      reactions += {
        case ButtonClicked(source) => {
          if (isProxying) {
            foreground = Color.BLACK
            background = Color.GREEN
            text = "Through..."
          } else {
            foreground = Color.WHITE
            background = Color.RED
            text = "Proxying!!"
          }
          isProxying = !isProxying
        }
      }
    }
  } -> c00

  var proxyLogTableModel = new ProxyLogTableModel
  var requestEditor = api.userInterface().createHttpRequestEditor()
  var responseEditor = api.userInterface().createHttpResponseEditor()

  val c01 = pair2Constraints(0,1)
  c01.fill = Fill.Both
  c01.weightx = 1
  c01.weighty = 1
  layout += new SplitPane(Orientation.Horizontal) {
    topComponent = new ScrollPane(new Table(proxyLogTableModel){
      autoResizeMode = AutoResizeMode.Off
      listenTo(selection)
      reactions += {
        case TableRowsSelected(s, r, a) => {
          proxyLogTableModel.getRequestResponseAt(s.selection.rows.head) match {
            case Some(res) => {
              responseEditor.setResponse(res)
              requestEditor.setRequest(res.initiatingRequest())
            }
            case None    => {}
          }
        }
      }
    })
    bottomComponent = new SplitPane(Orientation.Vertical) {
      dividerLocation = api.userInterface().swingUtils().suiteFrame().getWidth / 2
      leftComponent = Component.wrap(new JPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS))
        add(requestEditor.uiComponent())
      })
      rightComponent = Component.wrap(new JPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS))
        add(responseEditor.uiComponent())
      })
    }
  } -> c01

  api.http().registerHttpHandler(this)

  override def handleHttpRequestToBeSent(requestToBeSent: HttpRequestToBeSent): RequestToBeSentAction = RequestToBeSentAction.continueWith(requestToBeSent)

  override def handleHttpResponseReceived(responseReceived: HttpResponseReceived): ResponseReceivedAction = {
    if (isProxying) {
      // TODO
      proxyLogTableModel.add(new ProxyLogItemModel(Some(responseReceived), "test name", proxyLogTableModel.getRowCount + 1))
    }
    ResponseReceivedAction.continueWith(responseReceived)
  }
}

