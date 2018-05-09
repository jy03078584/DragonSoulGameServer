/**
 *
 *
 * 文件名称： ArmyRequestAnalysis.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-10-31 上午10:26:36
 */
package com.lk.dragon.server.module.analysis;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lk.dragon.db.domain.ArmsDeploy;
import com.lk.dragon.db.domain.ReinForceArm;
import com.lk.dragon.db.domain.RoleHero;
import com.lk.dragon.server.domain.ArmyDomain;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.JSONUtil;

public class ArmyRequestAnalysis {
	/******************** 请求字段键值 *******************/
	private static final String ARMY_TYPE_KEY = "armyType";
	private static final String SOLDIER_ID_KEY = "soldier_id";
	private static final String ROLE_ID_KEY = "role_id";
	private static final String CITY_ID_KEY = "city_id";
	private static final String BUILD_ID_KEY = "build_id";
	private static final String RECRUIT_NUM_KEY = "recruit_num";
	private static final String RECRUIT_TIME_KEY = "recruit_time";
	private static final String SOLDIER_CHANGE_KEY = "soldier_change";
	private static final String TARGET_CITY_ID_KEY = "target_city_id";
	private static final String USE_GOLD_KEY = "gold";
	private static final String USE_DIAMOND_KEY = "use_diamond";
	private static final String ONE_SOLDIER_TIME_KEY = "one_soldier_time";
	private static final String TOTAL_SOLDIER_TIME_KEY = "total_soldier_time";
	private static final String EAT_KEY = "eat";
	private static final String HERO_IDS_KEY = "hero_ids";
	private static final String SEQ_NUMBER_KEY = "seq_number";
	private static final String RACE_KEY = "race";
	private static final String HIRE_BUILD = "hire_build";
	private static final String ARMY_TRAIN_TIME = "tra_time";//部队派遣消耗时间
	private static final String ARMY_TRAIN_FOOD = "tra_food";//部队派遣消耗粮食
	private static final String TARGET_X = "tag_x";//目标X坐标
	private static final String TARGET_Y = "tag_y";//目标Y坐标
	private static final String FROM_CITY_KEY = "from_city";//出发城邦ID
	private static final String FROM_ROLE_KEY = "from_role";//攻方ID
	private static final String TRANS_LEVEL_KEY = "trans_level";//传送门等级

	/********************* 响应字符串 ****************************/
	// 响应的关键字段
	private static final String RESULT_KEY = "result";
	// private static final String REASON_KEY = "reason";

	// 造兵队列
	private static final String ARMY_KEY = "city_armys";
	private static final String LAST_TIME_KEY = "last_time";
	private static final String EXTRA_TIME_KEY = "extra_time";
	private static final String SOLDIER_NUM_KEY = "soldier_num";
	private static final String ARMY_NUM_KEY = "num";
	private static final String CONSCRIPT_WORK_ID_KEY = "conscript_work_id";

	// 英雄所带兵源列表信息
	private static final String HERO_ARMY_KEY = "hero_armys";

	// 可招募兵源信息
	private static final String ARMY_ICON_KEY = "icon";
	private static final String ARMY_NAME_KEY = "name";

	private static final String ARMY_ID_KEY = "army_id";
	private static final String ARMY_GRADE_KEY = "grade";
	private static final String ARMY_HP_KEY = "hp";
	private static final String ARMY_PHYSIC_ATTACK_KEY = "physic_attack";
	private static final String ARMY_PHYSIC_DEFENCE_KEY = "physic_defence";
	private static final String ARMY_MEGIC_ATTACK_KEY = "megic_attack";
	private static final String ARMY_MEGIC_DEFENCE_KEY = "megic_defence";
	private static final String ARMY_SPEED_KEY = "speed";
	private static final String ARMY_DISTANCE_ATTACK_KEY = "distance_attack";
	private static final String ARMY_HIRE_PRIVCE_KEY = "hire_privce";
	private static final String ARMY_HIRE_TIME_KEY = "hire_time";
	private static final String ARMY_EXP_KEY = "army_exp";
	private static final String ARMY_DESC_KEY = "army_desc";
	private static final String ARMY_USE_COMMAND_KEY = "command";
	private static final String ARMY_FAIGHT_CAP_KEY = "fc";

