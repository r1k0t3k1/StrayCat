package model

import burp.api.montoya.http.handler.HttpResponseReceived

import javax.swing.table.AbstractTableModel

class ProxyLogTableModel extends AbstractTableModel {
  private var data = List[ProxyLogItemModel]()
  override def getColumnName(c: Int): String = ProxyLogItemModel.getFieldName(c)
  override def getRowCount: Int = data.length

  override def getColumnCount: Int = ProxyLogItemModel.getFieldCount

  override def getValueAt(rowIndex: Int, columnIndex: Int): AnyRef = data(rowIndex).getFieldValueAt(columnIndex)
  def getRequestResponseAt(rowIndex: Int): Option[HttpResponseReceived] = data(rowIndex).requestResponse
  def add(proxyLogItemModel: ProxyLogItemModel): List[ProxyLogItemModel] = {
    data = data.appended(proxyLogItemModel)
    fireTableRowsInserted(data.length, data.length)
    data
  }
}
