package com.club.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.club.constant.Cnst;
import com.club.dao.ClubInfoDao;
import com.club.dao.ClubUserDao;
import com.club.dao.UserDao;
import com.club.entity.ClubInfo;
import com.club.entity.JsonResult;
import com.club.entity.RedisClub;
import com.club.redis.StringUtils;
import com.club.service.ClubInfoService;
import com.club.util.GameUtil;

@Controller
public class ClubInfoController {
	@Resource
	private ClubInfoDao clubInfoDao;
	@Resource
	private ClubInfoService clubInfoService;
	@Resource
	private ClubUserDao clubUserDao;
	@Resource
	private UserDao userDao;
	
	/*
	 * 如果不加cid区分俱乐部，俱乐部就不会知道是哪个地区的，混乱
	 * 
	 */
	
	
	@RequestMapping("/getMyClubs")//获取俱乐部
	@ResponseBody
	public JsonResult getMyClubs(Long userId,HttpServletRequest request,Integer cid){
		try{
//			String token_time = userDao.findUserToken(Integer.valueOf(userId+""));
//			if(ClubUtil.checkLogin(token_time))
//				return new JsonResult(-1,"登录已过时，重新登录",null,"");
			if(!StringUtils.isNum(userId.toString()) || cid==null ){
				return new JsonResult("13");
			}
			//通过代理id找到玩家id
			Integer user_Id = userDao.findUserId(Integer.valueOf(userId+""));
			System.out.println("==================>"+user_Id);
			System.out.println("获取俱乐部=======================>userID"+userId);
			JSONObject json = clubInfoService.getMyClubs(user_Id,Integer.valueOf(userId+""),cid);
			return new JsonResult(json);
		}
		catch(Exception e){
			System.out.println(e);
//			return new JsonResult("操作失败，请稍后再试");
			return new JsonResult("3");
		}
	}
	@RequestMapping("/createClub")//创建俱乐部
	@ResponseBody
	public JsonResult createClub(Long userId,String clubName,Integer lastMoney,Integer maxUseMoney,Integer moneyWarn,Integer cid){
		try{
//			String token_time = userDao.findUserToken(Integer.valueOf(userId+""));
//			if(ClubUtil.checkLogin(token_time))
//				return new JsonResult(-1,"登录已过时，重新登录",null,"");
			if(clubName.contains(".")||clubName.contains("/")||clubName.contains("//"))
				return new JsonResult("4");
//				return new JsonResult("含有非法字符");
			if(clubInfoDao.findClubByClubName(clubName)!=null){
//				return new JsonResult("该俱乐部已存在，换个名称吧");
				return new JsonResult("5");
			}
			//判断是不是数字
			if(!StringUtils.isNum(userId.toString()) ||  !StringUtils.isNum(lastMoney.toString())
			|| !StringUtils.isNum(maxUseMoney.toString()) ||  !StringUtils.isNum(moneyWarn.toString())
			|| !StringUtils.isNum(cid.toString())){
				return new JsonResult("15");
			}
			
			Integer user_Id = userDao.findUserId(Integer.valueOf(userId+""));
//			System.out.println("创建俱乐部=======>"+userId+clubName+lastMoney+maxUseMoney+moneyWarn+cid);
//			int canCreateNum = Integer.valueOf(getRedisInfo.getStringByKey(Cnst.REDIS_PREFIX_CREATEMAX));
			int canCreateNum = Cnst.maxClub;
			//俱乐部总次数  指其创建的和加入的和
			Integer num =clubUserDao.findClubNumByUserId(Integer.valueOf(user_Id+""),cid);
//			if(clubs.size()>=canCreateNum)
			if(num >=canCreateNum)
//				return new JsonResult("无法创建更多俱乐部");
				return new JsonResult("6");
			//根据userId获取缓存房卡数
			int money = userDao.findMoney(Integer.valueOf(userId+""));
			if(money<1000){
				//房卡不足1000，请充值
				return new JsonResult("25");
			}
			if(money<lastMoney)
//				return new JsonResult("房卡不足，无法创建俱乐部");
				return new JsonResult("7");
			if(moneyWarn>=lastMoney)
//				return new JsonResult("房间预警必须小于房卡库存值");
				return new JsonResult("8");
			ClubInfo club = new ClubInfo();
			RedisClub redisClub=new RedisClub();
			
			//检验clubId是否存在
			synchronized (ClubInfoController.class) {
				Integer clubId;
				while(true){
					clubId = GameUtil.createSixCode();
					//这里不加cid 是不是表示即使不区分cid ，所有俱乐部的id也不会重复。  
					if(clubInfoDao.findClubByClubId(clubId,cid)==null){
						club.setCLUB_ID(GameUtil.createSixCode());
						break;
					}	
				}
				long currentTimeMillis = System.currentTimeMillis();
				club.setCLUB_NAME(clubName);
				club.setCID(cid);
				club.setCREATE_ID(Integer.valueOf(user_Id+""));
				club.setCREATE_TIME(currentTimeMillis);
				club.setPERSON_QUOTA(Cnst.maxMember);
	//			club.setPERSON_QUOTA(Integer.valueOf(getRedisInfo.getStringByKey(Cnst.REDIS_PREFIX_CLUBMENBERMAX)));//人数上限redis配置
				club.setROOM_CARD_NOTICE(moneyWarn);
				club.setROOM_CARD_NUM(lastMoney);
				club.setROOM_CARD_QUOTA(maxUseMoney);
				//新加入的限免开始时间和限免结束时间  活动，只有在2018 1月有，时间月底1517414400
				club.setFREE_START(currentTimeMillis);
				if(currentTimeMillis< Cnst.HUODONG_TIME*1000){//如果满足活动时间
					club.setFREE_END(Cnst.HUODONG_TIME*1000);
				}else{
					club.setFREE_END(currentTimeMillis);
				}
				clubInfoService.createClub(club,Integer.valueOf(userId+""),lastMoney);
				redisClub.setCid(cid);
				redisClub.setClubName(clubName);
				redisClub.setCreateId(Long.valueOf(user_Id+""));
				redisClub.setCreateTime(currentTimeMillis);
				redisClub.setPersonQuota(Cnst.maxMember);
				redisClub.setRoomCardNotice(moneyWarn);
				redisClub.setRoomCardNum(lastMoney);
				redisClub.setRoomCardQuota(maxUseMoney);
				redisClub.setFreeStart(currentTimeMillis);
				redisClub.setFreeEnd(club.getFREE_END());
				//将新创建的俱乐部
				GameUtil.setClubInfoByClubId(Cnst.get_REDIS_PREFIX_CLUBMAP(cid.toString())+clubId.toString(), redisClub);
				
				
			}
			return new JsonResult(Cnst.SUCCESS);
		}catch(Exception e){
			System.out.println(e);
//			return new JsonResult("操作失败，请稍后再试");
			return new JsonResult("3");
		}
	}
	@RequestMapping("/searchClubs")//根据玩家的id取查询俱乐部信息
	@ResponseBody
	public JsonResult searchClubs(String userId,String toUserId,Integer cid){
		try{
			//判断用户输入参数是否正确
			if(!StringUtils.isNum(userId) ||!StringUtils.isNum(toUserId)){
				return new JsonResult("13");
			}
//			if(!StringUtils.isNum(toUserId)){
//				return new JsonResult("15");
//			}
			//userId标识查询的用户id;   toUserId标识被查询俱乐部详情的玩家id
			//查询代理权限  userId标识查询的用户id
			Integer daiLPower = userDao.findDaiLPower(Integer.valueOf(userId));
			if(daiLPower==null){
				daiLPower=0;
			}
			if(daiLPower<6){
				//权限不足
				return new JsonResult("16");
			}
			//查询别人创建的俱乐部信息
			JSONObject j=clubInfoService.getHisClubs(Integer.valueOf(toUserId),cid);
//			if(j==null){
//				return new JsonResult("");
//			}
			return new JsonResult(j);
		}catch(Exception e){
			System.out.println(e);
			return new JsonResult("3");
		}
	}
	
