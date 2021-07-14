//alice aidlin 206448326
//maayan nadivi 208207068
package UI;

import javax.swing.table.AbstractTableModel;

class TableA extends AbstractTableModel {
	
	private String[] col_names ;
	private boolean[][] data;
	public TableA(boolean[][] data, String[] col_names) {
		this.data = data; 
		this.col_names=col_names;}
	
	@Override
	public int getRowCount() { return data.length; }
	
	@Override
	public int getColumnCount() { return col_names.length; }
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data[rowIndex][columnIndex];
	}
	
	public String getColumnName(int column) { return col_names[column]; }
	public String getRowName(int column) { return col_names[column]; }

	public Class getColumnClass(int column) { return getValueAt(0, column).getClass(); }
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
		}
	@Override
	public void setValueAt(Object aValue, int row, int col) {
		if (aValue instanceof Boolean)
			data[row][col]= (boolean) aValue;
		fireTableCellUpdated(row, col);
	}

	public boolean[][] getData() {
		return data;

	}
}
