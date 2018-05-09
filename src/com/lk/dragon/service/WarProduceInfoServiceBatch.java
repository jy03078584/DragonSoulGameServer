/** 
 *
 * @Title: WarProduceInfoService.java 
 * @Package com.lk.dragon.service 
 * @Description:战斗模块
 * @author XiangMZh   
 * @date 2014-11-6 上午10:01:53 
 * @version V1.0   
 */
package com.lk.dragon.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.lk.dragon.db.dao.IArmsDeployDao;
import com.lk.dragon.db.dao.IBuffDao;
import com.lk.dragon.db.dao.ICityBuildDao;
import com.lk.dragon.db.dao.IFactionDao;
import com.lk.dragon.db.dao.IMailDao;
import com.lk.dragon.db.dao.IRelationDao;
import com.lk.dragon.db.dao.IRoleDao;
import com.lk.dragon.db.dao.IRolePropsDao;
import com.lk.dragon.db.dao.IWorldMapDao;
import com.lk.dragon.db.domain.ArmsDeploy;
import com.lk.dragon.db.domain.Attachment;
import com.lk.dragon.db.domain.BattleItem;
import com.lk.dragon.db.domain.BattleItemComparator;
import com.lk.dragon.db.domain.Buff;
import com.lk.dragon.db.domain.City;
import com.lk.dragon.db.domain.CityBuild;
import com.lk.dragon.db.domain.DaoVo;
import com.lk.dragon.db.domain.DropRate;
import com.lk.dragon.db.domain.Mail;
import com.lk.dragon.db.domain.Relation;
import com.lk.dragon.db.domain.Role;
import com.lk.dragon.db.domain.RoleHero;
import com.lk.dragon.db.domain.RoleProps;
import com.lk.dragon.db.domain.WarProduce;
import com.lk.dragon.db.domain.WildSrc;
import com.lk.dragon.server.domain.CreepsDemon;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.DateTimeUtil;
import com.lk.dragon.util.JSONUtil;


/**
 * @Description:战斗过程业务层 SQL操作 多线程直接处理SQL
 */
@Service
public class WarProduceInfoServiceBatch {

	@Autowired
	private IArmsDeployDao armsDeployDao;
	@Autowired
	private IFactionDao factionDao;
	@Autowired
	private IRoleDao roleDao;
	@Autowired
	private ICityBuildDao cityBuildDao;
	@Autowired
	private IRolePropsDao rolePropsDao;
	@Autowired
	private IRelationDao relationDao;
	@Autowired
	private IBuffDao buffDao;
	@Autowired
	private IMailDao mailDao;
	@Autowired
	private IWorldMapDao worldMapDao;
	@Autowired
	private MailInfoService mailInfoService;
	@Autowired
	private WarDeployInfoService warDeployInfoService;
	@Autowired
	private RolePropsInfoService rolePropsInfoService;
	@Autowired
	private CacheService cacheService;
	
	private List<DaoVo> daoVos;
	private Logger logger = Logger.getLogger(WarProduceInfoServiceBatch.class);

	// ------------------------------【战斗元素码】---------------------------------
	/** 英雄 **/
	private final static int BATTLE_HERO = 1;
	/** 已配备英雄部队 **/
	private final static int BATTLE_HERO_ARMS = 2;
	/** 城防 **/
	private final static int BATTLE_WALL = 3;
	/** 城内预备部队 **/
	private final static int BATTLE_CITY_ARMS = 4;
	/** 野怪NPC **/
	private final static int BATTLE_CREEP_ARMS = 5;
	/** 野外资源点增幅 **/
	//public final static int SRC_INCRE_COUNT = 200;

	