	@RequestMapping("/freeClub")//俱乐部充值限免时间
	@ResponseBody
	public JsonResult freeClub(String userId,String clubId,Integer state,Integer cid){
		try{
//			//判断用户输入参数是否正确
			if(!StringUtils.isNum(userId) || !StringUtils.isNum(clubId)){
				return new JsonResult("15");
			}
//			if(!StringUtils.isNum(clubId)){
//				return new JsonResult("15");
//			}
			if(!state.equals(1) && !state.equals(2))
				return new JsonResult("4");
			//查询代理权限
			Integer daiLPower = userDao.findDaiLPower(Integer.valueOf(userId));
			if(daiLPower<6){
				//权限不足
				return new JsonResult("15");
			}
			//获取俱乐部信息
			RedisClub redisClub = GameUtil.getClubInfoByClubId(Cnst.get_REDIS_PREFIX_CLUBMAP(String.valueOf(cid))+clubId);

			if (null == redisClub) {// 如果为空 从数据库查询

				redisClub = clubInfoDao.findClubNewByClubId(Integer.valueOf(clubId),cid);// 根据俱乐部id查询
			}
			//这个是限免开始时间
			Long freeStart = System.currentTimeMillis();
			Long freeEnd = redisClub.getFreeEnd();
			if(freeStart<freeEnd){//说明还有多余的限免时间
				if(state.equals(1)){//包周
					freeEnd=freeEnd+Cnst.WEEK_TIME*1000;
				}else if(state.equals(2)){//包月
					freeEnd=freeEnd+Cnst.MONTH_TIME*1000;
				}
			}else{//说明没有了限免时间
				if(state.equals(1)){//包周
					freeEnd=freeStart+Cnst.WEEK_TIME*1000;
				}else if(state.equals(2)){//包月
					freeEnd=freeStart+Cnst.MONTH_TIME*1000;
				}
			}
			//更新数据库
			clubInfoDao.updateFreeTimeByClubId(Integer.valueOf(clubId),freeStart,freeEnd,cid);
			//更改redis缓存
			redisClub.setFreeStart(freeStart);
			redisClub.setFreeEnd(freeEnd);
			//更新redis
			GameUtil.setClubInfoByClubId(Cnst.get_REDIS_PREFIX_CLUBMAP(cid.toString())+clubId.toString(), redisClub);
			return new JsonResult(Cnst.SUCCESS);
		}catch(Exception e){
			System.out.println(e);
			return new JsonResult("3");
		}
	}
	
	
	@RequestMapping("/closeClub")//俱乐部限免时间
	@ResponseBody
	public JsonResult closeClub(String userId,String clubId,Integer cid){
		try{
//			//判断用户输入参数是否正确
			if(!StringUtils.isNum(userId) || !StringUtils.isNum(clubId)){
				return new JsonResult("13");
			}
			//查询代理权限
			Integer daiLPower = userDao.findDaiLPower(Integer.valueOf(userId));
			if(daiLPower<6){
				//权限不足
				return new JsonResult("15");
			}
			long currentTimeMillis = System.currentTimeMillis();
			clubInfoDao.closeFreeTimeByClubId(Integer.valueOf(clubId),currentTimeMillis,cid);
			// 通过clubId从redis中获取俱乐部信息
			RedisClub redisClub = GameUtil.getClubInfoByClubId(Cnst.get_REDIS_PREFIX_CLUBMAP(String.valueOf(cid))+clubId);
			if (null == redisClub) {// 如果为空 从数据库查询
				redisClub = clubInfoDao.findClubNewByClubId(Integer.valueOf(clubId),cid);// 根据俱乐部id查询
			}else{
				//跟新俱乐部的免费结束时间
//				redisClub.setFREE_END(currentTimeMillis);
				redisClub.setFreeEnd(currentTimeMillis);
			}
			//更改redis缓存
			GameUtil.setClubInfoByClubId(Cnst.get_REDIS_PREFIX_CLUBMAP(cid.toString())+clubId.toString(), redisClub);
			return new JsonResult(Cnst.SUCCESS);
		}catch(Exception e){
			System.out.println(e);
			return new JsonResult("3");
		}
	}
	
	
	//修改俱乐部库存
	@RequestMapping("/lastMoneyUpdate")
	@ResponseBody
	public JsonResult lastMoneyUpdate(Integer clubId,Long userId,Integer addMoney,Integer cid){
		try{
//			String token_time = userDao.findUserToken(Integer.valueOf(userId+""));
//			if(ClubUtil.checkLogin(token_time))
//				return new JsonResult(-1,"登录已过时，重新登录",null,"");
//			System.out.println("修改俱乐部库存============>"+clubId+userId+addMoney);
			if(!StringUtils.isNum(clubId+"") || !StringUtils.isNum(addMoney+"")|| !StringUtils.isNum(userId+"")|| !StringUtils.isNum(cid+"")){
				return new JsonResult("13");
			}
			//redis查找房卡
			int money = userDao.findMoney(Integer.valueOf(userId+""));
			if(money<addMoney){
//				return new JsonResult("房卡数不足");
				return new JsonResult("9");
			}
			clubInfoService.addMoney(clubId,Integer.valueOf(userId+""), addMoney,cid);
			return new JsonResult(Cnst.SUCCESS);
		}catch(Exception e){
			System.out.println(e);
//			return new JsonResult("操作失败，请稍后再试");
			return new JsonResult("3");
		}
	}
	//房卡管理
	@RequestMapping("/moneyManage")
	@ResponseBody
	public JsonResult moneyManage(Integer clubId,Long userId,Integer cid){
		try{
//			String token_time = userDao.findUserToken(Integer.valueOf(userId+""));
//			if(ClubUtil.checkLogin(token_time))
//				return new JsonResult(-1,"登录已过时，重新登录",null,"");
//			System.out.println("房卡管理=================>"+clubId+userId);
			JSONObject info = clubInfoService.moneyManage(clubId,Integer.valueOf(userId+""),cid);
			//俱乐部不存在
			if(info==null){
				return new JsonResult(21);
			}
			return new JsonResult(info);
		}catch(Exception e){
//			return new JsonResult("操作失败，请稍后再试");
			return new JsonResult("3");
		}
	}
	//解散俱乐部
	@RequestMapping("/deleteClub")
	@ResponseBody
	public JsonResult deleteClub(Integer clubId,Long userId,Integer cid){
		try{
//			String token_time = userDao.findUserToken(Integer.valueOf(userId+""));
//			if(ClubUtil.checkLogin(token_time))
//				return new JsonResult(-1,"登录已过时，重新登录",null,"");
			if(clubId==null  || userId==null || cid==null){
				return new JsonResult("13");
			}
			//可能会要一个userid检验
//			System.out.println("解散俱乐部===================>"+clubId);
			RedisClub redisClub = GameUtil.getClubInfoByClubId(Cnst.get_REDIS_PREFIX_CLUBMAP(String.valueOf(cid))+clubId);
			if (null == redisClub) {// 如果为空 从数据库查询
				redisClub = clubInfoDao.findClubNewByClubId(Integer.valueOf(clubId),cid);// 根据俱乐部id查询
			}
			if(redisClub==null ){
				return new JsonResult(21);
			}
			if(redisClub.getCreateId()!=userId){
				//不是本人
				return new JsonResult(20);
			}
			//查看是不是本人创建的
			clubInfoService.deleteClub(clubId,cid);
			return new JsonResult(Cnst.SUCCESS);
		}catch(Exception e){
//			return new JsonResult("操作失败，请稍后再试");
			return new JsonResult("3");
		}
	}
	//俱乐部成员申请退出提示
	@RequestMapping("/haveAction")
	@ResponseBody
	public JsonResult haveAction(Long userId,Integer cid){
		try{
//			System.out.println("俱乐部人员变动预警===================>"+userId);
			Integer user_Id = userDao.findUserId(Integer.valueOf(userId+""));
			JSONObject info = clubInfoService.haveAction(user_Id,cid);
			return new JsonResult(info);
		}catch(Exception e){
			return new JsonResult();
		}
	}
	
	



	
	
}
