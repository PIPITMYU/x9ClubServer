package com.club.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.club.constant.Cnst;
import com.club.dao.ClubInfoDao;
import com.club.dao.ClubUserDao;
import com.club.dao.UserDao;
import com.club.entity.ClubInfo;
import com.club.entity.JsonResult;
import com.club.entity.RedisClub;
import com.club.redis.StringUtils;
import com.club.service.ClubInfoService;
import com.club.util.GameUtil;

@Controller
public class ClubInfoController {
	@Resource
	private ClubInfoDao clubInfoDao;
	@Resource
	private ClubInfoService clubInfoService;
	@Resource
	private ClubUserDao clubUserDao;
	@Resource
	private UserDao userDao;
	
	/*
	 * �������cid���־��ֲ������ֲ��Ͳ���֪�����ĸ������ģ�����
	 * 
	 */
	
	
	@RequestMapping("/getMyClubs")//��ȡ���ֲ�
	@ResponseBody
	public JsonResult getMyClubs(Long userId,HttpServletRequest request,Integer cid){
		try{
//			String token_time = userDao.findUserToken(Integer.valueOf(userId+""));
//			if(ClubUtil.checkLogin(token_time))
//				return new JsonResult(-1,"��¼�ѹ�ʱ�����µ�¼",null,"");
			if(!StringUtils.isNum(userId.toString()) || cid==null ){
				return new JsonResult("13");
			}
			//ͨ������id�ҵ����id
			Integer user_Id = userDao.findUserId(Integer.valueOf(userId+""));
			System.out.println("==================>"+user_Id);
			System.out.println("��ȡ���ֲ�=======================>userID"+userId);
			JSONObject json = clubInfoService.getMyClubs(user_Id,Integer.valueOf(userId+""),cid);
			return new JsonResult(json);
		}
		catch(Exception e){
			System.out.println(e);
//			return new JsonResult("����ʧ�ܣ����Ժ�����");
			return new JsonResult("3");
		}
	}
	@RequestMapping("/createClub")//�������ֲ�
	@ResponseBody
	public JsonResult createClub(Long userId,String clubName,Integer lastMoney,Integer maxUseMoney,Integer moneyWarn,Integer cid){
		try{
//			String token_time = userDao.findUserToken(Integer.valueOf(userId+""));
//			if(ClubUtil.checkLogin(token_time))
//				return new JsonResult(-1,"��¼�ѹ�ʱ�����µ�¼",null,"");
			if(clubName.contains(".")||clubName.contains("/")||clubName.contains("//"))
				return new JsonResult("4");
//				return new JsonResult("���зǷ��ַ�");
			if(clubInfoDao.findClubByClubName(clubName)!=null){
//				return new JsonResult("�þ��ֲ��Ѵ��ڣ��������ư�");
				return new JsonResult("5");
			}
			//�ж��ǲ�������
			if(!StringUtils.isNum(userId.toString()) ||  !StringUtils.isNum(lastMoney.toString())
			|| !StringUtils.isNum(maxUseMoney.toString()) ||  !StringUtils.isNum(moneyWarn.toString())
			|| !StringUtils.isNum(cid.toString())){
				return new JsonResult("15");
			}
			
			Integer user_Id = userDao.findUserId(Integer.valueOf(userId+""));
//			System.out.println("�������ֲ�=======>"+userId+clubName+lastMoney+maxUseMoney+moneyWarn+cid);
//			int canCreateNum = Integer.valueOf(getRedisInfo.getStringByKey(Cnst.REDIS_PREFIX_CREATEMAX));
			int canCreateNum = Cnst.maxClub;
			//���ֲ��ܴ���  ָ�䴴���ĺͼ���ĺ�
			Integer num =clubUserDao.findClubNumByUserId(Integer.valueOf(user_Id+""),cid);
//			if(clubs.size()>=canCreateNum)
			if(num >=canCreateNum)
//				return new JsonResult("�޷�����������ֲ�");
				return new JsonResult("6");
			//����userId��ȡ���淿����
			int money = userDao.findMoney(Integer.valueOf(userId+""));
			if(money<1000){
				//��������1000�����ֵ
				return new JsonResult("25");
			}
			if(money<lastMoney)
//				return new JsonResult("�������㣬�޷��������ֲ�");
				return new JsonResult("7");
			if(moneyWarn>=lastMoney)
//				return new JsonResult("����Ԥ������С�ڷ������ֵ");
				return new JsonResult("8");
			ClubInfo club = new ClubInfo();
			RedisClub redisClub=new RedisClub();
			
			//����clubId�Ƿ����
			synchronized (ClubInfoController.class) {
				Integer clubId;
				while(true){
					clubId = GameUtil.createSixCode();
					//���ﲻ��cid �ǲ��Ǳ�ʾ��ʹ������cid �����о��ֲ���idҲ�����ظ���  
					if(clubInfoDao.findClubByClubId(clubId,cid)==null){
						club.setCLUB_ID(GameUtil.createSixCode());
						break;
					}	
				}
				long currentTimeMillis = System.currentTimeMillis();
				club.setCLUB_NAME(clubName);
				club.setCID(cid);
				club.setCREATE_ID(Integer.valueOf(user_Id+""));
				club.setCREATE_TIME(currentTimeMillis);
				club.setPERSON_QUOTA(Cnst.maxMember);
	//			club.setPERSON_QUOTA(Integer.valueOf(getRedisInfo.getStringByKey(Cnst.REDIS_PREFIX_CLUBMENBERMAX)));//��������redis����
				club.setROOM_CARD_NOTICE(moneyWarn);
				club.setROOM_CARD_NUM(lastMoney);
				club.setROOM_CARD_QUOTA(maxUseMoney);
				//�¼�������⿪ʼʱ����������ʱ��  ���ֻ����2018 1���У�ʱ���µ�1517414400
				club.setFREE_START(currentTimeMillis);
				if(currentTimeMillis< Cnst.HUODONG_TIME*1000){//�������ʱ��
					club.setFREE_END(Cnst.HUODONG_TIME*1000);
				}else{
					club.setFREE_END(currentTimeMillis);
				}
				clubInfoService.createClub(club,Integer.valueOf(userId+""),lastMoney);
				redisClub.setCid(cid);
				redisClub.setClubName(clubName);
				redisClub.setCreateId(Long.valueOf(user_Id+""));
				redisClub.setCreateTime(currentTimeMillis);
				redisClub.setPersonQuota(Cnst.maxMember);
				redisClub.setRoomCardNotice(moneyWarn);
				redisClub.setRoomCardNum(lastMoney);
				redisClub.setRoomCardQuota(maxUseMoney);
				redisClub.setFreeStart(currentTimeMillis);
				redisClub.setFreeEnd(club.getFREE_END());
				//���´����ľ��ֲ�
				GameUtil.setClubInfoByClubId(Cnst.get_REDIS_PREFIX_CLUBMAP(cid.toString())+clubId.toString(), redisClub);
				
				
			}
			return new JsonResult(Cnst.SUCCESS);
		}catch(Exception e){
			System.out.println(e);
//			return new JsonResult("����ʧ�ܣ����Ժ�����");
			return new JsonResult("3");
		}
	}
	@RequestMapping("/searchClubs")//������ҵ�idȡ��ѯ���ֲ���Ϣ
	@ResponseBody
	public JsonResult searchClubs(String userId,String toUserId,Integer cid){
		try{
			//�ж��û���������Ƿ���ȷ
			if(!StringUtils.isNum(userId) ||!StringUtils.isNum(toUserId)){
				return new JsonResult("13");
			}
//			if(!StringUtils.isNum(toUserId)){
//				return new JsonResult("15");
//			}
			//userId��ʶ��ѯ���û�id;   toUserId��ʶ����ѯ���ֲ���������id
			//��ѯ����Ȩ��  userId��ʶ��ѯ���û�id
			Integer daiLPower = userDao.findDaiLPower(Integer.valueOf(userId));
			if(daiLPower==null){
				daiLPower=0;
			}
			if(daiLPower<6){
				//Ȩ�޲���
				return new JsonResult("16");
			}
			//��ѯ���˴����ľ��ֲ���Ϣ
			JSONObject j=clubInfoService.getHisClubs(Integer.valueOf(toUserId),cid);
//			if(j==null){
//				return new JsonResult("");
//			}
			return new JsonResult(j);
		}catch(Exception e){
			System.out.println(e);
			return new JsonResult("3");
		}
	}
	
