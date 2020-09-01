package com.haige.hr.core.ret;

/**
 * @Description: 将结果转换为封装后的对象
 * @author 灯芯科技 李远�?
 * @date 2018/4/19 09:45
 */
public class RetResponse {

	/** 对应200业务响应码的消息，其它响应码�?要自行定义消�? */
	private final static String SUCCESS = "处理成功";
	
	/** 成功，无数据返回 */
	public static <T> RetResult<T> makeOKRsp() {
		return new RetResult<T>().setCode(RetCode.SUCCESS).setMsg(SUCCESS);
	}
	
	/** 成功，无数据返回，自定义消息 */
	public static <T> RetResult<T> makeOKRspWithMsg(String msg) {
		return new RetResult<T>().setCode(RetCode.SUCCESS).setMsg(msg);
	}
	
	/** 成功，有数据返回 */
	public static <T> RetResult<T> makeOKRsp(T data) {
		return new RetResult<T>().setCode(RetCode.SUCCESS).setMsg(SUCCESS).setData(data);
	}
	
	/** 成功，有数据返回，自定义消息 */
	public static <T> RetResult<T> makeOKRspWithMsg(String msg,T data) {
		return new RetResult<T>().setCode(RetCode.SUCCESS).setMsg(msg).setData(data);
	}
	
	/** 失败-处理失败，无数据返回，自定义消息 */
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
	
	/** 失败-资源不存在，无数据返回，自定义消�? */
	public static <T> RetResult<T> makeNotfoundRsp(String message) {
		return new RetResult<T>().setCode(RetCode.NOTFOUND).setMsg(message);
	}
	
	/** 失败-系统错误，无数据返回，自定义消息 */
	public static <T> RetResult<T> makeErrorRsp(String message) {
		return new RetResult<T>().setCode(RetCode.ERROR).setMsg(message);
	}
	
	/** 自定义返�?  */
	public static <T> RetResult<T> makeRsp(int code, String msg, T data) {
		return new RetResult<T>().setCode(code).setMsg(msg).setData(data);
	}
	
	/** 自定义返回，无数据返�?  */
	public static <T> RetResult<T> makeRsp(int code, String msg) {
		return new RetResult<T>().setCode(code).setMsg(msg);
	}
}
