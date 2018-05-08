package com.lk.dragon.util;


public class SortTableRow implements Comparable {
	private Object[] arr;

	public int compareColumnIndex;

	public SortTableRow(int arrLength, int compareColumnIndex) {
		arr = new Object[arrLength];
		;
		this.compareColumnIndex = compareColumnIndex;
	}

	public void setArrValue(int i, Object value) {
		arr[i] = value;
	}

	public int compareTo(Object other) {
		SortTableRow another = (SortTableRow) other;

		try{			
			String op1 = (String) this.getCompareObj();
			String op2 = (String) another.getCompareObj();
			int op3=Integer.valueOf(op1).intValue();
			int op4=Integer.valueOf(op2).intValue();
			
			return op3-op4;
		}
		catch(Exception ex){
			String op1 = (String) this.getCompareObj();
			String op2 = (String) another.getCompareObj();
			return op1.compareTo(op2);
		}		
	}
	
	public Object[] getArr() {
		return arr;
	}

	public Object getCompareObj() {
		return arr[compareColumnIndex];
	}
}