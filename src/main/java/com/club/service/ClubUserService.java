package com.club.service;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONObject;

public interface ClubUserService {
	//查询俱乐部人数
	JSONObject findAllUser(int clubId,int page,Integer user_id,Integer cid);
	//查询申请加入人数
	JSONObject findJoinUser(int clubId,int page,int cid);
	//查询申请退出人数
	JSONObject findLeaveUser(int clubId,int page, Integer cid);
	//通过加入俱乐部申请 
	public void passJoin(Integer clubId,Integer userId, Integer cid); 
	//拒绝加入俱乐部申请 
	public void refuseJoin(Integer clubId,Integer userId, Integer cid);
	//通过退出俱乐部申请 
	public void passLeave(Integer clubId,Integer userId, Integer cid); 
	//拒绝退出俱乐部申请 
	public void refuseLeave(Integer clubId,Integer userId, Integer cid);
	//踢出俱乐部
	public void deleteUser(Integer clubId,Integer userId, Integer cid);
}
