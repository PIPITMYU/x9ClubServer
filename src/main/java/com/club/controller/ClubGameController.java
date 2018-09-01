package com.club.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.club.constant.Cnst;
import com.club.dao.ClubGameDao;
import com.club.entity.JsonResult;
import com.club.service.ClubGameService;
import com.club.util.GameUtil;
@Controller
public class ClubGameController {
	@Resource
	private ClubGameService clubGameService;
	@Resource
	private ClubGameDao clubGameDao;
	@RequestMapping("/clubUsersGame")//ս����ѯ
	@ResponseBody
	public JsonResult clubUsersGame(Integer clubId,Long userId,Long queryUserId,Long startData
			,Long endData,String order,String desc,Integer page,Integer cid ){
		try{
//			String token_time = userDao.findUserToken(Integer.valueOf(userId+""));
//			if(ClubUtil.checkLogin(token_time))
//				return new JsonResult(-1,"��¼�ѹ�ʱ�����µ�¼",null,"");
			System.out.println("ս����ѯ=================>"+clubId+userId+queryUserId+startData+endData+order+desc+page);
			JSONObject info = clubGameService.clubUsersGame(clubId, order, desc, page, startData, endData,cid);
			Long timesmorning = GameUtil.getTimesmorning();
			//1:����Ļ�Ծ����

			Long actNum = GameUtil.scard(Cnst.get_REDIS_CLUB_ACTIVE_PERSON(cid+"").concat(clubId+"_").concat(timesmorning+""));
			if(actNum==null ||actNum==0l){
				actNum=0l;
			}else{
				//��Ϊ���ù���ʱ��ʱ�����������ݣ���Ҫɾ��
				actNum=actNum-1;
			}
			info.put("activeNum", actNum);
			//����ľ���
			Integer juNum = GameUtil.getObject(Cnst.get_REDIS_CLUB_TODAYKAI_NUM(cid+"").concat(clubId+"_").concat(timesmorning+""), Integer.class);

			if(juNum==null ||juNum==0){
				juNum=0;
			}
			System.out.println("startData:"+startData);
			System.out.println("endData:"+endData);
			System.out.println("juNum:"+juNum);
			info.put("currNum", juNum);
//			info.put("currNum", clubGameService.todayGames(clubId,cid));
			info.put("allHeXiao", clubGameService.allHeXiao(clubId,startData,cid));
			return new JsonResult(info);
		}catch(Exception e){
			System.out.println(e.getMessage());
			return new JsonResult("3");
		}	
	}
	@RequestMapping("/addHeXiao")//�û�����
	@ResponseBody
	public JsonResult addHeXiao(Integer clubId,Integer userId,Integer inClubUserId,Long startData,Integer cid){
		try{
//			String token_time = userDao.findUserToken(Integer.valueOf(userId+""));
//			if(ClubUtil.checkLogin(token_time))
//				return new JsonResult(-1,"��¼�ѹ�ʱ�����µ�¼",null,"");
			System.out.println("�û�����================>"+clubId+userId+inClubUserId+startData);
			clubGameService.addHeXiao(clubId, inClubUserId, startData,cid);
			return new JsonResult(Cnst.SUCCESS);
		}catch(Exception e){
			e.printStackTrace();
			return new JsonResult("3");
		}
	}
}
