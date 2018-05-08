 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: Tools.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2015-4-28 上午11:48:31 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

/**  
 * @Description:系统工具Domain
 */
public class Tools {

	
	//-------------OPERAT_LOG_TAB操作日志表-----
	private Long role_id;//	n	number(18)	n			操作人id
	private String model_name;//	n	varchar2(20)	y			操作模块
	private String operat_detail;//	n	varchar2(1500)	y			详细操作记录
	private String log_time;//	n	varchar2(20)	y			操作时间
	private String partitionkey;//	n	char(2)	n			分区键
	private Integer operat_res;//	n	number(1)	y			操作结果 0:失败 1成功
	
	public Tools() {
	}

	
	public Tools(Long role_id, String model_name, String operat_detail,
			Integer operat_res) {
		this.role_id = role_id;
		this.model_name = model_name;
		this.operat_detail = operat_detail;
		this.operat_res = operat_res;
	}


	public Long getRole_id() {
		return role_id;
	}

	public void setRole_id(Long role_id) {
		this.role_id = role_id;
	}

	public String getModel_name() {
		return model_name;
	}

	public void setModel_name(String model_name) {
		this.model_name = model_name;
	}

	public String getOperat_detail() {
		return operat_detail;
	}

	public void setOperat_detail(String operat_detail) {
		this.operat_detail = operat_detail;
	}

	public String getLog_time() {
		return log_time;
	}

	public void setLog_time(String log_time) {
		this.log_time = log_time;
	}

	public String getPartitionkey() {
		return partitionkey;
	}

	public void setPartitionkey(String partitionkey) {
		this.partitionkey = partitionkey;
	}

	public Integer getOperat_res() {
		return operat_res;
	}

	public void setOperat_res(Integer operat_res) {
		this.operat_res = operat_res;
	}
	
	
}
