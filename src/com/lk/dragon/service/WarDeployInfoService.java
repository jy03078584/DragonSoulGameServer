/** 
 * Copyright ? 2014,成都乐控科技 
 * @Title: WarDeployInfoService.java 
 * @Package com.lk.dragon.service 
 * @Description: TODO 
 * @author XiangMZh   
 * @date 2014-10-31 上午9:59:40 
 * @version V1.0   
 */
package com.lk.dragon.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lk.dragon.db.dao.IArmsDeployDao;
import com.lk.dragon.db.dao.ICityBuildDao;
import com.lk.dragon.db.dao.IMailDao;
import com.lk.dragon.db.dao.IRoleDao;
import com.lk.dragon.db.dao.IRolePropsDao;
import com.lk.dragon.db.dao.IWorldMapDao;
import com.lk.dragon.db.domain.ArmsDeploy;
import com.lk.dragon.db.domain.City;
import com.lk.dragon.db.domain.DaoVo;
import com.lk.dragon.db.domain.Mail;
import com.lk.dragon.db.domain.ReinForceArm;
import com.lk.dragon.db.domain.RoleHero;
import com.lk.dragon.db.domain.WarProduce;
import com.lk.dragon.db.domain.WarTeamVo;
import com.lk.dragon.db.domain.WildSrc;
import com.lk.dragon.server.domain.CreepsDemon;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.DateTimeUtil;
import com.lk.dragon.util.JSONUtil;

/**
 * @Description:战斗整备业务层
 */
@Service
public class WarDeployInfoService {

	@Autowired
	private  IArmsDeployDao armsDeployDao ;
	@Autowired
	private  IRoleDao roleDao ;
	@Autowired
	private  IMailDao mailDao ;
	@Autowired
	private  IRolePropsDao rolePropsDao ;
	@Autowired
	private ICityBuildDao cityBuildDao;
	
	@Autowired
	private SqlToolsService toolsService;
	
	@Autowired
	private IWorldMapDao worldMapDao;
	@Autowired
	private CacheService cacheService;

	public final static String WAR_TEAM_ID_KEY = "warTeamId";
	public final static String TAG_CITY_ID_KEY = "tagCityId";
	
	public WarDeployInfoService(){
	}

	/**
	 * 创建征兵队列
	 * 
	 * @param city_id
	 *            征兵城邦ID
	 * @param arms_id
	 *            兵种ID
	 * @param use_time
	 *            此次任务总耗时(秒:S)
	 * @param hire_time
	 *            招募周期(秒:S)
	 * @param use_gold
	 *            此次任务消耗总金币(秒:S)
	 * @param city_build_id
	 *            城邦-建筑关联ID
	 * @param role_id
	 *            角色ID
	 * @param eat
	 *            该兵种占人口数
	 * @return
	 */
	public boolean createConscriptTeam(long city_id, int arms_id, int use_time,
			int hire_time, int use_gold, long city_build_id, long role_id,
			int eat) throws Exception {

		boolean flag = false;
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("city_id", city_id);
		map.put("arms_id", arms_id);
		map.put("use_time", use_time);
		map.put("hire_time", hire_time);
		map.put("city_build_id", city_build_id);
		map.put("add_eat", eat);

		//armsDeployDao.createConscriptTeam(map);
		map.put("operator", "-");
		map.put("gold", use_gold);
		map.put("role_id", role_id);
		//roleDao.sumPluRoleInfo(map);
		List<DaoVo> daoVos = new ArrayList<DaoVo>();
		daoVos.add(new DaoVo("roleMap.sumPluRoleInfo", map,2));
		daoVos.add(new DaoVo("warMap.createConscriptTeam", map,1));
		
		toolsService.doSqlByBatch(daoVos);
		return true;
	}

