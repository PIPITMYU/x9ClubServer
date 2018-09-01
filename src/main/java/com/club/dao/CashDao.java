package com.club.dao;

import java.util.HashMap;
import java.util.List;

import com.club.entity.Cash;

public interface CashDao {
	//��������
	void insertCash(HashMap<String,Object> map);
	void changeIfCashState(HashMap<String,Object> map);
	//ͬ������
	void agreeCash(HashMap<String,Object> map);
	//�������б�
	List<Cash> findHaveCash(HashMap<String, Object> map);
	//��ѯ�������ִ���
	Integer todayCashCount(HashMap<String, Object> map);
	//������ֶ��
	Integer minCash();
	//����������
	Integer haveCashCount();
	//δ�����б�
	List<Cash> findNoCash(HashMap<String, Object> map);
	//δ��������
	Integer noCashCount();
}
