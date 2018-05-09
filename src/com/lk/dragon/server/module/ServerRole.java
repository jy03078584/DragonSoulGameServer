/**
 *
 *
 * 文件名称： ServerRole.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-9-5 上午10:14:36
 */
package com.lk.dragon.server.module;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Element;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.lk.dragon.db.domain.Role;
import com.lk.dragon.db.domain.RoleProps;
import com.lk.dragon.db.domain.Tools;
import com.lk.dragon.server.domain.ConnDomain;
import com.lk.dragon.server.domain.RoleDomain;
import com.lk.dragon.server.module.analysis.PropRequestAnalysis;
import com.lk.dragon.server.module.analysis.RoleRequestAnalysis;
import com.lk.dragon.service.CacheService;
import com.lk.dragon.service.RoleInfoService;
import com.lk.dragon.service.RolePropsInfoService;
import com.lk.dragon.service.SqlToolsService;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.DateTimeUtil;
import com.lk.dragon.util.JSONUtil;
import com.lk.dragon.util.OperateLogUtil;
import com.lk.dragon.util.SocketUtil;
import com.lk.dragon.util.SpringBeanUtil;

public class ServerRole {
	/** 角色请求 **/
	private RoleDomain roleDomain;
	@Autowired
	private RoleInfoService roleInfoService;
	@Autowired
	private SqlToolsService toolsService;

	public ServerRole() {
	}

	/**
	 * 构造函数
	 * 
	 * @param roleDomain
	 */
	public ServerRole(RoleDomain roleDomain) {
		this.roleInfoService = SpringBeanUtil.getBean(RoleInfoService.class);
		this.toolsService = SpringBeanUtil.getBean(SqlToolsService.class);
		this.roleDomain = roleDomain;
	}

	/**
	 * 角色请求内部分析 将各个内部请求类型，下发到各个处理模块进行详细处理
	 */
	public void roleAnalysis() {
		switch (roleDomain.getType()) {
		case Constants.ROLE_ADD_TYPE: {
			// 新增角色
			addRole();
			break;
		}
		case Constants.ROLE_DELETE_TYPE: {
			// 删除角色
			deleteRole();
			break;
		}
		case Constants.ROLE_UPDATE_TYPE: {
			// 更新角色 名字
			updateRoleName();
			break;
		}
		case Constants.ROLE_LIST_TYPE: {
			// 获取角色列表信息
			getRoleList();
			break;
		}
		case Constants.ROLE_INFO_GET_TYPE: {
			// 同步角色相关的所有信息回客户端
			getRoleAllInfo();
			break;
		}
		case Constants.ROLE_LOGIN_TYPE: {
			// 角色登入请求
			roleLogin();
			break;
		}
		case Constants.ROLE_LOGOUT_TYPE: {
			// 角色登出请求处理
			roleLogout();
			break;
		}
		case Constants.ROLE_CHEST_CHECK_TYPE: {
			// 查看
			chestCheck();
			break;
		}
		case Constants.ROLE_CHEST_GET_TYPE: {
			// 领取
			chestGet();
			break;
		}
		}
	}

	/**
	 * 新增角色
	 */
	private void addRole() {
		// 创建角色对象
		Role role = new Role();

		// 设置相应值
		role.setUser_id(roleDomain.getUser_id());
		role.setRace(roleDomain.getRace());
		role.setIcon(roleDomain.getIcon());
		role.setRole_name(roleDomain.getRole_name());
		role.setSht_ico(roleDomain.getShortIcon());
		role.setSex(roleDomain.getSex());

		// 将新角色入库
		Map<String, Long> map = roleInfoService.createRole(role);

		// 根据结果，获取相应的处理
		String responseStr = RoleRequestAnalysis.getAddRoleResponseInfo(map,
				roleDomain.getUser_id());

		// 将信息写回到客户端
		SocketUtil.responseClient(roleDomain.getCtx(), responseStr);
	}

	/**
	 * 更新角色名
	 */
	private void updateRoleName() {
		// 创建role对象
		Role role = new Role();

		// 设置相应的数据
		role.setRole_id(roleDomain.getRole_id());
		role.setRole_name(roleDomain.getRole_name());

		JSONObject resJ = new JSONObject();
		// 更新数据库记录
		try {
			roleInfoService.changeRoleName(role, roleDomain.getRolePropId());
			resJ.put(Constants.RESULT_KEY, Constants.REQUEST_SUCCESS);
		} catch (Exception e) {
			if (e.getMessage().equals(RolePropsInfoService.PROPS_COUNT_ERROR_EN)) {
				resJ.put(Constants.RESULT_KEY, Constants.REQUEST_FAIL);
				resJ.put("reason", RolePropsInfoService.PROPS_COUNT_ERROR);
			} else {
				resJ.put(Constants.RESULT_KEY, Constants.REQUEST_ERROR);
				e.printStackTrace();
			}
		}

		// //将信息写回到客户端
		SocketUtil.responseClient(roleDomain.getCtx(), resJ.toString());
	}

	/**
	 * 删除角色
	 */
	private void deleteRole() {
		// 获取角色id
		long id = roleDomain.getRole_id();
		Role role = new Role();
		role.setRole_id(id);

		// 删除该条记录，以及相应其他所有的涉及信息
		boolean delete = false;
		int logRes = Constants.LOG_RES_FAIL;
		String title = "";
		try {
			delete = roleInfoService.deleteRole(role);
			title = "删除角色\r\n:被删除角色ID:" + id + "\r\n删除操作成功";
			logRes = Constants.LOG_RES_SUCESS;
		} catch (Exception e) {
			e.printStackTrace();
			title = OperateLogUtil.getExceptionStackInfo(e);
		}
		String responseStr = "";

		// 获取结果信息
		if (delete) {
			// 获取反馈结果
			responseStr = RoleRequestAnalysis.getRoleResponseInfo(
					Constants.REQUEST_SUCCESS, Constants.ROLE_DELETE_TYPE);
		} else {
			//
			responseStr = RoleRequestAnalysis.getRoleResponseInfo(
					Constants.REQUEST_FAIL, Constants.ROLE_DELETE_TYPE);
		}
		toolsService.addNewLogInfo(new Tools(id, "角色模块", title, logRes));
		// 将信息写回到客户端
		SocketUtil.responseClient(roleDomain.getCtx(), responseStr);
	}

