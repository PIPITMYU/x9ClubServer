package com.club.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.club.constant.Cnst;
import com.club.dao.ClubUserDao;
import com.club.dao.UserDao;
import com.club.entity.User;
import com.club.service.ClubUserService;

@Service("clubUserService")
public class ClubUserServiceimpl implements ClubUserService{
	@Resource
	private ClubUserDao clubUserDao;
	@Resource
	private UserDao userDao;
	//成员列表
	@Override
	public JSONObject findAllUser(int clubId,int page,Integer user_id,Integer cid) {
		List<Integer> list = clubUserDao.findAllUser(clubId,cid);
		//没有人
//		if(list==null||list.size()==0)
//			return null;
		JSONObject info = new JSONObject();
		int pages=0;
		if(list.size()%page==0){
			pages=list.size()/page;
//			}
		}else{
			pages=list.size()/page+1;
		}
		info.put("pages", pages);
//		info.put("pages", list.size()%page+1);
		JSONArray array = new JSONArray();
		for(int i=0;i<list.size();i++){
			JSONObject j = new JSONObject();
			int userId = list.get(i);
//			if(userId==user_id)
//				continue;
			j.put("userId", userId);
			//从redis中获取玩家姓名，图片 放入j中
			User findUser = userDao.findUser(userId,cid);
			j.put("userImg", findUser.getUSER_IMG());
			j.put("userName", findUser.getUSER_NAME());
			array.add(j);
		}
		info.put("users", array);
		info.put("totalNum", Cnst.maxMember);
		info.put("currNum",list.size());
		return info;
	}
	//申请加入列表
	@Override
	public JSONObject findJoinUser(int clubId, int page,int cid) {
		List<Integer> list = clubUserDao.findJoinUser(clubId,cid);
		//没有人
//		if(list==null||list.size()==0)
//			return null;
		JSONObject info = new JSONObject();
		int pages=0;
		if(list.size()%page==0){
			pages=list.size()/page;
		}else{
			pages=list.size()/page+1;
		}
		info.put("pages", pages);
//		info.put("pages", list.size()%page+1);
		JSONArray array = new JSONArray();
		for(int i=0;i<list.size();i++){
			JSONObject j = new JSONObject();
			int userId = list.get(i);
			j.put("userId", userId);
			User findUser = userDao.findUser(userId,cid);
			//从redis中获取玩家姓名，图片 放入j中
			j.put("userImg", findUser.getUSER_IMG());
			j.put("userName", findUser.getUSER_NAME());
			array.add(j);
		}
		info.put("users", array);
		return info;
	}
	//申请离开列表
	@Override
	public JSONObject findLeaveUser(int clubId, int page,Integer cid) {
		List<Integer> list = clubUserDao.findLeaveUser(clubId,cid);
		//没有人
//		if(list==null||list.size()==0)
//			return null;
		JSONObject info = new JSONObject();
		int pages=0;
		if(list.size()%page==0){
			pages=list.size()/page;
		}else{
			pages=list.size()/page+1;
		}
		info.put("pages", pages);
//		info.put("pages", list.size()%page+1);
		JSONArray array = new JSONArray();
		for(int i=0;i<list.size();i++){
			JSONObject j = new JSONObject();
			int userId = list.get(i);
			j.put("userId", userId);
			User findUser = userDao.findUser(userId,cid);
			//从redis中获取玩家姓名，图片 放入j中
			j.put("userImg", findUser.getUSER_IMG());
			j.put("userName", findUser.getUSER_NAME());
			array.add(j);
		}
		info.put("users", array);
		return info;
	}
	//同意加入
	@Override
	public void passJoin(Integer clubId, Integer userId,Integer cid) {
		clubUserDao.passJoin(clubId, userId,cid);
		//检验人数是否已满 
		//更新redis数据
	}
	//拒绝加入
	@Override
	public void refuseJoin(Integer clubId, Integer userId,Integer cid) {
		clubUserDao.refuseJoin(clubId, userId,cid);
		
	}
	//同意离开
	@Override
	public void passLeave(Integer clubId, Integer userId,Integer cid) {
		clubUserDao.passLeave(clubId, userId,cid);
		//更新redis数据
	}
	//拒绝离开
	@Override
	public void refuseLeave(Integer clubId, Integer userId,Integer cid) {
		clubUserDao.refuseLeave(clubId, userId,cid);
	}
	@Override
	public void deleteUser(Integer clubId, Integer userId,Integer cid) {
		clubUserDao.deleteUser(clubId, userId,cid );
		//更新redis
	}
}
