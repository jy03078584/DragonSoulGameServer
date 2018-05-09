/**
 *
 *
 * 文件名称： Constants.java
 * 摘 要：
 * 作 者：hex
 * 创建时间：2014-8-13 下午3:55:52
 */
package com.lk.dragon.util;

/**
 * 常量接口类，存储各种常量
 */
public interface Constants
{
    /** 地图坐标值 **/
    int MAP_BOUNDS = 1000;
    
    //英雄、装备品质定义
    int QUALITY_WHITE = 1;
    int QUALITY_GREEN = 2;
    int QUALITY_BLUE =  3;
    int QUALITY_PURPLE = 4;
    int QUALITY_ORANGE = 5;
    
    
    
    //判定socket服务是否成功启动
    
    /** 服务启动成功标志 **/
    int SERVER_START_SUCCESS = 1;
    /** 服务启动失败标志 **/
    int SERVER_START_FAIL = 0;
    
    //判定socket服务是否停服成功
    /** 服务停止成功 **/
    int SERVER_STOP_SUCCESS = 1;
    /** 服务停止失败 **/
    int SERVER_STOP_FAIL = 0;
        
    /** ====================【请求处理结果】=================== **/
    /** 请求处理成功 **/
    int REQUEST_SUCCESS = 1;
    /** 请求处理失败 **/
    int REQUEST_FAIL = 0;
    /** 请求处理异常 **/
    int REQUEST_ERROR = -1;
    
    /**************分隔符  **********************/
    String SPLIT = ",";
    
    /** ====================【请求类型】=================== **/
    /** 错误类型，即系统不能识别的类型 **/
    int ERROR_TYPE = -1;
    // 各个功能模块请求
    /** 邮件请求 **/
    int MAIL_TYPE = 1;
    /** 商城请求 **/
    int MALL_TYPE = 2;
    /** 拍卖请求 **/
    int AUCTION_TYPE = 3;
    /** 战斗请求 **/
    int BATTLE_TYPE = 4;
    /** 好友请求 **/
    int FRIEND_TYPE = 5;
    /** 公会请求 **/
    int GUILD_TYPE = 6;
    /** 聊天请求 **/
    int CHAT_TYPE = 7;
    /** 角色请求 **/
    int ROLE_TYPE = 8;
    /** 城市相关请求 **/
    int CITY_TYPE = 9;
    /** 道具相关请求 **/
    int PROP_TYPE = 11;
    /** 世界地图请求 **/
    int WORLD_MAP_TYPE = 12;
    /** 英雄相关请求 **/
    int HERO_TYPE = 13;
    /** 排行榜请求 **/
    int RANK_TYPE = 14;
    /** 装备请求 **/
    int EQUIP_TYPE = 15;
    /** 宝石请求 **/
    int DIAMOND_TYPE = 16;
    /** 军队请求 **/
    int ARMY_TYPE = 17;
    /** 奖励请求 **/
    int REWARD_TYPE = 18;
    /** 任务请求 **/
    int MISSION_TYPE = 19;
    /** 请求类型边界 **/
    int TYPE_BOUNDS = 19;
    /** 消息的分隔符 **/
    String DELIMITER = "<msg>";
    
    //各个模块中文
    String FRIEND_MODULE = "好友模块";
    
    /** ====================【种族编码】=================== **/
    /** 人族 **/
	final int RACE_HUM = 0;
	/** 精灵 **/
	final int RACE_GNS = 1;
	/** 天族 **/
	final int RACE_GOD = 2;
	/** 兽人 **/
	final int RACE_ORC = 3;
	/** 亡灵 **/
	final int RACE_SOU = 4;
	/** 魔族 **/
	final int RACE_MEG = 5;
    /** ====================【邮件请求模块】=================== **/
    /** 邮件列表查询 **/
    int MAIL_LIST_TYPE = 101;
    /** 发送邮件请求 **/
    int MAIL_SEND_TYPE = 102;
    /** 邮件阅读请求 **/
    int MAIL_READ_TYPE = 103;
    /** 删除邮件请求 **/
    int MAIL_DELETE_TYPE = 104;
    /** 删除某个类型的全部邮件 **/
    int MAIL_DELETE_ALL_TYPE = 105;
    /** 标记某个类型邮件为已读 **/
    int MAIL_MARK_ALL_TYPE = 106;
    /** 接收邮件附件 **/
    int MAIL_RECEIVE_ATTACH_TYPE = 107;
    
    //邮件类型
    //玩家邮件
    int MAIL_USER_TYPE = 1;
    //系统邮件
    int MAIL_SYSTEM_TYPE = 2;
    //社交模块
    int MAIL_FRIEND_TYPE = 3;
    
    //邮件是否来自系统邮件
    int MAIL_NOT_FROM_SYSTEM = 0;
    int MAIL_IS_FROM_SYSTEM = 1;
    