	/**
	 * 获取角色列表信息
	 */
	private void getRoleList() {
		long user_id = roleDomain.getUser_id();
		Role role = roleInfoService.findRolesByUserId(user_id);
		// 根据结果，获取相应的处理
		String responseStr = RoleRequestAnalysis.getRoleListResponseInfo(role);
		// 将信息写回到客户端
		SocketUtil.responseClient(roleDomain.getCtx(), responseStr);
	}

	/**
	 * 将某角色的所有信息同步到客户端
	 */
	private void getRoleAllInfo() {
		// 获取该角色主城的相关信息写回客户端

		// 然后关闭连接

	}

	/**
	 * 角色登入请求
	 */
	private void roleLogin() {
		// 获取角色登录记录
		Role role = roleInfoService.getRoleOnLineStatus(roleDomain.getRole_id());

		JSONObject resJ = new JSONObject();
		String response = "";
		
		if (role.getIs_online() == Constants.ROLE_ONLINE) {
			// 角色已在线
			resJ.put(JSONUtil.RESULT_KEY, Constants.REQUEST_FAIL);
			resJ.put(JSONUtil.REASON_KEY, Constants.REPEAT_LOGIN);
			response = resJ.toString();
			SocketUtil.responseClient(roleDomain.getCtx(), response);
			return;
		}
		try {
			resJ.put(Constants.RESULT_KEY, 1);
			int days = 0;
			//距上次登陆间隔天数
             days    = DateTimeUtil.compareDayWithNow(role.getLast_login_time());
			// 已签到次数(含今天)
			if (days > 0 ) {
				//是当日首次登陆  签到次数+days
				role.setSum_login_count(role.getSum_login_count() + days);
				// 领取当日登陆奖励
				Map<String, Object> loginMap = roleInfoService.rewardLoginDaily(roleDomain.getRole_id());
				resJ.put("goldDaily", (Integer) loginMap.get(RoleInfoService.TAXATION_DAILY_KEY));
				resJ.put("diamonDaily", (Integer) loginMap.get(RoleInfoService.DIAMOND_DAILY_KEY));
				//更新签到奖励信息
				role.setChests(checkCanGetChest(role));
			}

				role.setIs_online(Constants.ROLE_ONLINE);
				role.setRole_id(roleDomain.getRole_id());
				role.setLast_login_time("NULL");
				role.setLast_logout_time(null);
				roleInfoService.updateOnLineStatus(role);
				
				//缓存连接
				CacheService.connCache.put(roleDomain.getRole_id(),new ConnDomain(roleDomain.getCtx(), roleDomain.getRole_id()));
			// 将结果写回客户端
			SocketUtil.responseClient(roleDomain.getCtx(), resJ.toString());
		} catch (Exception e) {
			SocketUtil.responseClient(roleDomain.getCtx(),JSONUtil.getBooleanResponse(false));
			e.printStackTrace();
			return;
		}

	}

	/**
	 * 角色登出请求
	 */
	private void roleLogout() {
		// 将角色的状态更新为登出状态
		Role role = new Role();
		role.setIs_online(Constants.ROLE_OFFLINE);
		role.setRole_id(roleDomain.getRole_id());
		role.setLast_logout_time("TEMP");
		roleInfoService.updateOnLineStatus(role);

		// 将结果写回客户端
		// SocketUtil.responseClient(roleDomain.getCtx(),
		// JSONUtil.getBooleanResponse(result));
	}

	private void chestCheck() {
		String chests = roleInfoService.getRoleChest(roleDomain.getRole_id());
		if (chests == null || "".equals(chests)) {
			SocketUtil.responseClient(roleDomain.getCtx(),
					JSONUtil.getBooleanResponse(false));
		} else {
			SocketUtil.responseClient(roleDomain.getCtx(),
					RoleRequestAnalysis.getChestResponse(chests));
		}

	}

	/**
	 * 开启签到奖励
	 */
	private void chestGet() {
		// 领取奖励
		long role_id = roleDomain.getRole_id();
		int local = roleDomain.getLocal();

		String res = JSONUtil.getBooleanResponse(false);
		try {
			List<RoleProps> rwards = roleInfoService.doRoleChestGetReward(
					role_id, local);
			res = PropRequestAnalysis.getRewardPropResponse(rwards);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 响应客户端 返回获得道具信息
		SocketUtil.responseClient(roleDomain.getCtx(), res);
	}

	/**
	 * 更新奖励
	 * 
	 * @param role
	 */
	private String checkCanGetChest(Role role) {
		int sum = role.getSum_login_count();
		JSONArray array = JSONArray.fromObject(role.getChests());
		JSONObject o = null;
		if (sum >= 30) {
			o = array.getJSONObject(4);
		} else if (sum >= 20) {
			o = array.getJSONObject(3);
		} else if (sum >= 12) {
			o = array.getJSONObject(2);
		} else if (sum >= 6) {
			o = array.getJSONObject(1);
		} else if(sum >= 3) {
			o = array.getJSONObject(0);
		}

		if (o != null) {
			o.put("flag", 0);
		}

		// 存储
		return array.toString();
	}
}