	@SuppressWarnings("unchecked")
	public List<DaoVo> pollWarTeamInfo(WarProduce warProduce) throws Exception {
		    daoVos = new ArrayList<DaoVo>();
			Map<String, Object> map = new HashMap<String, Object>();

			String warInfo = ""; // 记录战报信息
			int x = warProduce.getTag_x(); // 目标X坐标
			int y = warProduce.getTag_y(); // 目标Y坐标

			// ---------------------------------------【准备基本信息】---------------------------------------//
			// --------------------城邦战
			long attackRoleId = warProduce.getRole_id(); // 队列所属角色
			long defenRoleId = 0; // 目标城邦所属角色

			long att_faction_id = factionDao.selectFactionRight(attackRoleId) == null ? -1
					: factionDao.selectFactionRight(attackRoleId)
							.getFaction_id();
			warProduce.setFaction_id(att_faction_id);// 设置攻方所属帮会

			// --------------------狩猎战
			List<ArmsDeploy> creepArms = null; // 记录野怪转兵种信息
			String creepsArmsId = ""; // 记录野怪ID
			Map<String, Object> creepMap = null; // 记录野怪转为战斗元素信息

			// --------------------资源点争夺战
			Map<String, Object> srcDefenMap = null; // 记录当前资源点驻守信息
			WarProduce wildSrc = null;
			int owner_type = 0; // 资源当前所属角色
			int srcDefeHerosCount = 0; // 资源点驻扎英雄数
			String srcHerosIds = ""; // 资源点驻扎英雄ID
			// -------------------------------------------【准备基本信息】------------------------------------//

			map.put("properties", "t.role_hero_id");
			map.put("condition", "t.team_id = " + warProduce.getWar_team_id());

			// 队列返回出发城邦
			map.put("status", Constants.TEAM_STATUS_BACK);// 部队返回
			map.put("need_time", warProduce.getUse_time());// 返回出发城邦时间
			map.put("war_team_id", warProduce.getWar_team_id());

			// 队列现有英雄数
			List<RoleHero> attHeros = warProduce.getHeros();
			StringBuilder roleHerosId = new StringBuilder();
			for (RoleHero hero : attHeros) {
				roleHerosId.append(hero.getRole_hero_id() + ",");
			}
			roleHerosId.setLength(roleHerosId.length() - 1);
			// 英雄ID转化为字符串
			String role_heros_id = roleHerosId.toString();
			
			City tagCity = null;
			long tagCityId = 0;
			int warType = warProduce.getWar_type();

			
			//数据准备
			if(warProduce.getStatus()  != Constants.TEAM_STATUS_BACK){
				if (warType == Constants.WAR_TYPE_ROB
						|| warType == Constants.WAR_TYPE_CONQUER
						|| warType == Constants.WAR_TYPE_REFINCITY
						|| warType == Constants.WAR_TYPE_RESIDSELF) {
					// 获取目标城邦信息
					tagCity = cityBuildDao.getCityInfo(
							" WHERE t.site_x = " + x + " AND t.site_y = " + y).get(
							0);
					// 目标城邦所属角色
					defenRoleId = tagCity.getRole_id();
					tagCityId = tagCity.getCity_id();
				} else if (warType == Constants.WAR_TYPE_WILD) {// 获取野怪信息

					// 获取野怪信息
					List<CreepsDemon> creeps = JSONUtil.getCreepsObj(warProduce
							.getTag_arms());
					creepArms = new ArrayList<ArmsDeploy>();

					Map<String, Object> creepResMap = creepsJsonToArms(creeps);
					creepArms = (List<ArmsDeploy>) creepResMap.get("creArms");
					creepsArmsId = (String) creepResMap.get("creIds");

					creepMap = getWarTeamInfo(null, "A", 1, creepArms);

				}else {// 野外资源争夺 获取目标当前军事信息

					// 判断是资源点所属类型
					wildSrc = armsDeployDao.selectWildSrcInfo(
							" WHERE t.tag_x = " + x + " AND t.tag_y = " + y).get(0);
					wildSrc.setTag_x(x);
					wildSrc.setTag_y(y);
					owner_type = wildSrc.getOwner_type();
					if (owner_type == 1) {
						// 该资源点被野怪占据

						// 部队英雄总统帅值
						int sum_command = armsDeployDao.selectTotalCommand(warProduce.getWar_team_id());
						// 构建野怪信息
						List<CreepsDemon> creeps = warDeployInfoService.makeCreepsInfo(warProduce.getUse_time() / 60,
										sum_command);
						// 将野怪对象转化为战斗元素
						Map<String, Object> creepResMap = creepsJsonToArms(creeps);
						creepArms = (List<ArmsDeploy>) creepResMap.get("creArms");
						srcDefenMap = getWarTeamInfo(null, "A", 1, creepArms);
						srcDefeHerosCount = 1;
					} else {
						// 资源点被其他玩家占领
						srcHerosIds = wildSrc.getArm_info() == null ? "" : wildSrc .getArm_info();
						if (!srcHerosIds.equals("")) {
							// 资源点被其他玩家占领后驻扎信息以英雄ID字符串格式保存
							List<RoleHero> heros = rolePropsDao.getHeroPropertyByCondition(" t.role_hero_id in ("+ srcHerosIds + ")");
							srcDefenMap = getWarTeamInfo(heros, "A", 0, null);
							srcDefeHerosCount = heros.size();

						}
					}

				}
			}
		

			// ---------------------------------------该队列军事相信信息
			// 攻方此次参战英雄列表
			//List<RoleHero> heros = rolePropsDao.getHeroPropertyByCondition(" t.role_hero_id in (" + role_heros_id + ")");
			// 攻方此次参战英雄个数
			int attHeroCount = attHeros.size();
			// 构建该队列阵营列表
			Map<String, Object> attackMap = getWarTeamInfo(attHeros, "D", 0, null);


			// ---------------------------------检查队列状态
			switch (warProduce.getStatus()) {

			case Constants.TEAM_STATUS_GO:

				if (warType == Constants.WAR_TYPE_WILD) {// 目标：野外狩猎

					wildWar(warProduce, attackMap, creepMap, attHeroCount,
							role_heros_id, creepsArmsId,creepArms);

				} else if (warType == Constants.WAR_TYPE_WILDSRC_ON || warType == Constants.WAR_TYPE_WILDSRC_OFF) {// 目标:野外资源点

					// 判断攻方与守方角色关系
					if (checkIsSameSide(attackRoleId, wildSrc.getOwner_id(),warProduce, 2)) {
						break;
					} else {
						if(owner_type == 2){
							if (srcHerosIds.equals("")) {// 资源点现无军队驻扎
								String nowSrcHerosId = "";
								String srcTypeSql = "";// 资源更新SQL
								String srcTypeValue = "";// 资源点类型
								//int addSrcCount = wildSrc.getSrc_leve()* SRC_INCRE_COUNT;// 产量增幅数值
								int addSrcCount = wildSrc.getSrc_leve() == 1 ? 400 : 1200;
								long roleIdSrc = wildSrc.getOwner_id();//资源点原所属
								
								//资源点数目已达当前上限
								if(onWildSrcMax(attackRoleId)){
									teamStatusUpdateBack(warProduce);
									String mailInfo = "我方已达最大资源点数(每增加一座城池 可增加两处野外资源点)\r\n部队返回";
									constrDaoVo("mailMap.addNewMail", new Mail("【战报】-资源点攻占", -20l, warProduce.getRole_id(), mailInfo, 2, 0), 1);
								}else{
									// 部队状态变更
									if (warProduce.getWar_type() == Constants.WAR_TYPE_WILDSRC_ON) {
										//  驻守资源点
										warProduce.setStatus(Constants.TEAM_STATUS_RESIDSRC);
										//构建该资源点军事信息
										nowSrcHerosId = role_heros_id;
									} else {
										warProduce.setStatus(Constants.TEAM_STATUS_BACK);
										long lastTime = DateTimeUtil.getDiffer(DateTimeUtil.praseStringToDate(warProduce.getEnd_time()), new Date(), 1) ;
										long backUseTime = warProduce.getUse_time() - (lastTime < 0 ? 0 : lastTime);
										warProduce.setEnd_time(DateTimeUtil.getEndTime(new Date(), backUseTime));
									}
									//更新缓存
									//更改队列状态
									constrDaoVo( "warMap.updateWarTeamInfo",warProduce,2);
									// 资源点所属角色变更
									constrDaoVo("worldMap.updateWildSrcInfo", new WildSrc(wildSrc.getTag_x(), wildSrc.getTag_y(), nowSrcHerosId, 2, warProduce.getRole_id()),2);
									
									switch (wildSrc.getSrc_type()) {
									case 1:// 粮食资源点
										srcTypeSql = "yield_food";
										srcTypeValue = "[粮食产量]";
										break;
									case 2:// 木材资源点
										srcTypeSql = "yield_wood";
										srcTypeValue = "[木材产量]";
										break;
									case 3:// 石料资源点
										srcTypeSql = "yield_stone";
										srcTypeValue = "[石料产量]";
										break;
									default:
										break;
									}

									Map<String,Object> roleMap1 = new HashMap<String,Object>();
									// 攻方获取产量增加权
									roleMap1.put("role_id", warProduce.getRole_id());
									roleMap1.put("operator", "+");
									roleMap1.put(srcTypeSql, addSrcCount);
									//roleDao.sumPluRoleInfo(roleMap1);
									constrDaoVo("roleMap.sumPluRoleInfo", roleMap1,2);
									// 守方丢失产量加成
									Map<String,Object> roleMap2 = new HashMap<String,Object>();
									roleMap2.put("role_id", roleIdSrc);
									roleMap2.put("operator", "-");
									roleMap2.put(srcTypeSql, addSrcCount);
									constrDaoVo("roleMap.sumPluRoleInfo", roleMap2,2);
									//roleDao.sumPluRoleInfo(roleMap2);
									
									//发送战报信息
									String attSrcWarInfo = "我方攻占资源点[" + x + ","
											+ y + "]\r\n " + srcTypeValue + "增幅"
											+ addSrcCount;
									String defSrcWarInfo = "我方丧失资源点[" + x + ","
											+ y + "]\r\n " + srcTypeValue + "减幅"
											+ addSrcCount;
									constrDaoVo("mailMap.addNewMail", new Mail("【战报】-资源点攻占", -20l, warProduce.getRole_id(), attSrcWarInfo, 2, 0), 1);
									if(roleIdSrc > 0)
										constrDaoVo("mailMap.addNewMail",new Mail("【战报】-资源点沦陷", -20l, roleIdSrc, defSrcWarInfo, 2, 0),1);
								}
								
							} else {
								// 野外资源争夺 战斗模拟
								wildSrcWar(warProduce, attackMap, srcDefenMap,
										wildSrc, attHeroCount, srcDefeHerosCount,
										role_heros_id, srcHerosIds);
							}
						}else{
							// 野外资源争夺 战斗模拟
							wildSrcWar(warProduce, attackMap, srcDefenMap,
									wildSrc, attHeroCount, srcDefeHerosCount,
									role_heros_id, srcHerosIds);
						}
						
					}

				} else {// 目标：城邦
						// 判断攻方与守方角色关系
					if (checkIsSameSide(attackRoleId, defenRoleId, warProduce,
							1)) {
						break;
					} else {
						cityWar(warProduce, attackRoleId, defenRoleId, tagCity,
								attackMap, attHeroCount, role_heros_id);
					}

				}
				break;

			case Constants.TEAM_STATUS_BACK:// ------该队列状态为返回所属城邦

				String condition = " t.is_free =" + Constants.HERO_STATUS_FREE
						+ ",t.now_cityid =" + warProduce.getCity_id()
						+ " WHERE t.role_hero_id in (" + role_heros_id + ")";
				//更改队列英雄状态
				//rolePropsDao.updateHerosStatus(condition);
				constrDaoVo("rolePropsMap.updateHerosStatus", condition,2);
				//删除队列信息
				//armsDeployDao.deleteWarTeamInfo(warProduce.getWar_team_id());
				constrDaoVo("warMap.deleteWarTeamInfo", warProduce.getWar_team_id(),3);
				CacheService.warTeamCache.remove(warProduce.getWar_team_id());
				break;

			case Constants.TEAM_STATUS_REINFORCE:// ----该队列状态为增援
				if (warType == Constants.WAR_TYPE_REFINCITY) {
					// 城邦增援
					Relation relation = relationDao
							.checkRelationShip(new Relation(attackRoleId,
									defenRoleId));
					int relType = relation == null ? 0 : relation
							.getRelation_type();
					if (factionDao.checkIsSameFaction(" WHERE t.role_id = "
							+ defenRoleId + " AND t.faction_id = "
							+ warProduce.getFaction_id()) > 0
							|| relType == 1) {

						// 超过目标城邦允许最大驻扎增援数
						if (cityBuildDao.getCityYieldReinInfoByCityId(tagCityId)
								.getReinforce() + attHeroCount >= Constants.CITY_MAX_REFINFORCE) {

							// 发送战报
							warInfo = warProduce.getTag_name() + "(" + x + ","
									+ y + ")\r\n目标城邦增援数已满,部队返回";
							
							constrDaoVo("mailMap.addNewMail",new Mail("【战报】", -20l, attackRoleId, warInfo, 2, 0),1);
							
							teamStatusUpdateBack(warProduce);
						} else {

							// 构建战报:增援部队已到达
							String warHelpLeft = "部队已到达友方城邦 "
									+ warProduce.getTag_name() + "(" + x + ","
									+ y + ")\r\n【部队详情】："
									+ attackMap.get("warInfo");
							String warHelpRight = "友方:"
									+ roleDao.selectRolesByRoleId(attackRoleId)
											.getRole_name() + ",增援部队已到达"
									+ warProduce.getTag_name() + "(" + x + ","
									+ y + ")\r\n【部队详情】："
									+ attackMap.get("warInfo");
							warProduce.setStatus(Constants.TEAM_STATUS_RESIDCITY);
							//更改队列状态
							constrDaoVo( "warMap.updateWarTeamInfo", warProduce,2);
							
							//更改英雄状态
							String heroSql = "t.is_free ="
									+ Constants.HERO_STATUS_QUARTER
									+ ",t.now_cityid =" + tagCityId
									+ "  WHERE t.role_hero_id in ("
									+ role_heros_id + ")";
							constrDaoVo("rolePropsMap.updateHerosStatus", heroSql,2);
							//城邦增援数增加
							Map<String,Object> cityMap = new HashMap<String,Object>();
							cityMap.put("city_id", tagCityId);
							cityMap.put("condition", " t.reinforce = t.reinforce + "+attHeroCount);
							
							constrDaoVo("cityBuildMap.updateCityInfo", cityMap,2);

							//战报发送
							constrDaoVo("mailMap.addNewMail",new Mail("【战报】-驰援", -20l, attackRoleId, warHelpLeft, 2, 0),1);
							constrDaoVo("mailMap.addNewMail",new Mail("【战报】-驰援", -20l, defenRoleId, warHelpRight, 2, 0),1);
						}

					} else {// 目标不符合增援条件

						// 发送战报
						warInfo = warProduce.getTag_name()+ "(" + x + "," + y + ")\r\n目标城邦非盟友,部队返回";
						
						//队列返回
						teamStatusUpdateBack(warProduce);
						//发送邮件
						constrDaoVo("mailMap.addNewMail",new Mail("【战报】-驰援", -20l, attackRoleId, warInfo, 2, 0),1);

					}
				} else if (warType == Constants.WAR_TYPE_REFINSRC) {// 增援资源点

					String newSrcHerosId = srcHerosIds.equals("") ? ""
							: srcHerosIds + ",";
					if (attackRoleId == wildSrc.getOwner_id()) {// 满足增援条件

						map.put("status", Constants.TEAM_STATUS_RESIDSRC);
						map.put("need_time", 0);
						warInfo = "增援队列到达资源点" + "(" + x + "," + y
								+ ")\r\n[队列详情]\r\n";
						warInfo = (String) attackMap.get("warInfo");
						// srcHerosIds 更新驻军信息
						String newArmInfo = newSrcHerosId + role_heros_id;
						constrDaoVo("worldMap.updateWildSrcInfo", new WildSrc(x,y, newArmInfo, 2, warProduce.getRole_id()),2);
						warProduce.setStatus(Constants.TEAM_STATUS_RESIDSRC);
					} else {
						// 目标非已方资源点 不满足资源条件 部队返回
						warInfo = "资源点" + "(" + x + "," + y
								+ ")不满足增援条件 部队返回";
						
						//获取返回需要消耗时间
						long lastTime = DateTimeUtil.getDiffer(DateTimeUtil.praseStringToDate(warProduce.getEnd_time()), new Date(), 1) ;
						long backUseTime = warProduce.getUse_time() - (lastTime < 0 ? 0 : lastTime);
						// 队列状态更改 --返回城邦
						//缓存更新
						warProduce.setEnd_time(DateTimeUtil.getEndTime(new Date(), backUseTime));
						warProduce.setStatus( Constants.TEAM_STATUS_BACK);
					}
					
					constrDaoVo( "warMap.updateWarTeamInfo", warProduce,2);
					constrDaoVo("mailMap.addNewMail",new Mail("【战报】-驰援", -20l, attackRoleId, warInfo, 2, 0),1);
				} else if (warType == Constants.WAR_TYPE_RESIDSELF) {// 向已方其他城邦转移部队

					if (attackRoleId == defenRoleId) {
						// 目标城邦所属角色合法

						warInfo = "派遣队列到达目标城邦" + "(" + x + "," + y+ ")";
						//更改队列英雄状态
						String herosInfoSql = "t.is_free = 1 ,t.now_cityid = "
								+ tagCityId + ",t.city_id=" + tagCityId
								+ " WHERE t.role_hero_id in ("
								+ role_heros_id + ")";
						constrDaoVo("rolePropsMap.updateHerosStatus", herosInfoSql,2);
						// 发送邮件 通知玩家派遣结束
						constrDaoVo("mailMap.addNewMail",new Mail("【军情】-派遣", -20l, attackRoleId, warInfo, 2, 0),1);
						//删除队列信息
						constrDaoVo("warMap.deleteWarTeamInfo", warProduce.getWar_team_id(),3);
						CacheService.warTeamCache.remove(warProduce.getWar_team_id());
					} else {
						warInfo = "目标城邦" + "(" + x + "," + y + ")非我方城邦 部队返回";
						// 发送邮件 通知玩家派遣结束
						constrDaoVo("mailMap.addNewMail",new Mail("【军情】-派遣", -20l, attackRoleId, warInfo, 2, 0),1);

						// 更改队列状态 返回城邦
						teamStatusUpdateBack(warProduce);
					}
				}
				break;
			default:
				break;
			}
			
			
			return daoVos;
	}

