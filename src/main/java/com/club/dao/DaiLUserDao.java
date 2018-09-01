package com.club.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.club.entity.DaiLUser;
import com.club.entity.Recharge;

public interface DaiLUserDao {
	//��ҳ��ѯ�����û���Ϣ
	List<DaiLUser> findDaiLUserByPages(HashMap<String, Object> map);
	//�����ܰ�����
	Integer findAllUserCount(Integer dail_id);
	//�����û�������(����ʾ����)
	Integer findUserAllBuy(Integer userId);
	//��ҳ��ѯֱ����ϸ
	List<Recharge> findRechargeByPages(HashMap<String,Object> map);
	//��ֵ��ϸ����(��������)
	Integer findAllBuyCount(Integer dail_id);
	//���ҷֳ�
	Integer findScaleById(Integer userId);
	//����ֱ�俨��
	Integer todayAllCard(HashMap<String, Object> map);
	//���Ҵ����ܳ�ֵ(������)
	Integer daiLAllBuy(HashMap<String, Object> map);
	//�����û�����
	Integer findDaiLId(Integer userId);
	//�޸��û�����
	void changeDaiLId(@Param("dail_id")Integer daiLId,@Param("user_id")Integer userId);
	//�û��󶨴���
	void insertDaiLUser(HashMap<String, Object> map);
}
