 /** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: IArmsDeployDao.java 
 * @Package com.lk.dragon.db.dao 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-10-31 上午9:43:11 
 * @version V1.0   
 */
package com.lk.dragon.db.dao;

import java.util.List;
import java.util.Map;

import com.lk.dragon.db.domain.ArmsDeploy;
import com.lk.dragon.db.domain.ReinForceArm;
import com.lk.dragon.db.domain.RoleHero;
import com.lk.dragon.db.domain.WarProduce;

/**  
 * @Description:军队整备接口
 */
public interface IArmsDeployDao {

	/**
	 * 创建征兵队列
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public long createConscriptTeam(Map<String,Object> map)throws Exception;
	
	
	/**
	 * 检查英雄当前是否配备有部队
	 * @param roleHeroId
	 * @return
	 */
	public int checkHeroHasArmsNow(long roleHeroId);
	/**
	 * 查看当前正在来犯的敌方队列
	 * @param roleId
	 * @return
	 */
	public List<WarProduce> getCurrentEnemyTeam(long roleId);
	
	/**
	 * 增援列表
	 * @param role_id
	 * @return
	 * @throws Exception
	 */
	public List<ReinForceArm> selectReinForceArm(long role_id)throws Exception;
	/**
	 * 根据armID查询arm信息
	 * @param arm_id
	 * @return
	 */
	public ArmsDeploy getArmInfoByArmId(int arm_id);
	
	/**
	 * 查看已到达目标的战斗队列信息
	 * @return
	 * @throws Exception
	 */
	public List<WarProduce> selectArriveTagWarTeam2(String value)throws Exception;
	
	/**
	 * 获取指定建筑兵种信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public ArmsDeploy getArmsBuildInfo(Map<String,Object> map)throws Exception;
	
	/**
	 * 获取全部兵种信息
	 * @return
	 */
	public List<ArmsDeploy> getAllArmsInfo();
	/**
	 * 新征兵/传送部队到达指定城邦  
	 * @param city_id
	 * @param arms_id
	 * @param arms_count
	 * @return long city_id, long arms_id, int arms_count
	 * @throws Exception
	 */
	public int callProductArms(ArmsDeploy armsDeploy)throws Exception;
	
	
	/**
	 * 更改征兵队列信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int updateConScriptTime(Map<String,Object> map)throws Exception;
	
	
	/**
	 * 删除队列任务
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int deleteConScript(Map<String,Object> map)throws Exception;
	
	/**
	 * 判断征兵是否结束
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public int findConScriptIsEnd(String condition)throws Exception;
	
	/**
	 * 查看角色所有队列信息
	 * @param role_id
	 * @return
	 * @throws Exception
	 */
	public List<ArmsDeploy> selectConScriptByRoleId(long role_id)throws Exception;
	
	/**
	 * 英雄-部队状态总览
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<RoleHero> selectHeroArmsStatus(String condition)throws Exception;
	
	/**
	 * 查询英雄所率领部队的详细信息
	 * @param roleHeroId
	 * @return
	 * @throws Exception
	 */
	public List<ArmsDeploy> selectHeroArmsDetail(Long roleHeroId) throws Exception;
	
	/**
	 * 查看城邦预备部队信息
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<ArmsDeploy> selectCityArmsInfo(String condition)throws Exception;
	
	
	/**
	 * 更新英雄部队信息
	 * @param roleHero
	 * @return
	 * @throws Exception
	 */
	public int updateHeroArmsStatus(ArmsDeploy heroArm)throws Exception;
	
	
	/**
	 * 删除城邦-部队信息
	 * @param city_id
	 * @return
	 * @throws Exception
	 */
	public int deleteCityArms(long city_id)throws Exception;
	
	/**
	 * 新增城邦-部队信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Object insertCityArms(ArmsDeploy armsDeploy)throws Exception;

	/**
	 * 创建出征队列信息
	 * @param =
	 * @return
	 * @throws Exception
	 */
	public Object createWarTeam(WarProduce warProduce)throws Exception;
	
	
	/**
	 * 创建出征队列信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public Long getWarTeamSeq()throws Exception;
	/**
	 * 查看已到达目标的战斗队列信息
	 * @return
	 * @throws Exception
	 */
	public List<WarProduce> selectArriveTagWarTeam()throws Exception;
	
	
	public List<WarProduce> initWarTeamCache();
	/**
	 * 更改战斗队列信息
	 * @return
	 * @throws Exception
	 */
	public int updateWarTeamInfo(WarProduce warProduce)throws Exception;
	
