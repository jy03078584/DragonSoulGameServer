/**
 *
 *
 * 文件名称： OperateLogUtil.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2015-4-28 下午2:57:24
 */
package com.lk.dragon.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.lk.dragon.db.domain.Tools;
import com.lk.dragon.service.SqlToolsService;

public class OperateLogUtil
{
    //定义模块
    public static final String ARMY_MODULE = "军事系统模块";
    public static final String AUCTION_MODULE = "拍卖行系统模块";
    public static final String BATTLE_MODULE = "战斗系统模块";
    public static final String CHAT_MODULE = "聊天系统模块";
    public static final String CITY_MODULE = "城镇系统模块";
    public static final String DIAMOND_MODULE = "宝石系统模块";
    public static final String EQUIP_MODULE = "装备系统模块";
    public static final String FRIEND_MODULE = "好友系统模块";
    
    private static SqlToolsService sqlToolsService = SpringBeanUtil.getBean(SqlToolsService.class);
    //定义是否成标志位
    public static final int SUCCESS = 1;
    public static final int FAIL = 0;
    
    /**
     * 向系统中插入一个操作日志
     * @param roleId 当前操作角色id
     * @param detail 详细操作日志信息
     * @param module 操作模块
     * @param isSuccess 是否成功标志位
     */
    public static void insertOperateLog(long roleId, String detail, String module, int isSuccess)
    {
        Tools tool = new Tools(roleId, module, detail, isSuccess);
        try
        {

            sqlToolsService.addNewLogInfo(tool);

        } 
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    /**
     * 获取异常信息的堆栈详细信息
     * @param ex
     * @return
     */
    public static String getExceptionStackInfo(Exception ex)
    {
        StringWriter writer = new StringWriter();
        ex.printStackTrace(new PrintWriter(writer, true));
        
        return writer.toString();
    }
}