    //邮件是否带有附件
    int MAIL_IS_HAS_ATTACH = 1;
    int MAIL_NOT_HAS_ATTACH = 0;
    
    //系统邮件id
    long MAIL_SYSTEM_ID = -10;
    
    /** ====================【商城请求模块】=================== **/
    /** 商城列表查询 **/
    int MALL_LIST_TYPE = 201;
    /** 购买商城商品 **/
    int MALL_BUY_TYPE = 202;
    /** 交易资源 **/
    int MALL_BACK_RES_TYPE = 203;
    
    /** ====================【拍卖请求模块】=================== **/
    /** 获取拍卖行所有商品列表 **/
    int AUCTION_LIST_TYPE = 301;
    /** 购买拍卖行商品 **/
    int AUCTION_BUY_TYPE = 302;
    /** 拍卖行出售商品登记 **/
    int AUCTION_SELL_TYPE = 303;
    /** 商品下架 **/
    int AUCTION_CANCEL_TYPE = 304;
    /** 查看自己寄售商品 **/
    int AUCTION_LIST_MYSELF_TYPE = 305;
    
    //拍卖行商品是否已经过期
    int GOODS_TIME_OUT = 1;
    int GOODS_NOT_TIME_OUT = 0;
    
    //卖家成功出售后税率
    float GOODS_SELL_RATE = 0.05f;
    
    /** ====================【战斗请求模块】=================== **/
    /** 某城镇可以派出英雄列表查询 **/
    int CAN_BATTLE_HERO_TYPE = 401;
    /** 派兵出征请求 **/
    int SEND_ARMY_TYPE = 402;
    /** 取消派遣部队请求 **/
    int CANCEL_SEND_ARMY_TYPE = 403;
    /** 加速派遣部队请求 **/
    int RATE_SEND_ARMY_TYPE = 404;
    /** 侦查某城镇的相关防守等信息 **/
    int DETECT_CITY_INFO_TYPE = 405;
    /** 收藏坐标请求 **/
    int COLLECT_POINT_TYPE = 406;
    /** 查看已经收藏的坐标请求 **/
    int POINT_LIST_TYPE = 407;
    /** 删除收藏坐标点 **/
    int DELE_POINT_TYPE = 408;
    /** 竞技场匹配请求 **/
    int ARENA_TYPE = 409;
    /** 竞技场结果上传 **/
    int ARENA_RESULT_TYPE = 410;
    /** 查看战报请求 **/
    int CHECK_BATTLE_RESULT_TYPE = 411;
    /** 创建出征队列 **/
    int CREATE_WAR_TEAM = 412;
    /** 查看出征队列信息 **/
    int GET_WAR_TEAM_INFO = 413;
    /** 召回在外队列 **/
    int CALL_BACK_TEAM = 414;
    /** 查看当前来犯部队 **/
    int CURRENT_ENEMY_TEAM = 415;
    
    
    
   
    /** ====================【好友请求模块】=================== **/
    /** 获取好友列表信息 **/
    int FRIEND_LIST_TYPE = 501;
    /** 添加好友请求 **/
    int FRIEND_ADD_TYPE = 502;
    /** 好友响应添加请求 **/
    int FRIEND_RESPONSE_ADD_TYPE = 503;
    /** 删除好友请求 **/
    int FRIEND_DELETE_TYPE = 504;
    /** 删除仇人请求 **/
    int FRIEND_DELETE_ENEMY_TYPE = 505;
    /** 添加仇人请求 **/
    int FRIEND_ADD_ENEMY_TYPE = 506;
    /** 好友请求查询 **/
    int FRIEND_REQUEST_LIST_TYPE = 507;
    /** 查询好友、仇人的详细信息 **/
    int FRIEND_DETAIL_INFO_TYPE = 508;
    /** 根据relation_id查询角色信息 **/
    int FRIEND_ONE_ROLE_TYPE = 509;
    
    //关系类型
    int FRIEND = 1;
    int ENEMY = 2;
    
    //好友请求，被添加对象处理结果
    int FRIEND_ACCEPT = 1;
    int FRIEND_REFUSE = 2;
    
    //好友邮件名
    String FRIEND_MAIL_NAME = "好友请求";
    //系统邮件
    String SYSTEM_MAIL_NAME = "系统邮件";
    
