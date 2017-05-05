package com.wgh;

import java.util.Vector;

public class UserInfo {
	private static UserInfo user = new UserInfo();
	private Vector vector = null;

	// ����private���ù��캯������ֹ���������µ�instance����
	public UserInfo() {
		this.vector = new Vector();
	}

	// ���ʹ�õ�instance����
	public static UserInfo getInstance() {
		return user;
	}

	// �����û�
	public boolean addUser(String user) {
		if (user != null) {
			this.vector.add(user);
			return true;
		} else {
			return false;
		}
	}

	// ��ȡ�û��б�
	public Vector getList() {
		return vector;
	}

	// �Ƴ��û�
	public void removeUser(String user) {
		if (user != null) {
			vector.removeElement(user);
		}
	}
}
