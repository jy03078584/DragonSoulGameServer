 /** 
 *
 * @Title: Map.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-24 下午6:00:33 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

/**  
 * @Description:世界地图表映射
 */
public class LkMap {
	private Long map_id;//地图元素id
	private Integer site_x;//x坐标
	private Integer site_y;//y坐标
	private Integer item;//地图元素,0：平原  1:城镇
	private Integer type;//详细类型
	public LkMap(){}

	
	
	public LkMap(Integer site_x, Integer site_y) {
		super();
		this.site_x = site_x;
		this.site_y = site_y;
	}



	public LkMap(Integer site_x, Integer site_y, Integer item,Integer type) {
		super();
		
		this.site_x = site_x;
		this.site_y = site_y;
		this.item = item;
		this.type = type;
	}



	public Integer getType() {
		return type;
	}



	public void setType(Integer type) {
		this.type = type;
	}



	public Long getMap_id() {
		return map_id;
	}

	public void setMap_id(Long map_id) {
		this.map_id = map_id;
	}

	public Integer getSite_x() {
		return site_x;
	}

	public void setSite_x(Integer site_x) {
		this.site_x = site_x;
	}

	public Integer getSite_y() {
		return site_y;
	}

	public void setSite_y(Integer site_y) {
		this.site_y = site_y;
	}

	public Integer getItem() {
		return item;
	}

	public void setItem(Integer item) {
		this.item = item;
	}
	
	
	

}
