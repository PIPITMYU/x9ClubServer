package com.club.service;

import com.alibaba.fastjson.JSONObject;

public interface ZongDaiLInfoService {
	//��ֵ����
	public JSONObject findAllBuy(Integer userId,Integer size,Integer start);
	//�����л�
	public void changeKouState(String orderNum);
	//�޸Ŀ���
	public void changeKou(Integer kou);
	//�������б�
	public JSONObject whiteList(Integer size,Integer start,Integer cid);
	//��ѯ����������id
	public Integer findWhiteById(Integer userId);
	//�޸İ�����
	public void chageWhite(Integer userId,Integer white);
	//�鿴����
	public Integer findKou();
}
