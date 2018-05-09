/**
 *
 *
 * 文件名称： IncDomain.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-10-15 下午6:45:38
 */
package com.lk.dragon.server.domain;

public class IncDomain
{
    /** 属性类型 **/
    private int incType;
    /** 属性值 **/
    private int value;
    
    
    
    public IncDomain() {
		super();
		// TODO Auto-generated constructor stub
	}
	public IncDomain(int incType, int value) {
		super();
		this.incType = incType;
		this.value = value;
	}
	public int getIncType()
    {
        return incType;
    }
    public void setIncType(int incType)
    {
        this.incType = incType;
    }
    public int getValue()
    {
        return value;
    }
    public void setValue(int value)
    {
        this.value = value;
    }
    
    
}
