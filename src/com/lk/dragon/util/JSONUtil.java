/**
 * Copyright ? 2014，成都乐控
 * All Rights Reserved.
 * 文件名称： JSONUtil.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-8-21 下午4:54:21
 */
package com.lk.dragon.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lk.dragon.server.domain.CreepsDemon;
import com.lk.dragon.server.domain.IncDomain;

public class JSONUtil
{
    // 定义特定键值
    // 请求解析关键字段
    private static final String DATA_KEY = "data";
    // 响应的关键字段
    public static final String RESULT_KEY = "result";
    public static final String REASON_KEY = "reason";
    
    //副属性关键字段
    private static final String TYPE_KEY = "type";
    private static final String VALUE_KEY = "value";
    private static final String INC_KEY = "incs";
        
    /** =======================【解析json字符串构成】============================ **/
    /**
     * 根据请求，获取请求类型
     * @param json
     * @return
     */
    public static int getDealType(String json)
    {
      //将json字符串转化为json数组
        JSONObject dataJson = JSONObject.fromObject(json);
        
        //获取类型
        String type = dataJson.getString("type").trim();
        
        //验证请求类型是否属于整型
        Pattern pattern = Pattern.compile("[0-9]+"); 
        Matcher matcher = pattern.matcher((CharSequence) type); 
        boolean result = matcher.matches();
        
        if (!result)
        {
            //如果和类型识别字符串无法匹配，则返回错误请求类型
            return Constants.ERROR_TYPE;
        }
        else
        {
            int requestType = Integer.parseInt(type);
            //请求是否在 请求范围内
            if (requestType >= 1 && requestType <= Constants.TYPE_BOUNDS)
            {
                //如果匹配，则返回相应的请求类型
                return requestType;
            }
            
            return Constants.ERROR_TYPE;
        }
    }
    
    /**
     * 将json字符串的data信息解析并返回
     * @param json
     * @return
     */
    public static JSONObject getData(String json)
    {
        // 将json字符串转化为json数组
        JSONObject dataJson = JSONObject.fromObject(json);

        // 获取请求数据
        JSONObject data = dataJson.getJSONObject(DATA_KEY);
        
        return data;
    }
    
    public static String getNewPropsIdResponse(int resKey,long newPropsId){
    	 
        if(resKey != Constants.REQUEST_SUCCESS)
        	return JSONUtil.getBooleanResponse(false);
        
        JSONObject resJson = new JSONObject();
        if(newPropsId == -2){
        	 resJson.put(Constants.RESULT_KEY, Constants.REQUEST_FAIL);
             resJson.put("reason", "商品已被购买");
        }else{
        	resJson.put(Constants.RESULT_KEY, resKey);
        	resJson.put("newPropsId", newPropsId);
        }
        
        return resJson.toString();
        
    }
    
    /**
     * 
     * 
     * @return
     */
    public static String getBooleanResponse(boolean result)
    {
        // 构造登录结果
        JSONObject resultObj = new JSONObject();
        
        if (result)
        {
            //存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
        }
        else
        {
            //存放结果 
            resultObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
            resultObj.put(REASON_KEY, Constants.NET_ERROR);
        }
        
        return resultObj.toString();
    }
    
    /**
     * 操作成功的响应字符串
     * @return
     */
    public static String getOpSuccess()
    {
        JSONObject obj = new JSONObject();
        

        obj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
        
        return  obj.toString();
    }
    
    /**
     * 将副属性list转化为json字符串
     * @return
     */
    public static String getIncStr(List<IncDomain> list)
    {
        // 构造登录结果
        JSONObject resultObj = new JSONObject();
        
        JSONArray arr = new JSONArray();
        for (int i = 0; i < list.size(); i++)
        {
            JSONObject obj = new JSONObject();
            IncDomain inc = list.get(i);
            obj.put(TYPE_KEY, inc.getIncType());
            obj.put(VALUE_KEY, inc.getValue());
            
            arr.add(obj);
        }
        
        resultObj.put(INC_KEY, arr);
        
        return resultObj.toString();
    }
    
    /**
     * 将副属性json字符串转化为list对象
     */
    public static List<IncDomain> getIncObj(String incStr)
    {
        //转化字符串为json对象
        JSONObject incObj = JSONObject.fromObject(incStr);
        
        //获取字符串
        JSONArray arr = incObj.getJSONArray(INC_KEY);
        
        //将json数组转化为副属性list对象
        List<IncDomain> list = new ArrayList<IncDomain>();
        for (int i = 0; i < arr.size(); i++)
        {
            JSONObject obj = (JSONObject) arr.get(i);
            IncDomain incDomain = new IncDomain();
            incDomain.setIncType(obj.getInt(TYPE_KEY));
            incDomain.setValue(obj.getInt(VALUE_KEY));
            
            list.add(incDomain);
        }
        
        return list;
    }
    
    
    /**
     * 将副属性list转化为json字符串
     * @return
     */
    public static String getCreepStr(List<CreepsDemon> list)
    {
        // 构造登录结果
        JSONObject resultObj = new JSONObject();
        
        JSONArray arr = new JSONArray();
        for (int i = 0; i < list.size(); i++)
        {
            JSONObject obj = new JSONObject();
            CreepsDemon creep = list.get(i);
            obj.put("arm_id", creep.getArm_id());
            obj.put("creeps_count", creep.getCreeps_count());
            
            arr.add(obj);
        }
        
        resultObj.put("creeps", arr);
        
        return resultObj.toString();
    }
    

    public static List<CreepsDemon> getCreepsObj(String creepStr)
    {
        JSONObject creepObj = JSONObject.fromObject(creepStr);
        
        JSONArray arr = creepObj.getJSONArray("creeps");
        
        //将json数组转化为副属性list对象
        List<CreepsDemon> list = new ArrayList<CreepsDemon>();
        for (int i = 0; i < arr.size(); i++)
        {
            JSONObject obj = (JSONObject) arr.get(i);
            CreepsDemon creepsDemon = new CreepsDemon(obj.getInt("arm_id"),obj.getInt("creeps_count"));
            
            list.add(creepsDemon);
        }
        
        return list;
    }
    
    /**
     * 单个错误原因回写
     * @return
     */
    public static String getWrongResponse(String reason)
    {
        // 构造登录结果
        JSONObject resultObj = new JSONObject();
        resultObj.put(RESULT_KEY, Constants.REQUEST_FAIL);
        resultObj.put(REASON_KEY, reason);
        
        return resultObj.toString();
    }
    
    /**
     * 错误响应
     * @return
     */
    public static String getErrorResponse()
    {
        JSONObject resultObj = new JSONObject();
        resultObj.put(RESULT_KEY, Constants.ERROR_TYPE);
        
        return resultObj.toString();
    }
}
