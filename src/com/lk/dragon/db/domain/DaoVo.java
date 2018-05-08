 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: DaoVo.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2015-7-9 下午5:00:52 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

/**  
 * @Description:
 */
public class DaoVo {
	private Object daoObj;
	private String methodName;
	private Object paramObj;
	
	private String ibatisSqlId;
	private int ibatisSqlType;//1:insert 2:update 3:delete 4:queryObject
	public DaoVo() {
		super();
	}
	public DaoVo(Object daoObj, String methodName, Object paramObj) {
		super();
		this.daoObj = daoObj;
		this.methodName = methodName;
		this.paramObj = paramObj;
	}
	
	public DaoVo(String ibatisSqlId, Object paramObj, int ibatisSqlType) {
		super();
		this.ibatisSqlId = ibatisSqlId;
		this.paramObj = paramObj;
		this.ibatisSqlType = ibatisSqlType;
	}
	public int getIbatisSqlType() {
		return ibatisSqlType;
	}
	public void setIbatisSqlType(int ibatisSqlType) {
		this.ibatisSqlType = ibatisSqlType;
	}
	public String getIbatisSqlId() {
		return ibatisSqlId;
	}
	public void setIbatisSqlId(String ibatisSqlId) {
		this.ibatisSqlId = ibatisSqlId;
	}
	public Object getDaoObj() {
		return daoObj;
	}
	public void setDaoObj(Object daoObj) {
		this.daoObj = daoObj;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public Object getParamObj() {
		return paramObj;
	}
	public void setParamObj(Object paramObj) {
		this.paramObj = paramObj;
	}
	
	
	
}
