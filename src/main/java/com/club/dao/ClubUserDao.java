package com.club.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ClubUserDao {
	//俱乐部人数
	public List<Integer> findAllUser(@Param("clubId")Integer clubId,@Param("cid") Integer cid);
	//俱乐部申请加入名单
	public List<Integer> findJoinUser(@Param("clubId")Integer clubId, @Param("cid")int cid);
	//俱乐部申请退出名单
	public List<Integer> findLeaveUser( @Param("clubId")Integer clubId,  @Param("cid")int cid);
	//通过加入俱乐部申请 0--1
	public void passJoin(@Param("clubId")Integer clubId,@Param("userId")Integer userId,  @Param("cid")Integer cid); 
	//拒绝加入俱乐部申请 删除记录
	public void refuseJoin(@Param("clubId")Integer clubId,@Param("userId")Integer userId,  @Param("cid")Integer cid);
	//通过退出俱乐部申请 删除记录
	public void passLeave(@Param("clubId")Integer clubId,@Param("userId")Integer userId,  @Param("cid")Integer cid); 
	//拒绝退出俱乐部申请 2--1
	public void refuseLeave(@Param("clubId")Integer clubId,@Param("userId")Integer userId,  @Param("cid")Integer cid); 
	//踢出俱乐部
	public void deleteUser(@Param("clubId")Integer clubId,@Param("userId")Integer userId, @Param("cid")Integer cid);
	//通过玩家ID查询出所加入的俱乐部数量
	public Integer findClubNumByUserId(@Param("userId")Integer userId, @Param("cid")Integer cid);
}
