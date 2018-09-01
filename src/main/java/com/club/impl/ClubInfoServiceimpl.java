package com.club.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.club.constant.Cnst;
import com.club.dao.ClubInfoDao;
import com.club.dao.ClubUserDao;
import com.club.dao.UserDao;
import com.club.entity.ClubInfo;
import com.club.entity.RedisClub;
import com.club.service.ClubInfoService;
import com.club.util.GameUtil;
@Service("clubInfoService")
public class ClubInfoServiceimpl implements ClubInfoService{
	@Resource
	private ClubInfoDao clubInfoDao;
	@Resource
	private UserDao userDao;
	@Resource
	private ClubUserDao clubUserDao;
	@Override
	//playid,代理id
	public JSONObject getMyClubs(int id,int id2,Integer cid) {
		//代理表不用cid 做区分
		int myMoney = userDao.findMoney(id2);
		List<ClubInfo> clubs = clubInfoDao.findClubByCreateId(id,cid);
		//还能创建的个数
		int canCreateNum = 3 - clubs.size();
		JSONObject club =  new JSONObject();
		JSONArray array = new JSONArray();
		for(int i=0;i<clubs.size();i++){
			JSONObject o = new JSONObject();
			int lastMoney = clubs.get(i).getROOM_CARD_NUM();
			int yuJing = clubs.get(i).getROOM_CARD_NOTICE();
			int clubId = clubs.get(i).getCLUB_ID();
			int dnum = clubs.get(i).getROOM_CARD_QUOTA();
			o.put("clubId", clubId);
			o.put("xiane", dnum);
			o.put("lastMoney", lastMoney);
			o.put("clubName", clubs.get(i).getCLUB_NAME());
			o.put("yuJing",yuJing);
			o.put("haveAction",clubInfoDao.haveAction(clubId,cid));
			//查询该地区举了不的所有玩家--- 
			o.put("allPerson", clubUserDao.findAllUser(clubId,cid).size());
			o.put("FE", clubs.get(i).getFREE_END());
			o.put("FS", clubs.get(i).getFREE_START());
			array.add(o);
		}
		club.put("myMoney", myMoney);
		club.put("canCreateNum", canCreateNum);
		club.put("clubs", array);
		return club;
	}
	@Override
	public void createClub(ClubInfo club,Integer userId,Integer change) {
		//更新数据库club信息
		clubInfoDao.createClub(club);
		//把房主放到club_user里
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("clubId", club.getCLUB_ID());
		map.put("cid", club.getCID());
		map.put("userId",club.getCREATE_ID());
		map.put("createTime", club.getCREATE_TIME());
		clubInfoDao.addCreater(map);
		//更新数据库user房卡
		userDao.updateUserMoney(userId, change);
		//更新redis缓存
	}
	@Override
	public void addMoney(Integer clubId, Integer userId, Integer addMoney,Integer cid) {
		//更新club信息
		clubInfoDao.addMoney(clubId, addMoney,cid);
		//更新user房卡
		userDao.updateUserMoney(userId, addMoney);
		//更新club Redis
		RedisClub club = GameUtil.getClubInfoByClubId(Cnst.get_REDIS_PREFIX_CLUBMAP(String.valueOf(cid))+clubId);
		if(club==null){
			club = clubInfoDao.findClubNewByClubId(Integer.valueOf(clubId),cid);// 根据俱乐部id查询
		}
		club.setRoomCardNum(club.getRoomCardNum()+addMoney);
		GameUtil.setClubInfoByClubId(Cnst.get_REDIS_PREFIX_CLUBMAP(cid.toString())+clubId.toString(), club);
		//更新user Redis
	}
	@Override
	public JSONObject moneyManage(Integer clubId,Integer userId,Integer cid) {
		//clubMoney
		RedisClub club = GameUtil.getClubInfoByClubId(Cnst.get_REDIS_PREFIX_CLUBMAP(String.valueOf(cid))+clubId);
		if(club==null){
			club = clubInfoDao.findClubNewByClubId(Integer.valueOf(clubId),cid);// 根据俱乐部id查询
		}
		if(club==null){
			return null;
		}
		GameUtil.setClubInfoByClubId(Cnst.get_REDIS_PREFIX_CLUBMAP(cid.toString())+clubId.toString(), club);
//		ClubInfo club = clubInfoDao.findClubByClubId(clubId,cid);
		//通过redis查询user房卡
		int userMoney = userDao.findMoney(userId);
		JSONObject info = new JSONObject();
		info.put("myMoney", userMoney);
		info.put("lastMoney", club.getRoomCardNum());
		info.put("clubName", club.getClubName());
		return info;
	}
	@Override
	public void deleteClub(Integer clubId,Integer cid) {
		ClubInfo club = clubInfoDao.findClubByClubId(clubId,cid);
		Integer userId = club.getCREATE_ID();
		Integer money = club.getROOM_CARD_NUM();
		//修改mysql数据
		clubInfoDao.deleteClubInfo(clubId);
		clubInfoDao.deleteClubUser(clubId);
		userDao.updateUserMoney(userId, -money);
		//更新redis
		
	}
	@Override
	public JSONObject haveAction(Integer userId,Integer cid) {
		List<ClubInfo> clubs = clubInfoDao.findClubByCreateId(userId,cid);
		JSONObject info = new JSONObject();
		Integer actions = 0;
		for(int i=0;i<clubs.size();i++){
			actions = actions + clubInfoDao.haveAction(clubs.get(i).getCLUB_ID(),cid);
		}
		info.put("haveAction",actions);
		return info;
	}
	@Override
	public JSONObject getHisClubs(Integer toUserId,Integer cid) {
		List<ClubInfo> clubs = clubInfoDao.findClubByCreateId(toUserId,cid);
		JSONObject club =  new JSONObject();
		JSONArray array = new JSONArray();
		for(int i=0;i<clubs.size();i++){
			JSONObject o = new JSONObject();
			int lastMoney = clubs.get(i).getROOM_CARD_NUM();
			int yuJing = clubs.get(i).getROOM_CARD_NOTICE();
			int clubId = clubs.get(i).getCLUB_ID();
			int dnum = clubs.get(i).getROOM_CARD_QUOTA();
			o.put("clubId", clubId);
			o.put("xiane", dnum);
			o.put("lastMoney", lastMoney);
			o.put("clubName", clubs.get(i).getCLUB_NAME());
			o.put("yuJing",yuJing);
			o.put("FS",clubs.get(i).getFREE_START());
			o.put("FE",clubs.get(i).getFREE_END());
			o.put("haveAction",clubInfoDao.haveAction(clubId,cid));
			o.put("allPerson", clubUserDao.findAllUser(clubId,cid).size()-1);
			array.add(o);
		}
		club.put("clubs", array);
		return club;
	}
}
