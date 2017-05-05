package com.wgh;

import java.io.*;
import java.text.*;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.wgh.StringUtils;

public class MessagesAction extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		String action = request.getParameter("action");
		if ("getMessages".equals(action)) { // ��XML�ļ��ж�ȡ������Ϣ
			this.getMessages(request, response);
		} else if ("sendMessage".equals(action)) { // ����������Ϣ
			this.sendMessages(request, response);
		} else if ("loginRoom".equals(action)) { // ��¼ʱ��д��ϵͳ����
			this.loginRoom(request, response);
		} else if ("exitRoom".equals(action)) {
			this.exitRoom(request, response); // �˳�ʱ��д��ϵͳ����
		}
	}

	// ��ȡ����������Ϣ��XML�ļ�
	public void getMessages(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=GBK");
		String newTime = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String fileURL = request.getSession().getServletContext().getRealPath(
				"xml/" + newTime + ".xml");
		createFile(fileURL); // ���ļ�������ʱ�������ļ�
		/******************* ��ʼ���������������ݵ�XML�ļ� **********************/
		try {
			SAXBuilder builder = new SAXBuilder();
			Document feedDoc = builder.build(new File(fileURL));
			Element root = feedDoc.getRootElement(); // ��ȡ���ڵ�
			Element channel = root.getChild("messages"); // ��ȡmessages�ڵ�
			Iterator items = channel.getChildren("message").iterator(); // ��ȡmessage�ڵ�
			String messages = "";
			// ��ȡ��ǰ�û�
			HttpSession session = request.getSession();
			String userName = "";
			if (null == session.getAttribute("username")) {
				request.setAttribute("messages", "error"); // ��������Ϣ����ʾ�û��˻��Ѿ�����
			} else {
				userName = session.getAttribute("username").toString();
				DateFormat df = DateFormat.getDateTimeInstance();
				while (items.hasNext()) {
					Element item = (Element) items.next();
					String sendTime = item.getChildText("sendTime"); // ��ȡ����ʱ��
					try {
						if (df.parse(sendTime).after(
								df.parse(session.getAttribute("loginTime")
										.toString()))
								|| sendTime.equals(session.getAttribute(
										"loginTime").toString())) {
							String from = item.getChildText("from"); // ��ȡ������
							String face = item.getChildText("face"); // ��ȡ����
							String to = item.getChildText("to"); // ��ȡ������
							String content = item.getChildText("content"); // ��ȡ��������
							boolean isPrivate = Boolean.valueOf(item
									.getChildText("isPrivate"));
							if (isPrivate) { // ��ȡ˽������
								if (userName.equals(to)
										|| userName.equals(from)) {
									messages += "<font color='red'>[˽�˶Ի�]</font><font color='blue'><b>"
											+ from
											+ "</b></font><font color='#CC0000'>"
											+ face
											+ "</font>��<font color='green'>["
											+ to
											+ "]</font>˵��"
											+ content
											+ "&nbsp;<font color='gray'>["
											+ sendTime + "]</font><br>";
								}
							} else if ("[ϵͳ����]".equals(from)) { // ��ȡϵͳ������Ϣ
								messages += "[ϵͳ����]��" + content
										+ "&nbsp;<font color='gray'>["
										+ sendTime + "]</font><br>";
							} else { // ��ȡ��ͨ������Ϣ
								messages += "<font color='blue'><b>" + from
										+ "</b></font><font color='#CC0000'>"
										+ face
										+ "</font>��<font color='green'>[" + to
										+ "]</font>˵��" + content
										+ "&nbsp;<font color='gray'>["
										+ sendTime + "]</font><br>";
							}
						}
					} catch (Exception e) {
						System.out.println("" + e.getMessage());
					}
				}
				request.setAttribute("messages", messages); // �����ȡ��������Ϣ
			}
			request.getRequestDispatcher("content.jsp").forward(request,
					response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ��¼ʱ��д��ϵͳ����

	public void loginRoom(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=GBK");
		HttpSession session = request.getSession();
		StringUtils su = new StringUtils();
		String username = su.toGBK(request.getParameter("username")); // ��õ�¼�û���
		UserInfo user = UserInfo.getInstance(); // ���UserInfo��Ķ���
		session.setMaxInactiveInterval(600); // ����Session�Ĺ���ʱ��Ϊ10����
		Vector vector = user.getList();
		boolean flag = true; // ����Ƿ��¼�ı���
		// �ж��û��Ƿ��¼
		if (vector != null && vector.size() > 0) {
			if (vector.contains(username)) {
				PrintWriter out;
				try {
					out = response.getWriter();
					out
							.println("<script language='javascript'>alert('���û��Ѿ���¼');window.location.href='index.jsp';</script>");
				} catch (IOException e) {
					e.printStackTrace();
				}
				flag = false;
			}
		}
		// �����û���Ϣ
		if (flag) {
			UserListener ul = new UserListener(); // ����UserListener�Ķ���
			ul.setUser(username); // ����û�
			user.addUser(ul.getUser()); // ����û���UserInfo��Ķ�����
			session.setAttribute("user", ul); // ��UserListener����󶨵�Session��
			session.setAttribute("username", username); // ���浱ǰ��¼���û���
			session.setAttribute("loginTime", new Date().toLocaleString()); // �����¼ʱ��
			/** *******************��ʼϵͳ����********************************** */
			String newTime = new SimpleDateFormat("yyyyMMdd")
					.format(new Date());
			String fileURL = request.getSession().getServletContext()
					.getRealPath("xml/" + newTime + ".xml");
			createFile(fileURL); // ���ļ�������ʱ�������ļ�
			// ��ȡ��ǰ�û�
			SAXBuilder builder = new SAXBuilder();
			try {
				Document feedDoc = builder.build(new File(fileURL));

				Element root = feedDoc.getRootElement();
				Element channel = root.getChild("messages");
				Element newNode = new Element("message");
				channel.addContent(newNode); // ����messages�ڵ�
				Element fromNode = new Element("from").setText("[ϵͳ����]");
				newNode.addContent(fromNode);
				Element faceNode = new Element("face").setText("");
				newNode.addContent(faceNode);
				Element toNode = new Element("to").setText("");
				newNode.addContent(toNode);
				Element contentNode = new Element("content")
						.setText("<font color='gray'>" + username
								+ "�߽��������ң�</font>");
				newNode.addContent(contentNode);
				// ��¼ʱ��
				Element sendTimeNode = new Element("sendTime")
						.setText(new Date().toLocaleString());
				newNode.addContent(sendTimeNode);
				Element isPrivateNode = new Element("isPrivate")
						.setText("false");
				newNode.addContent(isPrivateNode);
				request.getRequestDispatcher("login_ok.jsp").forward(request,
						response);
				XMLOutputter xml = new XMLOutputter(Format.getPrettyFormat());
				xml.output(feedDoc, new FileOutputStream(fileURL));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	// �˳�ʱ��д��ϵͳ����
	public void exitRoom(HttpServletRequest request,
			HttpServletResponse response) {
		HttpSession session = request.getSession();// ��ȡHttpSession
		session.invalidate(); // ����session
		try {
			request.getRequestDispatcher("index.jsp")
					.forward(request, response);// �ض���ҳ�浽��¼ҳ��
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ���û�����ʱ��XML�ļ���д��ϵͳ����
	public void writeInfo(String user, HttpSessionBindingEvent arg0) {
		HttpSession session = arg0.getSession();	//��ȡHttpSession
		String newTime = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String fileURL = session.getServletContext().getRealPath(
				"xml/" + newTime + ".xml");	//��ȡ�����������ݵ�XML�ļ����ļ���
		createFile(fileURL);// �ж�XML�ļ��Ƿ���ڣ�����������򴴽����ļ�
		// ��ȡ��ǰ�û�
		SAXBuilder builder = new SAXBuilder();
		try {
			Document feedDoc = builder.build(new File(fileURL));
			Element root = feedDoc.getRootElement();	//��ȡ���ڵ�
			Element channel = root.getChild("messages");	//��ȡmessages�ڵ�
			Element newNode = new Element("message");	//��ȡmessage�ڵ�
			channel.addContent(newNode); // ����messages�ڵ�
			Element fromNode = new Element("from").setText("[ϵͳ����]");//��ӷ������ӽڵ�
			newNode.addContent(fromNode);
			Element faceNode = new Element("face").setText("");//��ӱ����ӽڵ�
			newNode.addContent(faceNode);
			Element toNode = new Element("to").setText("");//��ӽ������ӽڵ�
			newNode.addContent(toNode);
			Element contentNode = new Element("content")
					.setText("<font color='gray'>" + user + "�뿪�����ң�</font>");//������������ӽڵ�
			newNode.addContent(contentNode);
			// �˳�ʱ��
			Element sendTimeNode = new Element("sendTime").setText(new Date()//��ӷ���ʱ���ӽڵ�
					.toLocaleString());
			newNode.addContent(sendTimeNode);
			Element isPrivateNode = new Element("isPrivate").setText("false");//����Ƿ�Ϊ���Ļ��ӽڵ�
			newNode.addContent(isPrivateNode);
			XMLOutputter xml = new XMLOutputter(Format.getPrettyFormat());
			xml.output(feedDoc, new FileOutputStream(fileURL));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ����������Ϣ
	public void sendMessages(HttpServletRequest request,
			HttpServletResponse response) {
		response.setContentType("text/html;charset=GBK");
		StringUtils su = new StringUtils();
		Random random = new Random();
		String from = su.toUTF8(request.getParameter("from")); // ������
		String face = su.toUTF8(request.getParameter("face")); // ����
		String to = su.toUTF8(request.getParameter("to")); // ������
		String color = request.getParameter("color"); // ������ɫ
		String content = su.toUTF8(request.getParameter("content")); // ��������
		String isPrivate = request.getParameter("isPrivate"); // �Ƿ�Ϊ���Ļ�
		String sendTime = new Date().toLocaleString(); // ����ʱ��
		/** *******************��ʼ���������Ϣ********************************** */
		String newTime = new SimpleDateFormat("yyyyMMdd").format(new Date());
		String fileURL = request.getSession().getServletContext().getRealPath(
				"xml/" + newTime + ".xml");
		createFile(fileURL); // �ж��ļ��Ƿ���ڣ����ļ�������ʱ�������ļ�
		SAXBuilder builder = new SAXBuilder();
		Document feedDoc;
		try {
			feedDoc = builder.build(new File(fileURL));

			Element root = feedDoc.getRootElement();
			Element channel = root.getChild("messages");
			Element newNode = new Element("message");
			channel.addContent(newNode); // ����messages�ڵ�
			Element fromNode = new Element("from").setText(from);
			newNode.addContent(fromNode); // ��ӷ������ӽڵ�
			Element faceNode = new Element("face").setText(face);
			newNode.addContent(faceNode); // ��ӱ����ӽڵ�
			Element toNode = new Element("to").setText(to);
			newNode.addContent(toNode); // ��ӽ������ӽڵ�
			Element contentNode = new Element("content")
					.setText("<font color='" + color + "'>" + content
							+ "</font>");
			newNode.addContent(contentNode); // ������������ӽڵ�
			// System.out.println("���͵���Ϣ��"+from+face+to+content);
			// ����ʱ��
			Element sendTimeNode = new Element("sendTime").setText(sendTime);
			newNode.addContent(sendTimeNode); // ��ӷ���ʱ���ӽڵ�
			Element isPrivateNode = new Element("isPrivate").setText(isPrivate);
			newNode.addContent(isPrivateNode); // ����Ƿ�Ϊ���Ļ��ӽڵ�
			request.getRequestDispatcher(
					"MessagesAction?action=getMessages&nocache="
							+ random.nextInt(10000)).forward(request, response);
			XMLOutputter xml = new XMLOutputter(Format.getPrettyFormat());
			xml.output(feedDoc, new FileOutputStream(fileURL));

			/** *****************��ӽ���******************************* */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ����������������XML�ļ��������жϸ��ļ��Ƿ���ڣ���������ڽ��������ļ�
	public void createFile(String fileURL) {
		/** **************�ж�XML�ļ��Ƿ���ڣ�����������򴴽����ļ�********** */
		File file = new File(fileURL);
		if (!file.exists()) { // �ж��ļ��Ƿ���ڣ���������ڣ��򴴽����ļ�
			try {
				file.createNewFile(); // �����ļ�
				String dataStr = "<?xml version=\"1.0\" encoding=\"GBK\"?>\r\n";
				dataStr = dataStr + "<chat>\r\n";
				dataStr = dataStr + "<messages></messages>";
				dataStr = dataStr + "</chat>\r\n";
				byte[] content = dataStr.getBytes();
				FileOutputStream fout = new FileOutputStream(file);
				fout.write(content); // ������д�������
				fout.flush(); // ˢ�»�����
				fout.close(); // �ر������
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void destroy() {
		super.destroy();
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
