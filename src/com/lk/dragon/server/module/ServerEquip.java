/**
 * Copyright ? 2014，成都乐控
 * All Rights Reserved.
 * 文件名称： ServerEquip.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-10-16 上午11:24:47
 */
package com.lk.dragon.server.module;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lk.dragon.server.domain.EquipDomain;
import com.lk.dragon.server.module.analysis.EquipRequestAnalysis;
import com.lk.dragon.service.RolePropsInfoService;
import com.lk.dragon.util.Constants;
import com.lk.dragon.util.JSONUtil;
import com.lk.dragon.util.OperateLogUtil;
import com.lk.dragon.util.SocketUtil;
import com.lk.dragon.util.SpringBeanUtil;

public class ServerEquip{
	private EquipDomain equipDomain;

	@Autowired
	private RolePropsInfoService rolePropsInfoService;


	/**
	 * 构造函数
	 * 
	 * @param battleDomain
	 */
	public ServerEquip(EquipDomain equipDomain) {
		this.rolePropsInfoService = SpringBeanUtil.getBean(RolePropsInfoService.class);
		this.equipDomain = equipDomain;
		
	}

	public void analysis() {
		switch (equipDomain.getType()) {
		case Constants.EQUIP_EQUIP_TYPE: {
			// 宝石装备
			diamondEquip();
			break;
		}
		case Constants.EQUIP_REPLACE_TYPE: {
			// 宝石替换
			diamondReplace();
			break;
		}
		case Constants.EQUIP_DELE_TYPE: {
			// 宝石卸下
			diamondDele();
			break;
		}
		}
	}

	/**
	 * 装备宝石
	 */
	private void diamondEquip() {
		// 道具id
		long role_props_id = equipDomain.getEquipId();
		long gem_role_props_id = equipDomain.getDiamondId();

		// 调用装备宝石接口函数
		Map<String, Long> map = new HashMap<String, Long>();

		try {
			map = rolePropsInfoService.equipOnGem(role_props_id,
					gem_role_props_id);

			// 新增操作日志
			String detail = "装备镶嵌宝石成功，装备id：" + role_props_id;
			OperateLogUtil.insertOperateLog(equipDomain.getRoleId(), detail,
					OperateLogUtil.EQUIP_MODULE, OperateLogUtil.SUCCESS);

		} catch (Exception e) {
			map.put(Constants.RESULT_KEY, -1l);
			map.put(Constants.ID_KEY, -1l);
			e.printStackTrace();

			// 新增操作日志
			String detail = "装备镶嵌宝石失败.";
			OperateLogUtil.insertOperateLog(equipDomain.getRoleId(), detail,
					OperateLogUtil.EQUIP_MODULE, OperateLogUtil.FAIL);

		}

		// 响应客户端
		String responseStr = EquipRequestAnalysis.getEquipDiamondResponse(map);
		SocketUtil.responseClient(equipDomain.getCtx(), responseStr);

	}

	/**
	 * 更换宝石
	 */
	private void diamondReplace() {
		// 获取信息
		long role_props_id = equipDomain.getEquipId();
		long roleId = equipDomain.getRoleId();
		long relaId = equipDomain.getRelaId();
		// int goldNum = equipDomain.getGoldNum();
		int diamondNum = equipDomain.getDiamondNum();
		long gem_role_props_id = equipDomain.getDiamondId();

		// 调用卸下宝石接口函数
		long result = -1;
		try {
			result = rolePropsInfoService.equipOffGem(role_props_id, roleId,
					diamondNum, relaId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			// 新增操作日志
			String detail = "装备更换宝石失败.详细信息："
					+ OperateLogUtil.getExceptionStackInfo(e);
			OperateLogUtil.insertOperateLog(equipDomain.getRoleId(), detail,
					OperateLogUtil.EQUIP_MODULE, OperateLogUtil.FAIL);

		}

		if (result == -1) {
			// 响应客户端
			SocketUtil.responseClient(equipDomain.getCtx(),
					JSONUtil.getBooleanResponse(false));

			return;
		}

		// 调用装备宝石接口函数
		Map<String, Long> map = new HashMap<String, Long>();

		try {
			map = rolePropsInfoService.equipOnGem(role_props_id,
					gem_role_props_id);

			// 新增操作日志
			String detail = "装备更换宝石成功，装备id：" + role_props_id;
			OperateLogUtil.insertOperateLog(equipDomain.getRoleId(), detail,
					OperateLogUtil.EQUIP_MODULE, OperateLogUtil.SUCCESS);

		} catch (Exception e) {
			map.put(Constants.RESULT_KEY, -1l);
			map.put(Constants.ID_KEY, -1l);
			e.printStackTrace();

			// 新增操作日志
			String detail = "装备更换宝石失败.详细信息："
					+ OperateLogUtil.getExceptionStackInfo(e);
			OperateLogUtil.insertOperateLog(equipDomain.getRoleId(), detail,
					OperateLogUtil.EQUIP_MODULE, OperateLogUtil.FAIL);

		}

		// 响应客户端
		String responseStr = EquipRequestAnalysis.getEquipDiamondResponse(map);
		SocketUtil.responseClient(equipDomain.getCtx(), responseStr);
	}

	/**
	 * 卸下宝石
	 */
	private void diamondDele() {
		// 获取信息
		long role_props_id = equipDomain.getEquipId();
		long roleId = equipDomain.getRoleId();
		long relaId = equipDomain.getRelaId();
		// int goldNum = equipDomain.getGoldNum();
		int diamondNum = equipDomain.getDiamondNum();

		int resKey = -1;
		// 调用卸下宝石接口函数
		long result = -1;
		try {
			result = rolePropsInfoService.equipOffGem(role_props_id, roleId,
					diamondNum, relaId);

			// 新增操作日志
			String detail = "装备卸下宝石成功，装备id：" + role_props_id;
			OperateLogUtil.insertOperateLog(equipDomain.getRoleId(), detail,
					OperateLogUtil.EQUIP_MODULE, OperateLogUtil.SUCCESS);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			// 新增操作日志
			String detail = "装备卸下宝石失败.详细信息："
					+ OperateLogUtil.getExceptionStackInfo(e);
			OperateLogUtil.insertOperateLog(equipDomain.getRoleId(), detail,
					OperateLogUtil.EQUIP_MODULE, OperateLogUtil.FAIL);

		}
		// 响应客户端
		if (result == Constants.BAGS_FULL || result > 0) {// 背包已满
			resKey = Constants.REQUEST_SUCCESS;
		}
		SocketUtil.responseClient(equipDomain.getCtx(),
				JSONUtil.getNewPropsIdResponse(resKey, result));
	}

}
