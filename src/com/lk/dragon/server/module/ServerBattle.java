/**
 *
 *
 * 文件名称： ServerBattle.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-8-28 上午10:14:35
 */
package com.lk.dragon.server.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.jca.cci.CciOperationNotSupportedException;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import com.lk.dragon.db.domain.ArmsDeploy;
import com.lk.dragon.db.domain.RoleHero;
import com.lk.dragon.db.domain.WarProduce;
import com.lk.dragon.db.domain.WarTeamVo;
import com.lk.dragon.db.domain.WorldMap;
import com.lk.dragon.server.domain.BattleDomain;
import com.lk.dragon.server.domain.CreepsDemon;
import com.lk.dragon.server.module.analysis.BattleRequestAnalysis;
import com.lk.dragon.service.WarDeployInfoService;
import com.lk.dragon.service.WorldMapService;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.JSONUtil;
import com.lk.dragon.util.OperateLogUtil;
import com.lk.dragon.util.SocketUtil;
import com.lk.dragon.util.SpringBeanUtil;

public class ServerBattle {
	/** 战斗数据实体 **/
	private BattleDomain battleDomain;

	private WarDeployInfoService warDeployInfoService;
	private WorldMapService worldMapService;

	public ServerBattle() {
	}

	/**
	 * 构造函数
	 * 
	 * @param battleDomain
	 */
	public ServerBattle(BattleDomain battleDomain) {
		this.battleDomain = battleDomain;
		this.warDeployInfoService = SpringBeanUtil
				.getBean(WarDeployInfoService.class);
		this.worldMapService = SpringBeanUtil.getBean(WorldMapService.class);
	}

	/**
	 * 战斗分析函数
	 */
	public void battleAnalysis() {
		switch (battleDomain.getType()) {
		case Constants.CAN_BATTLE_HERO_TYPE: {
			// 查询某个城镇可以出征的英雄列表查询
			canBattleHero();
			break;
		}
		case Constants.SEND_ARMY_TYPE: {
			// 派兵请求
			sendArmy();
			break;
		}
		case Constants.CANCEL_SEND_ARMY_TYPE: {
			// 派兵取消请求
			cancelSendArmy();
			break;
		}
		case Constants.RATE_SEND_ARMY_TYPE: {
			// 加速派兵请求
			rateSendArmy();
			break;
		}
		case Constants.DETECT_CITY_INFO_TYPE: {
			// 侦查城镇防守信息请求
			detectCityInfo();
			break;
		}
		case Constants.COLLECT_POINT_TYPE: {
			// 收藏坐标点请求
			collectPoint();
			break;
		}
		case Constants.DELE_POINT_TYPE: {
			// 删除已收藏的坐标点请求
			delePoint();
			break;
		}
		case Constants.POINT_LIST_TYPE: {
			// 查询已收藏坐标点请求
			pointList();
			break;
		}
		case Constants.ARENA_TYPE: {
			// 竞技场匹配请求
			arena();
			break;
		}
		case Constants.ARENA_RESULT_TYPE: {
			// 竞技场结果上传请求
			arenaResult();
			break;
		}
		case Constants.CHECK_BATTLE_RESULT_TYPE: {
			// 查看战报请求
			checkBattleResult();
			break;
		}
		case Constants.CREATE_WAR_TEAM: {
			// 创建出征队列
			createWarTeam();
			break;
		}
		case Constants.GET_WAR_TEAM_INFO: {
			// 查看出征队列信息
			selectWarTeamDetailInfo();
			break;
		}
		case Constants.CALL_BACK_TEAM: {
			// 召回在外部队
			callBackTeam();
			break;
		}
		case Constants.CURRENT_ENEMY_TEAM: {
			// 当前来袭部队
			getCurrentEnemyTeam();
			break;
		}
		}
	}

	/**
	 *  当前来袭部队
	 */
	private void getCurrentEnemyTeam() {
		List<WarProduce> teams =  warDeployInfoService.getCurrentEnemyTeam(battleDomain.getRoleId());
		SocketUtil.responseClient(battleDomain.getCtx(), BattleRequestAnalysis.getCurrentEnemyTeamResponse(teams));
	}

