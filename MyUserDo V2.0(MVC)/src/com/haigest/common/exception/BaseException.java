package com.haigest.common.exception;

/**
 * 自定义异常类
 * 
 * @author 灯芯科技 李远念
 *
 */
public class BaseException extends RuntimeException {
	/**
	 * 序列化版本UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 日志输出级别
	 */
	private String logLevel = null;// 如果是null，按error级别输出
	/**
	 * 日志输出消息
	 */
	private String adminMessage = null;// 如果是null，无需输出
	/**
	 * 用户提示消息
	 */
	private String userMessage = null;// 如果是null，无需输出

	/**
	 * 错误异常 简单构造:需要用户提示、需要日志输出
	 * 
	 * @param logLevel     日志输出级别
	 * @param adminMessage 提供给管理员的消息，写入log
	 * @param userMessage  提供给用户的消息，用于提示
	 */
	public BaseException(String logLevel, String adminMessage, String userMessage) {
		super(adminMessage);
		this.logLevel = logLevel ;
		this.adminMessage = adminMessage;
		this.userMessage = userMessage;
	}

	/**
	 * 错误异常 完整构造:需要用户提示、需要日志输出
	 * 
	 * @param logLevel     日志输出级别
	 * @param adminMessage 提供给管理员的消息，写入log
	 * @param userMessage  提供给用户的消息，用于提示
	 * @param ex           异常/错误
	 */
	public BaseException(String logLevel, String adminMessage, String userMessage, Throwable ex) {
		super(adminMessage, ex);
		this.logLevel = logLevel;
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
