 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: IRolePropsDao.java 
 * @Package com.lk.dragon.db.dao 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-9-15 上午11:38:38 
 * @version V1.0   
 */
package com.lk.dragon.db.dao;

import java.util.List;
import java.util.Map;

import com.lk.dragon.db.domain.DropRate;
import com.lk.dragon.db.domain.Equip;
import com.lk.dragon.db.domain.EquipGem;
import com.lk.dragon.db.domain.Gem;
import com.lk.dragon.db.domain.Hero;
import com.lk.dragon.db.domain.HeroEquip;
import com.lk.dragon.db.domain.RoleHero;
import com.lk.dragon.db.domain.RoleProps;
import com.lk.dragon.db.domain.RolePropsEquipPro;

/**  
 * @Description:角色-道具关联表 DAO接口
 */
public interface IRolePropsDao {
	
	/**
	 * 购买道具 数据库中插入一条对应数据
	 * @param props
	 * @return 
	 * @throws Exception
	 */
	public long addRoleProps(RoleProps props) throws Exception;
	
	/**
	 * 检测该道具是否存在且可用
	 * @param rolePropsId
	 * @return
	 * @throws Exception
	 */
	public RoleProps checkPropsEnabled(long rolePropsId) throws Exception;
	
	/**
	 * 使用道具 更新道具信息
	 * @param props
	 * @return
	 * @throws Exception
	 */
	public int useProps(RoleProps props)throws Exception;
	
	/**
	 * 更新道具剩余数量
	 * @param props
	 * @return
	 * @throws Exception
	 */
	public int updatePropsCount(RoleProps props) throws Exception;
	
	/**
	 * 
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int plSubPropsCount(Map<String,Object> map)throws Exception;
	
	/**
	 * 检测当前角色是否还有可用的同类道具
	 * @param props
	 * @return
	 * @throws Exception
	 */
	public RoleProps checkHasEnabledProps(RoleProps props) throws Exception;
	
	/**
	 * 道具使用完毕 删除数据
	 * @param props
	 * @return
	 * @throws Exception
	 */
	public int deleteProps(long role_props_id) throws Exception;
	
	/**
	 * 获取道具数量
	 * @param role_props_id
	 * @return
	 * @throws Exception
	 */
	public int getPropsCount(long role_props_id)throws Exception;
	
	/**
	 * 更新道具数量V3：处理收集类任务消耗
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<RoleProps> getPropsByPropsId(Map<String,Object> map)throws Exception;
	
	/**
	 * 绑定物品
	 * @param role_props_id
	 * @return
	 * @throws Exception
	 */
	public int bindProps(long role_props_id)throws Exception;
	
	
	/**
	 * 调用ORACLE函数
	 * @param roleProps
	 * @return
	 * @throws Exception
	 */
	public int callAddRoleProps(RoleProps roleProps)throws Exception;
	
	/**
	 * 查询角色道具列表
	 * @param role_id
	 * @return
	 * @throws Exception
	 */
	public List<RoleProps> selectRolePropsByCondition(long role_id)throws Exception;
	
	/**
	 * 查询道具详细信息
	 * @param conditonMap
	 * @return
	 * @throws Exception
	 */
	public RoleProps selectRolePropsDetailById(Map<String,Object> conditonMap)throws Exception;
	

//=================================================英雄-装备=========================================//

	/**
	 * 查看英雄基础5大属性
	 * @param hero_id
	 * @return
	 * @throws Exception
	 */
	public Hero checkHeroBasePro(long hero_id)throws Exception;
	
	/**
	 * 新招募英雄
	 * @param roleHero
	 * @return
	 * @throws Exception
	 */
	public long hireNewHero(Map<String,Object> map)throws Exception;
	
	/**
	 * Oracle存储过程刷新当日招募英雄列表
	 * @param city_id
	 * @return
	 * @throws Exception
	 */
	public int callFlushHiredHeros(long city_id)throws Exception;
	