    /** ====================【公会请求模块】=================== **/
    /** 获取公会列表 **/
    int GUILD_LIST_TYPE = 601;
    /** 创建公会请求 **/
    int GUILD_CREATE_TYPE = 602;
    /** 同意会员加入公会请求 **/
    int GUILD_ALLOW_TYPE = 603;
    /** 更新公会名、公告等信息请求 **/
    int GUILD_UPDATE_TYPE = 604;
    /** 申请加入加入公会请求 **/
    int GUILD_JOIN_TYPE = 605;
    /** 退出公会请求 **/
    int GUILD_QUIT_TYPE = 606;
    /** 会长任命某人为副会长 **/
    int GUILD_APPOINT_TYPE = 607;
    /** 会长修改副会长称号 **/
    int GUILD_EDIT_VICE_CHAIRMAN_TYPE = 608;
    /** 会长取消 某人副会长任命 **/
    int GUILD_DELE_APPOINT_TYPE = 609;
    /** 会长将会长让给某人 **/
    int GUILD_REPLACE_CHAIRMAN_TYPE = 610;
    /** 管理层踢人出会 **/
    int GUILD_CHAIRMAN_DELE_TYPE = 611;
    /** 会员领取公会活跃度奖励 **/
    int GUILD_MEMBER_GET_REWARD_TYPE = 612;
    /** 查看公会记录(包括人员变动，职位变动等信息) **/
    int GUILD_RECORD_INFO_TYPE = 613;
    /** 申请加入公会人员列表信息查询 **/
    int GUILD_APPLY_LIST_TYPE = 614;
    /** 公会副本列表查询功能 **/
    int GUILD_CARBON_LIST_TYPE = 615;
    /** 公会成员列表查询功能 **/
    int GUILD_MEMBER_LIST_TYPE = 616;
    /** 公会本人信息查询功能 **/
    int GUILD_MEMBER_INFO_TYPE = 617;
    /** 查询个人已经申请加入的公会列表 **/
    int GUILD_ROLE_HAS_APPLY_TYPE = 618;
    /** 拒绝玩家申请加入公会 **/
    int GUILD_REFUSE_APPLY_TYPE = 619;
    /** 解散公会请求 **/
    int GUILD_DELE_TYPE = 620;
    /** 新增公会职位 **/
    int GUILD_ADD_POSITION_TYPE = 621;
    /** 删除公会职位 **/
    int GUILD_DELE_POSITION_TYPE = 622;
    /** 获取公会职位列表 **/
    int GUILD_GET_POSITION_LIST_TYPE = 623;
    /** 成员个人信息 **/
    int GUILD_MEMBER_SINGLE_TYPE = 624;
    
    /** ====================【聊天请求模块】=================== **/
    /** 请求建立聊天连接 **/
    int CHAT_ESTABLISH_TYPE = 701;
    /** 断开连接 **/
    int CHAT_BREAK_TYPE = 702;
    /** 公共聊天 **/
    int CHAT_PUBLIC_CHAT_TYPE = 703;
    /** 公会聊天 **/
    int CHAT_GUILD_CHAT_TYPE = 704;
    /** 私聊 **/
    int CHAT_PRIVATE_CHAT_TYPE = 705;
    
    //信息类型
    /** 公共聊天 **/
    int PUBLIC_CHAT = 2;
    /**　私聊信息　**/
    int PRIVATE_CHAT = 3;
    /** 公会聊天 **/
    int GUILD_CHAT= 3;
    
    /** ====================【角色请求模块】=================== **/
    //角色详细请求类型
    /** 新增角色 **/
    int ROLE_ADD_TYPE = 801;
    /** 删除角色 **/
    int ROLE_DELETE_TYPE = 802;
    /** 修改角色名 **/
    int ROLE_UPDATE_TYPE = 803; 
    /** 查询角色列表 **/
    int ROLE_LIST_TYPE = 804;
    /** 同步角色相关信息请求 **/
    int ROLE_INFO_GET_TYPE = 805;
    /** 角色登录请求 **/
    int ROLE_LOGIN_TYPE = 806;
    /** 角色下线请求 **/
    int ROLE_LOGOUT_TYPE =  807;
    /** 角色登录奖励查询  **/
    int ROLE_CHEST_CHECK_TYPE = 808;
    /** 角色登录奖励领取 **/
    int ROLE_CHEST_GET_TYPE = 809;
    
