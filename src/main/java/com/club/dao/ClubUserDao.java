package com.club.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface ClubUserDao {
	//���ֲ�����
	public List<Integer> findAllUser(@Param("clubId")Integer clubId,@Param("cid") Integer cid);
	//���ֲ������������
	public List<Integer> findJoinUser(@Param("clubId")Integer clubId, @Param("cid")int cid);
	//���ֲ������˳�����
	public List<Integer> findLeaveUser( @Param("clubId")Integer clubId,  @Param("cid")int cid);
	//ͨ��������ֲ����� 0--1
	public void passJoin(@Param("clubId")Integer clubId,@Param("userId")Integer userId,  @Param("cid")Integer cid); 
	//�ܾ�������ֲ����� ɾ����¼
	public void refuseJoin(@Param("clubId")Integer clubId,@Param("userId")Integer userId,  @Param("cid")Integer cid);
	//ͨ���˳����ֲ����� ɾ����¼
	public void passLeave(@Param("clubId")Integer clubId,@Param("userId")Integer userId,  @Param("cid")Integer cid); 
	//�ܾ��˳����ֲ����� 2--1
	public void refuseLeave(@Param("clubId")Integer clubId,@Param("userId")Integer userId,  @Param("cid")Integer cid); 
	//�߳����ֲ�
	public void deleteUser(@Param("clubId")Integer clubId,@Param("userId")Integer userId, @Param("cid")Integer cid);
	//ͨ�����ID��ѯ��������ľ��ֲ�����
	public Integer findClubNumByUserId(@Param("userId")Integer userId, @Param("cid")Integer cid);
}
