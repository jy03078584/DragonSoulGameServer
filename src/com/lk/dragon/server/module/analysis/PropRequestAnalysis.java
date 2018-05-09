/**
 *
 *
 * 文件名称： PropAnalysis.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-9-18 下午2:07:16
 */
package com.lk.dragon.server.module.analysis;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lk.dragon.db.domain.Buff;
import com.lk.dragon.db.domain.RoleProps;
import com.lk.dragon.server.domain.PropDomain;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.JSONUtil;

public class PropRequestAnalysis {
	// 请求字段
	private static final String PROP_REQUEST_KEY = "propRequestType";
	/** 道具id **/
	private static final String PROP_ID_KEY = "prop_id";
	/** 角色id **/
	private static final String ROLE_ID_KEY = "role_id";
	/** 关联表id **/
	private static final String RELATION_KEY = "relation_id";
	/** 使用数量 **/
	private static final String USE_NUM_KEY = "use_num";
	/** 道具请求类型 **/
	private static final String PROP_TYPE_KEY = "prop_type";
	private static final String PROP_NUM_KEY = "prop_num";
	private static final String REWARD_IS_RANDOM = "is_random";
	// 消耗宝石数量
	private static final String USE_DIAMOND_NUM_KEY = "diamond_num";
	// 增加格数
	private static final String ADD_BAG_NUM_KEY = "bag_num";

	// 喝药
	private static final String ROLE_HERO_ID_KEY = "role_hero_id";
	private static final String ROLE_PROP_ID_KEY = "role_prop_id";
	private static final String VALUE_KEY = "value";

	private static final String BUFF_ID = "buff_id";
	private static final String BUFF_TYPE_ID = "buff_type";
	private static final String BUFF_TAG_ID = "tag_id";
	/****************** 响应字符串 ********************************/
	private static final String RESULT_KEY = "result";

	// 道具列表
	private static final String PROP_KEY = "props";
	private static final String PROP_IS_BIND_KEY = "prop_is_bind";
	private static final String PROP_NAME_KEY = "prop_name";
	private static final String PROP_ICON_KEY = "prop_icon";
	private static final String PROP_COMMENT_KEY = "prop_comment";
	private static final String PROP_REPURCHASE_KEY = "repurchase";
	private static final String USE_GOLD_KEY = "gold";
	private static final String GEM_LEV_KEY = "gem_lev";
	private static final String GEM_VALUE_KEY = "buf_value";
	private static final String GEM_TYPE_KEY = "gem_type";
	
	private static final String REWARD_ISRANDOM_KEY = "is_random";
	private static final String REWARD_RANDOM_CNT_KEY = "rand_cnt";

	private static final String EQUIP_LOCATION_KEY = "location";
	private static final String QUALITY_KEY = "quality";
	private static final String COMMAND_LEV_KEY = "command_lev";
	private static final String INC_PROPERTY_KEY = "inc_pro";
	private static final String EQUIP_GEM_KEY = "gems";

	/**
	 * 
	 * @param json
	 * @return
	 */
	public static PropDomain getPropInfo(String json) {
		// 道具请求实体
		PropDomain prop = new PropDomain();

		// 获取data信息
		JSONObject propData = JSONUtil.getData(json);
		prop.setType(propData.getInt(PROP_REQUEST_KEY));
		prop.setRelationId(propData.has(RELATION_KEY) ? propData
				.getLong(RELATION_KEY) : 0);
		prop.setRoleId(propData.has(ROLE_ID_KEY) ? propData
				.getLong(ROLE_ID_KEY) : 0);
		prop.setPropId(propData.has(PROP_ID_KEY) ? propData
				.getInt(PROP_ID_KEY) : 0);
		prop.setUseNum(propData.has(USE_NUM_KEY) ? propData.getInt(USE_NUM_KEY)
				: 0);
		prop.setPropNum(propData.has(PROP_NUM_KEY) ? propData
				.getInt(PROP_NUM_KEY) : 0);
		prop.setPropType(propData.has(PROP_TYPE_KEY) ? propData
				.getInt(PROP_TYPE_KEY) : 0);
		prop.setRoleHeroId(propData.has(ROLE_HERO_ID_KEY) ? propData
				.getLong(ROLE_HERO_ID_KEY) : 0);
		prop.setRolePropId(propData.has(ROLE_PROP_ID_KEY) ? propData
				.getLong(ROLE_PROP_ID_KEY) : 0);
		prop.setValue(propData.has(VALUE_KEY) ? propData.getInt(VALUE_KEY) : 0);
		prop.setIsRandom(propData.has(REWARD_IS_RANDOM) ? propData.getInt(REWARD_IS_RANDOM) : 0);
		prop.setRandCnt(propData.has(REWARD_RANDOM_CNT_KEY) ? propData.getInt(REWARD_RANDOM_CNT_KEY) : 0);
		prop.setGold(propData.has(USE_GOLD_KEY) ? propData.getInt(USE_GOLD_KEY) : 0);
		prop.setBuff_id(propData.has(BUFF_ID) ? propData.getInt(BUFF_ID) : 0);
		prop.setBuffKeyId(propData.has(BUFF_TAG_ID) ? propData.getLong(BUFF_TAG_ID) : 0);
		prop.setBuffType(propData.has(BUFF_TYPE_ID) ? propData.getInt(BUFF_TYPE_ID) : -1);

		return prop;
	}

