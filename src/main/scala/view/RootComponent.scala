package view

import burp.api.montoya.{MontoyaApi, ui}
import burp.api.montoya
import burp.api.montoya.ui.UserInterface

import java.awt.{Color, GridBagConstraints}
import javax.swing.SpringLayout.Constraints
import javax.swing.{BoxLayout, JComponent, JPanel}
import scala.language.implicitConversions
import scala.swing.GridBagPanel.{Anchor, Fill}
import scala.swing.{Button, Component, Dimension, GridBagPanel, GridPanel, Insets, Label, Panel, SequentialContainer, TabbedPane, UIElement}

class RootComponent(val api: MontoyaApi){
  val innerContents: TabbedPane = new TabbedPane {
    pages += new TabbedPane.Page(
      "Proxy",
      new ProxyComponent(api)
    )
  }

  def getComponent: JComponent = innerContents.peer

  def toComponent(c: java.awt.Component): scala.swing.Component = {
    val panel = new JPanel()
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS))
    panel.add(c)
    Component.wrap(panel)
  }
}
