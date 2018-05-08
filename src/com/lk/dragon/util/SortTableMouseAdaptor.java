package com.lk.dragon.util;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class SortTableMouseAdaptor extends MouseAdapter{
	private JTable table;
	
	public SortTableMouseAdaptor(JTable table){
		this.table=table;
	}
	
	public void mouseClicked(MouseEvent event) { // check for double
		/*
		 * if (event.getClickCount() < 2) return;
		 */

		// find column of click and
		int tableColumn = table.columnAtPoint(event.getPoint());

		// translate to table model index and sort
		DefaultTableModel tableModel = (DefaultTableModel) table.getModel();

		int rowCount = tableModel.getRowCount();
		int cloumnCount = tableModel.getColumnCount();

		SortTableRow[] tableRowArr = new SortTableRow[rowCount];

		int j;
		for (int i = 0; i < rowCount; i++) {
			tableRowArr[i] = new SortTableRow(cloumnCount, tableColumn);
			
			for (j = 0; j < cloumnCount; j++) {
				tableRowArr[i].setArrValue(j, tableModel.getValueAt(i,j));
			}
		}
		
		SortCompareColum compareColum=new SortCompareColum(rowCount);
		for (int i = 0; i < rowCount; i++) {
			compareColum.setArrValue(i, tableModel.getValueAt(i,tableColumn));
		}
		
		tableModel.setRowCount(0);
		Arrays.sort(tableRowArr);
		
		if(compareColum.isSorted()){
			List ls=Arrays.asList(tableRowArr);
			Collections.reverse(ls);
			
			for (int i = 0; i < ls.size(); i++) {
				SortTableRow tableRow=(SortTableRow)ls.get(i);
				
				tableModel.addRow(tableRow.getArr());
			}
		}
		else{				
			for (int i = 0; i < tableRowArr.length; i++) {
				tableModel.addRow(tableRowArr[i].getArr());
			}
		}

		
		table.invalidate();
	}
}