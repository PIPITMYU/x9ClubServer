package com.club.entity;

import java.io.Serializable;
import java.util.List;

public class ClubInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 740857240407272929L;
	private int ID;
	private int CLUB_ID;//���ֲ�id
	private int CID;//���ֲ�����������cid
	private String CLUB_NAME;//���ֲ�����
	private int CREATE_ID;//������id
	private int ROOM_CARD_NUM;//�������
	private int ROOM_CARD_QUOTA;//�������޶�
	private int ROOM_CARD_NOTICE;//�������Ԥ��ֵ
	private Long CREATE_TIME;//����ʱ��
	private int PERSON_QUOTA;//��������
	private Long FREE_START;//���⿪ʼʱ��
	private Long FREE_END;//�������ʱ��
	
	
	public int getCID() {
		return CID;
	}
	public void setCID(int cID) {
		CID = cID;
	}
	public Long getFREE_START() {
		return FREE_START;
	}
	public void setFREE_START(Long fREE_START) {
		FREE_START = fREE_START;
	}
	public Long getFREE_END() {
		return FREE_END;
	}
	public void setFREE_END(Long fREE_END) {
		FREE_END = fREE_END;
	}
	public ClubInfo(){
		
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getCLUB_ID() {
		return CLUB_ID;
	}
	public void setCLUB_ID(int cLUB_ID) {
		CLUB_ID = cLUB_ID;
	}
	public String getCLUB_NAME() {
		return CLUB_NAME;
	}
	public void setCLUB_NAME(String cLUB_NAME) {
		CLUB_NAME = cLUB_NAME;
	}
	public int getCREATE_ID() {
		return CREATE_ID;
	}
	public void setCREATE_ID(int cREATE_ID) {
		CREATE_ID = cREATE_ID;
	}
	public int getROOM_CARD_NUM() {
		return ROOM_CARD_NUM;
	}
	public void setROOM_CARD_NUM(int rOOM_CARD_NUM) {
		ROOM_CARD_NUM = rOOM_CARD_NUM;
	}
	public int getROOM_CARD_QUOTA() {
		return ROOM_CARD_QUOTA;
	}
	public void setROOM_CARD_QUOTA(int rOOM_CARD_QUOTA) {
		ROOM_CARD_QUOTA = rOOM_CARD_QUOTA;
	}
	public int getROOM_CARD_NOTICE() {
		return ROOM_CARD_NOTICE;
	}
	public void setROOM_CARD_NOTICE(int rOOM_CARD_NOTICE) {
		ROOM_CARD_NOTICE = rOOM_CARD_NOTICE;
	}
	public Long getCREATE_TIME() {
		return CREATE_TIME;
	}
	public void setCREATE_TIME(Long cREATE_TIME) {
		CREATE_TIME = cREATE_TIME;
	}
	public int getPERSON_QUOTA() {
		return PERSON_QUOTA;
	}
	public void setPERSON_QUOTA(int pERSON_QUOTA) {
		PERSON_QUOTA = pERSON_QUOTA;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + CLUB_ID;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClubInfo other = (ClubInfo) obj;
		if (CLUB_ID != other.CLUB_ID)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ClubInfo [ID=" + ID + ", CLUB_ID=" + CLUB_ID + ", CLUB_NAME="
				+ CLUB_NAME + ", CREATE_ID=" + CREATE_ID + ", ROOM_CARD_NUM="
				+ ROOM_CARD_NUM + ", ROOM_CARD_QUOTA=" + ROOM_CARD_QUOTA
				+ ", ROOM_CARD_NOTICE=" + ROOM_CARD_NOTICE + ", CREATE_TIME="
				+ CREATE_TIME + ", PERSON_QUOTA=" + PERSON_QUOTA + "]";
	}
	public ClubInfo(int iD, int cLUB_ID, String cLUB_NAME, int cREATE_ID,
			int rOOM_CARD_NUM, int rOOM_CARD_QUOTA, int rOOM_CARD_NOTICE,
			Long cREATE_TIME, int pERSON_QUOTA) {
		super();
		ID = iD;
		CLUB_ID = cLUB_ID;
		CLUB_NAME = cLUB_NAME;
		CREATE_ID = cREATE_ID;
		ROOM_CARD_NUM = rOOM_CARD_NUM;
		ROOM_CARD_QUOTA = rOOM_CARD_QUOTA;
		ROOM_CARD_NOTICE = rOOM_CARD_NOTICE;
		CREATE_TIME = cREATE_TIME;
		PERSON_QUOTA = pERSON_QUOTA;
	}
	

	
}
