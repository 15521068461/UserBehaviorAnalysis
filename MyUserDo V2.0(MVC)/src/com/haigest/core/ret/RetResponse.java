package com.haigest.core.ret;

/**
 * @Description: 将结果转换为封装后的对象
 * @author 灯芯科技 李远念
 * @date 2018/4/19 09:45
 */
public class RetResponse {

	private final static String SUCCESS = "成功";
	private final static String NOTFOUND = "找不到资源";
	private final static String INTERNAL_SERVER_ERROR = "服务器内部错误";

	public static <T> RetResult<T> makeOKRsp() {
		return new RetResult<T>().setCode(RetCode.SUCCESS).setMsg(SUCCESS);
	}

	/** 成功，无数据返回，自定义消息 */
	public static <T> RetResult<T> makeOKRspWithMsg(String msg) {
		return new RetResult<T>().setCode(RetCode.SUCCESS).setMsg(msg);
	}
	
	public static <T> RetResult<T> makeOKRsp(T data) {
		return new RetResult<T>().setCode(RetCode.SUCCESS).setMsg(SUCCESS).setData(data);
	}

	/** 成功，有数据返回，自定义消息 */
	public static <T> RetResult<T> makeOKRspWithMsg(String msg,T data) {
		return new RetResult<T>().setCode(RetCode.SUCCESS).setMsg(msg).setData(data);
	}
	
	public static <T> RetResult<T> makeFailRsp(String message) {
		return new RetResult<T>().setCode(RetCode.FAIL).setMsg(message);
	}
	
	/** 失败-校验失败，无数据返回，自定义消息 */
	public static <T> RetResult<T> makeUnvalidateRsp(String message) {
		return new RetResult<T>().setCode(RetCode.UNVALIDATE).setMsg(message);
	}
	
	/** 失败-授权失败，无数据返回，自定义消息 */
	public static <T> RetResult<T> makeUnauthorizedRsp(String message) {
		return new RetResult<T>().setCode(RetCode.UNAUTHORIZED).setMsg(message);
	}
	
	public static <T> RetResult<T> makeNotfoundRsp() {
		return new RetResult<T>().setCode(RetCode.NOTFOUND).setMsg(NOTFOUND);
	}

	public static <T> RetResult<T> makeErrorRsp() {
		return new RetResult<T>().setCode(RetCode.INTERNAL_SERVER_ERROR).setMsg(INTERNAL_SERVER_ERROR);
	}
	
	public static <T> RetResult<T> makeRsp(int code, String msg) {
		return new RetResult<T>().setCode(code).setMsg(msg);
	}
	
	public static <T> RetResult<T> makeRsp(int code, String msg, T data) {
		return new RetResult<T>().setCode(code).setMsg(msg).setData(data);
	}
}
