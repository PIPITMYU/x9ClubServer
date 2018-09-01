package com.club.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.club.entity.Recharge;

public interface ZongDaiLUserDao {
	//��ֵ������ ������
	Integer findAllBuyCountKou();
	//��ֵ���� ������ ��ҳ
	List<Recharge> findRechargeByPagesKou(HashMap<String, Object> map);
	//������
	Integer findKouCount();
	//�������
	Integer findKouAll();
	//�ܶ�����
	Integer findOrderCount();
	//�ܷ������
	Integer findOrderNum();
	//�޸ķֳ�---����
	void changeKouState(String orderNum);
	//�޸Ŀ���
	void changeKou(@Param("kou")Integer kou);
	//�������б�
	List<Integer> whiteList(HashMap<String, Object> map);
	//����userid���Ұ�����
	Integer findWhiteById(Integer userId);
	//�޸İ�����
	void changeWhite(@Param("userId")Integer userId,@Param("white")Integer white);
	//����������
	Integer allWhiteCount();
	//�鿴����
	Integer findKou();
	
	//���붩������
	void saveRecharge(HashMap<String, Object> map);
	//���ն�����
	Integer todayAllCount(HashMap<String, Object> map);
	//���տ�����
	Integer todayAllKouCount(HashMap<String, Object> map);
	//���ն����ܶ�
	Integer todayAll(HashMap<String, Object> map);
	//���տ����ܶ�
	Integer todayAllKou(HashMap<String, Object> map);
}
