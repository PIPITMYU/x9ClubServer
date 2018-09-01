package com.club.controller;

import java.util.HashMap;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.club.constant.Cnst;
import com.club.dao.CashDao;
import com.club.dao.DaiLUserDao;
import com.club.dao.UserDao;
import com.club.entity.DaiL;
import com.club.entity.JsonResult;
import com.club.service.CashService;
import com.club.util.GameUtil;

@Controller
public class CashController {
	@Resource
	private CashService cashService;
	@Resource
	private UserDao userDao;
	@Resource
	private DaiLUserDao daiLUserDao;
	@Resource
	private CashDao cashDao;
	@RequestMapping("/applyCash")
	@ResponseBody
	public JsonResult applyCash(Integer userId){
		try{
			//���ֽ�� ����100 24Сʱǰ�����Ķ���  һ��һ��
			System.out.println("====================>��������"+userId);
			Long time = System.currentTimeMillis();
			Long morning = GameUtil.getTimesmorning();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("dail_id", userId);
			map.put("time",morning);
			Integer count = cashDao.todayCashCount(map);
			if(count>=1){
//				return new JsonResult("����������");
				return new JsonResult("1");
			}
			DaiL dail = userDao.findDaiLById(userId);
			double scale = dail.getScale()*0.1;//�ֳ�
			Long time1 = time-86400000;
			map.replace("time",time1);//24Сʱǰ
			Integer allPay = daiLUserDao.daiLAllBuy(map);
			if(allPay==null)
				allPay = 0;
			double allMoney = allPay*scale;//�����ֽ��
			Integer minCash = cashDao.minCash();//ÿ����Ͷ��
			if(minCash>allMoney){
//				return new JsonResult("δ�ﵽ������ֶ��");
				return new JsonResult("2");
			}
			map.put("money", allMoney);
			map.put("state", 0);
			map.put("cashCode", String.valueOf(userId).concat(String.valueOf(time)));
			map.replace("time", time);
			cashService.applyCash(map,time1);
			return new JsonResult(Cnst.SUCCESS);
		}catch(Exception e){
			e.printStackTrace();
//			return new JsonResult("3");
			return new JsonResult("3");
		}
	}
	@RequestMapping("/agreeCash")
	@ResponseBody
	public JsonResult agreeCash(Integer userId,String cashCode){
		try{
			System.out.println("====================>ͬ������"+userId+cashCode);
			userId = Integer.parseInt(cashCode.substring(0,5));
			cashService.agreeCash(userId, cashCode);
			return new JsonResult(Cnst.SUCCESS);
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResult("3");
		}
	}
	@RequestMapping("/havaCashList")
	@ResponseBody
	public JsonResult cashList(Integer userId,Integer page,Integer currentPage){
		try{
			System.out.println("======================>�����б�"+userId+page+currentPage);
			JSONObject info = cashService.haveCashList(userId, page, currentPage);
			return new JsonResult(info);
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResult("3");
		}
	}
	@RequestMapping("/noCashList")
	@ResponseBody
	public JsonResult noCashList(Integer userId,Integer page,Integer currentPage){
		try{
			System.out.println("======================>δ�����б�"+userId+page+currentPage);
			JSONObject info = cashService.noCashList(userId, page, currentPage);
			return new JsonResult(info);
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResult("3");
		}
	}
}
