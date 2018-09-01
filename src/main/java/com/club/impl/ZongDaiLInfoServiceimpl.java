package com.club.impl;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.club.constant.Cnst;
import com.club.dao.UserDao;
import com.club.dao.ZongDaiLUserDao;
import com.club.entity.Recharge;
import com.club.entity.User;
import com.club.service.ZongDaiLInfoService;
import com.club.util.GameUtil;
@Service("zongDaiLInfoService")
public class ZongDaiLInfoServiceimpl implements ZongDaiLInfoService{
	@Resource
	private ZongDaiLUserDao zongDaiLUserDao;
	@Resource
	private UserDao userDao;
	//��ҳ��ѯ��ֵ��¼
	@Override
	public JSONObject findAllBuy(Integer userId, Integer size, Integer start) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("dail_id", userId);
		map.put("start", (start-1)*size);
		map.put("size", size);
		//�ܶ�������
		Integer allCount = zongDaiLUserDao.findOrderCount();
		List<Recharge> users = zongDaiLUserDao.findRechargeByPagesKou(map);
		JSONObject info = new JSONObject();
		//�ܶ�����
		info.put("allDing", allCount);
		//�ܽ��
		Integer allMoney = zongDaiLUserDao.findOrderNum();
		info.put("allMoney",allMoney==null?0:allMoney);
		//������
		Integer allKou = zongDaiLUserDao.findKouCount();
		info.put("allKou", allKou);
		//�����ܶ�
		Integer allKouMoney = zongDaiLUserDao.findKouAll();
		info.put("allKouMoney", allKouMoney==null?0:allKouMoney);
		//��ǰ��������
		Integer kou = zongDaiLUserDao.findKou();
		info.put("currentKou", kou);
		Long morning = GameUtil.getTimesmorning();
		map.put("morning", morning);
		map.put("night", morning+86400000);
		//���ն�����
		Integer todayAllCount = zongDaiLUserDao.todayAllCount(map);
		info.put("todayAllCount", todayAllCount);
		//���տ�����
		Integer todayAllKouCount = zongDaiLUserDao.todayAllKouCount(map);
		info.put("todayAllKouCount", todayAllKouCount);
		//�����ܶ�
		Integer todayAll = zongDaiLUserDao.todayAll(map);
		info.put("todayAllMoney", todayAll==null?0:todayAll);
		//���տ����ܶ�
		Integer todayAllKou = zongDaiLUserDao.todayAllKou(map);
		info.put("todayAllKouMoney", todayAllKou==null?0:todayAllCount);
		JSONArray array = new JSONArray();
		for(int i=0;i<users.size();i++){
			JSONObject js = new JSONObject();
			Recharge r = users.get(i); 
			js.put("openId", r.getUser_id());
			js.put("openBuy", r.getMoney());
			js.put("openCard", r.getCard());
			js.put("openTime",r.getTime());
			js.put("dailId",userDao.findUserId(r.getDail_id()));
			js.put("status", r.getIfkou());
			js.put("orderNum",r.getOrderNum());
			js.put("cashStatus",r.getIfcash()==0?0:1);//0Ϊ���л���1�����л�
			array.add(js);
		}
		info.put("users", array);
		info.put("pages", allCount/size+1);
		return info;
	}
	//�������� �ֳ�---->���� �����������
	@Override
	public void changeKouState(String orderNum) {
		zongDaiLUserDao.changeKouState(orderNum);
	}
	//�����޸�
	@Override
	public void changeKou(Integer kou) {
		zongDaiLUserDao.changeKou(kou);
		//�޸�redis�����������
		GameUtil.setKouInfo(Cnst.REDIS_PAY_CURRENTKOU,0);
		GameUtil.setKouInfo(Cnst.REDIS_PAY_CURRENTKOU,kou);
		
	}
	//�������б�
	@Override
	public JSONObject whiteList(Integer size, Integer start,Integer cid) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("start", (start-1)*size);
		map.put("size", size);
		List<Integer> whiteList = zongDaiLUserDao.whiteList(map);
		Integer count = zongDaiLUserDao.allWhiteCount();
		JSONObject info = new JSONObject();
		info.put("pages", count/size+1);
		JSONArray array = new JSONArray();
		for(int i=0;i<whiteList.size();i++){
			Integer userId = whiteList.get(i);
			User user = userDao.findUser(whiteList.get(i),cid);
			JSONObject j = new JSONObject();
			j.put("openId", userId);
			j.put("openImg",user.getUSER_IMG());
			j.put("openName", user.getUSER_NAME());
			array.add(j);
		}
		info.put("users", array);
		return info;
	}
	//����id��ѯ������
	@Override
	public Integer findWhiteById(Integer userId) {

		return zongDaiLUserDao.findWhiteById(userId);
	}
	//�޸İ�����
	@Override
	public void chageWhite(Integer userId, Integer white) {
		zongDaiLUserDao.changeWhite(userId, white);
	}
	//�鿴����
	@Override
	public Integer findKou() {
		return zongDaiLUserDao.findKou();
	}
	
}
