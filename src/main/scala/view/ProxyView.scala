package view

import burp.api.montoya.ui.editor.{HttpRequestEditor, HttpResponseEditor}
import controller.ProxyController

import java.awt.Color
import javax.swing.border.EmptyBorder
import javax.swing.event.{DocumentEvent, DocumentListener}
import javax.swing.{BoxLayout, DefaultComboBoxModel, JPanel, JTextField}
import scala.swing.GridBagPanel.Fill
import scala.swing.Table.AutoResizeMode
import scala.swing.event.{ButtonClicked, Key, KeyPressed, KeyTyped, TableRowsSelected}
import scala.swing._

class ProxyView(
  val proxyController: ProxyController
) extends GridBagPanel {
  // パネルの外側マージン
  border = new EmptyBorder(10,10,10,10)

  private var requestNameComboBox = new ComboBox[String](List.empty) { makeEditable() }
  requestNameComboBox.peer.getEditor.getEditorComponent.asInstanceOf[JTextField].getDocument.addDocumentListener(new DocumentListener {
    override def insertUpdate(e: DocumentEvent): Unit = update(e)

    override def removeUpdate(e: DocumentEvent): Unit = {}

    override def changedUpdate(e: DocumentEvent): Unit = {}

    def update(e: DocumentEvent): Unit = {
      if (e.getDocument.getLength <= 0) return
      val text = e.getDocument.getText(0, e.getDocument.getLength)
      proxyController.currentRequestName = text
      text.lastOption match {
        case Some(c) => if (c == '　') {
          proxyController.appendRequestNameHistory(text)
          requestNameComboBox.peer.setModel(proxyController.requestNameHistory)
        }
        case None       => {}
      }
    }
  })

  val c00 = pair2Constraints(0,0)
  c00.fill = Fill.Both
  c00.weightx = 1
  c00.weighty = 0.05
  layout += new BoxPanel(Orientation.Horizontal) {
    border = new EmptyBorder(5,5,5,5)
    contents += new Label("Request name: ")
    contents += requestNameComboBox
    contents += new Button("Through...") {
      foreground = Color.BLACK
      background = Color.GREEN
      reactions += {
        case ButtonClicked(_) => {
          if (proxyController.isProxying) {
            foreground = Color.BLACK
            background = Color.GREEN
            text = "Through..."
          } else {
            foreground = Color.WHITE
            background = Color.RED
            text = "Proxying!!"
          }
          proxyController.toggleProxying()
        }
      }
    }
  } -> c00

  var requestEditor: HttpRequestEditor = proxyController.createHttpRequestEditor
  var responseEditor: HttpResponseEditor = proxyController.createHttpResponseEditor

  val c01 = pair2Constraints(0,1)
  c01.fill = Fill.Both
  c01.weightx = 1
  c01.weighty = 1
  layout += new SplitPane(Orientation.Horizontal) {
    topComponent = new ScrollPane(new Table(proxyController.proxyLogTableModel){
      autoResizeMode = AutoResizeMode.Off
      listenTo(selection)
      reactions += {
        case TableRowsSelected(s, r, a) => {
          proxyController.proxyLogTableModel.getRequestResponseAt(s.selection.rows.head) match {
            case Some(res) => {
              responseEditor.setResponse(res)
              requestEditor.setRequest(res.initiatingRequest())
            }
            case None => {}
          }
        }
      }
    })
    bottomComponent = new SplitPane(Orientation.Vertical) {
      dividerLocation = proxyController.getFrameCenter
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
}

