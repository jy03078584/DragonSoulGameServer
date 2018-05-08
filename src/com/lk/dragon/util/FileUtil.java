package com.lk.dragon.util;

import java.io.FileInputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 配置文件读取类
 * 
 * @author xmz
 * 
 */
public class FileUtil {
	private static Logger logger = Logger.getLogger(FileUtil.class);
	private static Properties properties = new Properties();

	/**
	 * 读取指定路径人配置文件
	 * 
	 * @param fileName
	 *            文件名称
	 * @param key
	 *            Properties文件KEY值
	 * @return
	 */
	public static String getValue(String fileName, String key) {
		String value = "";
		try {
			properties.load(new FileInputStream(fileName));
			value = properties.getProperty(key);

		} catch (Exception e) {
			logger.info("读取配置文件失败：" + e.getMessage());
		}
		return value;
	}
}