	/**
	 * 野外资源争夺战
	 * 
	 * @param warProduce
	 *            攻方队列信息
	 * @param attackMap
	 *            攻方战斗元素信息
	 * @param srcDefenMap
	 *            守方战斗元素信息
	 * @param wildSrc
	 *            资源点信息
	 * @param attHeroCount
	 *            攻方参战英雄
	 * @param srcDefeHerosCount
	 *            守方参战英雄数(守方为野怪时为0)
	 * @param role_heros_id
	 *            攻方参战英雄ID
	 * @param srcHerosIds
	 *            守方参战英雄ID
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void wildSrcWar(WarProduce warProduce,
			Map<String, Object> attackMap, Map<String, Object> srcDefenMap,
			WarProduce wildSrc, int attHeroCount, int srcDefeHerosCount,
			String role_heros_id, String srcHerosIds) throws Exception {
		
		//
		if(onWildSrcMax(warProduce.getRole_id())){
			teamStatusUpdateBack(warProduce);
			String mailInfo = "我方已达最大资源点数(每增加一座城池 可增加两处野外资源点)\r\n部队返回";
			constrDaoVo("mailMap.addNewMail", new Mail("【战报】-资源点攻占", -20l, warProduce.getRole_id(), mailInfo, 2, 0), 1);
			return;
		}else{
			// --------模拟战斗过程--------
			int x = warProduce.getTag_x();
			int y = warProduce.getTag_y();
			// 记录战报信息
			String warInfoHead = "资源点(" + x + "," + y + ")\r\n";
			String warInfo = "";
			String warInfoBegAtt = "";
			String warInfoBegDef = "";
			String warInfoEnd = "\r\n【战斗奖励】\r\n";
			String deadHerosId = "";

			Map<String, Object> endMap;
			int getExpAttack = 0;
			int getExpDefen = 0;

		
			// 构建攻方阵营部队列表
			List<BattleItem> attackList = (List<BattleItem>) attackMap
					.get("warTeamList");
			List<BattleItem> attackListTemp = cloneTempBattleItem(attackList);
			int sumExpAttens = (Integer) attackMap.get("sumExp");// 攻方初始总经验值

			// 构建守方阵营列表
			List<BattleItem> defenList = (List<BattleItem>) srcDefenMap
					.get("warTeamList");
			List<BattleItem> defenListTemp = cloneTempBattleItem(defenList);
			int sumExpDefens = (Integer) srcDefenMap.get("sumExp");// 目标初始总经验

			// 发送战报:目标城邦处于保护时间类
			warInfo = "\r\n【战前兵力对比】\r\n\r\n" + attackMap.get("warInfo")
					+ Constants.SINGLE_SPLIT + srcDefenMap.get("warInfo") +  "\r\n【战后兵力对比】\r\n\r\n";
			// 模拟双方战斗
			Map<String, Object> warEndMap = battleProduce(attackList, defenList);
			List<BattleItem> allArms = (List<BattleItem>) warEndMap.get("key_item");

			// 记录SQL语句
			String sql = "";
			// List<String> sqlList = new ArrayList<String>();

			for (BattleItem battleItem : allArms) {
				if (battleItem.getType() == BATTLE_HERO) {
					String status = "";
					int hp = battleItem.getItemSumHp();
					if (hp == 0) {
						deadHerosId += battleItem.getKey_id() + ",";
						status = "t.hp = 0,t.is_free =" + Constants.HERO_STATUS_DEAD
								+ ",t.now_cityid = t.city_id";
						sql = status+ " WHERE t.role_hero_id = " + battleItem.getKey_id();
						//rolePropsDao.updateHerosStatus(sql);
						constrDaoVo("rolePropsMap.updateHerosStatus", sql,2);
					}

				} else if (battleItem.getType() == BATTLE_HERO_ARMS) {
					sql = " t.count = "
							+ battleItem.getItem_count_now()
							+ " WHERE t.hero_arms_id = " + battleItem.getKey_id();
					//armsDeployDao.updateHeroArms(sql);
					constrDaoVo("warMap.updateHeroArms", sql,2);
				}

			}
			// 战斗结束 结算双方KDA
			if (warEndMap.get("key_flag").equals("D")) {

				String srcTypeSql = "";
				String srcTypeValue = ""; // 资源点类型 中文
				int addSrcCount = 400; // 产量
				if (wildSrc.getSrc_leve() == 2) {
					addSrcCount = 1200;
				}

				// 攻方胜
				warInfoBegAtt = "【胜】我方已占领资源点";
				warInfoBegDef = "【败】我方资源点被攻陷";
				endMap = toCreateWarInfoEnd(attackListTemp, attackList);
				warInfo += endMap.get("warMail") + Constants.SINGLE_SPLIT
						+ "守方全军覆灭";
				getExpAttack = (int) Math
						.ceil((double) sumExpDefens / attHeroCount);
				getExpDefen = (int) Math.ceil((double) sumExpAttens
						- (Integer) endMap.get("lastExp") / srcDefeHerosCount);
				String liveH = (String) endMap.get("liveHeroId");

				// 部队状态变更
				if (warProduce.getWar_type() == Constants.WAR_TYPE_WILDSRC_ON) {
					// 更改队列状态 驻守资源点
					warProduce.setStatus(Constants.TEAM_STATUS_RESIDSRC);
				} else {
					// 更改队列状态 部队返回
					//获取返回需要消耗时间
					long lastTime = DateTimeUtil.getDiffer(DateTimeUtil.praseStringToDate(warProduce.getEnd_time()), new Date(), 1) ;
					long backUseTime = warProduce.getUse_time() - (lastTime < 0 ? 0 : lastTime);
					// 队列状态更改 --返回城邦
					//缓存更新
					warProduce.setEnd_time(DateTimeUtil.getEndTime(new Date(), backUseTime));
					warProduce.setStatus( Constants.TEAM_STATUS_BACK);
					liveH = "";
				}
				constrDaoVo("warMap.updateWarTeamInfo", warProduce,2);

				switch (wildSrc.getSrc_type()) {
				case 1:// 粮食资源点
					srcTypeSql = "yield_food";
					srcTypeValue = "[粮食产量]";
					break;
				case 2:// 木材资源点
					srcTypeSql = "yield_wood";
					srcTypeValue = "[木材产量]";
					break;
				case 3:// 石料资源点
					srcTypeSql = "yield_stone";
					srcTypeValue = "[石料产量]";
					break;
				default:
					break;
				}

				warInfoBegAtt += srcTypeValue + "提高" + addSrcCount;
				warInfoBegDef += srcTypeValue + "降低" + addSrcCount;

				Map<String,Object> roleMap1 = new HashMap<String,Object>();
				// 攻方获取产量增加权
				roleMap1.put("role_id", warProduce.getRole_id());
				roleMap1.put("operator", "+");
				roleMap1.put(srcTypeSql, addSrcCount);
				constrDaoVo("roleMap.sumPluRoleInfo", roleMap1,2);
				
				// 守方丢失产量加成
				Map<String,Object> roleMap2 = new HashMap<String,Object>();
				roleMap2.put("role_id", wildSrc.getOwner_id());
				roleMap2.put("operator", "-");
				roleMap2.put(srcTypeSql, addSrcCount);
				constrDaoVo("roleMap.sumPluRoleInfo", roleMap2,2);
				
				// 资源点所属角色变更
				constrDaoVo("worldMap.updateWildSrcInfo", new WildSrc(wildSrc.getTag_x(), wildSrc.getTag_y()	, liveH, 2, warProduce.getRole_id()),2);
			} else {
				// 守方胜
				warInfoBegAtt = "【败】我方攻占资源点失败";
				warInfoBegDef = "【胜】我方成功戍守资源点";
				endMap = toCreateWarInfoEnd(defenListTemp, defenList);
				warInfo += "\r\n攻方全军覆灭" + "\r\n" + Constants.SINGLE_SPLIT
						+ endMap.get("warMail");
				getExpAttack = (int) Math.ceil((double) sumExpDefens
						- (Integer) endMap.get("lastExp") / attHeroCount);
				getExpDefen = (int) Math.ceil((double) sumExpDefens
						/ srcDefeHerosCount);
				String liveSrcHeros = (String) endMap.get("liveHeroId");
				// 更新驻军信息
				// 资源点所属角色变更
				constrDaoVo("worldMap.updateWildSrcInfo", new WildSrc(wildSrc.getTag_x(), wildSrc.getTag_y(), liveSrcHeros, 2, wildSrc.getOwner_id()),2);

			}

			warInfoEnd += "攻方英雄经验值+" + getExpAttack;
			//更改队列英雄状态
			String heroSql = " t.hero_exp = t.hero_exp +"
					+ getExpAttack + " WHERE t.role_hero_id in (" + role_heros_id
					+ ")";
			//rolePropsDao.updateHerosStatus(heroSql);
			constrDaoVo("rolePropsMap.updateHerosStatus", heroSql,2);
			String sumAttWarInfo = warInfoHead + warInfoBegAtt + warInfo + warInfoEnd;

			constrDaoVo("mailMap.addNewMail",new Mail("【战报】-资源点攻占", -20l, warProduce.getRole_id(), sumAttWarInfo, 2, 0),1);
			//constrDaoVo(mailDao, "addNewMail", new Mail("【战报】-资源点攻占", -20l, warProduce.getRole_id(), warInfo, 2, 0));
			if (wildSrc.getOwner_type() == 2) {
				warInfoEnd += "\r\n守方英雄经验值+" + getExpDefen;
				//更改队列英雄状态
				String heroSql2 = " t.hero_exp = t.hero_exp +"
						+ getExpDefen + " WHERE t.role_hero_id in (" + srcHerosIds
						+ ")";
				//rolePropsDao.updateHerosStatus(heroSql2);
				constrDaoVo("rolePropsMap.updateHerosStatus", heroSql2,2);
				
				String sumDefWarInfo = warInfoHead+warInfo + warInfoBegDef + warInfoEnd;
				constrDaoVo("mailMap.addNewMail",new Mail("【战报】-资源点沦陷", -20l, wildSrc.getOwner_id(), sumDefWarInfo, 2, 0),1);
				//constrDaoVo(mailDao, "addNewMail", new Mail("【战报】-资源点沦陷", -20l, wildSrc.getOwner_id(), warInfoDefSrc, 2, 0));
			}

			// 判断队列是否需要删除 测试期屏蔽
			if (deadHerosId.length() > 0) {
				checkDelTeam(deadHerosId);
			}
		}
		

	}

	/**
	 * 判断是否需要删除英雄所在队列
	 * 
	 * @param deadHerosId
	 * @param updateMap
	 * @throws Exception
	 */
	private void checkDelTeam(String deadHerosId) throws Exception {
		// 获取阵亡英雄所在队列ID
		String condition = "t.role_hero_id in ("
				+ deadHerosId.substring(0, deadHerosId.length() - 1) + ")";
		List<Long> lTeamIds = armsDeployDao.selectDistintTeamId(condition);
		if (lTeamIds != null && lTeamIds.size() > 0) {
			String teamsId = "";
			for (long tid : lTeamIds) {
				teamsId += tid + ",";
			}
			condition =" t.role_hero_id in("+ deadHerosId.substring(0,deadHerosId.length() - 1) + ")";
			// 队列-英雄表中删除已阵亡英雄
			//armsDeployDao.deleteTeamHeroInfo(condition);
			constrDaoVo("warMap.deleteTeamHeroInfo", condition,3);
			// 判断是否需要删除队列信息
			//armsDeployDao.callCheckTeamHero(teamsId.substring(0,teamsId.length() - 1));
			Map<String,String> callMap = new HashMap<String,String>();
			callMap.put("dead_teams_id", teamsId.substring(0,teamsId.length() - 1));
			constrDaoVo("warMap.callCheckTeamHero", callMap,4);
		}
	}

