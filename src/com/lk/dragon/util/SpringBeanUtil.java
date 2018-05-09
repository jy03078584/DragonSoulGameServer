 /** 
 *
 * @Title: SpringBeanUtil.java 
 * @Package com.lk.dragon.util 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2015-3-9 下午3:08:23 
 * @version V1.0   
 */
package com.lk.dragon.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**  
 * @Description:
 */
public class SpringBeanUtil {
	
	private static ApplicationContext ctx ;
	
	static{
		try {
			System.out.println("----------------");
			ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static <T>T   getBean(Class<T> clazz){
		return ctx.getBean(clazz);
	}
}
