package com.club.dao;

public interface UtilDao {
	//ɾ��club��Ϣ
	void deleteCheckStatus();
	void deleteRecord();
	void deleteRoom();
	void deleteInfo();
	void deleteUser();
	void deleteUse();
	//ɾ��ֱ����Ϣ
	void deleteDaiLUser();
	void deleteRecharge();
	void deleteCash();
	//ɾ����Ϸ������Ϣ
	void deleteGamePlayRecord();
	void deleteGameRoom();
	void deleteGameUser();
	void deleteGameUse();
}
