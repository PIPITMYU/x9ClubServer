package com.club.service;

import org.apache.ibatis.annotations.Param;

import com.alibaba.fastjson.JSONObject;

public interface ClubUserService {
	//��ѯ���ֲ�����
	JSONObject findAllUser(int clubId,int page,Integer user_id,Integer cid);
	//��ѯ�����������
	JSONObject findJoinUser(int clubId,int page,int cid);
	//��ѯ�����˳�����
	JSONObject findLeaveUser(int clubId,int page, Integer cid);
	//ͨ��������ֲ����� 
	public void passJoin(Integer clubId,Integer userId, Integer cid); 
	//�ܾ�������ֲ����� 
	public void refuseJoin(Integer clubId,Integer userId, Integer cid);
	//ͨ���˳����ֲ����� 
	public void passLeave(Integer clubId,Integer userId, Integer cid); 
	//�ܾ��˳����ֲ����� 
	public void refuseLeave(Integer clubId,Integer userId, Integer cid);
	//�߳����ֲ�
	public void deleteUser(Integer clubId,Integer userId, Integer cid);
}
