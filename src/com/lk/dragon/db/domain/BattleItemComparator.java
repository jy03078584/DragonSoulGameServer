 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: BattleItemComparator.java 
 * @Package com.lk.dragon.db.domain 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-11-7 上午10:41:37 
 * @version V1.0   
 */
package com.lk.dragon.db.domain;

import java.util.Comparator;

/**  
 * @Description:排序List<BattleItem>
 */
public class BattleItemComparator implements Comparator<BattleItem>{

	@Override
	public int compare(BattleItem battleItem1, BattleItem battleItem2) {
		int flag = battleItem2.getSpeed().compareTo(battleItem1.getSpeed());
		if(flag == 0){
			if(Math.random()*10 >=5 ){
				
				flag =1;
			}else{
				flag = -1;
			}
			
		}
		return flag;
	}






}
