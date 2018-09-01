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
	//playid,����id
	public JSONObject getMyClubs(int id,int id2,Integer cid) {
		//�������cid ������
		int myMoney = userDao.findMoney(id2);
		List<ClubInfo> clubs = clubInfoDao.findClubByCreateId(id,cid);
		//���ܴ����ĸ���
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
			//��ѯ�õ������˲����������--- 
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
		//�������ݿ�club��Ϣ
		clubInfoDao.createClub(club);
		//�ѷ����ŵ�club_user��
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("clubId", club.getCLUB_ID());
		map.put("cid", club.getCID());
		map.put("userId",club.getCREATE_ID());
		map.put("createTime", club.getCREATE_TIME());
		clubInfoDao.addCreater(map);
		//�������ݿ�user����
		userDao.updateUserMoney(userId, change);
		//����redis����
	}
	@Override
	public void addMoney(Integer clubId, Integer userId, Integer addMoney,Integer cid) {
		//����club��Ϣ
		clubInfoDao.addMoney(clubId, addMoney,cid);
		//����user����
		userDao.updateUserMoney(userId, addMoney);
		//����club Redis
		RedisClub club = GameUtil.getClubInfoByClubId(Cnst.get_REDIS_PREFIX_CLUBMAP(String.valueOf(cid))+clubId);
		if(club==null){
			club = clubInfoDao.findClubNewByClubId(Integer.valueOf(clubId),cid);// ���ݾ��ֲ�id��ѯ
		}
		club.setRoomCardNum(club.getRoomCardNum()+addMoney);
		GameUtil.setClubInfoByClubId(Cnst.get_REDIS_PREFIX_CLUBMAP(cid.toString())+clubId.toString(), club);
		//����user Redis
	}
	@Override
	public JSONObject moneyManage(Integer clubId,Integer userId,Integer cid) {
		//clubMoney
		RedisClub club = GameUtil.getClubInfoByClubId(Cnst.get_REDIS_PREFIX_CLUBMAP(String.valueOf(cid))+clubId);
		if(club==null){
			club = clubInfoDao.findClubNewByClubId(Integer.valueOf(clubId),cid);// ���ݾ��ֲ�id��ѯ
		}
		if(club==null){
			return null;
		}
		GameUtil.setClubInfoByClubId(Cnst.get_REDIS_PREFIX_CLUBMAP(cid.toString())+clubId.toString(), club);
//		ClubInfo club = clubInfoDao.findClubByClubId(clubId,cid);
		//ͨ��redis��ѯuser����
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
		//�޸�mysql����
		clubInfoDao.deleteClubInfo(clubId);
		clubInfoDao.deleteClubUser(clubId);
		userDao.updateUserMoney(userId, -money);
		//����redis
		
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
