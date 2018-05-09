/**
 *
 *
 * 文件名称： MallDomain.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-8-26 上午11:44:48
 */
package com.lk.dragon.server.domain;

import io.netty.channel.ChannelHandlerContext;


public class MallDomain
{
    /** 连接 **/
    private ChannelHandlerContext ctx;
    /** 商品名称 **/
    private String goodsName;
    /** 商品类型 **/
    private Integer goodsType;
    /** 角色id **/
    private long roleId;
    /**购买 数量 **/
    private int buyNum;
    /** 道具id **/
    private int propId;
    /** 消耗金币数量 **/
    private int goldNum;
    /** 消耗点券数量 **/
    private int dimondNum;
    /** 商城操作请求类型（商品列表查询、商品购买请求） **/
    private int type;
    
    //资源回收部分
    private int foodNum;
    private int woodNum;
    private int stoneNum;
    
    
    public ChannelHandlerContext getCtx() {
		return ctx;
	}
	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}
	public int getFoodNum() {
		return foodNum;
	}
	public void setFoodNum(int foodNum) {
		this.foodNum = foodNum;
	}
	public int getWoodNum() {
		return woodNum;
	}
	public void setWoodNum(int woodNum) {
		this.woodNum = woodNum;
	}
	public int getStoneNum() {
		return stoneNum;
	}
	public void setStoneNum(int stoneNum) {
		this.stoneNum = stoneNum;
	}
	public int getType()
    {
        return type;
    }
    public void setType(int type)
    {
        this.type = type;
    }
    public String getGoodsName()
    {
        return goodsName;
    }
    public void setGoodsName(String goodsName)
    {
        this.goodsName = goodsName;
    }
    public Integer getGoodsType()
    {
        return goodsType;
    }
    public void setGoodsType(Integer goodsType)
    {
        this.goodsType = goodsType;
    }
    public long getRoleId()
    {
        return roleId;
    }
    public void setRoleId(long roleId)
    {
        this.roleId = roleId;
    }
    public int getBuyNum()
    {
        return buyNum;
    }
    public void setBuyNum(int buyNum)
    {
        this.buyNum = buyNum;
    }
    public int getPropId()
    {
        return propId;
    }
    public void setPropId(int propId)
    {
        this.propId = propId;
    }
    public int getGoldNum()
    {
        return goldNum;
    }
    public void setGoldNum(int goldNum)
    {
        this.goldNum = goldNum;
    }
    public int getDimondNum()
    {
        return dimondNum;
    }
    public void setDimondNum(int dimondNum)
    {
        this.dimondNum = dimondNum;
    }
    
}