	/**
	 * 获取指定等级、类型的宝石
	 * @param gem
	 * @return
	 * @throws Exception
	 */
	public Gem getGemByLevType(Gem gem)throws Exception;
	
	
	/**
	 * 获取宝石信息
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<Gem> getGemInfo(String condition)throws Exception;
	/**
	 * 装备穿卸:修改穿戴标志位与绑定标志位
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int onOffEquip(Map<String,Object> map)throws Exception;
	
	/**
	 * 英雄穿戴装备
	 * @param heroEquip
	 * @return
	 * @throws Exception
	 */
	public long heroUseEquip(HeroEquip heroEquip)throws Exception;
	
	
	/**
	 * 获取装备属性(含已嵌套宝石)
	 * @param role_props_id
	 * @return
	 */
	public RolePropsEquipPro getEquipProperty(long role_props_id)throws Exception;
	
	
	/**
	 * 更新装备属性(含已嵌套宝石)
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateEquipProperty(Map<String,Object> map)throws Exception;
	
	/**
	 * 英雄卸下装备：删除hero_equip_tab数据
	 * @param role_props_id
	 * @return
	 * @throws Exception
	 */
	public int heroOffEquip(long role_props_id)throws Exception;

	
	/**
	 * 更改英雄属性
	 * @return
	 */
	public int updateHeroProperty(Map<String,Object> map)throws Exception;
	
	/**
	 * 丢弃回收装备：删除装备属性数据
	 * @param role_props_id
	 * @return
	 * @throws Exception
	 */
	public int deleteEquipValue(long role_props_id)throws Exception;
	
	/**
	 * 装备-宝石中间表 增加数据
	 * @param equipGem
	 * @return
	 * @throws Exception
	 */
	public long addEquipGem(EquipGem equipGem)throws Exception;
	
	/**
	 * 装备-宝石中间表 删除数据
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public int deleteEquipGem(String condition)throws Exception;
	
	/**
	 * 获取道具所在包裹信息
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<RoleProps> getRolePropsInfo(String condition)throws Exception;
	
	
	/**
	 * 获取英雄信息
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<RoleHero> getHeroPropertyByCondition(String condition)throws Exception;

	/**
	 * 获取当前城市可招募英雄
	 * @param city_id
	 * @return
	 * @throws Exception
	 */
	public List<Hero> getHeroCanHired(long city_id)throws Exception;
	
	/**
	 * 查看招募英雄详细属性
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public Hero getHeroBaseProperties(String condition)throws Exception; 
	
	/**
	 * 设置该英雄已被招募
	 * @param hero_id
	 * @return
	 * @throws Exception
	 */
	public int updateHireFlag(long hero_id)throws Exception;
	
	/**
	 * 获取宝石道具信息
	 * @param conditon
	 * @return
	 * @throws Exception
	 */
	public Gem getGemPropsInfo(String condition)throws Exception;
	
	
	/**
	 * 流放英雄
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public int deleteRoleHero(String condition)throws Exception;
	
	
	/**
	 * 更改状态
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public int updateHerosStatus(String condition)throws Exception;
	
	
	/**
	 * 查看英雄已穿戴装备基本信息
	 * @param role_hero_id
	 * @return
	 * @throws Exception
	 */
	public List<RolePropsEquipPro> getHeroOnEquipBase(long role_hero_id)throws Exception;
	
	
	/**
	 * 英雄进入训练室
	 * @param roleHero
	 * @return
	 * @throws Exception
	 */
	public boolean addHeroTrain(RoleHero roleHero)throws Exception;
	
	/**
	 * 查看当前城邦训练室内英雄信息
	 * @param city_id
	 * @return
	 * @throws Exception
	 */
	public List<RoleHero> getHeroTrainInfo(String condition)throws Exception;
	
	/**
	 * 终止训练
	 * @param condtion
	 * @return
	 * @throws Exception
	 */
	public boolean cancelHeroTrain(String condtion)throws Exception;
	
	/**
	 * 获取NPC掉落概率基数
	 * @param npcId
	 * @return
	 * @throws Exception
	 */
	public int getNpcDropRateMax(int npcId)throws Exception;
	
	/**
	 * 本次掉落物品信息
	 * @param dropRate
	 * @return
	 * @throws Exception
	 */
	public RoleProps getNpcDropInfo(DropRate dropRate)throws Exception;
	
	/**
	 * 获取装备初始属性
	 * @param props_id
	 * @return
	 * @throws Exception
	 */
	public Equip getEquipBaseInfo(int props_id)throws Exception;
	
	/**
	 * 获取英雄当前状态
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<RoleHero> checkHerosStatus(String condition)throws Exception;
	
	/**
	 * 系统奖励物品
	 * @param props_id
	 * @return
	 * @throws Exception
	 */
	public String getSysReward(int props_id)throws Exception;
	
	/**
	 * 获取宝箱(系统奖励)中的道具
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public List<RoleProps> getSysRewardDetail(Map<String,Integer> map)throws Exception;
	
	/**
	 * 
	 * @param role_hero_id
	 * @return
	 * @throws Exception
	 */
	public RoleHero getHeroPropertyByRoleHeroId(long role_hero_id)throws Exception;
}



