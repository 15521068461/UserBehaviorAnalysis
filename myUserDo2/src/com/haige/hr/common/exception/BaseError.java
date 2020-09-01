package com.haige.hr.common.exception;

/**
 * 	自定义错误类（类无法初始化，日志输出级别按fatal处理）
 * @author 灯芯科技 李远念
 *
 */
public class BaseError extends Error {
	/**
	 * 序列化版本UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 日志输出消息
	 */
	private String adminMessage;//如果是null，无需输出
	/**
	 * 用户提示消息
	 */
	private String userMessage;//如果是null，无需输出
	/**
	 * 错误异常 简单构造:需要用户提示、需要日志输出
	 * @param adminMessage 提供给管理员的消息，写入log
	 * @param userMessage 提供给用户的消息，用于提示
	 */
	public BaseError(String adminMessage,String userMessage) {
		super(adminMessage);
		this.adminMessage=adminMessage;
		this.userMessage=userMessage;
    }
	/**
	 * 错误异常 完整构造:需要用户提示、需要日志输出
	 * @param adminMessage 提供给管理员的消息，写入log
	 * @param userMessage 提供给用户的消息，用于提示
	 * @param ex 异常/错误
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