	/**
	 * 获取指定军事建筑兵种信息
	 * @param race
	 * @param build_type
	 * @return
	 */
	public ArmsDeploy getArmsBuildInfo(int race,int build_type){
		HashMap<String,Object> map = new HashMap<String,Object>();
		ArmsDeploy armsDeploy = null;
		map.put("race", race);
		map.put("hire_build", build_type);
		try {
			armsDeploy = armsDeployDao.getArmsBuildInfo(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return armsDeploy;
	}
	
	/**
	 * 获取全部兵种信息 缓存到CacheHandlerUtil ARMS_INFO_CURRMAP
	 * @return
	 */
	public List<ArmsDeploy> getAllArmsInfo(){
		return armsDeployDao.getAllArmsInfo();
	}
	
	/**
	 * 取消招募
	 * 
	 * @param role_id
	 *            角色ID
	 * @param back_gold
	 *            返还金币
	 * @param city_id
	 *            城市ID
	 * @param arms_id
	 *            兵种ID
	 * @return
	 */
	public boolean cancelConscriptTeam(long role_id, int back_gold,
			long city_id, int arms_id) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("operator", "+");
		map.put("role_id", role_id);
		map.put("gold", back_gold);

		map.put("city_id", city_id);
		map.put("arms_id", arms_id);
//		roleDao.sumPluRoleInfo(map);// 返还部分金币
//		armsDeployDao.deleteConScript(map);// 删除招募队列信息

		List<DaoVo> daoVos = new ArrayList<DaoVo>();
		daoVos.add(new DaoVo("roleMap.sumPluRoleInfo", map,2));
		daoVos.add(new DaoVo("warMap.deleteConScript", map,3));
		
		toolsService.doSqlByBatch(daoVos);
		
		return true;
	}

	/**
	 * 加速完成招募
	 * 
	 * @param role_id
	 *            角色ID
	 * @param use_diamon
	 *            消耗钻石数
	 * @param city_id
	 *            城市ID
	 * @param arms_id
	 *            兵种ID
	 * @param add_eat
	 *            增加人口数
	 * @param arms_count
	 *            招募数量
	 * @return
	 */
	public boolean usePropsHiredArms(long role_id, int use_diamon,
			long city_id, int arms_id, int add_eat, int arms_count) throws Exception {

		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("operator", "-");
		map.put("role_id", role_id);
		map.put("diamon", use_diamon);

		map.put("city_id", city_id);
		map.put("arms_id", arms_id);

		
//		armsDeployDao.callProductArms(armsDeploy);
//		armsDeployDao.deleteConScript(map);// 征兵结束 删除队列信息
//		roleDao.sumPluRoleInfo(map);// 消耗钻石
		
		List<DaoVo> daoVos = new ArrayList<DaoVo>();
		daoVos.add(new DaoVo("roleMap.sumPluRoleInfo", map,2));
		daoVos.add(new DaoVo("warMap.deleteConScript", map,3));
		
		Map<String, Object> callMap = new HashMap<String, Object>();
		callMap.put("city_id_param", city_id);
		callMap.put("arm_id_param", arms_id);
		callMap.put("arm_count_param",arms_count);
		daoVos.add(new DaoVo("warMap.callProductArms", callMap,4));
		toolsService.doSqlByBatch(daoVos);
		return true;
	}

	/**
	 * 正常征兵周期下产兵
	 * 
	 * @param city_id
	 *            城邦ID
	 * @param role_id
	 *            角色ID
	 * @param arms_id
	 *            兵种ID
	 * @param use_time
	 *            消耗时间(秒/s)
	 * @param eat
	 *            此兵种人口消耗量
	 * @return
	 */
	public boolean productArmNormal(long city_id, long role_id, int arms_id,
			int use_time, int eat) throws Exception {
		List<DaoVo> daoVos = new ArrayList<DaoVo>();
		
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("condition","t.extra_seconds = 0,t.last_seconds = t.last_seconds - "+ use_time);
		map.put("city_id", city_id);
		map.put("arms_id", arms_id);
		int hireLast = armsDeployDao.findConScriptIsEnd("WHERE t.city_id = " + city_id+ " AND t.arms_id= " + arms_id +" and rownum <=1 ");
		if (hireLast < 0) {
			//armsDeployDao.updateConScriptTime(map);// 更新队列剩余时间
			daoVos.add(new DaoVo("warMap.updateConScriptTime", map,2));
		} else if(hireLast == 0){
//			armsDeployDao.updateConScriptTime(map);// 更新队列剩余时间
//			armsDeployDao.deleteConScript(map);// 任务已结束 删除数据
			daoVos.add(new DaoVo("warMap.updateConScriptTime", map,2));
			daoVos.add(new DaoVo("warMap.deleteConScript", map,3));
		}else{
//			armsDeployDao.deleteConScript(map);// 任务已结束 删除数据
			daoVos.add(new DaoVo("warMap.deleteConScript", map,3));
			
		}
		
		Map<String, Object> callMap = new HashMap<String, Object>();
		callMap.put("city_id_param", city_id);
		callMap.put("arm_id_param", arms_id);
		callMap.put("arm_count_param",1);
		//armsDeployDao.callProductArms(city_id, arms_id, 1);
		daoVos.add(new DaoVo("warMap.callProductArms", callMap,4));
		toolsService.doSqlByBatch(daoVos);
		
		return true;
	}

	/**
	 * 下线重连后 征兵队列信息
	 * 
	 * @param role_id
	 * @return ArmsDeploy集合 
	 * offLineHiredCount:下线期间招募人数； 
	 * hrie_time:招募周期时间；
	 * extra_seconds：距下次产兵时间(秒:S)；
	 * isEnd:剩余招募时间； 
	 * city_id:城邦ID；
	 * arms_id:兵种ID ；
	 * city_build_id:建筑ID;
	 * offLineAddEat:增长人口数List<ArmsDeploy> 
	 */
	public List<ArmsDeploy> productArmbreakPoint(long role_id) throws Exception {
		
		List<DaoVo> daoVos = new ArrayList<DaoVo>();
		
		List<ArmsDeploy> armsList = new ArrayList<ArmsDeploy>();
		HashMap<String, Object> map;
		ArmsDeploy armsDeployTemp = null;
		List<ArmsDeploy> armsDeploys = armsDeployDao.selectConScriptByRoleId(role_id);
		if(armsDeploys.size()<=0){
			return null;
		}
		for (ArmsDeploy armsDeploy : armsDeploys) {
			map = new HashMap<String, Object>();
			map.put("city_id", armsDeploy.getCity_id());
			map.put("arms_id", armsDeploy.getArms_id());
			int hasHiredCount = 0;
			int hireTime = armsDeploy.getHire_time();

			if (armsDeploy.getIsEnd() > 0) {// 征兵未结束
				int offLineTime = armsDeploy.getLast_seconds()
						+ armsDeploy.getExtra_seconds()
						- armsDeploy.getIsEnd();// 下线总时长
				hasHiredCount = offLineTime / hireTime;// 下线期间产兵量
				int extraTime = offLineTime % hireTime;
				map.put("condition", "t.extra_seconds = " + extraTime
						+ ",t.last_seconds = " + armsDeploy.getIsEnd());
				armsDeployTemp = new ArmsDeploy(hasHiredCount, hireTime
						- extraTime, armsDeploy.getIsEnd(),
						armsDeploy.getHire_time(), armsDeploy.getCity_id(),
						armsDeploy.getArms_id(),
						armsDeploy.getCity_build_id(), hasHiredCount
								* armsDeploy.getEat());
				armsDeployTemp.setIsEnd(armsDeploy.getIsEnd());
				armsDeployTemp.setExtra_seconds(extraTime);
				//armsDeployDao.updateConScriptTime(map);// 更新队列剩余时间
				daoVos.add(new DaoVo("warMap.updateConScriptTime", map,2));
			} else {
				armsDeployTemp = new ArmsDeploy();
				armsDeployTemp.setIsEnd(armsDeploy.getIsEnd());
				hasHiredCount = (armsDeploy.getLast_seconds() + armsDeploy.getExtra_seconds())/hireTime;
				//armsDeployDao.deleteConScript(map);// 任务已结束 删除数据
				daoVos.add(new DaoVo("warMap.deleteConScript", map,3));
			}
			// 新招募军队 进入预备军队表
			Map<String, Object> callMap = new HashMap<String, Object>();
			callMap.put("city_id_param", armsDeploy.getCity_id());
			callMap.put("arm_id_param", armsDeploy.getArms_id());
			callMap.put("arm_count_param",hasHiredCount);
			daoVos.add(new DaoVo("warMap.callProductArms", callMap,4));
			armsList.add(armsDeployTemp);
		}
		
		toolsService.doSqlByBatch(daoVos);
		return armsList;
	}

	/**
	 * 查询角色当前所有英雄战备状态:
	 * 
	 * @param role_id
	 * @return RoleHero集合 role_hero_id:角色-英雄关联ID
	 *         hero_name：英雄名,command：统帅值,icon：头像
	 *         ,sum_command：已使用统帅值,hero_arm_count
	 *         ：军队总人数,city_name：所在城邦,is_free：当前状态
	 */
	public List<RoleHero> selectHeroArmsStatus(long role_id) {
		List<RoleHero> hero_arms = null;
		String condition = " t.role_id = " + role_id;
		try {
			// 查询英雄详细信息
			hero_arms = armsDeployDao.selectHeroArmsStatus(condition);

			// 查询英雄所率领的部队信息
			if (hero_arms != null && hero_arms.size() > 0) {
				for (RoleHero roleHero : hero_arms) {
					List<ArmsDeploy> armsDeploy = armsDeployDao
							.selectHeroArmsDetail(roleHero.getRole_hero_id());
					roleHero.setHeroArmys(armsDeploy);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return hero_arms;
	}

	/**
	 * 获取英雄军备信息
	 * @param role_hero_id
	 * @return
	 */
	public List<ArmsDeploy> selectHeroArmsDetailByHeroId(long role_hero_id){
		List<ArmsDeploy> armsDeploy = null;
		try {
			armsDeploy =  armsDeployDao.selectHeroArmsDetail(role_hero_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return armsDeploy;
	}
	
	/**
	 * 查看城邦预备部队信息
	 * 
	 * @param city_id
	 * @return ArmsDeploy集合 关联ID arm_id:兵种ID arm_icon：兵种图标 arm_name:兵种名称
	 *         grade:兵种等级 arm_count:部队数量
	 */
	public List<ArmsDeploy> selectCityArmsInfo(long city_id) {
		List<ArmsDeploy> city_arms = null;
		String condition = " WHERE t.city_id = " + city_id;
		try {
			city_arms = armsDeployDao.selectCityArmsInfo(condition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return city_arms;
	}


	/**
	 * 查看当前城邦中友军增援部队
	 * @param city_id
	 * @return
	 */
	public List<ReinForceArm> selectCityReinforceInfo(long role_id){
		List<ReinForceArm> reinForceArms = null;
		try {
			reinForceArms = armsDeployDao.selectReinForceArm(role_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reinForceArms;
	} 
	
	/**
	 * 调整英雄部队信息
	 * 
	 * @param cityArms
	 *            城邦现存部队信息 city_id,arm_id,arm_count
	 * @param city_id
	 *            城邦ID
	 * @param roleHero
	 *            ：使用特定的构造函数构造的RoleHero对象 包含英雄部队兵种和数量
	 * @return
	 */
	public boolean adjustHeroArms(List<ArmsDeploy> cityArms, long city_id,List<ArmsDeploy> heroArms) throws Exception {
		
		List<DaoVo> daoVos = new ArrayList<DaoVo>();
		
		//armsDeployDao.deleteCityArms(city_id);// 删除原数据
		daoVos.add(new DaoVo("warMap.deleteCityArms", city_id,3));
		if(cityArms!=null && cityArms.size() > 0){
			for (ArmsDeploy armsDeploy : cityArms) {// 新增数据
				daoVos.add(new DaoVo("warMap.insertCityArms", armsDeploy,1));
			}
		}
		

		for (ArmsDeploy heroArm : heroArms) {
			//armsDeployDao.updateHeroArmsStatus(heroArm);// 更新英雄部队
			daoVos.add(new DaoVo("warMap.updateHeroArmsStatus", heroArm,2));
		}
		
		toolsService.doSqlByBatch(daoVos);
		return true;
	}

	
	/**
	 * 查看队列状态及所辖英雄状态
	 * @param conditonId
	 * @return
	 */
	public List<WarProduce> selectWarTeamDetailInfo(String conditionId){
		List<WarProduce> warTeams = null;
		try {
			warTeams = armsDeployDao.selectWarTeamDetailInfo(conditionId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return warTeams;
	}
	
	
	public WarProduce selectWarTeamById(long teamId) throws Exception{
		return armsDeployDao.selectWarTeamById(teamId);
	}
	/**
	 * 创建出征队列信息
	 * 
	 * @param city_id出征城邦ID
	 * @param role_id 角色ID
	 * @param status 目标方式：1：出征 3：增援 
	 * @param use_time 到达目的地消耗时间(秒:S)
	 * @param tag_name 目标名 城邦名/野怪名
	 * @param tag_x  目标X坐标
	 * @param tag_y 目标Y坐标
	 * @param use_food  消耗粮草
	 * @param creeps 狩猎：野怪对象  种类 数量
	 * @param war_type 出征类型
	 * @param warTeamVo WarTeamVo对象 用于向守方发送战情提醒 包含属性：攻方角色名 攻方出发坐标 守方角色ID
	 * @param herosId  队列英雄ID集合
	 * @return
	 */
	public Map<String,Long> createWarTeam(long city_id, long role_id, int status, int use_time, String tag_name, int tag_x,
			int tag_y, int use_food, List<CreepsDemon> creeps,int war_type,WarTeamVo warTeamVo,String herosId) throws Exception {
		long war_team_id = 0;
		List<DaoVo> daoVos = new ArrayList<DaoVo>();
		
		Map<String,Long> resMap = new HashMap<String,Long>();
		
		//判断客户端选择出征的英雄状态是否满足
		List<RoleHero> herosChecked = rolePropsDao.checkHerosStatus(" where (t.hp = 0 or t.is_free <>"+ Constants.HERO_STATUS_FREE +" or t.now_cityid <> "+city_id+") and t.role_hero_id in("+herosId+")");
		
		if(herosChecked.size() > 0){
			//所选英雄列表中有英雄状态不满足出征条件
			resMap.put(WAR_TEAM_ID_KEY, -10l);
			return resMap;
		}
		
		

		String endTime = DateTimeUtil.getEndTime(new Date(), use_time);
		String tagArms = creeps == null ? "" : JSONUtil.getCreepStr(creeps);
		war_team_id = armsDeployDao.getWarTeamSeq();
		
		WarProduce warProduce = new WarProduce(war_team_id, city_id, role_id, status, endTime, tag_name, tag_x, tag_y, tagArms, use_time, war_type);
		//获取队列军事信息
		List<RoleHero> heros = armsDeployDao.getHerosInfoByCondition(" where t.role_hero_id in ("+herosId+")");
		//设置队列所辖英雄
		warProduce.setHeros(heros);
		
		
		
		//战报提醒守方
		int fromX = warTeamVo.getFrom_x();
		int fromY = warTeamVo.getForm_y();
		if(war_type == Constants.WAR_TYPE_CONQUER || war_type == Constants.WAR_TYPE_ROB || war_type == Constants.WAR_TYPE_RESIDSELF){
					//查看攻方 守方城邦信息
					City attCity = cityBuildDao.getCitysInfoByCondition(" where t.site_x = "+ fromX +" and t.site_y = "+fromY);
					City defCity = cityBuildDao.getCitysInfoByCondition(" where t.site_x = "+ tag_x +" and t.site_y = "+tag_y);
					if(defCity == null){
						resMap.put(WAR_TEAM_ID_KEY, -10l);
						return resMap;
					}
					//设置目标城邦ID
					warProduce.setTag_city_id(defCity.getCity_id());
					resMap.put(TAG_CITY_ID_KEY, defCity.getCity_id());
					
					if(war_type != Constants.WAR_TYPE_RESIDSELF){
						String attinfo = "【战报】-军情概要：情报显示敌方部队靠近中\r\n[所属城邦]:"+attCity.getName()+"("+fromX+","+fromY+")\r\n"+Constants.MUILT_SPLIT+"[敌方目标]:"+defCity.getName()+"("+tag_x+","+tag_y+")"+"\r\n[状态]:出征中"+"\r\n[预计到达时间]："+endTime+"\r\n请提早布防";
						daoVos.add(new DaoVo("mailMap.addNewMail", new Mail("【战报】-军情",-20l,warTeamVo.getTag_role_id(),attinfo,4,0),1));
					}
					
		}else if(war_type == Constants.WAR_TYPE_WILDSRC_OFF || war_type == Constants.WAR_TYPE_WILDSRC_ON){
			//检查当前目标资源点信息
			List<WildSrc> wildSrcs =  worldMapDao.getWildSrcInfo(" where t.tag_x = "+tag_x+" and t.tag_y = "+tag_y);
			if(wildSrcs == null || wildSrcs.size() == 0)
				throw new RuntimeException(" THE POINT ITEM ERROR");
			WildSrc wildSrc = wildSrcs.get(0);
			if(wildSrc.getOwner_id() != -10){
				//查看攻方城邦信息
				City attCity = cityBuildDao.getCitysInfoByCondition(" where t.site_x = "+ fromX +" and t.site_y = "+fromY);
				
				String attinfo = "【战报】-军情概要：情报显示敌方部队靠近中\r\n[所属城邦]:"+attCity.getName()+"("+fromX+","+fromY+")\r\n"+Constants.MUILT_SPLIT+"[敌方目标]-资源点:"+"("+tag_x+","+tag_y+")"+"\r\n[状态]:出征中"+"\r\n[预计到达时间]："+endTime+"\r\n请提早布防";
				daoVos.add(new DaoVo("mailMap.addNewMail", new Mail("【战报】-军情",-20l,wildSrc.getOwner_id(),attinfo,4,0),1));

			}
		}
		
		
		//放入缓存
		cacheService.putNewWarTeamToCache(warProduce);
		
		//准备插入数据库
		daoVos.add(new DaoVo("warMap.createWarTeam", warProduce,1));
		HashMap<String,Long> map2 ;
		//出征英雄数据 
		for (String heroId : herosId.split(",")) {
			map2  = new HashMap<String, Long>();
			map2.put("role_hero_id", Long.parseLong(heroId));
			map2.put("team_id", war_team_id);
			daoVos.add(new DaoVo("warMap.insertTeamHero", map2,1));
		}
		
		
		//更改出征英雄状态
		daoVos.add(new DaoVo("rolePropsMap.updateHerosStatus", "t.is_free = "+Constants.HERO_STATUS_BATTLE+",t.now_cityid = 0 Where t.role_hero_id in("+herosId+")",2));
		
		// 消耗粮草
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("operator", "-");
		map.put("food", use_food);
		map.put("role_id", role_id);
		daoVos.add(new DaoVo("roleMap.sumPluRoleInfo", map,2));
		
		

		
		toolsService.doSqlByBatch(daoVos);
		resMap.put(WAR_TEAM_ID_KEY, war_team_id);
		return resMap;
	}

	/**
	 * 查看当前正在来犯的敌方队列
	 * @param roleId
	 * @return
	 * @throws Exception 
	 */
	public List<WarProduce>  getCurrentEnemyTeam(long roleId){
		List<WarProduce>  teams =  armsDeployDao.getCurrentEnemyTeam(roleId);
		if(teams != null && teams.size() > 0){
			//计算剩余到达剩余时间
			for (WarProduce warProduce : teams) {
				long lastTime = 0;
				try {
					lastTime = DateTimeUtil.getDiffer(DateTimeUtil.praseStringToDate(warProduce.getEnd_time()), new Date(), 1);
				} catch (Exception e) {
					
					try {
						warProduce.setEnd_time(DateTimeUtil.sdf.format(new Date(new Date().getTime() + 20000)));
						lastTime = 20;
						SqlToolsService.sqlQueue.put(new DaoVo("warMap.updateWarTeamInfo", warProduce, 2));
						CacheService.warTeamCache.put(new Element(warProduce.getWar_team_id(), warProduce));
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
				warProduce.setLastTime(lastTime);
			}
		}
		return teams;
	}
	
	/**
	 * 召回在外部队
	 * 
	 * @param team_id
	 *            队列ID
	 */
	public long callBackWarTeam(long team_id) throws Exception {
		
		List<DaoVo> daoVos = new ArrayList<DaoVo>();
		
		String condition = "";
		
		
		// 查看队列信息
		WarProduce produce = armsDeployDao.selectWarTeamById(team_id);
		if(produce == null)
			return 0 ;
		//出征模式
		int war_type = produce.getWar_type();
		//查看攻方 守方城邦信息
		City attCity = cityBuildDao.getCitysInfoByCondition(" where t.city_id ="+produce.getCity_id());
		
		if(war_type == Constants.WAR_TYPE_CONQUER || war_type == Constants.WAR_TYPE_ROB){
			//通知城邦战 防守方 攻方已退兵
			City defCity = cityBuildDao.getCitysInfoByCondition(" where t.city_id ="+produce.getTag_city_id());

			String mailInfo = "【战报】-军情概要：情报显示敌方部队已返回\r\n[所属城邦]:"+attCity.getName()+"("+attCity.getSite_x()+","+attCity.getSite_y()+")\r\n"+Constants.MUILT_SPLIT+"[敌方目标]:"+defCity.getName()+"("+produce.getTag_x()+","+produce.getTag_y()+")\r\n[状态]:返回中";
			daoVos.add(new DaoVo("mailMap.addNewMail", new Mail("【战报】-军情",-20l,defCity.getRole_id(),mailInfo,4,0),1));

		}else if(war_type == Constants.WAR_TYPE_WILDSRC_OFF || war_type == Constants.WAR_TYPE_WILDSRC_ON){
			//获取守方ID
			WildSrc defWildSrc = worldMapDao.getWildSrcInfo(" where t.tag_x = "+produce.getTag_x()+" and t.tag_y = "+produce.getTag_y()).get(0);
			
			if(defWildSrc.getOwner_id() != -10){
				String srcTypeStr = "资源点";
				switch (defWildSrc.getSrc_type()) {
				case 1:
					srcTypeStr = "农场";
					break;
				case 2:
					srcTypeStr = "伐木场";
					break;
				case 3:
					srcTypeStr = "采石场";
					break;
				default:
					break;
				}
				String mailInfo = "【战报】-军情概要：情报显示敌方部队已返回\r\n[所属城邦]:"+attCity.getName()+"("+attCity.getSite_x()+","+attCity.getSite_y()+")\r\n"+Constants.MUILT_SPLIT+"[敌方目标]:"+srcTypeStr+"("+produce.getTag_x()+","+produce.getTag_y()+")\r\n[状态]:返回中";
				daoVos.add(new DaoVo("mailMap.addNewMail", new Mail("【战报】-军情",-20l,defWildSrc.getOwner_id(),mailInfo,4,0),1));

			}
		}
		
		
		int srcStatus = produce.getStatus();
		
		//获取返回需要消耗时间
		long lastTime = DateTimeUtil.getDiffer(DateTimeUtil.praseStringToDate(produce.getEnd_time()), new Date(), 1) ;
		long backUseTime = produce.getUse_time() - (lastTime < 0 ? 0 : lastTime);
		// 队列状态更改 --返回城邦
		//缓存更新
		produce.setEnd_time(DateTimeUtil.getEndTime(new Date(), backUseTime));
		produce.setStatus( Constants.TEAM_STATUS_BACK);
		cacheService.putNewWarTeamToCache(produce);
		
		
		//DB层更新
		daoVos.add(new DaoVo("warMap.updateWarTeamInfo", produce, 2));
		
		//获取队伍状态 处理特殊队列状态
		if (srcStatus == Constants.TEAM_STATUS_RESIDCITY) {

			// 所属英雄状态变更 now_cityid = 0 is_free=2 在外
			condition = " t.is_free = "+Constants.HERO_STATUS_BATTLE+",t.now_cityid = 0 WHERE t.role_hero_id in(select t2.role_hero_id from sod.team_hero_tab t2 where t2.team_id ="
					+ team_id + ")";
			daoVos.add(new DaoVo("rolePropsMap.updateHerosStatus", condition, 2));
		} else if (srcStatus == Constants.TEAM_STATUS_RESIDSRC) {
			//野外资源点
			
			String srcHerosId = "";
			 condition = " t.team_id in ( SELECT t1.war_team_id FROM sod.war_team_tab t1 WHERE  t1.status = "+Constants.TEAM_STATUS_RESIDSRC+" AND t1.tag_x = "
							+ produce.getTag_x()
							+ " AND t1.tag_y ="
							+ produce.getTag_y()
							+ " AND t1.war_team_id <>"
							+ team_id + ")";
			List<WarProduce> teamHeros = armsDeployDao.selectHerosInTeam(condition);

			if(teamHeros.size() > 0 && teamHeros != null){//部队撤回后 资源点还有剩余部队
				for (WarProduce warProduce : teamHeros) {
					srcHerosId += warProduce.getRole_hero_id() + ",";
				}
				srcHerosId = srcHerosId.substring(0, srcHerosId.length() -1);
			}
			
			//重新构建资源点军事信息
			daoVos.add(new DaoVo("worldMap.updateWildSrcInfo", new WildSrc(produce.getTag_x(), produce.getTag_y(), srcHerosId, 2, produce.getRole_id()), 2));
		}
		
		toolsService.doSqlByBatch(daoVos);
		return backUseTime;
	}
	/**
	 * 获取军事单位详细信息
	 * @param condition
	 * @return
	 */
	public List<ArmsDeploy> selectArmsDetailInfo(String condition){
		List<ArmsDeploy> arms = null;
		try {
			arms = armsDeployDao.selectArmsDetailInfo(condition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arms;
	}
	
	/**
	 * 随机获取Arm_tab
	 * @param min_grade 随机Arm最低等级
	 * @param max_grade 随机Arm最高等级
	 * @param kinds 随机种类数
	 * @return
	 * @throws Exception
	 */
	private List<ArmsDeploy> selectWildHurtRandomInfo(int min_grade,int max_grade,int kinds) throws Exception{
		HashMap<String,Integer> conditionMap = new HashMap<String,Integer>();
		conditionMap.put("min_grade", min_grade);
		conditionMap.put("max_grade", max_grade);
		conditionMap.put("kinds", kinds);
		
		List<ArmsDeploy> arms = armsDeployDao.selectWildHurtRandomInfo(conditionMap);
		return arms;
	}
	
	/**
	 * 根据出征距离和英雄统帅值 构建野怪信息
	 * @param sum_command
	 * @param distance
	 * @return
	 * @throws Exception
	 */
	public List<CreepsDemon> makeCreepsInfo(int sum_command,int distance)throws Exception{
		List<CreepsDemon> creeps = new ArrayList<CreepsDemon>();
		int min_grade = 1;
		int max_grade = 1;
		int creepCount = 10;
		//根据狩猎距离确定野怪等级
		int creeGradeRandom = distance / 10;
		switch (creeGradeRandom) {
		case 0://distance:1-9
			min_grade = 1;
			max_grade = 2;
			break;
		case 1://distance:10-19
			min_grade = 2;
			max_grade = 3;
			break;
		case 2://distance:20-29
			min_grade = 3;
			max_grade = 4;
			break;
		case 3://distance:30-39
			min_grade = 4;
			max_grade = 5;
			break;
		default://distance>40
			min_grade = 5;
			max_grade = 5;
			break;
		}
		//确定本次野怪的种类数 1---3
		int kinds = new Random().nextInt(3)+1;
		List<ArmsDeploy> creeArms = selectWildHurtRandomInfo(min_grade, max_grade, kinds);
		//构建野怪信息
		for (ArmsDeploy armsDeploy : creeArms) {

			creepCount = (int) Math.ceil((double)(5 * sum_command * (15 + 5 * distance)) / (armsDeploy.getGrade() * armsDeploy.getGrade() * 900));
			creeps.add(new CreepsDemon(armsDeploy.getArm_id(), creepCount));
			
		}
		return creeps;
	}
}
