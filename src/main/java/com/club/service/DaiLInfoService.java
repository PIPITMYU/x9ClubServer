package com.club.service;

import com.alibaba.fastjson.JSONObject;

public interface DaiLInfoService {
	//����dail��Ϣ
	public JSONObject findDaiLById(Integer dail_id);
	//�Ѱ��û�
	public JSONObject findBindUser(Integer userId,Integer size,Integer start,Integer cid);
	//���ҳ�ֵ��ϸ
	public JSONObject findAllBuyInfo(Integer userId,Integer size,Integer start);
	//�����û�����
	public Integer findUserDai(Integer userId);
	//�޸��û�����
	public void changeUserDai(Integer daiLId,Integer userId);
}
