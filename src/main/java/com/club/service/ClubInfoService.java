package com.club.service;



import com.alibaba.fastjson.JSONObject;
import com.club.entity.ClubInfo;


public interface ClubInfoService {
	//获取我的俱乐部
	JSONObject getMyClubs(int id,int id2, Integer cid);
	//创建俱乐部
	void createClub(ClubInfo club,Integer userId,Integer change);
	//库存管理
	void addMoney(Integer clubId,Integer userId,Integer addMoney,Integer cid);
	//查看库存
	JSONObject moneyManage(Integer clubId,Integer userId, Integer cid);
	//解散俱乐部
	void deleteClub(Integer clubId,Integer cid);
	//人员变动预警
	JSONObject haveAction(Integer userId,Integer cid);
	//获取别人创建的俱乐部
	JSONObject getHisClubs(Integer toUserId,Integer cid);
	
}