	/**
	 * 删除战斗信息
	 * @param war_team_id
	 * @return
	 * @throws Exception
	 */
	public int deleteWarTeamInfo(long war_team_id)throws Exception;
	
	
	/**
	 * 战斗后更新英雄部队信息
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public int updateHeroArms(String condition)throws Exception;
	
	
	/**
	 * 获取战后部队信息
	 * @param role_hero_id
	 * @return
	 * @throws Exception
	 */
	public List<ArmsDeploy> selectHeroArmsInfoWarEnd(long role_hero_id)throws Exception;
	
	
	/**
	 * 战后更新预备队信息
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public int updateCityArmsWarEnd(String condition)throws Exception;
	
	
	
	
	
	
	/**
	 * 查看当前队列剩余英雄ID
	 * @param 
	 * @return
	 * @throws Exception
	 */
	public List<WarProduce> selectHerosInTeam(String condition)throws Exception;
	
	
	/**
	 * 删除队列-英雄信息
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public int deleteTeamHeroInfo(String condition)throws Exception;
	
	
	
	/**
	 * 调用Oracle函数 队列英雄全部阵亡 则队列删除
	 * @param dead_teams_id
	 * @return
	 * @throws Exception
	 */
	public int callCheckTeamHero(String dead_teams_id)throws Exception;
	
	/**
	 * 查询当前英雄部队情况
	 * @param role_hero_id
	 * @return
	 * @throws Exception
	 */
	public List<ArmsDeploy> selectHeroArmsInfoProduce(long role_hero_id)throws Exception;
	
	/**
	 * 获取当前城邦预备队详细情况
	 * @param city_id
	 * @return
	 * @throws Exception
	 */
	public List<ArmsDeploy> selectCityArmsInfoProduce(long city_id)throws Exception;

	/**
	 * 查询战斗元素(兵种/野怪)详细信息
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<ArmsDeploy> selectArmsDetailInfo(String condition)throws Exception;
	
	/**
	 * 查看资源点信息
	 * @param conditon
	 * @return
	 * @throws Exception
	 */
	public List<WarProduce> selectWildSrcInfo(String conditon)throws Exception;
	
	/**
	 * 修改资源点所属角色
	 * @param owner_id
	 * @return
	 * @throws Exception
	 */
	public int updateWildSrcOwner(Map<String,Object> map)throws Exception;
	
	
	/**
	 * 根据条件查询队列信息
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public WarProduce selectWarTeamById(long war_team_id)throws Exception;
	
	/**
	 * 查看队列状态及所辖英雄状态
	 * @param conditionId
	 * @return
	 * @throws Exception
	 */
	public List<WarProduce> selectWarTeamDetailInfo(String conditionId)throws Exception;
	
	
	/**
	 * 查询给定条件的英雄军事信心
	 * @param condition
	 * @return
	 */
	public List<RoleHero> getHerosInfoByCondition(String condition);
	/**
	 * 插入英雄ID到team_hero_tab
	 * @param team_id
	 * @return
	 * @throws Exception
	 */
	public Object insertTeamHero(Map<String,Long> map)throws Exception;
	
	/**
	 * 获取出征队列到达目标地时间
	 * @param use_time
	 * @return
	 * @throws Exception
	 */
	public String getArriveTimeDb(int use_time)throws Exception;
	
	
	/**
	 * 获取队列-英雄表 ID
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<Long> selectDistintTeamId(String condition)throws Exception;
	
	/**
	 * 野外资源点驻扎军队信息
	 * @param condition
	 * @return
	 * @throws Exception
	 */
	public List<RoleHero> getSrcHerosInfoByCondition(String condition)throws Exception;
	
	/**
	 * 随机获取野外狩猎 野怪队列
	 * @param conditionMap
	 * @return
	 * @throws Exception
	 */
	public List<ArmsDeploy> selectWildHurtRandomInfo(Map<String,Integer> conditionMap)throws Exception;
	
	/**
	 * 查看部队总统帅数值
	 * @param team_id
	 * @return
	 * @throws Exception
	 */
	public int selectTotalCommand(long team_id)throws Exception;
}