    /** ====================【主城请求信息】=================== **/
    /** 获取城市已建造建筑列表请求 **/
    int CITY_HAVE_BUILD_LIST_TYPE = 901;
    /** 城市建造请求 **/
    int CITY_BUILD_BUILD_TYPE = 902;
    /** 城市建筑升级请求 **/
    int CITY_BUILD_UPGRADE_TYPE = 903;
    /** 新建城市请求 **/
    int CITY_ESTABLISH_TYPE = 904;
    /** 查询城市可建造建筑列表 **/
    int CITY_CAN_BUILD_TYPE = 905;
    /**　城市列表查询 **/
    int CITY_LIST_TYPE = 906;
    /** 城市名修改 **/
    int CITY_RENAME_TYPE = 907;
    /** 指定地点城市迁移 **/
    int CITY_MOVE_TYPE = 908;
    /** 随机地点城市迁移 **/
    int CITY_MOVE_RANDOM_TYPE = 909;
    /** 建筑升级完成请求 **/
    int CITY_BUILD_UPGRADE_FINISH_TYPE = 910;
//    /** 建筑升级情况查询请求 **/
//    int CITY_BUILD_UPGRADE_RATE_TYPE = 911;
    /** 建筑升级、建造取消功能 **/
    int CITY_UPGRADE_CANCEL_TYPE = 911;
    /** 建筑拆除请求 **/
    int CITY_BUILD_DELETE_TYPE = 912;
    /** 建筑升级消耗请求 **/
    int CITY_BUILD_UPGRADE_USE_TYPE = 913;
    /** 城镇保护 **/
    int CITY_PROTECT_TYPE = 914;
    /** 城市建筑升级加速 **/
    int CITY_BUILD_UPGRADE_ACCE_TYPE = 915;
    /** 城镇部署的英雄列表查询 **/
    int CITY_HAVE_HERO_TYPE = 916;
    /** 清除传送门CD **/
    int CITY_CLEAN_TRANS_CD_TYPE = 917;
    /** 获取资源建筑对应等级产量 **/
    int GET_RESOURCE_YIELD = 918;
    
    /**城邦允许驻扎增援数**/
    int CITY_MAX_REFINFORCE = 8;
    
    //城市 类型
    int MAIN_CITY = 1;
    int VICE_CITY = 0;
    
    //地图点标志变量
    int MAP_CITY_POINT = 1;
    int MAP_EMPTY_POINT = 0;
    
    //主城默认名
    String CITY_DEFAULT_NAME = "龙谷新城";
    
    //城市随机点坐标键值
    String X_KEY = "site_x";
    String Y_KEY = "site_y";
    
    //升级完成查询状态时
    int RATE_GET_SUCCESS = 1;
    int RATE_DOING = 2;
    int RATE_WRONG = 3;
    
    
    /** ====================【道具请求信息】=================== **/
    /** 获取道具列表基本请求 **/
    int PROP_LIST_TYPE = 1101;
    /** 使用BUFF道具 **/
    int PROP_GET_BUFF_TYPE = 1102;
    /** 丢弃道具 **/
    int PROP_DISCARD_TYPE = 1103;
    /** 包裹格子购买 **/
    int PROP_GRID_TYPE = 1104;
    /** 获取道具列表详细请求 **/
    int PROP_LIST_DETAIL_TYPE = 1105;
    /** 查看系统奖励 **/
    int PROP_GET_SYSREWARD = 1106;
    /** 开启系统奖励 **/
    int PROP_DO_SYSREWARD = 1107;
    /** 查看目标对象BUFF **/
    int BUFF_INFO_TYPE = 1108;
    /** BUFF结束 **/
    int BUFF_FINISH_TYPE = 1109;
    /** 背包已满 道具以邮件方式发送 **/
    long BAGS_FULL = -10;
    
	//系统BUFF对象类型
	/**角色BUFF**/
	public final static int BUFF_TAG_TYPE_ROLE = 0;
	/**城邦BUFF**/
	public final static int BUFF_TAG_TYPE_CITY = 1;
    //道具类型
    /** 装备类**/
    int PROP_EQUIP = 1;
	    /*坐骑类装备*/
	    int EQUIP_LOCATION_MOUNT = 6;
    /** 消耗类**/
    int PROP_CONSUME = 2;
    /** 任务类**/
    int PROP_TASK = 3;
    /** 宝石类**/
    int PROP_GEM = 4;
    /** 礼包类**/
    int PROP_REWARD = 5;
    /** ====================【世界地图请求信息】=================== **/
    /** 出生点生成请求 **/
    int WORLD_MAP_BORTH_TYPE =  1201;
    /** 获取世界地图的部分信息 **/
    int WORLD_MAP_DETAIL_TYPE =  1202;
    /** 根据坐标点获取城镇的信息 **/
    int MAP_CITY_INFO_TYPE =  1203;
    /** 角色消耗钻石 **/
    int MAP_ROLE_USE_TYPE =  1204;
    /** 单资源目标点信息 **/
    int WILD_SRC_SINGL_INFO_TYPE =  1205;
    /** 多资源点信息 **/
    int WILD_SRC_MULTI_INFO_TYPE =  1206;
    /** 随机点野怪信息 **/
    int RANDOM_CREEPS_INFO_TYPE =  1207;
    /** 放弃资源点 **/
    int GIVEUP_WILD_SRC_INFO =  1208;

