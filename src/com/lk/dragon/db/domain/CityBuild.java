 /** 
 *
 * @Title: CityBuild.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-24 下午3:04:33 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

import java.util.ArrayList;
import java.util.List;


/**  
 * @Description:城市-建筑
 */
public class CityBuild {

	private Long rela_id;//关联id
	private Long city_id;//城邦id
	private Integer bulid_id;//建筑物id
	private Integer curr_lev;//当前等级
	private Integer levup;//是否正在升级 1:是 0：否
	private String lev_up_t;//升级完成时间

	private String now_db;//数据库当前时间
    private String bulidName;//建筑名
    private String bulidIcon;//建筑图标
    private Integer bulidMaxLev;//最大等级
    private Integer type;
    private Integer locate;//建造位置
    private Integer create_time;//建筑建造时间
	
    private Integer race;//种族
    
    //build_gain_tab 外城建筑对应等级产量表
    private Integer build_type;//	n	number(2)	y			建筑类型
    private Integer lev;//	n	number(2)	y			建筑等级
    private Integer val;//	n	number	y			对应增加的数值

	public CityBuild(){}



	public Integer getBuild_type() {
		return build_type;
	}






	public void setBuild_type(Integer build_type) {
		this.build_type = build_type;
	}






	public Integer getLev() {
		return lev;
	}






	public void setLev(Integer lev) {
		this.lev = lev;
	}






	public Integer getVal() {
		return val;
	}






	public void setVal(Integer val) {
		this.val = val;
	}






	public Integer getLocate() {
		return locate;
	}






	public void setLocate(Integer locate) {
		this.locate = locate;
	}






	public Integer getRace() {
		return race;
	}



	public void setRace(Integer race) {
		this.race = race;
	}



	public CityBuild(Long city_id, int bulid_id,int locate) {
		super();
		this.city_id = city_id;
		this.bulid_id = bulid_id;
		this.locate = locate;
	}


	public Integer getCreate_time() {
		return create_time;
	}



	public void setCreate_time(Integer create_time) {
		this.create_time = create_time;
	}



	public String getNow_db() {
		return now_db;
	}



	public void setNow_db(String now_db) {
		this.now_db = now_db;
	}



	public Integer getType() {
		return type;
	}



	public void setType(Integer type) {
		this.type = type;
	}



	public String getBulidName() {
		return bulidName;
	}



	public void setBulidName(String bulidName) {
		this.bulidName = bulidName;
	}



	public String getBulidIcon() {
		return bulidIcon;
	}



	public void setBulidIcon(String bulidIcon) {
		this.bulidIcon = bulidIcon;
	}



	public Integer getBulidMaxLev() {
		return bulidMaxLev;
	}



	public void setBulidMaxLev(Integer bulidMaxLev) {
		this.bulidMaxLev = bulidMaxLev;
	}



	public Long getRela_id() {
		return rela_id;
	}


	public void setRela_id(Long rela_id) {
		this.rela_id = rela_id;
	}


	public Long getCity_id() {
		return city_id;
	}


	public void setCity_id(Long city_id) {
		this.city_id = city_id;
	}


	public Integer getBulid_id() {
		return bulid_id;
	}


	public void setBulid_id(Integer bulid_id) {
		this.bulid_id = bulid_id;
	}


	public Integer getCurr_lev() {
		return curr_lev;
	}


	public void setCurr_lev(Integer curr_lev) {
		this.curr_lev = curr_lev;
	}


	public Integer getLevup() {
		return levup;
	}


	public void setLevup(Integer levup) {
		this.levup = levup;
	}


	public String getLev_up_t() {
		return lev_up_t;
	}


	public void setLev_up_t(String lev_up_t) {
		this.lev_up_t = lev_up_t;
	}
	
	
}