	/**
	 * 获取道具详细信息响应字符串
	 * 
	 * @param props
	 * @return
	 */
	public static String getPropDetailResponse(RoleProps prop,int type) {
		// 构造登录结果
		JSONObject resultObj = new JSONObject();
		JSONObject propObj = new JSONObject();
		if(prop != null){
			resultObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
			propObj.put(PROP_NUM_KEY, prop.getProps_count());
	        propObj.put(PROP_ID_KEY, prop.getProps_id());
			propObj.put(PROP_IS_BIND_KEY, prop.getIs_bind());
			propObj.put(PROP_NAME_KEY, prop.getProps_name());
			propObj.put(PROP_ICON_KEY, prop.getProps_icon());
			propObj.put(PROP_COMMENT_KEY, prop.getProps_comment());
			propObj.put(EQUIP_LOCATION_KEY, prop.getEquip_location());
			propObj.put(QUALITY_KEY, prop.getQuality());
			propObj.put(COMMAND_LEV_KEY, prop.getCommand_lev());
			propObj.put(INC_PROPERTY_KEY, prop.getInc_property());
			propObj.put(PROP_REPURCHASE_KEY, prop.getProps_repurchase());
			
			if(type == Constants.PROP_EQUIP){
				//装备
				if(prop.getGems() != null && !prop.getGems().equals(""))
					propObj.put(EQUIP_GEM_KEY, "[" + prop.getGems()+ "]");
			}else if(type == Constants.PROP_GEM){
				//宝石
				propObj.put(GEM_TYPE_KEY, prop.getBuff_type());
				propObj.put(GEM_LEV_KEY, prop.getGem_equaitly());
				propObj.put(GEM_VALUE_KEY, prop.getBuff_value());
			}else if(type == Constants.PROP_REWARD){
				//礼包
				propObj.put(REWARD_ISRANDOM_KEY, prop.getIs_random());
				propObj.put(REWARD_RANDOM_CNT_KEY, prop.getRand_cnt());
			}
			
		}
		resultObj.put(PROP_KEY, propObj);
		return resultObj.toString();
	}

	/**
	 * 获取系统奖励响应字符串
	 * 
	 * @param props
	 * @return
	 */
	public static String getRewardPropResponse(List<RoleProps> props) {
		
		// 构造登录结果
		JSONObject resultObj = new JSONObject();
		resultObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
		//道具集合
		JSONArray rewardJA = new JSONArray();
		
		//遍历集合
		if(props != null && props.size() > 0){
			for (RoleProps prop : props) {
				//单个道具对象
				JSONObject propObj = new JSONObject();
				propObj.put(ROLE_PROP_ID_KEY, prop.getRole_props_id());
				propObj.put(PROP_NAME_KEY, prop.getProps_name());
				propObj.put(PROP_ICON_KEY, prop.getProps_icon());
				propObj.put(PROP_NUM_KEY, prop.getProps_count());
				propObj.put(PROP_TYPE_KEY, prop.getProps_type());
				//存入JSON数组
				rewardJA.add(propObj);
			}
		}
		resultObj.put(PROP_KEY, rewardJA);
		return resultObj.toString();
	}
	/**
	 * 获取道具列表响应字符串
	 * 
	 * @param propList
	 * @return
	 */
	public static String getPropListResponse(List<RoleProps> propList) {
		// 构造登录结果
		JSONObject resultObj = new JSONObject();
		resultObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);

		JSONArray arr = new JSONArray();
		if (propList != null) {
			for (int i = 0; i < propList.size(); i++) {
				JSONObject propObj = new JSONObject();
				RoleProps prop = propList.get(i);
				propObj.put(RELATION_KEY, prop.getRole_props_id());
				propObj.put(PROP_TYPE_KEY, prop.getProps_type());
				arr.add(propObj);
			}
		}
		resultObj.put(PROP_KEY, arr);

		return resultObj.toString();
	}

	/**
	 * 包裹扩容
	 * 
	 * @param map
	 * @return
	 */
	public static String addBagResponse(Map<String, Integer> map) {
		// 构造登录结果
		JSONObject resultObj = new JSONObject();
		resultObj.put(RESULT_KEY, Constants.REQUEST_SUCCESS);
		resultObj.put(USE_DIAMOND_NUM_KEY, map.get(Constants.USE_DIAMON));
		resultObj.put(ADD_BAG_NUM_KEY, map.get(Constants.ADD_BAG_COUNT));

		return resultObj.toString();
	}
	
	/**
	 * BUFF信息
	 * @param buff
	 * @return
	 */
	public static JSONArray getBuffInfoResponse(List<Buff> buffList){
		// 构造登录结果
		JSONArray buffAJ = new JSONArray();
		if(buffList != null && buffList.size() > 0 ){
			for (Buff buff : buffList) {
				buffAJ.add(getBuffInfoResponse(buff));
			}
		}
		return buffAJ;
	}
	
	public static JSONObject getBuffInfoResponse(Buff  buff){
		JSONObject buffJ = new JSONObject();
		buffJ.put("buff_id", buff.getBuff_id());
		buffJ.put("buff_name", buff.getBuff_name());
		buffJ.put("buff_desc", buff.getBuff_desc());
		buffJ.put("buff_type", buff.getBuff_type());
		buffJ.put("buff_icon", buff.getBuff_icon());
		buffJ.put("last_second", buff.getLastSecond() < 0 ? 0 : buff.getLastSecond());
		return buffJ;
	}
}