    /** ====================【英雄相关请求信息】=================== **/
    /** 英雄列表查询（可召唤英雄列表） **/
    int CAN_CALL_HERO_LIST_TYPE = 1301;
    /** 英雄列表查询（角色拥有的英雄列表）**/
    int HAVE_HERO_LIST_TYPE = 1302;
    /** 召唤英雄请求 **/
    int CALL_HERO_TYPE = 1303;
    /** 英雄解雇请求 **/
    int HERO_DELETE_TYPE = 1304;
    /** 英雄穿戴装备请求 **/
    int HERO_EQUIP_TYPE = 1305;
    /** 英雄卸下装备 **/
    int HERO_REMOVE_EQUIP_TYPE = 1306;
    /** 英雄换装 **/
    int HERO_REPLACE_EQUIP_TYPE = 1307;
    /** 获取英雄详细信息 **/
    int HERO_DETAIL_INFO_TYPE = 1308;
    /** 英雄招募点刷新 **/
    int HERO_CALL_REFRESH_TYPE = 1309;
    /** 可招募英雄的详细信息查询 **/
    int CALL_HERO_DETAIL_INFO_TYPE = 1310;
    /** 英雄使用回复药品 **/
    int HERO_USE_MEDICINE_TYPE = 1311;
    /** 英雄复活请求 **/
    int HERO_RELIVE_TYPE = 1312;
    /** 英雄升级请求 **/
    int HERO_UPGRADE_TYPE = 1313;
    /** 英雄分配加点请求 **/
    int HERO_ASSIGN_TYPE = 1314;
    /** 英雄已穿戴装备基本信息 **/
    int HERO_EQUIP_BASE_INFO_TYPE = 1315;
    /** 英雄改名 **/
    int HERO_CHANGE_NAME_TYPE = 1316;
    /** 英雄进入训练室**/
    int HERO_BEGIN_TRAIN = 1317;
    /** 查看训练室信息**/
    int CITY_TRAIN_INFO = 1318;
    /** 中断训练**/
    int CANCEL_TRAIN = 1319;
    /** 英雄复活完成**/
    int HERO_REVIVE_FINISH = 1320;
    
    
    /** ====================【英雄状态码】=================== **/
    /** 空闲中 **/
    final int HERO_STATUS_FREE = 1;
    /** 出征中 **/
    final int HERO_STATUS_BATTLE = 2;
    /** 死亡 **/
    final int HERO_STATUS_DEAD = 3;
    /** 驻扎中(驻守友军城邦\驻守野外资源点) **/
    final int HERO_STATUS_QUARTER = 4;
    /** 复活中 **/
    final int HERO_STATUS_REVIVE = 5;
    
    /** ====================【排行榜相关请求信息】=================== **/
    /** 竞技场排行榜相关请求 **/
    int GET_RANK_TYPE = 1401;
    /** 综合战斗力排行榜相关请求 **/
    int SWORD_RANK_TYPE = 1402;
    /** 财富排行榜相关请求 **/
    int WEALTH_RANK_TYPE = 1403;
    /** 英雄排行榜相关请求 **/
    int HERO_RANK_TYPE = 1404;
    /** 兵力排行榜 **/
    int TROOPS_RANK_TYPE = 1405;
    /** 工会排行榜 **/
    int GUILD_RANK_TYPE = 1406;
    
    //数据库定义的排行榜类型数据
    int ARENA = 1;
    int SWORD = 2;
    int WEALTH = 3;
    int HERO = 4;
    int TROOPS = 5;
    int GUILD = 6;
    
    /** ====================【装备相关请求信息】=================== **/
    /** 穿戴宝石请求 **/
    int EQUIP_EQUIP_TYPE =  1501;
    /** 取下宝石请求 **/
    int EQUIP_DELE_TYPE = 1502;
    /** 替换宝石请求 **/
    int EQUIP_REPLACE_TYPE = 1503;
    
    /** ====================【宝石相关请求信息】=================== **/
    /** 宝石合成请求 **/
    int DIAMOND_UP_TYPE =  1601;
    /** 宝石袋开启请求 **/
    int DIAMOND_GET_TYPE = 1602;
    
    /** ====================【军队相关请求信息】=================== **/
    /** 兵源招募请求 **/
    int ARMY_RECRUIT_TYPE = 1701;
    /** 兵源招募取消请求 **/
    int ARMY_RECRUIT_CANCEL_TYPE = 1702;
    /** 兵源招募加速请求 **/
    int ARMY_RECRUIT_ACCE_TYPE = 1703;
    /** 英雄可分配兵源 **/
    int ARMY_CAN_ALLOT_TYPE = 1704;
    /** 英雄兵力修改列表查询 **/
    int HERO_EDIT_ARMY_TYPE = 1705;
    /** 增兵请求 （正常请求：一个周期生产一个兵 ）） **/
    int PRODUCT_ARMY_NORMAL_TYPE = 1706;
    /** 增兵请求  （非正常请求：下线重连后，多个兵一起生产请求） **/
    int PRODUCT_ARMY_SPECIAL_TYPE = 1707;
    /** 英雄部队总览表查询 **/
    int HERO_ARMY_LIST_TYPE = 1708;
    /** 城镇兵力部署信息查询 **/
    int CITY_ARMY_TYPE = 1709;
    /** 传送门功能 **/
    int HERO_TP_TYPE = 1710;
    /** 查看军事建筑兵种信息 **/
    int ARM_BUILD_INFO = 1711;
    /** 查看指定英雄军事信息 **/
    int HERO_ARM_INFO = 1712;
    /** 向已方其他城邦派遣军队 **/
    int HERO_TRANS_INFO = 1713;
    /** 查看城邦当前增援列表 **/
    int ARM_REINFORCE_INFO = 1714;
    
