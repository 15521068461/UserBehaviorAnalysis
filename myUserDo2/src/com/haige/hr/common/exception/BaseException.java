package com.haige.hr.common.exception;

/**
 * �Զ����쳣��
 * 
 * @author ��о�Ƽ� ��Զ��
 *
 */
public class BaseException extends RuntimeException {
	/**
	 * ���л��汾UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ��־�������
	 */
	private String logLevel = null;// �����null����error�������
	/**
	 * ��־�����Ϣ
	 */
	private String adminMessage = null;// �����null���������
	/**
	 * �û���ʾ��Ϣ
	 */
	private String userMessage = null;// �����null���������

	/**
	 * �����쳣 �򵥹���:��Ҫ�û���ʾ����Ҫ��־���
	 * 
	 * @param logLevel     ��־�������
	 * @param adminMessage �ṩ������Ա����Ϣ��д��log
	 * @param userMessage  �ṩ���û�����Ϣ��������ʾ
	 */
	public BaseException(String logLevel, String adminMessage, String userMessage) {
		super(adminMessage);//���Ƶ���Ϣ��Ϣ�����������
		this.logLevel = logLevel;
		this.adminMessage = adminMessage;
		this.userMessage = userMessage;
	}

	/**
	 * �����쳣 ��������:��Ҫ�û���ʾ����Ҫ��־���
	 * 
	 * @param logLevel     ��־�������
	 * @param adminMessage �ṩ������Ա����Ϣ��д��log
	 * @param userMessage  �ṩ���û�����Ϣ��������ʾ
	 * @param ex           �쳣/����
	 */
	public BaseException(String logLevel, String adminMessage, String userMessage, Throwable ex) {
		super(adminMessage, ex);
		this.logLevel= logLevel ; 
		this.adminMessage = adminMessage;
		this.userMessage = userMessage;
	}

	public String getAdminMessage() {
		return adminMessage;
	}

	public void setAdminMessage(String adminMessage) {
		this.adminMessage = adminMessage;
	}

	public String getUserMessage() {
		return userMessage;
	}

	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getLogLevel() {
		return logLevel;
	}

	public void setLogLevel(String logLevel) {
		this.logLevel = logLevel;
	}
}