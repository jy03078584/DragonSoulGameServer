package com.lk.dragon.util;

public class SortCompareColum{
	private Object[] arr;
	
	public SortCompareColum(int arrLength){
		arr = new Object[arrLength];
	}
	
	public void setArrValue(int i, Object value) {
		arr[i] = value;
	}
	
	public boolean isSorted(){
		boolean retval=true;
		
		for(int i=0;i<arr.length-1;i++){
			
			try{
				int op1 =Integer.valueOf((String) arr[i]).intValue();
				int op2 = Integer.valueOf((String) arr[i+1]).intValue();
				
				retval=retval && (op1-op2>0?false:true);
			}
			catch(Exception e){
				String op1 = (String) arr[i];
				String op2 = (String) arr[i+1];
				
				retval=retval && (op1.compareTo(op2)>0?false:true);
			}
		}
		
		return retval;
	}
}