    /** ====================【奖励相关请求信息】=================== **/
    /** 生成邀请码请求 **/
    int REWARD_GET_CODE_TYPE = 1801;
    /** 查询奖励领取情况 **/
    int REWARD_INFO_TYPE = 1802;
    /** 绑定邀请码请求 **/
    int REWARD_BUND_TYPE = 1803;
    /** 领取邀请奖励请求 **/
    int REWARD_RECEIVE_TYPE = 1804;
    /** 查看已邀请人信息请求 **/
    int REWARD_ROLE_TYPE = 1805;
    
    /** ====================【任务相关请求信息】=================== **/
    /** 查看当前任务 **/
    int MISSION_INFO_SHOW = 1901;
    /** 领取任务**/
    int MISSION_GET_NEW = 1902;
    /** 任务完成 **/
    int MISSION_FINISH = 1903;
    /** 重置任务 **/
    int MISSION_REFRESH = 1904;
    /** 放弃任务 **/
    int MISSION_LOSE = 1905;
    /** 任务完成中 更新任务进度 **/
    int MISSION_ADDRATE = 1906;
    /** ====================【请求错误信息】=================== **/
    String NET_ERROR = "网络异常";
    //角色模块
    //添加角色
    /** 角色数量已达上限 **/
    String ROLE_NUM_MAX = "服务器上创建角色数量已达上限";
    /** 角色名已存在 **/
    String ROLE_NAME_REPEAT = "角色名已存在";
    //修改角色
    /** 没有修改角色资格 **/
    String CAN_NOT_EDIT = "没有修改角色权限";
    //删除角色
    /** 角色正在游戏中，不能进行修改操作 **/
    String ROLE_IS_GAMING = "角色正在游戏中，不能进行该项操作";
    //登录
    /** 重复登录  **/
    String REPEAT_LOGIN =  "该角色已在游戏中，禁止重复登录";
    
    //城镇模块
    //地图点已被占用
    String MAP_POINT_HAS_APPLYED = "该地图点已被申请占用";
    //建筑升级不能重复
    String BUILD_IS_UPGRADING = "建筑正在升级过程中";
    
    //好友模块
    //请求对象已经在您的好友列表中
    String FRIEND_ALREADY_EXIT = "请求对象已经在您的好友列表中";
    //请求对象已经在您的仇人列表中
    String ENEMY_ALREADY_EXIT = "请求对象已经在您的仇人列表中";
    //您已经在对方的仇人列表中
    String ENEMY_EXIT_OPPOSITE = "您已经在对方的仇人列表中";
    //申请的角色名不存在
    String APPLY_ROLE_NAME_NOT_EXIT = "申请的角色名不存在";
    //不能添加自己为好友
    String APPLY_MYSELF_FRIEND = "不能添加自己";
    
    //拍卖行列表
    //商品数量不够
    String AUCTION_GOODS_NUM_NOT_ENOUGH = "您购买的商品数量不足";
    //商品记录不存在
    String AUCTION_GOODS_SOLD_OUT = "您购买的商品已售罄";
    //您登记的商品记录已超过上限
    String AUCTION_MAX_NUM = "您登记的商品记录数已超过上限";
    
    //公会模块
    //公会名重复
    String GUILD_NAME_REPEAT = "该公会名已存在";
    //玩家已经在公会中
    String ROLE_HAS_GUILD = "玩家已经拥有公会";
    //已经申请过加入该帮会
    String ALREADY_APPLY_IN_GUILD = "您已经申请过加入该公会";
    //没有权限执行这项操作
    String ROLE_CAN_NOT_DO = "您没有权限执行这项操作";
    //职位正在使用中
    String POSITION_IS_USED = "该公会职位正在使用中，禁止删除";
    
    //邮件模块
    //邮件发送对象不存在
    String MAIL_SEND_ROLE_NOT_EXIT = "该角色不存在，请确认输入是否正确";
    
    //英雄模块
    //指定的英雄已刷新 招募失败
    String HIRE_HERO_ALREADY_FLUSH = "该招募点已刷新，招募失败";
    
