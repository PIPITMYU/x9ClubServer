package com.club.service;



import com.alibaba.fastjson.JSONObject;
import com.club.entity.ClubInfo;


public interface ClubInfoService {
	//��ȡ�ҵľ��ֲ�
	JSONObject getMyClubs(int id,int id2, Integer cid);
	//�������ֲ�
	void createClub(ClubInfo club,Integer userId,Integer change);
	//������
	void addMoney(Integer clubId,Integer userId,Integer addMoney,Integer cid);
	//�鿴���
	JSONObject moneyManage(Integer clubId,Integer userId, Integer cid);
	//��ɢ���ֲ�
	void deleteClub(Integer clubId,Integer cid);
	//��Ա�䶯Ԥ��
	JSONObject haveAction(Integer userId,Integer cid);
	//��ȡ���˴����ľ��ֲ�
	JSONObject getHisClubs(Integer toUserId,Integer cid);
	
}
