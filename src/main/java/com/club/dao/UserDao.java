package com.club.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.club.entity.DaiL;
import com.club.entity.User;

public interface UserDao {
	//�޸���ҿ��
	public void updateUserMoney(@Param("id")Integer id,@Param("change")Integer change);
	//������ҷ���
	public Integer findMoney(Integer userId);
	//���������Ϣ
	public User findUser(@Param("userId")Integer userId, @Param("cid")Integer cid);
	//���Ҵ���token��Ϣ
	String findUserToken(Integer id);
	//���Ҵ���user_id
	Integer findUserId(Integer id);
	//����daili������
	DaiL findDaiLById(Integer dail_id);
	//���Ҵ���������ͼƬ������
	Map<String,Object> findDaiLInfo(Integer userId);
	//��ѯ����Ȩ��
	Integer findDaiLPower(Integer userId);
	//������ʵ����id
	Integer findTrueId(Integer userId);
	//���³�ֵ���
	void changePayMoney(@Param("id")Integer id,@Param("change")Integer change);
}
