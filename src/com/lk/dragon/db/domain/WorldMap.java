/**
 * Copyright ? 2014，成都乐控
 * All Rights Reserved.
 * 文件名称： WorldMap.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-12-15 下午3:32:58
 */
package com.lk.dragon.db.domain;

public class WorldMap
{
    private int map_id;
    private int site_x;
    private int site_y;
    private int item;           //坐标点的类型
    private Integer type;//坐标点子类型
    /** 世界坐标点查询的x下限坐标 **/
    private int min_x;
    /** 世界坐标点查询的x上限坐标 **/
    private int max_x;
    /** 世界坐标点查询的y下限坐标 **/
    private int min_y;
    /** 世界坐标点查询的y上限坐标 **/
    private int max_y;
    
    //坐标建筑所属
    private long ownerId;
    private String ownerName;
    
    /** 野外随机点野怪信息 **/
    private ArmsDeploy wildArms;
    
    
    
    
    
    public long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}
	public String getOwnerName() {
		return ownerName;
	}
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	public ArmsDeploy getWildArms() {
		return wildArms;
	}
	public void setWildArms(ArmsDeploy wildArms) {
		this.wildArms = wildArms;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public int getMap_id()
    {
        return map_id;
    }
    public void setMap_id(int map_id)
    {
        this.map_id = map_id;
    }
    public int getSite_x()
    {
        return site_x;
    }
    public void setSite_x(int site_x)
    {
        this.site_x = site_x;
    }
    public int getSite_y()
    {
        return site_y;
    }
    public void setSite_y(int site_y)
    {
        this.site_y = site_y;
    }
    public int getItem()
    {
        return item;
    }
    public void setItem(int item)
    {
        this.item = item;
    }
    public int getMin_x()
    {
        return min_x;
    }
    public void setMin_x(int min_x)
    {
        this.min_x = min_x;
    }
    public int getMax_x()
    {
        return max_x;
    }
    public void setMax_x(int max_x)
    {
        this.max_x = max_x;
    }
    public int getMin_y()
    {
        return min_y;
    }
    public void setMin_y(int min_y)
    {
        this.min_y = min_y;
    }
    public int getMax_y()
    {
        return max_y;
    }
    public void setMax_y(int max_y)
    {
        this.max_y = max_y;
    }
    
    
}
