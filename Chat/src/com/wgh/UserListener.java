package com.wgh;

import javax.servlet.http.HttpSessionBindingEvent;

public class UserListener implements
		javax.servlet.http.HttpSessionBindingListener {
	private String user;
	private UserInfo container = UserInfo.getInstance();

	public UserListener() {
		user = "";
	}

	// �������߼�����Ա
	public void setUser(String user) {
		this.user = user;
	}

	// ��ȡ���߼���
	public String getUser() {
		return this.user;
	}

	// ��Session�ж������ʱִ�еķ���
	public void valueBound(HttpSessionBindingEvent arg0) {
		System.out.println("�����û���" + this.user);

	}

	// ��Session�ж����Ƴ�ʱִ�еķ���
	public void valueUnbound(HttpSessionBindingEvent arg0) {
		System.out.println("�����û���" + this.user);
		MessagesAction message=new MessagesAction();//ʵ����MessagesAction�Ķ���
		message.writeInfo(this.user,arg0);	//���û�������Ϣд��ϵͳ����
		if (user != "") {
			container.removeUser(user);
		}
	}
}
