/** 
 *
 * @Title: ConvertComminuEdian.java 
 * @Package com.lk.dragon.util 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2015-5-15 下午4:51:21 
 * @version V1.0   
 */
package com.lk.dragon.util;

/**
 * @Description:转化通信大/小端工具类 Java：大端 C++： 小端
 */
public class ConvertComminuEdian {

	/***
	 * 大端转小端
	 * 
	 * @param a
	 * @return
	 */
	public static int bigToLittle(int i) {
		return (i & 0xFF) << 24 | (0xFF & i >> 8) << 16 | (0xFF & i >> 16) << 8
				| (0xFF & i >> 24);
	}
public static void main(String[] args) {
	System.out.println(bigToLittle(10002));
}
	/**
	 * int转byte[]
	 * 
	 * @param res
	 * @return
	 */
	public static byte[] int2byte(int i) {
//		byte[] targets = new byte[4];
//		targets[0] = (byte) (res & 0xff);
//		targets[1] = (byte) ((res >> 8) & 0xff);
//		targets[2] = (byte) ((res >> 16) & 0xff);
//		targets[3] = (byte) (res >>> 24);
//		return targets;
		byte[] result = new byte[4];   
        //由高位到低位
        result[0] = (byte)((i >> 24) & 0xFF);
        result[1] = (byte)((i >> 16) & 0xFF);
        result[2] = (byte)((i >> 8) & 0xFF); 
        result[3] = (byte)(i & 0xFF);
        return result;

	}

	public static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
		byte[] byte_3 = new byte[byte_1.length + byte_2.length];
		System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
		System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
		return byte_3;
	}

	/**
	 * 构造C++小端信息
	 * @param length
	 * @param info
	 * @return
	 */
	public static String constrInfoToClient(int length,String info){
		String infoLength = length+"";
		int ll = infoLength.length();
		for (int i = 0; i < 5 -ll; i++) {
			infoLength = "0"+infoLength;
		}
		return infoLength+info;
	}
}
