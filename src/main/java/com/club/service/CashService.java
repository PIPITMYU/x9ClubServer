package com.club.service;

import java.util.HashMap;

import com.alibaba.fastjson.JSONObject;

public interface CashService {
	//��������
	void applyCash(HashMap<String, Object> map,Long time);
	//ͬ������
	void agreeCash(Integer userId,String cashCode);
	//�����б�
	JSONObject haveCashList(Integer userId,Integer size,Integer start);
	//δ�����б�
	JSONObject noCashList(Integer userId, Integer size, Integer start);
}