	@RequestMapping("/freeClub")//���ֲ���ֵ����ʱ��
	@ResponseBody
	public JsonResult freeClub(String userId,String clubId,Integer state,Integer cid){
		try{
//			//�ж��û���������Ƿ���ȷ
			if(!StringUtils.isNum(userId) || !StringUtils.isNum(clubId)){
				return new JsonResult("15");
			}
//			if(!StringUtils.isNum(clubId)){
//				return new JsonResult("15");
//			}
			if(!state.equals(1) && !state.equals(2))
				return new JsonResult("4");
			//��ѯ����Ȩ��
			Integer daiLPower = userDao.findDaiLPower(Integer.valueOf(userId));
			if(daiLPower<6){
				//Ȩ�޲���
				return new JsonResult("15");
			}
			//��ȡ���ֲ���Ϣ
			RedisClub redisClub = GameUtil.getClubInfoByClubId(Cnst.get_REDIS_PREFIX_CLUBMAP(String.valueOf(cid))+clubId);

			if (null == redisClub) {// ���Ϊ�� �����ݿ��ѯ

				redisClub = clubInfoDao.findClubNewByClubId(Integer.valueOf(clubId),cid);// ���ݾ��ֲ�id��ѯ
			}
			//��������⿪ʼʱ��
			Long freeStart = System.currentTimeMillis();
			Long freeEnd = redisClub.getFreeEnd();
			if(freeStart<freeEnd){//˵�����ж��������ʱ��
				if(state.equals(1)){//����
					freeEnd=freeEnd+Cnst.WEEK_TIME*1000;
				}else if(state.equals(2)){//����
					freeEnd=freeEnd+Cnst.MONTH_TIME*1000;
				}
			}else{//˵��û��������ʱ��
				if(state.equals(1)){//����
					freeEnd=freeStart+Cnst.WEEK_TIME*1000;
				}else if(state.equals(2)){//����
					freeEnd=freeStart+Cnst.MONTH_TIME*1000;
				}
			}
			//�������ݿ�
			clubInfoDao.updateFreeTimeByClubId(Integer.valueOf(clubId),freeStart,freeEnd,cid);
			//����redis����
			redisClub.setFreeStart(freeStart);
			redisClub.setFreeEnd(freeEnd);
			//����redis
			GameUtil.setClubInfoByClubId(Cnst.get_REDIS_PREFIX_CLUBMAP(cid.toString())+clubId.toString(), redisClub);
			return new JsonResult(Cnst.SUCCESS);
		}catch(Exception e){
			System.out.println(e);
			return new JsonResult("3");
		}
	}
	
	
	@RequestMapping("/closeClub")//���ֲ�����ʱ��
	@ResponseBody
	public JsonResult closeClub(String userId,String clubId,Integer cid){
		try{
//			//�ж��û���������Ƿ���ȷ
			if(!StringUtils.isNum(userId) || !StringUtils.isNum(clubId)){
				return new JsonResult("13");
			}
			//��ѯ����Ȩ��
			Integer daiLPower = userDao.findDaiLPower(Integer.valueOf(userId));
			if(daiLPower<6){
				//Ȩ�޲���
				return new JsonResult("15");
			}
			long currentTimeMillis = System.currentTimeMillis();
			clubInfoDao.closeFreeTimeByClubId(Integer.valueOf(clubId),currentTimeMillis,cid);
			// ͨ��clubId��redis�л�ȡ���ֲ���Ϣ
			RedisClub redisClub = GameUtil.getClubInfoByClubId(Cnst.get_REDIS_PREFIX_CLUBMAP(String.valueOf(cid))+clubId);
			if (null == redisClub) {// ���Ϊ�� �����ݿ��ѯ
				redisClub = clubInfoDao.findClubNewByClubId(Integer.valueOf(clubId),cid);// ���ݾ��ֲ�id��ѯ
			}else{
				//���¾��ֲ�����ѽ���ʱ��
//				redisClub.setFREE_END(currentTimeMillis);
				redisClub.setFreeEnd(currentTimeMillis);
			}
			//����redis����
			GameUtil.setClubInfoByClubId(Cnst.get_REDIS_PREFIX_CLUBMAP(cid.toString())+clubId.toString(), redisClub);
			return new JsonResult(Cnst.SUCCESS);
		}catch(Exception e){
			System.out.println(e);
			return new JsonResult("3");
		}
	}
	
	
	//�޸ľ��ֲ����
	@RequestMapping("/lastMoneyUpdate")
	@ResponseBody
	public JsonResult lastMoneyUpdate(Integer clubId,Long userId,Integer addMoney,Integer cid){
		try{
//			String token_time = userDao.findUserToken(Integer.valueOf(userId+""));
//			if(ClubUtil.checkLogin(token_time))
//				return new JsonResult(-1,"��¼�ѹ�ʱ�����µ�¼",null,"");
//			System.out.println("�޸ľ��ֲ����============>"+clubId+userId+addMoney);
			if(!StringUtils.isNum(clubId+"") || !StringUtils.isNum(addMoney+"")|| !StringUtils.isNum(userId+"")|| !StringUtils.isNum(cid+"")){
				return new JsonResult("13");
			}
			//redis���ҷ���
			int money = userDao.findMoney(Integer.valueOf(userId+""));
			if(money<addMoney){
//				return new JsonResult("����������");
				return new JsonResult("9");
			}
			clubInfoService.addMoney(clubId,Integer.valueOf(userId+""), addMoney,cid);
			return new JsonResult(Cnst.SUCCESS);
		}catch(Exception e){
			System.out.println(e);
//			return new JsonResult("����ʧ�ܣ����Ժ�����");
			return new JsonResult("3");
		}
	}
	//��������
	@RequestMapping("/moneyManage")
	@ResponseBody
	public JsonResult moneyManage(Integer clubId,Long userId,Integer cid){
		try{
//			String token_time = userDao.findUserToken(Integer.valueOf(userId+""));
//			if(ClubUtil.checkLogin(token_time))
//				return new JsonResult(-1,"��¼�ѹ�ʱ�����µ�¼",null,"");
//			System.out.println("��������=================>"+clubId+userId);
			JSONObject info = clubInfoService.moneyManage(clubId,Integer.valueOf(userId+""),cid);
			//���ֲ�������
			if(info==null){
				return new JsonResult(21);
			}
			return new JsonResult(info);
		}catch(Exception e){
//			return new JsonResult("����ʧ�ܣ����Ժ�����");
			return new JsonResult("3");
		}
	}
	//��ɢ���ֲ�
	@RequestMapping("/deleteClub")
	@ResponseBody
	public JsonResult deleteClub(Integer clubId,Long userId,Integer cid){
		try{
//			String token_time = userDao.findUserToken(Integer.valueOf(userId+""));
//			if(ClubUtil.checkLogin(token_time))
//				return new JsonResult(-1,"��¼�ѹ�ʱ�����µ�¼",null,"");
			if(clubId==null  || userId==null || cid==null){
				return new JsonResult("13");
			}
			//���ܻ�Ҫһ��userid����
//			System.out.println("��ɢ���ֲ�===================>"+clubId);
			RedisClub redisClub = GameUtil.getClubInfoByClubId(Cnst.get_REDIS_PREFIX_CLUBMAP(String.valueOf(cid))+clubId);
			if (null == redisClub) {// ���Ϊ�� �����ݿ��ѯ
				redisClub = clubInfoDao.findClubNewByClubId(Integer.valueOf(clubId),cid);// ���ݾ��ֲ�id��ѯ
			}
			if(redisClub==null ){
				return new JsonResult(21);
			}
			if(redisClub.getCreateId()!=userId){
				//���Ǳ���
				return new JsonResult(20);
			}
			//�鿴�ǲ��Ǳ��˴�����
			clubInfoService.deleteClub(clubId,cid);
			return new JsonResult(Cnst.SUCCESS);
		}catch(Exception e){
//			return new JsonResult("����ʧ�ܣ����Ժ�����");
			return new JsonResult("3");
		}
	}
	//���ֲ���Ա�����˳���ʾ
	@RequestMapping("/haveAction")
	@ResponseBody
	public JsonResult haveAction(Long userId,Integer cid){
		try{
//			System.out.println("���ֲ���Ա�䶯Ԥ��===================>"+userId);
			Integer user_Id = userDao.findUserId(Integer.valueOf(userId+""));
			JSONObject info = clubInfoService.haveAction(user_Id,cid);
			return new JsonResult(info);
		}catch(Exception e){
			return new JsonResult();
		}
	}
	
	



	
	
}