	/***
	 * 野外狩猎战
	 * 
	 * @param warProduce
	 * @param attackMap
	 * @param creepMap
	 * @param herosCount
	 * @param role_heros_id
	 * @param creepIds
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void wildWar(WarProduce warProduce, Map<String, Object> attackMap,
			Map<String, Object> creepMap, int herosCount, String role_heros_id,
			String creepsArmsId,List<ArmsDeploy> creeArms) throws Exception {
		
		
		int x = warProduce.getTag_x();
		int y = warProduce.getTag_y();
		// 记录战报信息
		String warInfoHead = warProduce.getTag_name() + "("
				+ x + "," + y + ")";
		String warInfo = "";
		String deadHerosId = "";
		// 邮件业务 用于战报发送
		Mail mail = new Mail();
		mail.setMail_title("【战报】-野外狩猎");
		mail.setMail_from(-20l);
		mail.setMail_to(warProduce.getRole_id());
		mail.setMail_type(2);
		mail.setHas_attachments(0);

		// 构建攻方阵营部队列表
		List<BattleItem> attackList = (List<BattleItem>) attackMap
				.get("warTeamList");
		List<BattleItem> attackListTemp = cloneTempBattleItem(attackList);
		int getExpHero = 0;// 英雄战后获得经验
		int getGold = 0;//玩家战后获得金币
		// 构建野怪阵营列表
		List<BattleItem> creepList = (List<BattleItem>) creepMap
				.get("warTeamList");
		List<BattleItem> creepListTemp = cloneTempBattleItem(creepList);
		int sumExpCreeps = (Integer) creepMap.get("sumExp");// 目标初始总经验

		Map<String, Object> endMap;

		// 发送战报
		warInfo = "\r\n【战前兵力对比】\r\n" + attackMap.get("warInfo")
				+ Constants.SINGLE_SPLIT + creepMap.get("warInfo")+ "\r\n【战后兵力对比】\r\n";

		// 模拟双方战斗
		Map<String, Object> warEndMap = battleProduce(attackList, creepList);
		List<BattleItem> allArms = (List<BattleItem>) warEndMap.get("key_item");
		for (BattleItem battleItem : allArms) {
			if (battleItem.getType() == BATTLE_HERO) {
				String status = "";
				int hp = battleItem.getItemSumHp();
				if (hp == 0) {
					deadHerosId += battleItem.getKey_id() + ",";
					status = "t.hp = 0,t.is_free =" + Constants.HERO_STATUS_DEAD
							+ ",t.now_cityid = t.city_id";
					String	heroSql = status+ " WHERE t.role_hero_id = " + battleItem.getKey_id();
					//rolePropsDao.updateHerosStatus(sql);
					constrDaoVo("rolePropsMap.updateHerosStatus", heroSql,2);
				}
				
			} else if (battleItem.getType() == BATTLE_HERO_ARMS) {
//				armsDeployDao.updateHeroArms(" t.count = "
//						+ battleItem.getItem_count_now()
//						+ " WHERE t.hero_arms_id = " + battleItem.getKey_id());
				constrDaoVo("warMap.updateHeroArms", " t.count = "
						+ battleItem.getItem_count_now()
						+ " WHERE t.hero_arms_id = " + battleItem.getKey_id(),2);
			}
		}
		if (warEndMap.get("key_flag").equals("D")) {// 野怪败
			warInfoHead += " 【胜】我方已成功肃清野外";
			endMap = toCreateWarInfoEnd(attackListTemp, attackList);
			warInfo += endMap.get("warMail") + Constants.SINGLE_SPLIT
					+ "对方已被肃清";
			getExpHero = (int) Math.ceil((double) sumExpCreeps / herosCount); // 每个英雄获得经验
			
			//此次野外狩猎获得金币
			getGold = (int) Math.ceil((double) sumExpCreeps * 0.55);
			// 掉落物品
			mail.setAttachments(spoilsWildWar(creepsArmsId,creeArms));
			mail.setHas_attachments(1);
			
			// 狩猎结束 队伍返回 状态更改 队列返回出发城邦
			teamStatusUpdateBack(warProduce);
		} else {
			warInfoHead += " 【败】我方不敌 狩猎失败";
			endMap = toCreateWarInfoEnd(creepListTemp, creepList);
			warInfo += "我方全军覆没\r\n" + Constants.SINGLE_SPLIT
					+ endMap.get("warMail");
			//单个英雄获得经验值
			getExpHero = (int) Math
					.ceil((double) (sumExpCreeps - (Integer) endMap
							.get("lastExp")) / herosCount);
			//此次野外狩猎获得金币
			getGold = (int) Math.ceil((double) getExpHero * herosCount * 0.55);
			deadHerosId = "";

			// 狩猎失败 英雄阵亡返回酒馆 队列删除
			//armsDeployDao.deleteWarTeamInfo(warProduce.getWar_team_id());
			constrDaoVo("warMap.deleteWarTeamInfo", warProduce.getWar_team_id(),3);
			CacheService.warTeamCache.remove(warProduce.getWar_team_id());
		}

		warInfo = warInfoHead + warInfo + "\r\n\r\n【战斗奖励】\r\n" + "英雄经验值+" + getExpHero+"\r\n获取金币量:"+getGold;
		
		//英雄经验数值增加
		String sql = " t.hero_exp = t.hero_exp +"
				+ getExpHero + " WHERE t.role_hero_id in(" + role_heros_id
				+ ")";
		
		//更改队列英雄状态
		//rolePropsDao.updateHerosStatus(sql);
		constrDaoVo("rolePropsMap.updateHerosStatus", sql,2);
		//角色金币数值增加
		Map<String,Object> ibatisMap = new HashMap<String,Object>();
		ibatisMap.put("operator", "+");
		ibatisMap.put("role_id", warProduce.getRole_id());
		ibatisMap.put("gold", getGold);
		//roleDao.sumPluRoleInfo(ibatisMap);
		constrDaoVo("roleMap.sumPluRoleInfo", ibatisMap,2);
		
		// 从队列中删除阵亡英雄
		if (deadHerosId.trim().length() > 0) {
			// 判断是否需要删除英雄所在队列
			checkDelTeam(deadHerosId);
		}
		
		mail.setMail_content(warInfo);
		// 发送战报
		long mailId = mailDao.getMailSeq();
		mail.setMail_id(mailId);
		constrDaoVo("mailMap.addNewMailIncludeId", mail,1);
		if(mail.getAttachments()!= null && mail.getAttachments().size() > 0){
			for (Attachment attachment : mail.getAttachments()) {
				attachment.setMail_id(mailId);
				constrDaoVo("mailMap.addNewAttachment", attachment,1);
			}
		}
		//mailDao.addNewAttachment(attachment);
		//mailDao.addNewMailIncludeId(mail);
//		mailInfoService.addNewMailByService(mail);
//		constrDaoVo(mailInfoService, "addNewMailByService", mail);
	}

	/**
	 * 随机野外狩猎 掉落物品
	 * 
	 * @param warEndMap
	 * @param warInfo
	 */
	private List<Attachment> spoilsWildWar(String creepsArmsId,List<ArmsDeploy> creeArms)throws Exception {
		List<Attachment> attachments = new ArrayList<Attachment>();
		
		
	//-----------------【处理随机掉落物品】-------------------------
		// 掉落次数 1--3次掉落
		int dropTime = new Random().nextInt(3) + 1;
		String[] creepsIds = creepsArmsId.split(",");
		for (int i = 0; i < dropTime; i++) {
			//
			int dropRand = new Random().nextInt(creepsIds.length);
			int dropCreepId = Integer.valueOf(creepsIds[dropRand]);// 获取本次掉落来自
																	// NPC-ID
				int rateMax = (Integer) rolePropsDao
						.getNpcDropRateMax(dropCreepId);
				if (rateMax == 0)
					continue;
				// 获取随机点数
				int rateRandom = new Random().nextInt(rateMax); // 随机0<--->rateMax-1
				// 获取本次掉落物品信息
				RoleProps dropProps = rolePropsDao.getNpcDropInfo(new DropRate(
						dropCreepId, rateRandom));
				if (dropProps == null)// 本轮随机数处于不掉落装备概率内
					continue;
				Attachment attachment = new Attachment();// 附件对象
				attachment.setRole_prop_id(-1l);
				attachment.setCounts(1);
				attachment.setProps_id(dropProps.getProps_id());
				
				// ------------------针对装备类道具特殊处理：随机属性
				if (dropProps.getProps_type() == Constants.PROP_EQUIP) {// 掉落物品为装备类道具
					//根据掉落装备等级 随机增益属性
					String inc_property = rolePropsInfoService.makeEquipRandProperty(dropProps.getProps_id());
					attachment.setExtra_info(inc_property);
					
				}// --道具为装备

				attachments.add(attachment);
		}
		
	//-----------------【处理日常任务掉落所需物品】-------------------------
		//必掉任务道具
		Attachment attachTask = new Attachment();
		attachTask.setRole_prop_id(-1l);
		attachTask.setProps_id(271);//任务道具固定ID
		
		int attTaskCount = 0;
		//根据野怪阶级与数量 获取本次掉落任务道具的数量
		if(creeArms!=null && creeArms.size() > 0){
			double ratePara;
			for (ArmsDeploy armsDeploy : creeArms) {
				switch (armsDeploy.getGrade()) {
				case 1:
					ratePara = 10;
					break;
				case 2:
					ratePara = 5;
					break;
				case 3:
					ratePara = 10/3;
					break;
				case 4:
					ratePara = 5/2;
					break;
				case 5:
					ratePara = 2;
					break;
				default:
					ratePara = armsDeploy.getArm_count();
					break;
				}
				attTaskCount += Math.ceil(armsDeploy.getArm_count()/ratePara);
			}
		}
		attachTask.setCounts(attTaskCount == 0 ? 1 : attTaskCount);
		//任务道具加入总附件
		attachments.add(attachTask);
	
		return attachments;
	}