	// 英雄/部队情况总览表
	private static final String HERO_KEY = "heros";
	private static final String ROLE_HERO_ID_KEY = "role_hero_id";
	private static final String HERO_NAME_KEY = "hero_name";
	private static final String HERO_COMMAND_KEY = "hero_command";
	private static final String HERO_ICON_KEY = "hero_icon";
	private static final String CITY_NAME_KEY = "city_name";
	private static final String HERO_IS_FREE_KEY = "is_free";
	
	private static final String TRANS_CD_KEY = "trans_cd";

	/**
	 * 获取战斗请求数据
	 * 
	 * @param json
	 * @return
	 */
	public static ArmyDomain getArmyInfo(String json) {
		// 创建公会实体
		ArmyDomain armyDomain = new ArmyDomain();

		// 获取data信息
		JSONObject armyData = JSONUtil.getData(json);

		// 将信息分解到公会实体中
		armyDomain.setType(armyData.getInt(ARMY_TYPE_KEY));
		armyDomain.setRole_id(armyData.has(ROLE_ID_KEY) ? armyData
				.getLong(ROLE_ID_KEY) : 0);
		armyDomain.setSoldierId(armyData.has(SOLDIER_ID_KEY) ? armyData
				.getInt(SOLDIER_ID_KEY) : 0);
		armyDomain.setCity_id(armyData.has(CITY_ID_KEY) ? armyData
				.getLong(CITY_ID_KEY) : 0);
		armyDomain.setRecruit_num(armyData.has(RECRUIT_NUM_KEY) ? armyData
				.getInt(RECRUIT_NUM_KEY) : 0);
		armyDomain.setRecruit_time(armyData.has(RECRUIT_TIME_KEY) ? armyData
				.getInt(RECRUIT_TIME_KEY) : 0);
		armyDomain
				.setSoldier_change(armyData.has(SOLDIER_CHANGE_KEY) ? armyData
						.getString(SOLDIER_CHANGE_KEY) : null);
		armyDomain
				.setTarget_city_id(armyData.has(TARGET_CITY_ID_KEY) ? armyData
						.getLong(TARGET_CITY_ID_KEY) : 0);
		armyDomain.setOne_time(armyData.has(ONE_SOLDIER_TIME_KEY) ? armyData
				.getInt(ONE_SOLDIER_TIME_KEY) : 0);
		armyDomain
				.setTotal_time(armyData.has(TOTAL_SOLDIER_TIME_KEY) ? armyData
						.getInt(TOTAL_SOLDIER_TIME_KEY) : 0);
		armyDomain.setUse_gold(armyData.has(USE_GOLD_KEY) ? armyData
				.getInt(USE_GOLD_KEY) : 0);
		armyDomain.setBuild_id(armyData.has(BUILD_ID_KEY) ? armyData
				.getLong(BUILD_ID_KEY) : 0);
		armyDomain.setEat(armyData.has(EAT_KEY) ? armyData.getInt(EAT_KEY) : 0);
		armyDomain.setRoleHeroId(armyData.has(ROLE_HERO_ID_KEY) ? armyData
				.getLong(ROLE_HERO_ID_KEY) : 0);
		armyDomain.setUse_diamond(armyData.has(USE_DIAMOND_KEY) ? armyData
				.getInt(USE_DIAMOND_KEY) : 0);
		armyDomain.setRace(armyData.has(RACE_KEY) ? armyData.getInt(RACE_KEY)
				: -1);
		armyDomain.setHire_build(armyData.has(HIRE_BUILD) ? armyData
				.getInt(HIRE_BUILD) : -1);
		armyDomain.setTrans_time(armyData.has(ARMY_TRAIN_TIME) ? armyData.getInt(ARMY_TRAIN_TIME):-1);
		armyDomain.setTrans_food(armyData.has(ARMY_TRAIN_FOOD) ? armyData.getInt(ARMY_TRAIN_FOOD):-1);
		armyDomain.setTag_x(armyData.has(TARGET_X) ? armyData.getInt(TARGET_X):-1);
		armyDomain.setTag_y(armyData.has(TARGET_Y) ? armyData.getInt(TARGET_Y):-1);
		armyDomain.setFrom_city_id(armyData.has(FROM_CITY_KEY) ? armyData.getLong(FROM_CITY_KEY):-1);
		armyDomain.setTrans_level(armyData.has(TRANS_LEVEL_KEY) ? armyData.getInt(TRANS_LEVEL_KEY): 0);
		armyDomain.setFrom_role_id(armyData.has(FROM_ROLE_KEY) ? armyData.getLong(FROM_ROLE_KEY): -1);
		if (armyData.has(HERO_IDS_KEY)) {
			// 将上传的id字符串分割
			String ids = armyData.getString(HERO_IDS_KEY);
			String[] idArr = ids.split(Constants.SPLIT);
			List<Long> heroIdList = new ArrayList<Long>();

			for (String id : idArr) {
				heroIdList.add(Long.parseLong(id));
			}
			armyDomain.setHerosId(ids);
			armyDomain.setHeroIds(heroIdList);
		}
		if (armyData.has(ARMY_KEY)) {
			// 上传数据中包含城镇赋闲部队信息
			armyDomain.setCityArmyList(null);
			JSONArray cityArmyArr = armyData.getJSONArray(ARMY_KEY);
			long cityId = armyData.getLong(CITY_ID_KEY);
			if (cityArmyArr.size() != 0) {
				// 将城镇的部队信息解析到list中
				List<ArmsDeploy> armyList = new ArrayList<ArmsDeploy>();

				for (int i = 0; i < cityArmyArr.size(); i++) {
					JSONObject cityArmyObj = cityArmyArr.getJSONObject(i);
					ArmsDeploy armsDeploy = new ArmsDeploy();
					armsDeploy
							.setArm_count(cityArmyObj.getInt(SOLDIER_NUM_KEY));
					armsDeploy.setArm_id(cityArmyObj.getInt(SOLDIER_ID_KEY));
					armsDeploy.setCity_id(cityId);

					armyList.add(armsDeploy);
				}

				armyDomain.setCityArmyList(armyList);
			}
		}
		if (armyData.has(HERO_ARMY_KEY)) {
			// 上传数据中包含英雄随带部队信息
			JSONArray heroArmyArr = armyData.getJSONArray(HERO_ARMY_KEY);

			// 上传数据中包含城镇赋闲部队信息
			long roleHeroId = armyData.getLong(ROLE_HERO_ID_KEY);

			// 将城镇的部队信息解析到list中
			List<ArmsDeploy> armyList = new ArrayList<ArmsDeploy>();

			for (int i = 0; i < 5; i++) {
				ArmsDeploy armsDeploy = new ArmsDeploy();
				armsDeploy.setRole_hero_id(roleHeroId);

				if (i < heroArmyArr.size()) {
					JSONObject heroArmyObj = heroArmyArr.getJSONObject(i);
					armsDeploy.setCount(heroArmyObj.getInt(SOLDIER_NUM_KEY));
					armsDeploy.setArm_id(heroArmyObj.getInt(SOLDIER_ID_KEY));
					armsDeploy
							.setSeq_number(heroArmyObj.getInt(SEQ_NUMBER_KEY));
				} else {
					armsDeploy.setCount(0);
					armsDeploy.setArm_id(0);
					armsDeploy.setSeq_number(i + 1);
				}

				armyList.add(armsDeploy);
			}

			armyDomain.setHeroArmyList(armyList);
		}

		return armyDomain;
	}

