package com.club.entity;

public class RedisClub {
		private Integer id;
		private Integer cid;//���ֲ�cid
		private Integer clubId;//���ֲ�id
		private String clubName;//���ֲ�����
		private Long createId;//������
		private Integer personQuota;//�������޶�
		private Integer roomCardNum;//������
		private Integer roomCardQuota;//�����޶�
		private Integer	roomCardNotice;//��������Ԥ����
		private Long createTime;//����ʱ��
		private Long freeStart;//���ֲ����⿪ʼʱ��
		private Long freeEnd;//���ֲ��������ʱ��
		/**************���������ݿ��******************/
//		private String createName;//����������
//		private Integer userNum;//���ֲ���ǰ����
//		private Integer usedCard;//�������ķ�����
		
		
		public Integer getId() {
			return id;
		}
		public Integer getCid() {
			return cid;
		}
		public void setCid(Integer cid) {
			this.cid = cid;
		}
		public Long getFreeStart() {
			return freeStart;
		}
		public void setFreeStart(Long freeStart) {
			this.freeStart = freeStart;
		}
		public Long getFreeEnd() {
			return freeEnd;
		}
		public void setFreeEnd(Long freeEnd) {
			this.freeEnd = freeEnd;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public Integer getClubId() {
			return clubId;
		}
		public void setClubId(Integer clubId) {
			this.clubId = clubId;
		}
		public String getClubName() {
			return clubName;
		}
		public void setClubName(String clubName) {
			this.clubName = clubName;
		}
		public Long getCreateId() {
			return createId;
		}
		public void setCreateId(Long createId) {
			this.createId = createId;
		}
//		public String getCreateName() {
//			return createName;
//		}
//		public void setCreateName(String createName) {
//			this.createName = createName;
//		}
		public Integer getPersonQuota() {
			return personQuota;
		}
		public void setPersonQuota(Integer personQuota) {
			this.personQuota = personQuota;
		}
		public Integer getRoomCardNum() {
			return roomCardNum;
		}
		public void setRoomCardNum(Integer roomCardNum) {
			this.roomCardNum = roomCardNum;
		}
		public Integer getRoomCardQuota() {
			return roomCardQuota;
		}
		public void setRoomCardQuota(Integer roomCardQuota) {
			this.roomCardQuota = roomCardQuota;
		}
		public Integer getRoomCardNotice() {
			return roomCardNotice;
		}
		public void setRoomCardNotice(Integer roomCardNotice) {
			this.roomCardNotice = roomCardNotice;
		}
		public Long getCreateTime() {
			return createTime;
		}
		public void setCreateTime(Long createTime) {
			this.createTime = createTime;
		}
//		public Integer getUserNum() {
//			return userNum;
//		}
//		public void setUserNum(Integer userNum) {
//			this.userNum = userNum;
//		}
//		public Integer getUsedCard() {
//			return usedCard;
//		}
//		public void setUsedCard(Integer usedCard) {
//			this.usedCard = usedCard;
//		}
		
		
	}