  //=================================【角色新建标志】======================================
    /** 角色新建成功 **/
    long CREATE_ROLE_SUCCESS = 1 ;
    /** 角色名已存在 **/
    long CREATE_ROLE_NAMEREPEAT = 2 ;
    /** 角色新建失败 **/
    long CREATE_ROLE_FAIL = 0 ;
    /** 角色已达上限 **/
    long CREATE_ROLE_NUM_MAX = 3;
    
    /** 更新成功 **/
    long UPDATE_SUCCESS = 1;
    /** 更新失败 **/
    long UPDATE_FAIL = 0;
    
    //=================================【数据传输中map的键值】======================================
    /** 返回成功/失败键值 **/
    String RESULT_KEY = "result";
    /** 返回id键值 **/
    String ID_KEY = "role_id";
    
    //=================================【角色在线状态】======================================
    /** 角色在线 **/
    int ROLE_ONLINE = 1;
    /** 角色不在线 **/
    int ROLE_OFFLINE = 0;
    /** 角色每轮签到最大次数 **/
    int ROLE_LOGIN_MARK_MAX = 25 ;
    
    //=================================【允许创建角色数】======================================
    /** 允许最大角色数 **/
    int ROLES_MAX = 3;
    
    //=================================【游戏道具模块】======================================
    /** 购买道具成功 **/
    long BUY_PROPS_SUCCESS = 1;
    /** 购买道具失败 **/
    long BUY_PROPS_FAIL = 0;
    
    /** 使用道具正常 **/
    long USE_PROPS_SUCCESS = 1;
    /** 使用道具异常 **/
    long USE_PROPS_FAIL = 0;
    
    /** 保存道具成功 **/
    long SAVE_PROPS_SUCCESS = 1;
    /** 保存道具失败 **/
    long SAVE_PROPS_FAIL = 0;
    
   //=================================【拍卖行模块】======================================
    /** 寄售成功 **/
    long CONSIGN_SUCCESS = 1;
    /** 寄售失败 **/
    long CONSIGN_FAIL = 0;
    /** 最大寄售量 **/
    int CONSIGN_MAX_COUNTS = 20;
    /** 超过最大寄售量 **/
    long OVER_CONSIGN_MAX_COUNTS = 2;
    
    /** 按下架时间排序 **/
    int ORDER_BY_OVERDUE = 1;
    /** 按价格排序 **/
    int ORDER_BY_PRIVCE = 2;
    /** 按名称排序 **/
    int ORDER_BY_PRO_NAME = 3;
    
    //=================================【城邦-建筑模块】======================================
    /** 城邦新建成功 **/
    long CREATE_NEW_CITY_SUCCESS = 1;
    /** 城邦新建失败 **/
    long CREATE_NEW_CITY_FAIL = 0;
    /** 该坐标不能新建城邦 **/
    long CREATE_NEW_CITY_DISABLE_POINT = 2;

    
    /** 迁移城邦成功 **/
    long MOVE_CITY_SUCCESS = 1;
    /** 迁移城邦失败 **/
    long MOVE_CITY_FAIL = 0;
    /** 迁移目的点不可新建城邦 **/
    long MOVE_CITY_UNAVAILABLE = 2;
    

    /** 点在已在申请建造中 **/
    long CREATE_CITY_HAS_APPLYED = 3;
    
    /** 城邦建筑新建成功 **/
    long CREATE_NEW_BUILD_SUCCESS = 1;
    /** 城邦建筑新建失败 **/
    long CREATE_NEW_BUILD_FAIL = 0;

   //=================================【角色社交模块】======================================
    
    /** 双方符合好友条件 **/
    long CAN_BE_FRIEND = 1;
    
    /** 新建关系成功 **/
    long CREATE_RELATION_SUCCESS = 1;
    /** 对方已存在好友列表中 **/
    long TARGET_ALEARDY_FRIEND = 2;
    /** 对方已存在仇人列表中 **/
    long TARGET_ALEARDY_ENEMY = 3;
    /** 自己已在对方仇人列表中**/
    long SOURCE_ALEARDY_INENEMY = 4;
    /** 角色名不存在 **/
    long ROLE_NOT_EXIT = 5;
    /** 不能添加自己为好友 **/
    long ROLE_ADD_MYSELF = 6;
    
    
    /** 新建关系失败 **/
    long CREATE_RELATION_FAIL = -1;
    
    /** 双方互相申请好友 **/
    long APPLY_FRIEND_EACH = 5;
    /** 已在对方申请列表中 **/
    long APPLY_FRIEND_ALREADY = 6;
    
    /** 收件人不存在 **/
    long MAIL_TONAME_NOFOUND = -2;
    //=================================【游戏排行榜】======================================
    /** 全员排行 **/
    String RANKS_ALL = "ranks_all_role";
    /** 我的排行 **/
    String RANKS_MYSELF = "ranks_myself";
    
    
    

