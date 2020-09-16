package com.haige.hr.common.exception;

/**
 * 	�Զ�������ࣨ���޷���ʼ������־�������fatal����
 * @author ��о�Ƽ� ��Զ��
 *
 */
public class BaseError extends Error {
	/**
	 * ���л��汾UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ��־�����Ϣ
	 */
	private String adminMessage;//�����null���������
	/**
	 * �û���ʾ��Ϣ
	 */
	private String userMessage;//�����null���������
	/**
	 * �����쳣 �򵥹���:��Ҫ�û���ʾ����Ҫ��־���
	 * @param adminMessage �ṩ������Ա����Ϣ��д��log
	 * @param userMessage �ṩ���û�����Ϣ��������ʾ
	 */
	public BaseError(String adminMessage,String userMessage) {
		super(adminMessage);
		this.adminMessage=adminMessage;
		this.userMessage=userMessage;
    }
	/**
	 * �����쳣 ��������:��Ҫ�û���ʾ����Ҫ��־���
	 * @param adminMessage �ṩ������Ա����Ϣ��д��log
	 * @param userMessage �ṩ���û�����Ϣ��������ʾ
	 * @param ex �쳣/����
	 */
	public BaseError(String adminMessage,String userMessage, Throwable ex) {
		super(adminMessage, ex);
		this.adminMessage=adminMessage;
		this.userMessage=userMessage;
	}
	/**
	 * @return the message
	 */
	public String getAdminMessage() {
		return adminMessage;
	}
	/**
	 * @param message the message to set
	 */
	public void setAdminMessage(String adminMessage) {
		this.adminMessage = adminMessage;
	}
	/**
	 * @return the userMessage
	 */
	public String getUserMessage() {
		return userMessage;
	}
	/**
	 * @param userMessage the userMessage to set
	 */
	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}