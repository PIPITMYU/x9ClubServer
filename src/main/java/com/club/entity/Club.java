package com.club.entity;

import java.util.List;

public class Club {
	//��װredis���ֲ���Ϣ
	private String name;//����
	private int id;//��ʼ��id
	private int sum;//������
	private int num;//������
	private String code;//��ά��
	private int quota;//���������޶�
	private int notice;//����Ԥ����
	private List<Integer> members;//�����Ա
	private int max;//��Ա����
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getSum() {
		return sum;
	}
	public void setSum(int sum) {
		this.sum = sum;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public int getQuota() {
		return quota;
	}
	public void setQuota(int quota) {
		this.quota = quota;
	}
	public int getNotice() {
		return notice;
	}
	public void setNotice(int notice) {
		this.notice = notice;
	}
	public List<Integer> getMembers() {
		return members;
	}
	public void setMembers(List<Integer> members) {
		this.members = members;
	}
	public int getMax() {
		return max;
	}
	public void setMax(int max) {
		this.max = max;
	}
	public Club(String name, int id, int sum, int num, String code, int quota,
			int notice, List<Integer> members, int max) {
		super();
		this.name = name;
		this.id = id;
		this.sum = sum;
		this.num = num;
		this.code = code;
		this.quota = quota;
		this.notice = notice;
		this.members = members;
		this.max = max;
	}
	public Club(){
		
	}
	@Override
	public String toString() {
		return "Club [name=" + name + ", id=" + id + ", sum=" + sum + ", num="
				+ num + ", code=" + code + ", quota=" + quota + ", notice="
				+ notice + ", members=" + members + ", max=" + max + "]";
	}
	
}
