package com.club.dao;

import java.util.HashMap;
import java.util.List;


public interface ClubGameDao {
	//���ջ�Ծ����
	List<Integer> todayPerson(HashMap<String, Object> map);
	//���վ���
	Integer todayGames(HashMap<String, Object> map);
	//���˱����ܾ���
	Integer allNum(HashMap<String, Object> map);
	//���˱����ܷ�
//	Integer allScore(HashMap<String, Object> map);
	//�鿴����״̬
	Integer findHeXiao(HashMap<String, Object> map);
	//�û�����
	void addHeXiao(HashMap<String, Object> map);
	//���պ�����
	Integer allHeXiao(HashMap<String, Object> map);
}