	/**
	 * 城邦攻防战
	 * 
	 * @param warProduce
	 *            队列信息
	 * @param attackRoleId
	 *            攻防角色ID
	 * @param defenRoleId
	 *            守方ID
	 * @param tagCity
	 *            目标城邦
	 * @param attackMap
	 *            转换后的攻方元素
	 * @param attHeroCount
	 *            攻方英雄数
	 * @param role_heros_id
	 *            功方英雄ID字符串
	 */
	@SuppressWarnings("unchecked")
	private void cityWar(WarProduce warProduce, long attackRoleId,
			long defenRoleId, City tagCity, Map<String, Object> attackMap,
			int attHeroCount, String role_heros_id) throws Exception {

	
		

		// 战斗城邦ID
		long tagCityId = tagCity.getCity_id();
		// 记录战报信息
		String warInfo = "";
		// 邮件业务 用于战报发送
		// 构建攻方阵营部队列表
		List<BattleItem> attackList = (List<BattleItem>) attackMap
				.get("warTeamList");
		List<BattleItem> attackListTemp = cloneTempBattleItem(attackList);

		// 战斗模式 征服/掠夺
		int war_type = warProduce.getWar_type();
		
		//验证城邦当前是否处于保护时间内
		List<Buff> cityBuff = buffDao.getBuffInfo(new Buff(RolePropsInfoService.PROPS_ID_CITY_PROTECT, tagCityId));
		System.out.println("CITYBUFF iS NULL:"+(cityBuff == null)+" CITYBUFF SIZE:"+cityBuff.size());
		// ----------------------对方处于保护时间内 部队返回
		if (cityBuff != null && cityBuff.size() > 0) {
			if(cityBuff.get(0).getLastSecond() >= 0){
				warInfo = warProduce.getTag_name() + "("
						+ warProduce.getTag_x() + "," + warProduce.getTag_y()
						+ ")攻防战\r\n目标已使用[神域屏障] 城邦处于保护时间内  部队返回";

				//constrDaoVo(mailDao, "addNewMail", new Mail("【战报】", -20l, attackRoleId, warInfo, 2, 0));
				constrDaoVo("mailMap.addNewMail",new Mail("【战报】", -20l, attackRoleId, warInfo, 2, 0),1);
				// 发送战报:目标城邦处于保护时间类
				// 修改队伍状态
				teamStatusUpdateBack(warProduce);
				return;
			}
			
		}

		// ----------------------开始城邦攻防战
		Set<Long> defendsRoleSet = new HashSet<Long>();
		// 双方兵力总经验值
		int sumAttackExp = 0;
		int sumDefenExp = 0;

		// 模拟城邦攻防战
		int cityWallLev = 0;
		// 查询目标城邦城墙等级
		List<CityBuild> cityWalls = cityBuildDao
				.selectBuildInfo(",(select c.city_id, c.race from sod.city_tab c where c.city_id ="
						+ tagCityId
						+ " ) c  where t.city_id = c.city_id and t.bulid_id = (select b.bulid_id from sod.bulid_tab b where b.race = c.race and b.type = 16)");
		if (cityWalls.size() > 0 && cityWalls != null) {
			cityWallLev = cityWalls.get(0).getCurr_lev();
		}
		// 城墙
		List<BattleItem> cityWallList = new ArrayList<BattleItem>();

		warInfo = "\r\n【战后兵力对比】\r\n";

		String warInfoBefore =warProduce.getTag_name() + "("
				+ warProduce.getTag_x() + "," + warProduce.getTag_y()
				+ ")攻防战\r\n" + "【战前兵力对比】\r\n";
		String warInfoEnd = "\r\n【城墙破损度】:";

		if (cityWallLev == 0) {// 守方没有城墙或城墙等级为0
			warInfoBefore += (String) attackMap.get("warInfo")
					+ Constants.SINGLE_SPLIT + "守方城墙等级过低 丧失外城防御能力\r\n";
			warInfoEnd += "100%  ----城防等级下降----";

		} else {// 守方有城防设施
			int race = tagCity.getRace();
			int wallHp = 1000 * (cityWallLev + 1);
			int wallMp = 1000 * (cityWallLev + 1);
			
			int wallPhyDef = 100 * (cityWallLev + 1);
			int wallMegDef = 100 * (cityWallLev + 1);
			int wallPhyAtt;
			int wallMegAtt;
			
			warInfoBefore += (String) attackMap.get("warInfo")
					+ Constants.SINGLE_SPLIT
					+ "城墙<icon>{\"url\":\"citywall.png\",\"count\":1}</icon> LEVEL:"+cityWallLev+" HP:"+wallHp+" MP:"+wallMp+"\r\n";
			/**********************************************************************
			 * ----------------------城墙WALL 属性值--------------------- *
			 **********************************************************************/


			// 种族对城墙攻击模式加成
			if (Constants.RACE_HUM == race || Constants.RACE_ORC == race
					|| Constants.RACE_MEG == race) {
				wallPhyAtt = 100 * (cityWallLev + 1);
				wallMegAtt = 80 * (cityWallLev + 1);
			} else {
				wallPhyAtt = 80 * (cityWallLev + 1);
				wallMegAtt = 100 * (cityWallLev + 1);
			}
			BattleItem cityWallItem = new BattleItem(cityWalls.get(0)
					.getRela_id(), "城墙", BATTLE_WALL, wallPhyAtt, wallPhyDef,
					wallMegAtt, wallMegDef, wallHp, wallMp, 0, 1, "A", wallHp,
					1);
			cityWallItem.setIcon("citywall.png");
			cityWallList.add(cityWallItem);

			// 模拟攻方与守方城墙战斗过程
			Map<String, Object> battleMap = battleProduce(attackList,
					cityWallList);
			if (battleMap.get("key_flag") == "D") {// 城防破
				warInfoEnd += "100%  ----城防等级下降----";
				if (cityWallLev >= 2) {
					// 城墙等级下降
					Map<String,Object> cityMap = new HashMap<String,Object>();
					cityMap.put("condition", " t.curr_lev = t.curr_lev - 1"); 
					cityMap.put("rela_id", cityWalls.get(0).getRela_id()); 
					//cityBuildDao.updateCityBuidInfo(cityMap);
					constrDaoVo("cityBuildMap.updateCityBuidInfo", cityMap,2);
				}
			} else {
				warInfoEnd = "攻方于外城城墙攻防战中全军覆灭\r\n" + Constants.SINGLE_SPLIT
						+ warInfoEnd + cityWallItem.getItemSumHp() * 100
						/ cityWallItem.getHp() + "%";
				List<BattleItem> allArms = (List<BattleItem>) battleMap
						.get("key_item");
				for (BattleItem battleItem : allArms) {
					if (battleItem.getAttact_defen_flag().equals("D")) {// 进攻方
						String sql = "";
						if (battleItem.getType() == BATTLE_HERO) {// 英雄
							sql = " t.now_cityid = t.city_id,t.hp = 0,t.is_free ="
									+ Constants.HERO_STATUS_DEAD
									+ "WHERE t.role_hero_id = "
									+ battleItem.getKey_id();
							//rolePropsDao.updateHerosStatus(sql);
							constrDaoVo("rolePropsMap.updateHerosStatus", sql,2);
						} else {
							sql = " t.count = 0 WHERE t.hero_arms_id = "+ battleItem.getKey_id();
							//armsDeployDao.updateHeroArms(sql);
							constrDaoVo("warMap.updateHeroArms", sql,2);
						}

					}

				}

				// 最终战报
				warInfo = warInfoBefore + warInfo + warInfoEnd;
				constrDaoVo("mailMap.addNewMail",new Mail("【战报】", -20l, attackRoleId, warInfo, 2, 0),1);
				constrDaoVo("mailMap.addNewMail",new Mail("【战报】", -20l, defenRoleId, warInfo, 2, 0),1);
				// 全军覆没 删除该队列
				constrDaoVo("warMap.deleteWarTeamInfo", warProduce.getWar_team_id(),3);
				
				CacheService.warTeamCache.remove(warProduce.getWar_team_id());
				return;
			}// 攻防未能突破城墙

		}// 城墙攻防战结束

		// --------【双方进入内城攻防战】

		// 计算攻方剩余部队总经验值
		for (BattleItem battleItem : attackList) {
			sumAttackExp += battleItem.getExp()
					* battleItem.getItem_count_now();
		}

		String conditoion = " t.now_cityid = " + tagCityId
				+ " AND (t.is_free =" + Constants.HERO_STATUS_FREE
				+ "OR t.is_free = " + Constants.HERO_STATUS_QUARTER + ")";
		// 守方此次参战英雄列表
		List<RoleHero> heros2 = rolePropsDao.getHeroPropertyByCondition(conditoion);
		// 守方此次参战英雄个数
		int defenHeroCount = heros2.size();
		//城邦是否被占
		boolean isDroped = false;
		//城邦战结束产物结算
		Map<String,String> spoilMap ;
		
		if (heros2.size() <= 0 || heros2 == null) {
			// 内城没有守卫部队
			warInfoBefore += "守方城内没有戍守英雄";
			warInfoEnd = toCreateWarInfoEnd(attackListTemp, attackList).get(
					"warMail")
					+ Constants.SINGLE_SPLIT
					+ warInfoEnd+ "\r\n【战斗奖励】\r\n攻方：英雄EXP+0\r\n守方：英雄EXP+0";
			spoilMap = spoilsOfWar(attackRoleId, defenRoleId, tagCityId,warProduce, role_heros_id);
			warInfoEnd += "\r\n"+ spoilMap.get("spolisInfo");
			
			isDroped = spoilMap.get("isDroped").equals("NO") ? false : true;
		} else {
			String defenHerosId = "";

			for (RoleHero roleHero : heros2) {
				defenHerosId += roleHero.getRole_hero_id() + ",";
				if (roleHero.getIs_free() == Constants.HERO_STATUS_QUARTER)
					defendsRoleSet.add(roleHero.getRole_id());// 记录所有增援角色ID
			}
			defenHerosId = defenHerosId.substring(0, defenHerosId.length() - 1);

			// 构建守方部队参战信息
			Map<String, Object> attackMap2 = getWarTeamInfo(heros2, "A", 0,null);

			if (war_type == Constants.WAR_TYPE_CONQUER) {// 征服战 部分城邦预备军参战
				List<ArmsDeploy> cityArmsPre = armsDeployDao.selectCityArmsInfoProduce(tagCityId);
				if (cityArmsPre.size() > 0 && cityArmsPre != null)
					attackMap2 = getWarTeamInfoCityArms(cityArmsPre, attackMap2);
			}

			List<BattleItem> attackList2 = (List<BattleItem>) attackMap2.get("warTeamList");
			List<BattleItem> defenceListTemp = cloneTempBattleItem(attackList2);
			warInfoBefore += (String) attackMap2.get("warInfo") + "\r\n";

			// 守方部队总经验值
			sumDefenExp = (Integer) attackMap2.get("sumExp");

			// 模拟双方战斗
			Map<String, Object> warEndMap = battleProduce(attackList,
					attackList2);
			List<BattleItem> allArms = (List<BattleItem>) warEndMap.get("key_item");

			String deadHeroId = "";

			// 获取获胜方剩余兵力 双方获取经验值结算
			Map<String, Object> endMap;
			String warInfoTemp;
			int useExp;
			int getExpAttack = 0;
			int getExpDefence = 0;
			if (warEndMap.get("key_flag").equals("D")) {// 守方失败
				warInfoEnd += "\r\n内城守卫部队全军覆灭";
				endMap = toCreateWarInfoEnd(attackListTemp, attackList);
				warInfoTemp = (String) endMap.get("warMail");
				useExp = sumAttackExp - (Integer) endMap.get("lastExp");
				getExpAttack = (int) (Math.ceil((double) sumDefenExp
						/ (double) attHeroCount));
				getExpDefence = (int) (Math.ceil((double) useExp
						/ (double) defenHeroCount));
				warInfoEnd = warInfoTemp + Constants.SINGLE_SPLIT + warInfoEnd+ "\r\n【战斗奖励】\r\n攻方：英雄EXP+"
						+ getExpAttack + "\r\n守方：英雄EXP+" + getExpDefence;
				
				spoilMap = spoilsOfWar(attackRoleId, defenRoleId, tagCityId,warProduce, role_heros_id);
				warInfoEnd += "\r\n"+ spoilMap.get("spolisInfo");
				
				isDroped = spoilMap.get("isDroped").equals("NO") ? false : true;

			} else {
				warInfoEnd = "攻方浴血奋战突破城墙 但寡不敌众 于内城攻防战中全军覆灭\r\n"
						+ Constants.SINGLE_SPLIT + warInfoEnd + "\r\n";
				endMap = toCreateWarInfoEnd(defenceListTemp, attackList2);
				warInfoTemp = (String) endMap.get("warMail");
				useExp = sumDefenExp - (Integer) endMap.get("lastExp");
				//战败方经验获取折损40%
				getExpAttack = (int) (Math.ceil((double)(useExp * 0.6) / attHeroCount));
				getExpDefence = (int) (Math.ceil((double) sumAttackExp
						/ (double) defenHeroCount));
				warInfoEnd += warInfoTemp + "\r\n" + "【战斗结算】\r\n攻方：英雄EXP+"
						+ getExpAttack + "\r\n守方：英雄EXP+" + getExpDefence;
				constrDaoVo("warMap.deleteWarTeamInfo", warProduce.getWar_team_id(),3);
				CacheService.warTeamCache.remove(warProduce.getWar_team_id());
			}
			// 更新战后各战斗元素信息
			String sql = "";
			for (BattleItem battleItem : allArms) {

				switch (battleItem.getType()) {
				case BATTLE_HERO:
					if (battleItem.getKey_id() > 0) {
						int hp = battleItem.getItemSumHp();
						String status = "";
						if (hp == 0) {
							status = " t.hp=0,t.is_free ="+ Constants.HERO_STATUS_DEAD+ ",t.now_cityid = t.city_id";
							if (battleItem.getAttact_defen_flag().equals("D")
									|| battleItem.getIsfource() == 1) {
								deadHeroId += battleItem.getKey_id() + ",";
							}
							sql = status + " WHERE t.role_hero_id = "
									+ battleItem.getKey_id();
							constrDaoVo("rolePropsMap.updateHerosStatus", sql,2);
						}
					}
					break;
				case BATTLE_HERO_ARMS:
					String condition = " t.count ="
							+ battleItem.getItem_count_now()
							+ " WHERE t.hero_arms_id = "
							+ battleItem.getKey_id();
					constrDaoVo("warMap.updateHeroArms", condition,2);
					break;
				case BATTLE_CITY_ARMS:
					int armsPreCount = battleItem.getArmPreLastCount()
							+ battleItem.getItem_count_now();
						sql = "  t.arm_count = "
								+ armsPreCount + " WHERE t.city_id = "
								+ tagCityId + " AND t.arm_id = "
								+ battleItem.getKey_id();
					constrDaoVo("warMap.updateCityArmsWarEnd", sql,2);
					break;
				default:
					break;
				}

			}

			// 双方出征队列中有英雄阵亡
			if (deadHeroId.length() > 0) {
				checkDelTeam(deadHeroId);
			}

			// 更新战后双方奖励 经验 掠夺物 入库
			//攻方经验增加
			String heroRewardSqlAtt = " t.hero_exp = t.hero_exp +"
					+ getExpAttack + " WHERE t.role_hero_id in ("
					+ role_heros_id + ")";
			constrDaoVo("rolePropsMap.updateHerosStatus", heroRewardSqlAtt,2);
			//守方经验增加
			String heroRewardDef = " t.hero_exp = t.hero_exp +"
					+ getExpDefence + " WHERE t.role_hero_id in ("
					+ defenHerosId + ")";
			constrDaoVo("rolePropsMap.updateHerosStatus", heroRewardDef,2);
		}

		// 最终战报
		warInfo = warInfoBefore + warInfo + warInfoEnd;
		
		//防守方战报类型
		int deffMailType = 2;
		String title = "【战报】";
//		if(isDroped){
//			deffMailType = 4;//特殊类型战报：城邦被占
//			title +="|"+tagCityId;
//		}
		constrDaoVo("mailMap.addNewMail",new Mail(title, -20l, attackRoleId, warInfo, deffMailType, 0),1);
		constrDaoVo("mailMap.addNewMail",new Mail(title, -20l, defenRoleId, warInfo, deffMailType, 0),1);
		//向增援方发送战报
		if(defendsRoleSet.size() > 0){
			for (long forceRole : defendsRoleSet) {
				constrDaoVo("mailMap.addNewMail",new Mail(title, -20l, forceRole, warInfo, deffMailType, 0),1);
			}
		}

			//若Ibatis无法插入>4000数据 由原生JDBC处理方式
			//DbConnectionUtil.doLongMail(toRoleId, title, info);
	}

