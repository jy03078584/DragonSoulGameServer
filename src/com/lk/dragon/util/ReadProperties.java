/**
 *
 *
 * 文件名称： ReadProperties.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-8-22 下午2:58:59
 */
package com.lk.dragon.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties
{
    /**
     * 读取配置文件中的信息
     * @param key
     * @return
     */
    public static String getProperties(String key, String fileName)
    {
        //存储值
        String val = "";
        //获取路径
        String pre=System.getProperty("user.dir");  
        String path = pre + File.separator + "properties" + File.separator + fileName;
        path = path.replace("/", File.separator);
        //加载配置文件
        Properties prop = new Properties();
//        InputStream in = Object.class
//                .getResourceAsStream(path);
        try
        {
            InputStream in=new BufferedInputStream(new FileInputStream(path));
            prop.load(in);
            
            //读取对应字段的值
            val = prop.getProperty(key).trim();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
        return val;
    }
    
}