	/**
	 * 获取产兵队列响应字符串
	 * 
	 * @param proList
	 * @param type
	 *            1-------生产部队 2-------可分配部队列表
	 * @return
	 */
	public static String getArmyListResponse(List<ArmsDeploy> proList, int type) {
		// 队列实体
		JSONObject listObj = new JSONObject();
		listObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);

		// 队列数组实体
		JSONArray cityArmyArr = new JSONArray();
		// 当前该城镇赋闲的部队
		if (proList != null) {
			for (ArmsDeploy armsDeploy : proList) {
				JSONObject armsObj = new JSONObject();
				if (type == 1 && armsDeploy.getIsEnd() > 0) {
					// 生产部队信息
					armsObj.put(CONSCRIPT_WORK_ID_KEY,
							armsDeploy.getConscript_work_id());
					armsObj.put(CITY_ID_KEY, armsDeploy.getCity_id());
					armsObj.put(SOLDIER_ID_KEY, armsDeploy.getArms_id());
					armsObj.put(BUILD_ID_KEY, armsDeploy.getCity_build_id());
					armsObj.put(EXTRA_TIME_KEY, armsDeploy.getExtra_seconds());
					armsObj.put(LAST_TIME_KEY, armsDeploy.getIsEnd());
					armsObj.put(ARMY_HIRE_TIME_KEY, armsDeploy.getHire_time());
					cityArmyArr.add(armsObj);
				} else if (type == 2) {
					// 可以分配的部队信息
					armsObj.put(SOLDIER_ID_KEY, armsDeploy.getArm_id());
					armsObj.put(ARMY_ICON_KEY, armsDeploy.getArm_icon());
					armsObj.put(ARMY_NAME_KEY, armsDeploy.getArm_name());
					armsObj.put(ARMY_FAIGHT_CAP_KEY, armsDeploy.getFc());
					armsObj.put(ARMY_NUM_KEY, armsDeploy.getArm_count());
					armsObj.put(ARMY_USE_COMMAND_KEY,armsDeploy.getUse_command());
					armsObj.put(ARMY_GRADE_KEY,armsDeploy.getGrade());
					cityArmyArr.add(armsObj);
				}

			}
		}