	/**
	 * 战斗结算 掠夺产物 减少守方城邦忠诚度
	 * 
	 * @param role_id_attack
	 *            进攻方ID
	 * @param role_id_defence
	 *            防守方ID
	 * @param city_id
	 *            战斗城市ID
	 * @param war_type
	 *            战争类型 1:掠夺战 2：征服战
	 * @throws Exception
	 */
	private Map<String,String> spoilsOfWar(long role_id_attack, long role_id_defence,
			long city_id, WarProduce produce, String attackHerosId)
			throws Exception {
		Role defenceRole = roleDao.selectRolesByRoleId(role_id_defence);
		Role attackRole = roleDao.selectRolesByRoleId(role_id_attack);
		City defenceCity = cityBuildDao.getCityYieldReinInfoByCityId(city_id);
		String spolisInfo = "";
		String isDroped = "NO";
		int war_type = produce.getWar_type();

		

		if (war_type == Constants.WAR_TYPE_ROB) {// 掠夺战 掠夺产物

			// 角色总产物
			int sumFood = defenceRole.getFood();
			int sumWood = defenceRole.getWood();
			int sumStone = defenceRole.getStone();
			
			// 角色所有城邦建筑总人口
			int sumCityEats = cityBuildDao.getCityEatByRoleId(role_id_defence);

			// 资源占比
			double per = (double) defenceCity.getEat() / sumCityEats;
			int robFood = (int) Math
					.ceil(sumFood * per * 0.25);
			int robWood = (int) Math
					.ceil(sumWood * per * 0.25);
			int robStone = (int) Math
					.ceil(sumStone * per * 0.25);
			int foodLast = (sumFood - robFood) <= 0 ? 0 : (sumFood - robFood);
			int woodLast = (sumWood - robWood) <= 0 ? 0 : (sumWood - robWood);
			int stoneLast = (sumStone - robStone) <= 0 ? 0
					: (sumStone - robStone);

			// 守方剩余资源
			defenceRole.setFood(foodLast);
			defenceRole.setWood(woodLast);
			defenceRole.setStone(stoneLast);
			//roleDao.updateRoleInfo(defenceRole);
			constrDaoVo("roleMap.updateRoleInfo", defenceRole,2);
			
			// 攻防剩余资源
			attackRole.setFood(attackRole.getFood()+ robFood);
			attackRole.setWood(attackRole.getWood() + robWood);
			attackRole.setStone(attackRole.getStone() + robStone);
			constrDaoVo("roleMap.updateRoleInfo", attackRole,2);
			spolisInfo = "【掠夺战】攻方掠夺资源："
					+ "<icon>{\"url\":\"food.png\",\"count\":"+robFood+"}</icon>   "
					+ "<icon>{\"url\":\"wood.png\",\"count\":"+robWood+"}</icon>   "
					+ "<icon>{\"url\":\"stone.png\",\"count\":"+robStone+"}</icon>   ";
			//队列状态变更 返回城邦
			teamStatusUpdateBack(produce);
		} else {
			int loyalLast = defenceCity.getLoyal() - 10 <= 0 ? 0 : defenceCity.getLoyal() - 10;
			
			if (loyalLast == 0) {// 城邦忠诚度为0
				//判断攻方拥有城邦数是否已达上限
				if( attackRole.getLev()/10 + 1 > cityBuildDao.getCitysByRoleId(role_id_attack).size()){
					// 城邦被攻陷
					cityDroped(city_id, role_id_attack, role_id_defence,attackHerosId);
					spolisInfo = "【征服战】守方城邦剩余忠诚度:0;  攻方攻占城池,守方部队撤回主城";
					//队列状态更新 删除队列
					//删除队列
					CacheService.warTeamCache.remove(produce.getWar_team_id());
					constrDaoVo("warMap.deleteWarTeamInfo", produce.getWar_team_id(),3);
					isDroped = "YES";
				}else{
					spolisInfo = "【征服战】守方城邦剩余忠诚度:0;  攻方已达最大城池数,占领城邦失败,部队返回";
					// 队列状态更改 --返回城邦
					teamStatusUpdateBack(produce);
				}
				
			} else {
				
				spolisInfo = "【征服战】守方城邦剩余忠诚度:" + loyalLast;
				// 队列状态更改 --返回城邦
				teamStatusUpdateBack(produce);
			}
			
			// 更新城邦忠诚度
			Map<String,Object> ibatisMap3 = new HashMap<String,Object>();
			ibatisMap3.put("condition", " t.loyal = "+ loyalLast);
			ibatisMap3.put("city_id", city_id);
			constrDaoVo("cityBuildMap.updateCityInfo", ibatisMap3,2);
		}
		Map<String,String> resMap = new HashMap<String,String>();
		resMap.put("spolisInfo", spolisInfo);
		resMap.put("isDroped", isDroped);
		return resMap;
	}

	/**
	 * 城池沦陷
	 * 
	 * @param city_id
	 * @param role_id_new
	 * @param role_id_old
	 * @throws Exception
	 */
	private void cityDroped(long city_id, long role_id_new, long role_id_old,
			String attackHerosId) throws Exception {
		
		// 原占有者主城所在地
		long homeId = cityBuildDao.getCitysInfoByCondition(
				" WHERE t.role_id = " + role_id_old + " AND t.home = 1")
				.getCity_id();

		// 原城邦所属英雄回归角色主城
		//rolePropsDao.updateHerosStatus(" t.city_id = " + homeId + " WHERE t.city_id = " + city_id);
		constrDaoVo("rolePropsMap.updateHerosStatus", " t.city_id = " + homeId
						+ " WHERE t.city_id = " + city_id,2);
		// 更改原城邦现存出征队列信息
		//armsDeployDao.updateWarTeamByCondition(" t.city_id = " + homeId + " WHERE t.city_id = " + city_id);
		constrDaoVo("warMap.updateWarTeamByCondition", " t.city_id = " + homeId
						+ " WHERE t.city_id = " + city_id,2);
		
		// 攻防占据该城邦
		Map<String,Object> ibatisMap = new HashMap<String,Object>();
		ibatisMap.put("condition", " t.role_id = "+ role_id_new);
		ibatisMap.put("city_id", city_id);
		//cityBuildDao.updateCityInfo(ibatisMap);
		constrDaoVo("cityBuildMap.updateCityInfo", ibatisMap,2);
		
		
		// 攻方部队进驻该城邦
		String heroSql = " t.city_id = "
				+ city_id +" ,t.now_cityid = "+city_id+ ",t.is_free = 1 WHERE t.role_hero_id in ("
				+ attackHerosId + ")";
		//rolePropsDao.updateHerosStatus(heroSql);
		constrDaoVo("rolePropsMap.updateHerosStatus", heroSql,2);
	}

