package com.club.entity;

import java.io.Serializable;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.club.constant.Cnst;



public class JsonResult implements Serializable{

	/**
	 * ��Ϣ��ʽ
	 */
	private static final long serialVersionUID = 7357119924777596989L;
	private int state;//״̬
	private String message;//������Ϣ state = Error
	private JSONObject info;//������Ϣ
	private String others;//������Ϣ
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public JSONObject getInfo() {
		return info;
	}
	public void setInfo(JSONObject info) {
		this.info = info;
	}
	public String getOthers() {
		return others;
	}
	public void setOthers(String others) {
		this.others = others;
	}
	@Override
	public String toString() {
		return "JsonResult [state=" + state + ", message=" + message
				+ ", info=" + info + ", others=" + others
				+ "]";
	}
	public JsonResult(int state, String message, JSONObject info,
			String others) {
		super();
		this.state = state;
		this.message = message;
		this.info = info;
		this.others = others;
	}
	public JsonResult(){

	}
	//Ϊ�˷��㣬����N��������
	public JsonResult(
			int state, String message, JSONObject info) {
		this.state = state;
		this.message = message;
		this.info = info;
		
	}
	
	public JsonResult(String message){
		this(Cnst.ERROR, message,null,"" );
	}
	
	public JsonResult(JSONObject info){
		this(Cnst.SUCCESS, "", info,"");
	}
	
	public JsonResult(Throwable e){
		this(Cnst.ERROR, e.getMessage(), null,"");
	}

	public JsonResult(int state){
		this(state,"",null,"");
	}
}
