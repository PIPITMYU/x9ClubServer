package com.club.dao;

import java.util.HashMap;
import java.util.List;


import org.apache.ibatis.annotations.Param;

import com.club.entity.ClubInfo;
import com.club.entity.RedisClub;

public interface ClubInfoDao {
	//��ѯ�ҵľ��ֲ�
	public List<ClubInfo> findClubByCreateId(@Param("id")Integer id, @Param("cid")int cid);
	//�������ֲ�ѯ���ֲ�
	Integer findClubByClubName(String name);
	//�������ֲ�
	public void createClub(ClubInfo club);
	//�Ѵ����߼�����ֲ���
	public void addCreater(HashMap<String, Object> map);
	//��������
	public void addMoney(@Param("clubId")Integer clubId,@Param("change")Integer change,@Param("cid")int cid);
	//��ѯ���ֲ���Ϣ
	ClubInfo findClubByClubId(@Param("clubId")Integer clubId, @Param("cid")Integer cid);
	//��ɢ���ֲ�
	public void deleteClubInfo(Integer clubId);
	public void deleteClubUser(Integer clubId);
	//��ѯ�Ƿ����������
	Integer haveAction(@Param("clubId")Integer clubId, @Param("cid")Integer cid);
	//���ݾ��ֲ���idȥ���¾��ֲ����������ʱ�䣨�رվ��ֲ�����ʱ�䣩
	public void updateFreeTimeByClubId(@Param("clubId")Integer clubId, @Param("freeStart")Long freeStart,
			@Param("freeEnd")Long freeEnd, @Param("cid")Integer cid);
	//���ݾ��ֲ���idȥ�رվ��ֲ����������ʱ�䣨�رվ��ֲ�����ʱ�䣩
	public void closeFreeTimeByClubId(@Param("clubId")Integer clubId, @Param("freeEnd")long freeEnd, @Param("cid")Integer cid);
	//��ѯ���ֲ���Ϣ
	public RedisClub findClubNewByClubId(@Param("clubId")Integer clubId, @Param("cid")Integer cid);
}