	/**
	 * 征服战 城邦部分预备队参战
	 * 
	 * @param cityArmsList
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> getWarTeamInfoCityArms(
			List<ArmsDeploy> cityArmsList, Map<String, Object> map) {
		String warInfoMain = (String) map.get("warInfo");
		List<BattleItem> warTeamList = (List<BattleItem>) map
				.get("warTeamList");
		int sumExp = (Integer) map.get("sumExp");
		BattleItem battleItem = new BattleItem(-100l, "【城邦预备】", 1, 0, 0, 0, 0,
				0, 0, 0, 0, "A", 0, 0);
		battleItem.setExp(0);
		battleItem.setIcon("heada011.png");
		battleItem.setIsfource(0);
		warInfoMain += "\r\n" + battleItem.getName() + "<icon>{\"url\":\"heada011.png\"}</icon>";
		warTeamList.add(battleItem);

		for (ArmsDeploy armsDeploy : cityArmsList) {
			int battleCount = (int) Math.ceil((double) armsDeploy
					.getArm_count() * 0.1);
			battleCount = battleCount > 10 ? battleCount : armsDeploy
					.getArm_count();
			BattleItem battleItem2 = new BattleItem(
					(long) armsDeploy.getArm_id(), armsDeploy.getArm_name(),
					BATTLE_CITY_ARMS, armsDeploy.getPhysic_attack(),
					armsDeploy.getPhysic_defence(),
					armsDeploy.getMagic_attack(),
					armsDeploy.getMagic_defence(), armsDeploy.getHp(), 0,
					armsDeploy.getSpeed(), battleCount, "A", armsDeploy.getHp()
							* battleCount, battleCount);
			battleItem2.setExp(armsDeploy.getExp());
			battleItem2.setIcon(armsDeploy.getArm_icon());
			battleItem2.setIsfource(0);
			battleItem2.setArmPreLastCount(armsDeploy.getArm_count()
					- battleCount);
			sumExp += armsDeploy.getExp() * armsDeploy.getArm_count();
			warInfoMain += "<icon>{\"url\":\""+armsDeploy.getArm_icon()+"\",\"count\":"+armsDeploy.getArm_count()+"}</icon>";
			warTeamList.add(battleItem2);
		}

		map.put("warTeamList", warTeamList);
		map.put("warInfo", warInfoMain);
		map.put("sumExp", sumExp);

		return map;
	}

	/**
	 * 野怪JSON字符串转化为ArmsDeploy对象
	 * 
	 * @param creeps
	 * @return
	 * @throws Exception
	 */
	private Map<String, Object> creepsJsonToArms(List<CreepsDemon> creeps)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ArmsDeploy> creepArms = new ArrayList<ArmsDeploy>();
		String creepsArmsId = "";