		// 加入城镇赋闲部队信息
		listObj.put(ARMY_KEY, cityArmyArr);

		return listObj.toString();
	}

	/**
	 * 英雄部队总览表列表查询
	 * 
	 * @param proList
	 */
	public static String getReinforceArmyListResponse(List<ReinForceArm> reinArms) {
		// 队列实体
		JSONObject listObj = new JSONObject();
		listObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);

		// 队列数组实体
		JSONArray arr = new JSONArray();
		if (reinArms != null) {
			for (ReinForceArm reinArm : reinArms) {
				JSONObject reinArmsObj = new JSONObject();
				reinArmsObj.put(ROLE_HERO_ID_KEY, reinArm.getRole_hero_id());
				reinArmsObj.put(HERO_NAME_KEY, reinArm.getHero_name());
				reinArmsObj.put(HERO_COMMAND_KEY, reinArm.getCommand());
				reinArmsObj.put(HERO_ICON_KEY, reinArm.getSht_icon());
				reinArmsObj.put("toId", reinArm.getToCityId());
				reinArmsObj.put("toName", reinArm.getToCityName());
				reinArmsObj.put("fromId", reinArm.getFromCityId());
				reinArmsObj.put("fromName", reinArm.getFromCityName());

				// 每个英雄所拥有的部队信息
				JSONArray heroHasArmyArr = new JSONArray();
				List<ArmsDeploy> armsList = reinArm.getHeroArms();
				if (armsList != null && armsList.size() > 0) {
					for (ArmsDeploy arms : armsList) {
						JSONObject army = new JSONObject();
						army.put(SOLDIER_ID_KEY, arms.getArm_id());
						army.put(ARMY_NUM_KEY, arms.getCount());
						army.put(SEQ_NUMBER_KEY, arms.getSeq_number());
						army.put(ARMY_ICON_KEY, arms.getArm_icon());
						heroHasArmyArr.add(army);
					}
				}
				reinArmsObj.put(HERO_ARMY_KEY, heroHasArmyArr);

				// 英雄信息
				arr.add(reinArmsObj);
			}
		}

		listObj.put("rein_arms", arr);

		return listObj.toString();
	}
	/**
	 * 英雄部队总览表列表查询
	 * 
	 * @param proList
	 */
	public static String getHeroArmyListResponse(List<RoleHero> heroList) {
		// 队列实体
		JSONObject listObj = new JSONObject();
		listObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);

		// 队列数组实体
		JSONArray arr = new JSONArray();
		if (heroList != null) {
			for (RoleHero roleHero : heroList) {
				JSONObject heroArmsObj = new JSONObject();
				heroArmsObj.put(ROLE_HERO_ID_KEY, roleHero.getRole_hero_id());
				heroArmsObj.put(HERO_NAME_KEY, roleHero.getHero_name());
				heroArmsObj.put(HERO_COMMAND_KEY, roleHero.getCommand());
				heroArmsObj.put(HERO_ICON_KEY, roleHero.getIcon());
				heroArmsObj.put(ARMY_NUM_KEY, roleHero.getHero_arm_count());
				heroArmsObj.put(CITY_NAME_KEY, roleHero.getCity_name());
				heroArmsObj.put(HERO_IS_FREE_KEY, roleHero.getIs_free());

				// 每个英雄所拥有的部队信息
				JSONArray heroHasArmyArr = new JSONArray();
				List<ArmsDeploy> armsList = roleHero.getHeroArmys();
				if (armsList != null && armsList.size() > 0) {
					for (ArmsDeploy arms : armsList) {
						JSONObject army = new JSONObject();
						army.put(SOLDIER_ID_KEY, arms.getArm_id());
						army.put(ARMY_NUM_KEY, arms.getCount());
						army.put(SEQ_NUMBER_KEY, arms.getSeq_number());
						army.put(ARMY_ICON_KEY, arms.getArm_icon());
						heroHasArmyArr.add(army);
					}
				}
				heroArmsObj.put(HERO_ARMY_KEY, heroHasArmyArr);

				// 英雄信息
				arr.add(heroArmsObj);
			}
		}

		listObj.put(HERO_KEY, arr);

		return listObj.toString();
	}

	/**
	 * 构建指定英雄当前军事信息JSON字符串
	 * 
	 * @param heroArms
	 * @return
	 */
	public static String getHeroArmyResponseByHeroId(List<ArmsDeploy> heroArms) {
		// 队列实体
		JSONObject listObj = new JSONObject();
		listObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);

		// 队列数组实体
		JSONArray arr = new JSONArray();

		if(heroArms!=null && heroArms.size()>0){
			for (ArmsDeploy arms : heroArms) {
				JSONObject army = new JSONObject();
				army.put(SOLDIER_ID_KEY, arms.getArm_id());
				army.put(ARMY_NUM_KEY, arms.getCount());
				army.put(SEQ_NUMBER_KEY, arms.getSeq_number());
				army.put(ARMY_ICON_KEY, arms.getArm_icon());
				army.put(ARMY_NAME_KEY, arms.getArm_name());
				army.put(ARMY_USE_COMMAND_KEY, arms.getUse_command());
				army.put(ARMY_FAIGHT_CAP_KEY, arms.getFc());
				arr.add(army);
			}

		}

		listObj.put(HERO_ARMY_KEY, arr);

		return listObj.toString();
	}

	/**
	 * 获取兵种详细信息JSON表达式
	 * 
	 * @param armsDeploy
	 * @return
	 */
	public static String getArmsDetailInfo(ArmsDeploy armsDeploy) {
		// 队列实体
		JSONObject armObj = new JSONObject();
		armObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
		armObj.put(ARMY_ICON_KEY, armsDeploy.getArm_icon());
		armObj.put(ARMY_NAME_KEY, armsDeploy.getArm_name());
		armObj.put(ARMY_ID_KEY, armsDeploy.getArm_id());
		armObj.put(ARMY_GRADE_KEY, armsDeploy.getGrade());
		armObj.put(ARMY_HP_KEY, armsDeploy.getHp());
		armObj.put(ARMY_PHYSIC_ATTACK_KEY, armsDeploy.getPhysic_attack());
		armObj.put(ARMY_PHYSIC_DEFENCE_KEY, armsDeploy.getPhysic_defence());
		armObj.put(ARMY_MEGIC_ATTACK_KEY, armsDeploy.getMagic_attack());
		armObj.put(ARMY_MEGIC_DEFENCE_KEY, armsDeploy.getMagic_defence());
		armObj.put(ARMY_SPEED_KEY, armsDeploy.getSpeed());
		armObj.put(ARMY_DISTANCE_ATTACK_KEY, armsDeploy.getDistance_attack());
		armObj.put(ARMY_USE_COMMAND_KEY, armsDeploy.getUse_command());
		armObj.put(ARMY_HIRE_PRIVCE_KEY, armsDeploy.getHire_privce());
		armObj.put(ARMY_HIRE_TIME_KEY, armsDeploy.getHire_time());
		armObj.put(ARMY_EXP_KEY, armsDeploy.getExp());
		armObj.put(ARMY_DESC_KEY, armsDeploy.getArm_desc());
		armObj.put(ARMY_FAIGHT_CAP_KEY, armsDeploy.getFc());

		return armObj.toString();
	}
}