   //=================================【装备属性码】======================================
	//---------主属性
    int PHYSIC_ATTACK_FLAG = 6;//物理攻击
	int PHYSIC_DEFENCE_FLAG = 8;//物理防御
	int MAGIC_ATTACK_FLAG = 7;//魔法攻击
	int MAGIC_DEFENCE_FLAG = 9;//魔法防御
	int HP_FLAG = 10;//生命值
	int MP_FLAG = 14;//魔法值
	int SPEED_FLAG = 11;//移动速度
	int DISTANCE_ATTACK_FLAG = 12;//攻击距离
	int DISTANCE_MOVE_FLAG = 13;//移动距离
	int PSEED_INMAP_FLAG = 15;//世界地图移动速度
	//-----------副属性
    int PHYSIQUE_FLAG = 5;//英雄体质
	int MENTALITY_FLAG = 1;//英雄智力
	int HERO_POWER_FLAG = 2;//英雄力量
	int ENDURANCE_FLAG = 3;//英雄耐力
	int AGILITY_FLAG = 4;//英雄敏捷
	
	String PHYSIQUE = "physique";//英雄体质
	String MENTALITY = "mentality";//英雄智力
	String HERO_POWER = "hero_power";//英雄力量
	String ENDURANCE = "endurance";//英雄耐力
	String AGILITY = "agility";//英雄敏捷
	String PHYSIC_ATTACK = "physic_attack";//物理攻击
	String PHYSIC_DEFENCE = "physic_defence";//物理防御
	String MAGIC_ATTACK = "magic_attack";//魔法攻击
	String MAGIC_DEFENCE = "magic_defence";//魔法防御
	String MAX_HP = "max_hp";//生命值
	String MAX_MP = "max_mp";//魔法值
	String SPEED = "speed";//移动速度
	String DISTANCE_ATTACK = "distance_attack";//攻击距离
	String DISTANCE_MOVE = "distance_move";//移动距离
	String SPEED_INMAP = "speed_inmap";//世界地图移动速度
	
	
	
	String USE_DIAMON = "use_diamon";
	String ADD_BAG_COUNT = "add_bag_count";
	
	//=================================【帮会模块】======================================
	/**帮会名已被占用**/
	long FACTION_NAME_USED = -1l;
	/**建帮成功*/
	long FACTION_CREATE_SUCCESS = 1l;
	
	/******************************** 【系统消息】 **********************************/
	//消息未发送完毕标志位
	String MESS_END_FLAG = "<END>";
	String MESS_START_FLAG = "<BEGIN>";
    String DATA_KEY = "data";
    
    //消息清理时间间隔
    int CLEAR_TIME = 1000 * 30;
    //每轮清理死消息的间隔时间
    int INTERVAL_TIME = 1000 * 10 * 60;
    
    /******************************** 【英雄升级后加点变化】 **********************************/
    /** 升级后属性值变化 **/
    int ADD_POINT = 1;
    /** 升级后可以分配点变化 **/
    int CAN_ASSIGN_POINT = 5;
    
    /******************************** 【军备模块】 **********************************/
    
	// ------------------------------【队列战争类型码】---------------------------------
	/** 城邦掠夺 **/
	public final static int WAR_TYPE_ROB = 1;
	/** 城邦征服 **/
	public final static int WAR_TYPE_CONQUER = 2;
	/** 野外狩猎 **/
	public final static int WAR_TYPE_WILD = 3;
	/** 野外资源-攻占后驻守 **/
	public final static int WAR_TYPE_WILDSRC_ON = 4;
	/** 野外资源-攻占后返回 **/
	public final static int WAR_TYPE_WILDSRC_OFF = 5;
	/** 增援城邦 **/
	public final static int WAR_TYPE_REFINCITY = 6;
	/** 增援资源点 **/
	public final static int WAR_TYPE_REFINSRC = 7;
	/** 向自己其他城邦转移部队 **/
	public final static int WAR_TYPE_RESIDSELF = 8;

	// ------------------------------【队列状态码】---------------------------------
	/** 出征中 **/
	public final static int TEAM_STATUS_GO = 1;
	/** 返回中 **/
	public final static int TEAM_STATUS_BACK = 2;
	/** 增援中 **/
	public final static int TEAM_STATUS_REINFORCE = 3;
	/** 驻扎城邦 **/
	public final static int TEAM_STATUS_RESIDCITY = 4;
	/** 驻扎野外资源点 **/
	public final static int TEAM_STATUS_RESIDSRC = 5;

	

	// ------------------------------【战报分割符】---------------------------------
	/** 单行分割 **/
	public final static String SINGLE_SPLIT = "------------------------------------------\r\n";
	/** 多行分割 **/
	public final static String MUILT_SPLIT = "===========================================\r\n";

	/**LOG操作结果码**/
 	public final static int LOG_RES_SUCESS = 1;
 	public final static int LOG_RES_FAIL = 0;
}