		// 将野怪信息转化为战斗元素
		for (CreepsDemon creepsDemon : creeps) {
			creepsArmsId += creepsDemon.getArm_id() + ",";
			map.put(creepsDemon.getArm_id() + "", creepsDemon.getCreeps_count());
		}
		creepArms = armsDeployDao.selectArmsDetailInfo(" WHERE t.arm_id in ("
				+ creepsArmsId.substring(0, creepsArmsId.length() - 1) + ")");
		for (ArmsDeploy armsDeploy : creepArms) {
			armsDeploy.setArm_count((Integer) map.get(armsDeploy.getArm_id()
					+ ""));
		}
		map.put("creArms", creepArms);
		map.put("creIds", creepsArmsId);
		return map;
	}

	/**
	 * 将战斗队列信息转化为战斗元素
	 * 
	 * @param warProduce
	 * @param att_def_flag
	 *            攻守标志 攻防：D 守方：A
	 * @return
	 */
	private Map<String, Object> getWarTeamInfo(List<RoleHero> heros,
			String att_def_flag, int isWild, List<ArmsDeploy> creepArms)
			throws Exception {
		String warInfoMain = "";
		Map<String, Object> map = new HashMap<String, Object>();
		List<BattleItem> warTeamList = new ArrayList<BattleItem>();
		Integer sumExp = 0;
		String leaderName;

		if (isWild == 1) {// 野怪信息
			for (ArmsDeploy armsDeploy : creepArms) {
				// 构建野怪战斗元素
				BattleItem battleItem2 = new BattleItem(
						armsDeploy.getHero_arms_id(), armsDeploy.getArm_name(),
						BATTLE_CREEP_ARMS, armsDeploy.getPhysic_attack(),
						armsDeploy.getPhysic_defence(),
						armsDeploy.getMagic_attack(),
						armsDeploy.getMagic_defence(), armsDeploy.getHp(), 0,
						armsDeploy.getSpeed(), armsDeploy.getArm_count(),
						att_def_flag, (armsDeploy.getHp())
								* armsDeploy.getArm_count(),
						armsDeploy.getArm_count());

				battleItem2.setExp(armsDeploy.getExp());
				battleItem2.setIcon(armsDeploy.getArm_icon());

				// 部队对应总经验值
				sumExp += armsDeploy.getExp() * armsDeploy.getArm_count();
				warInfoMain += "<icon>{\"url\":\""+armsDeploy.getArm_icon()+"\",\"count\":"+armsDeploy.getArm_count()+"}</icon>";
				warTeamList.add(battleItem2);
			}
			warInfoMain += "\r\n";
		} else {

			for (RoleHero roleHero : heros) {

				// 获取英雄战斗属性值
				RoleHeroInfoService.calculateHeroPropertyShow(roleHero);

				// 英雄对部队属性提供的BUFF值
				// 物理攻击BUFF
				int physic_attack_buff = (roleHero.getHero_power() / 100 + 1) * 3;
				// 物理防御BUFF
				int physic_defenc_buff = roleHero.getAgility() / 100 + 1
						+ roleHero.getPhysique() / 100 + 1
						+ (roleHero.getEndurance() / 100 + 1) * 4;
				// 魔法攻击BUFF
				int magic_attack_buff = (roleHero.getMentality() / 100 + 1) * 5;
				// 魔法防御BUFF
				int magic_defenc_buff = roleHero.getAgility() / 100 + 1
						+ roleHero.getPhysique() / 100 + 1
						+ (roleHero.getEndurance() / 100 + 1) * 4;
				// 血量BUFF
				int hp_buff = (roleHero.getHero_power() / 100 + 1) * 2
						+ (roleHero.getPhysique() / 100 + 1) * 5;

				leaderName = roleHero.getHero_name();
				
				double attBuffPersent = roleHero.getCommand()/(roleHero.getHero_lev() * 1.5) < 1 ? 1 :roleHero.getCommand()/(roleHero.getHero_lev() * 1.5);
				BattleItem battleItem = new BattleItem(
						roleHero.getRole_hero_id(), leaderName, BATTLE_HERO,
						roleHero.getPhysic_attack_show() * attBuffPersent,
						roleHero.getPhysic_defence_show(),
						roleHero.getMagic_attack_show() * attBuffPersent,
						roleHero.getMagic_defence_show(), roleHero.getHp(),
						roleHero.getMp(), roleHero.getSpeed_show(), 1,
						att_def_flag, roleHero.getHp_max_show(), 1);
				battleItem.setExp(0);
				battleItem.setIcon(roleHero.getSht_icon());
				battleItem.setMax_hp(roleHero.getHp_max_show());
				// 该英雄是否是增援部队
				if (roleHero.getIs_free() == Constants.HERO_STATUS_QUARTER) {
					String roleName = roleDao.selectRolesByRoleId(
							roleHero.getRole_id()).getRole_name();
					battleItem.setIsfource(1);
					battleItem.setFourceName("[" + roleName + "-增援]");
					warInfoMain += roleHero.getHero_name() + "【援】" + "<icon>" +"{\"url\":\"" +roleHero.getSht_icon() + "\",\"hp\":"+roleHero.getHp_max_show()+",\"max_hp\":"+roleHero.getHp_max_show()+"}</icon>";
				} else {
					battleItem.setIsfource(0);
					battleItem.setFourceName("");
					warInfoMain += roleHero.getHero_name()+ "<icon>" +"{\"url\":\"" +roleHero.getSht_icon() + "\",\"hp\":"+roleHero.getHp_max_show()+",\"max_hp\":"+roleHero.getHp_max_show()+"}</icon>";

				}
				warTeamList.add(battleItem);

				// 获取该英雄携带部队信息
				List<ArmsDeploy> armsList = roleHero.getHeroArmys();
				if(armsList == null || armsList.size() == 0)
					// 获取该英雄携带部队信息
					 armsList = armsDeployDao.selectHeroArmsInfoProduce(roleHero.getRole_hero_id());
				for (ArmsDeploy armsDeploy : armsList) {
					// 构建英雄对应的部队信息 且给部队提供英雄BUFF效应
					BattleItem battleItem2 = new BattleItem(
							armsDeploy.getHero_arms_id(),
							armsDeploy.getArm_name(),
							BATTLE_HERO_ARMS,
							armsDeploy.getPhysic_attack() + physic_attack_buff,
							armsDeploy.getPhysic_defence() + physic_defenc_buff,
							armsDeploy.getMagic_attack() + magic_attack_buff,
							armsDeploy.getMagic_defence() + magic_defenc_buff,
							armsDeploy.getHp() + hp_buff, 0, armsDeploy
									.getSpeed(), armsDeploy.getArm_count(),
							att_def_flag, (armsDeploy.getHp() + hp_buff)
									* armsDeploy.getArm_count(), armsDeploy
									.getArm_count());

					battleItem2.setExp(armsDeploy.getExp());
					battleItem2.setIcon(armsDeploy.getArm_icon());
					// 部队对应总经验值
					sumExp += armsDeploy.getExp() * armsDeploy.getArm_count();
					warInfoMain += "<icon>{\"url\":\""+armsDeploy.getArm_icon()+"\",\"count\":"+armsDeploy.getArm_count()+"}</icon>";
					warTeamList.add(battleItem2);
				}
				warInfoMain += "\r\n";
			}
		}

		map.put("warTeamList", warTeamList);
		map.put("warInfo", warInfoMain);
		map.put("sumExp", sumExp);
		return map;
	}

	/**
	 * COPY战斗队列
	 * 
	 * @param items
	 * @return
	 */
	private List<BattleItem> cloneTempBattleItem(List<BattleItem> items) {
		List<BattleItem> battleItemsTemp = new ArrayList<BattleItem>();
		for (BattleItem battleItem : items) {
			battleItemsTemp.add(battleItem.clone());
		}
		return battleItemsTemp;
	}

	/**
	 * 构建战后兵力表
	 * 
	 * @param beginList 战前部队List
	 * @param endList 战后部队List
	 * @return
	 */
	private Map<String, Object> toCreateWarInfoEnd(List<BattleItem> beginList,
			List<BattleItem> endList) {
		String warInfoTemp = "";
		int lastExp = 0;
		String liveHerosId = "";

		Map<String, Object> warEndMap = new HashMap<String, Object>();
		for (BattleItem battleItemBeg : beginList) {

			if (battleItemBeg.getType() == BATTLE_HERO) {// 英雄
				boolean flag = false;
				int hp = 0;
				for (BattleItem battleItemEnd : endList) {
					if (battleItemBeg.equals(battleItemEnd)) {//英雄未阵亡
						flag = true;
						hp = battleItemEnd.getItemSumHp();
						endList.remove(battleItemEnd);//移除已判断集合元素
						break;
					}
				}
				if (flag) {
					warInfoTemp += "\r\n" + battleItemBeg.getName()
							+ battleItemBeg.getFourceName()
							+ "<icon>" +"{\"url\":\"" +battleItemBeg.getIcon() + "\",\"hp\":"+hp+",\"max_hp\":"+battleItemBeg.getMax_hp()+"}</icon>";
					liveHerosId += battleItemBeg.getKey_id() + ",";
				} else {
					warInfoTemp += "\r\n" + battleItemBeg.getName()
							+ battleItemBeg.getFourceName()
							+ "<icon>" +"{\"url\":\"" +battleItemBeg.getIcon() + "\",\"hp\":"+0+",\"max_hp\":"+battleItemBeg.getMax_hp()+"}</icon>";
				}
			} else {// 部队
				boolean flag = false;
				int count_now = 0;
				for (BattleItem battleItemEnd : endList) {
					if (battleItemBeg.equals(battleItemEnd)) {

						flag = true;
						count_now = battleItemEnd.getItem_count_now();
						lastExp += battleItemEnd.getExp() * count_now;
						endList.remove(battleItemEnd);
						break;
					}
					continue;
				}
				if (flag) {
					warInfoTemp += "<icon>{\"url\":\"" + battleItemBeg.getIcon()
							+ "\"" + ",\"count\":"+count_now+"}</icon>";
				} else {
					warInfoTemp += "<icon>{\"url\":\"" + battleItemBeg.getIcon()
							+ "\"" + ",\"count\":"+0+"}</icon>";
				}

			}

		}
		liveHerosId = liveHerosId == "" ? "" : liveHerosId.substring(0,
				liveHerosId.length() - 1);
		warEndMap.put("liveHeroId", liveHerosId);
		warInfoTemp += "\r\n";
		warEndMap.put("warMail", warInfoTemp);
		warEndMap.put("lastExp", lastExp);
		return warEndMap;
	}

	/**
	 * 战斗过程
	 * 
	 * @param attactArm
	 *            进攻方
	 * @param defenceArm
	 *            防守方
	 */
	private Map<String, Object> battleProduce(List<BattleItem> attactArm,
			List<BattleItem> defenceArm) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<BattleItem> allArms = new ArrayList<BattleItem>();
		allArms.addAll(attactArm);
		allArms.addAll(defenceArm);
		Comparator<BattleItem> comp = new BattleItemComparator();
		Collections.sort(allArms, comp);
		Map<String, List<BattleItem>> armsMap = new HashMap<String, List<BattleItem>>();
		armsMap.put("D", defenceArm);// 存放守方部队
		armsMap.put("A", attactArm);// 存放攻方部队

		while (true) {
			for (BattleItem battleItem : allArms) {

				// 该小队已全军覆灭
				if (battleItem.getItem_count_now() == 0)
					continue;

				// 该轮被攻击方集合
				String key_flag = battleItem.getAttact_defen_flag();
				List<BattleItem> beenAttackList = armsMap.get(key_flag);

				// 一方已全军覆灭 本场战斗结束
				if (beenAttackList.size() == 0 || beenAttackList == null) {
					//
					result.put("key_flag", key_flag);
					result.put("key_item", allArms);
					return result;
				}
				
				
				// 物理攻击 1 魔法攻击0
				int attack_type = battleItem.getPhysic_attack() >= battleItem.getMagic_attack() ? 1 : 0;
				attackOneTurn(battleItem, beenAttackList, attack_type);
			}
		}
	}

	/**
	 * 每回合战斗
	 * 
	 * @param battleItem
	 *            该回合攻击方
	 * @param defenceArm
	 *            该回合防守方
	 * @param attack_type
	 *            攻击方式：魔法 物理
	 * @return
	 */
	private void attackOneTurn(BattleItem battleItem,
			List<BattleItem> defenceArm, int attack_type) {

		// 随机选取防守方一个元素
				BattleItem battleDefen = defenceArm.get((int) Math.rint(Math.random()* (defenceArm.size() - 1)));
				if(battleDefen.getItem_count_now() == 0){
					defenceArm.remove(battleDefen);
					return;
				}
					
				// 降低英雄被攻击的几率
				if (battleDefen.getType() == 1) {
					for (int i = 0; i < defenceArm.size(); i++) {
						battleDefen = defenceArm.get((int) Math.rint(Math.random()* (defenceArm.size() - 1)));
						if (battleDefen.getType() != 1)
							break;
					}
				}
				
				double attValue =0;
				int defValue = 0;
				String attTypeStr = "";
				String defTypeStr = "";
				if(attack_type == 1){
					attValue = battleItem.getPhysic_attack();
					defValue = battleDefen.getPhysic_defence();
					attTypeStr = "物攻:";
					defTypeStr = "物防:";
				}else{
					attValue = battleItem.getMagic_attack();
					defValue = battleDefen.getMagic_defence();
					attTypeStr = "魔攻:";
					defTypeStr = "魔防:";
				}
				logger.info("/r/n/r/n/r/n********************");
				logger.info("********************");
				logger.info("********************");
				logger.info("********************");
				logger.info("*****************************【本轮攻击BEGIN】********************************");
				logger.info(">>>>>>>本轮攻方："+battleItem.getAttact_defen_flag()+"--->icon:"+battleItem.getIcon()+"---->"+attTypeStr+attValue+"--->元素类型:"+battleItem.getType()+"--->keyID"+battleItem.getKey_id());
				logger.info(">>>>>>>本轮守方："+battleDefen.getAttact_defen_flag()+"--->icon:"+battleDefen.getIcon()+"---->"+defTypeStr+defValue + "--->守方生命值:"+battleDefen.getItemSumHp()+ "--->守方元素个数:"+battleDefen.getItem_count_now()+"--->元素类型:"+battleDefen.getType()+"--->keyID"+battleDefen.getKey_id()+"---->元素血量："+battleDefen.getHp());

				int attCount = 0;
				if (attack_type == 1) {// 物理攻击
					// 本轮造成伤害
					attCount = (int) Math.ceil(battleItem.getItem_count_now()
							* attValue
							* (100.0 / (100 + battleDefen.getPhysic_defence())));

				} else {// 魔法攻击
					// 本轮造成伤害
					attCount = (int) Math.ceil(battleItem.getItem_count_now()
							* attValue
							* (100.0 / (100 + battleDefen.getMagic_defence())));
				}
				logger.info(">>>>>>>本轮攻防对目标造成伤害:"+attCount);
				// 本轮防守方剩余生命值
				int defeLastHp = battleDefen.getItemSumHp()
						- (int) (attCount * Math.pow(
								(2201.0 - battleDefen.getItem_count_now()) / 2200, 2));
				logger.info(">>>>>>>本轮守方剩余生命:"+defeLastHp);
				// 本轮防守方剩余人数
				int defeLastCount = defeLastHp / battleDefen.getHp();
				logger.info(">>>>>>>本轮防守方剩余人数:"+defeLastCount);
				if (battleDefen.getType() == BATTLE_HERO)
					defeLastCount = defeLastHp <= 0 ? 0 : 1;

				// 该回合结束
				if (defeLastCount <= 0) {
					battleDefen.setItem_count_now(0);
					battleDefen.setItemSumHp(0);
					defenceArm.remove(battleDefen);
				} else {
					battleDefen.setItemSumHp(defeLastHp);
					battleDefen.setItem_count_now(defeLastCount);
				}
				logger.info("*****************************【本轮攻击END】********************************");
				logger.info("********************");
				logger.info("********************");
				logger.info("********************");
				logger.info("********************");
	}

	/**
	 * 检查攻防双方是否是同盟
	 * 
	 * @param attackRoleId
	 * @param defenRoleId
	 * @param faction_id
	 * @return
	 * @throws Exception
	 */
	private boolean checkIsSameSide(long attackRoleId, long defenRoleId,
			WarProduce warProduce, int type) throws Exception {
		
		int x = warProduce.getTag_x();
		int y = warProduce.getTag_y();
		String warInfo = "";
		String warTitle = "";
		

		Relation relation2 = relationDao.checkRelationShip(new Relation(
				attackRoleId, defenRoleId));
		int relType2 = relation2 == null ? 0 : relation2.getRelation_type();
		if (factionDao.checkIsSameFaction(" WHERE t.role_id = " + defenRoleId
				+ " AND t.faction_id = " + warProduce.getFaction_id()) > 0
				|| relType2 == 1 || attackRoleId == defenRoleId) {
			if (2 == type) {
				// 资源争夺战
				warTitle = "【战报】-资源争夺";
				// 发送战报
				warInfo = "资源点(" + x + "," + y
						+ ")\r\n  目标已被友方占领 部队返回";
			} else {
				// 资源争夺战
				warTitle = "【战报】-城邦攻防";
				// 发送战报
				warInfo =  warProduce.getTag_name() + "(" + x
						+ "," + y + ")\r\n  目标已被友方占领 部队返回";
			}
			// 发送邮件
			constrDaoVo("mailMap.addNewMail",new Mail(warTitle, -20l, attackRoleId, warInfo, 2, 0),1);
			
			teamStatusUpdateBack(warProduce);
			return true;

		}
		return false;

	}

	/**
	 * 角色当前野外资源点数目是否已达上限
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	private boolean onWildSrcMax(long roleId) throws Exception{
		boolean flag = false;
		//查询角色当前拥有城邦数
		int cityCount = cityBuildDao.getCitysByRoleId(roleId).size();
		//角色当前拥有野外资源点数
		List<WildSrc> wildSrcs = worldMapDao.getWildSrcInfo(" where t2.role_id = "+roleId);
		int wildSrcCount= wildSrcs == null ? 0 : wildSrcs.size();
		flag = cityCount * 2 <= wildSrcCount;
		return flag;
	}
	
	/**
	 * 队列返回
	 * @param produce
	 * @throws Exception
	 */
	private void teamStatusUpdateBack(WarProduce produce) throws Exception{
		long lastTime = DateTimeUtil.getDiffer(DateTimeUtil.praseStringToDate(produce.getEnd_time()), new Date(), 1) ;
		long backUseTime = produce.getUse_time() - (lastTime < 0 ? 0 : lastTime);
		// 队列状态更改 --返回城邦
		produce.setEnd_time(DateTimeUtil.getEndTime(new Date(), backUseTime));
		produce.setStatus( Constants.TEAM_STATUS_BACK);
		constrDaoVo( "warMap.updateWarTeamInfo", produce,2);
	}
	
	/**
	 * 整合完整战斗过程SQL 
	 * @param daoObj
	 * @param methodName
	 * @param paramObj
	 */
	private void constrDaoVo(String ibatisSqlId, Object paramObj, int ibatisSqlType){
		this.daoVos.add(new DaoVo(ibatisSqlId, paramObj, ibatisSqlType));
	}
	
}