	/**
	 * 召回在外部队
	 */
	private void callBackTeam() {
		// 队列ID
		long war_team_id = battleDomain.getWar_team_id();
		// 构造响应JSON对象
		JSONObject callBackTeamJ = new JSONObject();

		try {
			// 获取返回时间
			long backUseTime = warDeployInfoService
					.callBackWarTeam(war_team_id);

			callBackTeamJ.put(Constants.RESULT_KEY, Constants.REQUEST_SUCCESS);
			callBackTeamJ.put("last_time", backUseTime);
		} catch (Exception e) {
			e.printStackTrace();
			callBackTeamJ.put(Constants.RESULT_KEY, Constants.REQUEST_FAIL);

			// 记录错误日志
			String detail = "召回在外部队：队列ID" + war_team_id + "\r\n"
					+ OperateLogUtil.getExceptionStackInfo(e);
			OperateLogUtil.insertOperateLog(battleDomain.getRoleId(), detail,
					OperateLogUtil.BATTLE_MODULE, OperateLogUtil.FAIL);

		}
		SocketUtil.responseClient(battleDomain.getCtx(),
				callBackTeamJ.toString());
	}

	/**
	 * 查看出征队列信息
	 */
	private void selectWarTeamDetailInfo() {
		String condition = "";
		List<WarProduce> war_teams = null;
		long role_id = battleDomain.getRoleId();
		long war_team_id = battleDomain.getWar_team_id();
		if (role_id > 0) {
			condition = " WHERE t.role_id = " + role_id;
			war_teams = warDeployInfoService.selectWarTeamDetailInfo(condition);
			//throw new RuntimeException("413 ERROR");
		}
		if (war_team_id > 0) {
			try {
				WarProduce produce = warDeployInfoService.selectWarTeamById(war_team_id);
				if (produce == null) {
					war_teams = null;
				}else{
					war_teams = new ArrayList<WarProduce>();
					war_teams.add(produce);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		SocketUtil.responseClient(battleDomain.getCtx(),BattleRequestAnalysis.selectWarTeamDetailInfo(war_teams));
	}

	/**
	 * 创建出征队列
	 */
	private void createWarTeam() {
		try {
			boolean flag = false;
			// -------------------获取队列基本信息
			int war_type = battleDomain.getWar_type();// 出征模式
			int status = 1;// 构建队列状态码
			int tagx = battleDomain.getTag_x();// 目标X坐标
			int tagy = battleDomain.getTag_y();// 目标Y坐标
			String tag_name = battleDomain.getTag_name();// 目标名
			long tag_role_id = battleDomain.getTag_role_id();// 目标角色ID
			long from_role_id = battleDomain.getRoleId();// 队列所属角色ID
			long from_city_id = battleDomain.getCityId();// 出发城邦ID
			int fromX = battleDomain.getFrom_x();// 出发X坐标
			int fromY = battleDomain.getFrom_y();// 出发Y坐标
			int use_food = battleDomain.getUse_food();// 本次出征消耗粮草
			int use_time = battleDomain.getUse_time();// 本次出征消耗时间
			List<CreepsDemon> creeps = null;// 针对野外狩猎 野怪信息
			String herosId = battleDomain.getHerosId();// 本次出征辖下英雄ID结合

			// 判断客户端传入数据是否有效
			if (war_type <= 0 || herosId.trim().length() <= 0) {
				SocketUtil.responseClient(battleDomain.getCtx(),
						JSONUtil.getBooleanResponse(flag));
				return;
			}

			// --根据出征模式获取所需信息
			switch (war_type) {
			case Constants.WAR_TYPE_ROB:// 城邦掠夺战
				// 设置队伍状态
				status = Constants.TEAM_STATUS_GO;
				break;
			case Constants.WAR_TYPE_CONQUER:// 城邦征服战
				// 设置队伍状态
				status = Constants.TEAM_STATUS_GO;
				break;
			case Constants.WAR_TYPE_WILD:// 野外狩猎战
				creeps = new ArrayList<CreepsDemon>();
				int sum_command = battleDomain.getSum_command();// 队列统帅总值
				int creepCount = 0;// 野怪数量
				if (battleDomain.getCreeps_type() == 2) {
					// 随机野怪
					WorldMap wildHurt = worldMapService.getRandomWildInfo(tagx,
							tagy);
					if (wildHurt == null || wildHurt.getWildArms() == null) {
						SocketUtil.responseClient(battleDomain.getCtx(),
								JSONUtil.getBooleanResponse(flag));
						return;
					}
					int creepId = wildHurt.getWildArms().getArm_id();// 野怪ID
					int creepGrade = wildHurt.getWildArms().getGrade();// 野怪等级
					creepCount = (int) Math.ceil((double) (sum_command * 2)
							/ (creepGrade * 3));// 获取野怪数量
					// 构建野怪信息
					creeps.add(new CreepsDemon(creepId, creepCount));

				} else {
					// 隐形野怪

					// 根据distance确定此次狩猎处野怪等级 获取分组标志
					int distance = battleDomain.getDistance();
					// 构建野怪信息
					creeps = warDeployInfoService.makeCreepsInfo(sum_command,
							distance);
				}
				// 设置队伍状态
				status = Constants.TEAM_STATUS_GO;
				break;
			case Constants.WAR_TYPE_WILDSRC_ON: // 资源争夺战-攻占后部队驻守
				// 设置队伍状态
				status = Constants.TEAM_STATUS_GO;
				break;
			case Constants.WAR_TYPE_WILDSRC_OFF:// 资源争夺战-攻占后部队返回
				// 设置队伍状态
				status = Constants.TEAM_STATUS_GO;
				break;
			case Constants.WAR_TYPE_REFINCITY:// 增援友军城邦
				// 设置队伍状态
				status = Constants.TEAM_STATUS_REINFORCE;
				break;
			case Constants.WAR_TYPE_REFINSRC:// 增援已方资源点
				// 设置队伍状态
				status = Constants.TEAM_STATUS_REINFORCE;
				break;
			default:
				SocketUtil.responseClient(battleDomain.getCtx(),
						JSONUtil.getBooleanResponse(flag));
				break;
			}
			// 创建出征队列 返回队列ID
			Map<String,Long> resMap = warDeployInfoService.createWarTeam(from_city_id,
					from_role_id, status, use_time, tag_name, tagx, tagy,
					use_food, creeps, war_type, new WarTeamVo(tag_role_id,
							fromX, fromY), herosId);
			long teamId = resMap.get(WarDeployInfoService.WAR_TEAM_ID_KEY);
			if (teamId > 0) {
				flag = true;
				JSONObject res = new JSONObject();
				res.put(Constants.RESULT_KEY, Constants.REQUEST_SUCCESS);
				res.put("team_id", teamId);
				
				//目标城邦ID
				if(war_type == Constants.WAR_TYPE_CONQUER || war_type == Constants.WAR_TYPE_ROB)
					res.put("tag_city_id", resMap.get(WarDeployInfoService.TAG_CITY_ID_KEY));
				SocketUtil.responseClient(battleDomain.getCtx(), res.toString());

				// 新增操作日志
				String detail = "成功创建出征队列,出征类型：" + war_type + ", 出发城镇id:"
						+ from_city_id;
				OperateLogUtil.insertOperateLog(battleDomain.getRoleId(),
						detail, OperateLogUtil.BATTLE_MODULE,
						OperateLogUtil.SUCCESS);

			} else if (teamId == -10l) {
				// 队列中有英雄状态不符合出征条件
				JSONObject res = new JSONObject();
				res.put(Constants.RESULT_KEY, Constants.REQUEST_FAIL);
				res.put("reason", "队列中存在状态不符的英雄,请重建队列");
				SocketUtil.responseClient(battleDomain.getCtx(), res.toString());
			} else {
				SocketUtil.responseClient(battleDomain.getCtx(),
						JSONUtil.getBooleanResponse(false));
				// 新增操作日志
				String detail = "创建出征队列失败";
				OperateLogUtil.insertOperateLog(battleDomain.getRoleId(),
						detail, OperateLogUtil.BATTLE_MODULE,
						OperateLogUtil.FAIL);

			}
		} catch (Exception e) {
			e.printStackTrace();

			// 新增操作日志
			String detail = "创建出征队列失败,详细信息："
					+ OperateLogUtil.getExceptionStackInfo(e);
			OperateLogUtil.insertOperateLog(battleDomain.getRoleId(), detail,
					OperateLogUtil.BATTLE_MODULE, OperateLogUtil.FAIL);
		}

	}

	/**
	 * 城镇能够出征的英雄列表查询
	 */
	private void canBattleHero() {
		// 查询当前城镇的可以派出的英雄列表
		long cityId = battleDomain.getCityId();
		List<Object> heroList = null;

		// 响应客户端请求
		SocketUtil.responseClient(battleDomain.getCtx(),
				BattleRequestAnalysis.canBattleHeroResponse(heroList));
	}

	/**
	 * 出兵请求
	 */
	private void sendArmy() {

	}

	/**
	 * 取消出兵
	 */
	private void cancelSendArmy() {

	}

	/**
	 * 加速出兵
	 */
	private void rateSendArmy() {

	}

	/**
	 * 侦查敌方城镇的防守信息
	 */
	private void detectCityInfo() {

	}

	/**
	 * 收藏坐标点
	 */
	private void collectPoint() {

	}

	/**
	 * 删除收藏的坐标点
	 */
	private void delePoint() {

	}

	/**
	 * 坐标点查询
	 */
	private void pointList() {

	}

	/**
	 * 竞技场匹配请求
	 */
	private void arena() {

	}

	/**
	 * 竞技场匹配请求
	 */
	private void arenaResult() {

	}

	/**
	 * 查看战斗结果
	 */
	private void checkBattleResult() {

	}
}
