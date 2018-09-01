package com.club.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.club.constant.Cnst;
import com.club.entity.RedisClub;
import com.club.redis.MyRedis;

public class GameUtil {
	//创建六位俱乐部id
	public static int createSixCode(){
		int radomInt = new Random().nextInt(899998)+100000;
		return radomInt;
	}
	//获取0点时间戳
	public static long getTimesmorning(){
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTimeInMillis();
	}
	//获取24点时间戳
	public static long getTimesNight(){
		return getTimesmorning()+86400000;
	}
	//判断token时间是否有效
	public static boolean checkLogin(String token_time){
		  if(token_time==null)
			  return true;
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  Date date;
		try {
			date = sdf.parse(token_time);
			 if(date.getTime()<System.currentTimeMillis()){
				return true;	
			  }
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}
	//检验用户权限
	public static boolean checkPower(Integer grade){
		System.out.println("====================>用户权限"+grade);
		if(grade>5){
			return true;
		}
		return false;
		
	}
	 /**
     * 通过clubId更新redis中俱乐部信息    --old
     */
//    public synchronized static boolean setClubInfoByClubId(String clubId,RedisClub clubInfo){
//    	boolean result = true;
//    	Jedis jedis = null;
//    	try {
//    		jedis = MyRedis.getRedisClient().getJedis();
//    		jedis.set(Cnst.REDIS_PREFIX_CLUBMAP.concat(clubId), JSON.toJSONString(clubInfo));
//		} catch (Exception e) {
//			result = false;
//			MyRedis.getRedisClient().returnBrokenJedis(jedis);
//		}finally{
//			if (jedis!=null) {
//				MyRedis.getRedisClient().returnJedis(jedis);
//			}
//		}
//    	return result;
//    }
    
	

	
	/**
	 * 通过clubId更新redis中俱乐部信息  --new 
	 */
	public synchronized static boolean setClubInfoByClubId(String key,
			RedisClub clubInfo) {
		boolean result = true;
		Jedis jedis = null;
		try {
			jedis = MyRedis.getRedisClient().getJedis();
			jedis.set(key,JSON.toJSONString(clubInfo));
		} catch (Exception e) {
			result = false;
			MyRedis.getRedisClient().returnBrokenJedis(jedis);
		} finally {
			if (jedis != null) {
				MyRedis.getRedisClient().returnJedis(jedis);
			}
		}
		return result;
	}
	
    
    /**
     * 通过clubId从redis中获取俱乐部信息 --old
     */
//    public synchronized static RedisClub getClubInfoByClubId(String clubId){
//    	RedisClub info = null;
//    	Jedis jedis = null;
//    	try {
//    		jedis = MyRedis.getRedisClient().getJedis();
//    		String string = jedis.get(Cnst.REDIS_PREFIX_CLUBMAP.concat(clubId));
//    		if(string!=null){
//    			info = JSON.parseObject(string,RedisClub.class);
//    		}
//		} catch (Exception e) {
//			e.printStackTrace();
//			MyRedis.getRedisClient().returnBrokenJedis(jedis);
//		}finally{
//			if (jedis!=null) {
//				MyRedis.getRedisClient().returnJedis(jedis);
//			}
//		}
//    	return info;
//    }
	 /**
	 * 通过clubId从redis中获取俱乐部信息
	 * 	key ： 前缀+cid+clubid ------ value :俱乐部信息     
	 */
	public synchronized static RedisClub getClubInfoByClubId(String key) {
		RedisClub info = null;
		Jedis jedis = null;
		try {
			jedis = MyRedis.getRedisClient().getJedis();
			String string = jedis.get(key);
			if (string != null) {
				info = JSON.parseObject(string, RedisClub.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
			MyRedis.getRedisClient().returnBrokenJedis(jedis);
		} finally {
			if (jedis != null) {
				MyRedis.getRedisClient().returnJedis(jedis);
			}
		}
		return info;
	}
    
    
    /**
	 * 获取支付集合
	 * @return
	 */
    public synchronized static JSONArray getPayList(){
    	Jedis jedis = null;
    	JSONArray jsonArray = null;
    	try {
    		jedis = MyRedis.getRedisClient().getJedis();
    		String string = jedis.get(Cnst.REDIS_PAY_ORDERNUM);
    		if(string!=null){
    			jsonArray = JSONArray.parseArray(string);
    		}
		} catch (Exception e) {
			MyRedis.getRedisClient().returnBrokenJedis(jedis);
		}finally{
			if (jedis!=null) {
				MyRedis.getRedisClient().returnJedis(jedis);
			}
		}
    	return jsonArray;
    }
    /**
     * 获取扣量信息
     * @param key
     * @return
     */
    public synchronized static Integer getKouInfo(String key){
    	Integer info = null;
    	Jedis jedis = null;
    	try {
    		jedis = MyRedis.getRedisClient().getJedis();
    		String string = jedis.get(key);
    		if(string!=null){
    			info = Integer.parseInt(string);
    		}
		} catch (Exception e) {
			e.printStackTrace();
			MyRedis.getRedisClient().returnBrokenJedis(jedis);
		}finally{
			if (jedis!=null) {
				MyRedis.getRedisClient().returnJedis(jedis);
			}
		}
    	return info;
    }
    /**
     * 更改扣量信息
     */
	public synchronized static boolean setKouInfo(String key,Integer value){
    	boolean result = true;
    	Jedis jedis = null;
    	try {
    		jedis = MyRedis.getRedisClient().getJedis();
    		jedis.set(key, String.valueOf(value));
		} catch (Exception e) {
			result = false;
			MyRedis.getRedisClient().returnBrokenJedis(jedis);
		}finally{
			if (jedis!=null) {
				MyRedis.getRedisClient().returnJedis(jedis);
			}
		}
    	return result;
    }
	 /**
     * 更新redis订单集合
     * @return
     */
	public synchronized static boolean setPayList(JSONArray array) {
		boolean result = true;
    	Jedis jedis = null;
    	try {
    		jedis = MyRedis.getRedisClient().getJedis();
    		jedis.set(Cnst.REDIS_PAY_ORDERNUM, JSON.toJSONString(array));
		} catch (Exception e) {
			result = false;
			MyRedis.getRedisClient().returnBrokenJedis(jedis);
		}finally{
			if (jedis!=null) {
				MyRedis.getRedisClient().returnJedis(jedis);
			}
		}
    	return result;
	}
	/**
	 * 初始化订单号
	 * @return
	 */
	public synchronized static boolean initPayList() {
		boolean result = true;
    	Jedis jedis = null;
    	JSONArray json = new JSONArray();
    	json.add("这里是订单号集合");
    	try {
    		jedis = MyRedis.getRedisClient().getJedis();
    		jedis.set(Cnst.REDIS_PAY_ORDERNUM, JSON.toJSONString(json));
		} catch (Exception e) {
			result = false;
			MyRedis.getRedisClient().returnBrokenJedis(jedis);
		}finally{
			if (jedis!=null) {
				MyRedis.getRedisClient().returnJedis(jedis);
			}
		}
    	return result;
	}
	
	public  static boolean exists(String key){
    	boolean result = true;
    	Jedis jedis = null;
    	try {
    		jedis = MyRedis.getRedisClient().getJedis();
    		result = jedis.exists(key);
		} catch (Exception e) {
			result = false;
			MyRedis.getRedisClient().returnBrokenJedis(jedis);
		}finally{
			if (jedis!=null) {
				MyRedis.getRedisClient().returnJedis(jedis);
			}
		}
    	return result;
    }
	
	public  static <T> T getObject(String key,Class<T> T) {
		String result = null;
		Jedis jedis = null;
		try {
			jedis = MyRedis.getRedisClient().getJedis();
			result = jedis.get(key);
			if (result!=null) {
				return JSONObject.parseObject(result, T);
			}
		} catch (Exception e) {
			e.printStackTrace();
			MyRedis.getRedisClient().returnBrokenJedis(jedis);
		} finally {
			if (jedis != null) {
				MyRedis.getRedisClient().returnJedis(jedis);
			}
		}
		return null;
	}
	 /** 
	  * 和游戏中的保持一致
	 * 查询数量(存储的userId是唯一的)
	 * @param concat
	 */
	public static Long scard(String key) {
		Long result = 0l;
		Jedis jedis = null;
		try {
			jedis = MyRedis.getRedisClient().getJedis();
			result=jedis.scard(key);
		} catch (Exception e) {
			MyRedis.getRedisClient().returnBrokenJedis(jedis);
		} finally {
			if (jedis != null) {
				MyRedis.getRedisClient().returnJedis(jedis);
			}
		}
		return result;		
	}
}
