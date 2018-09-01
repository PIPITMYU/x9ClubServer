package com.club.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

public interface ClubGameService {
	//���ջ�Ծ����
	List<Integer> todayPerson(Integer clubId);
	//���վ���
	Integer todayGames(Integer clubId, Integer cid);
	//ս���б�
	JSONObject clubUsersGame(Integer clubId,String order,String desc,Integer page,Long morning,Long night, Integer cid);
	//�û�����
	void addHeXiao(Integer clubId,Integer userId,Long morning, Integer cid);
	//�û�������
	Integer allHeXiao(Integer clubId,Long morning,Integer cid);
}
