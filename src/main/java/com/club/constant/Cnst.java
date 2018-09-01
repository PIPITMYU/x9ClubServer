package com.club.constant;

import com.club.util.ProjectInfoPropertyUtil;



public class Cnst {
	//JsonResult返回状态
	public static final int SUCCESS = 1;//成功
	public static final int ERROR = 0;//失败
	//俱乐部状态 0申请中 1已通过 2已驳回 
	public static final int APPLY = 0;//申请加入
	public static final int PASS = 1;//已通过
	public static final int REFUSE = 2;//申请退出
	//redis配置
    public static final String REDIS_HOST = ProjectInfoPropertyUtil.getProperty("redis.host", "");
    public static final String REDIS_PORT = ProjectInfoPropertyUtil.getProperty("redis.port", "");
    public static final String REDIS_PASSWORD = ProjectInfoPropertyUtil.getProperty("redis.password", "");
    //redis清理数据前缀
    public static final int maxClub = 3;
    public static final int maxMember = 60;
    
    public static final String REDIS_PAY_ORDERNUM = "PAY_ORDERNUM";//充值订单号
    
    public static final String REDIS_PAY_CURRENTKOU = "PAY_CURRENTKOU";//当前扣量局数
    
    public static final String REDIS_PAY_KOU = "PAY_KOU";//规定扣量
    public static final long HUODONG_TIME = 1517414400;//创建俱乐部的限免时间(活动，时间到月底)
    public static final String splitStr = "_";

    public static final long WEEK_TIME = 7*24*3600;//包星期
    public static final long MONTH_TIME = 30*24*3600;//包月
    
    public static final String REDIS_RECORD_PREFIX ="TSDDZ";//要和斗地主的系列保持一致

    /**
     * 存放俱乐部的信息  key:cid+clubid ---  value:club实体
     */
    public static String get_REDIS_PREFIX_CLUBMAP(String cid) {
    	if(cid == null || "".equals(cid))
    		return null;
    	return REDIS_CLUB_CLUBMAP.concat(cid).concat(splitStr);
	}
    /*
     * 俱乐部未开房间信息 key:cid+clubid ---  value: roomid
     */
    public static String get_REDIS_CLUB_ROOM_LIST(String cid) {
    	if(cid == null || "".equals(cid))
    		return null;
    	return REDIS_CLUB_ROOM_LIST.concat(cid).concat(splitStr);
    }
    /*
     * 玩家信息 key:cid userId,clubId,时间 ---  value:roomid实体
     */
    public static String get_REDIS_CLUB_PLAY_RECORD_PREFIX_ROE_USER(String cid) {
    	if(cid == null || "".equals(cid))
    		return null;
    	return REDIS_CLUB_PLAY_RECORD_PREFIX_ROE_USER.concat(cid).concat(splitStr);
    }
    /*
     * 玩家房间信息 key:
     */
    public static String get_REDIS_CLUB_PLAY_RECORD_PREFIX(String cid) {
    	if(cid == null || "".equals(cid))
    		return null;
    	return REDIS_CLUB_PLAY_RECORD_PREFIX.concat(cid).concat(splitStr);
    }
    /*
     * 俱乐部活跃人数   
     */
    public static String get_REDIS_CLUB_ACTIVE_PERSON(String cid) {
    	if(cid == null || "".equals(cid))
    		return null;
    	return REDIS_CLUB_ACTIVE_PERSON.concat(cid).concat(splitStr);
    }
    
    /*
     * 俱乐部今天开局数
     */
    public static String get_REDIS_CLUB_TODAYKAI_NUM(String cid) {
    	if(cid == null || "".equals(cid))
    		return null;
    	return REDIS_CLUB_TODAYKAI_NUM.concat(cid).concat(splitStr);
    }
    
    
    /*
     * 俱乐部玩家今日局数
     */
    public static String getREDIS_CLUB_TODAYJUNUM_ROE_USER(String cid) {
    	if(cid == null || "".equals(cid))
    		return null;
    	return REDIS_CLUB_TODAYJUNUM_ROE_USER.concat(cid).concat(splitStr);
    }
    
    
    //俱乐部
    public static final String REDIS_CLUB_CLUBMAP = REDIS_RECORD_PREFIX+"_CLUB_MAP_";//俱乐部信息
    public static final String REDIS_CLUB_ROOM_LIST = REDIS_RECORD_PREFIX+"_CLUB_MAP_LIST_";//存放俱乐部未开局房间信息
    //key：cid +clubId+当天0点的时间   value: Set<userId> 
    public static final String REDIS_CLUB_PLAY_RECORD_PREFIX = REDIS_RECORD_PREFIX+"_CLUB_PLAY_RECORD_";//房间战绩
    public static final String REDIS_CLUB_PLAY_RECORD_PREFIX_ROE_USER = REDIS_RECORD_PREFIX+"_CLUB_PLAY_RECORD_FOR_USER_";//玩家字段
    
    public static final String REDIS_CLUB_TODAYSCORE_ROE_USER = REDIS_RECORD_PREFIX+"_CLUB_TODAYSCORE_FOR_USER_";//存放俱乐部玩家当天的分数
    public static final String REDIS_CLUB_TODAYJUNUM_ROE_USER = REDIS_RECORD_PREFIX+"_CLUB_TODAYJUNUM_FOR_USER_";//统计玩家今日局数
    public static final String REDIS_CLUB_ACTIVE_PERSON = REDIS_RECORD_PREFIX+"_CLUB_ACTIVE_PERSON_";//存放俱乐部当天活跃人数
    public static final String REDIS_CLUB_TODAYKAI_NUM = REDIS_RECORD_PREFIX+"_CLUB_TODAYKAI_NUM_";//今天开局数

    public static final int REDIS_CLUB_DIE_TIME = 3*24*60*60;//玩家战绩和俱乐部每天活跃保存时间
    public static final int REDIS_CLUB_PLAYERJUNUM_TIME =3* 24*60*60;//玩家今日局数和昨日局数保存时间



    
}
