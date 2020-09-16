package com.haigest.core.ret;

/**
 * @Description: 响应码枚举，参考HTTP状态码的语义：但不是HTTP状态码，不管有无异常，在发送响应对象时、HTTP状态码都是200
 * @author 灯芯科技 李远念
 * @date 2018/4/19 09:42
 */
public enum RetCode {

	// 成功
	SUCCESS(200),

	// 失败：已知异常（除了下列特殊定义、以及未知异常，都按400处理，自行传入提示信息）
	FAIL(400),

	/** 授权验证失败 */ //2 授权验证失败，用户没有权限进入，包括 接口签名验证、用户登录验证�?�业务权限验�? 等验�?
	UNAUTHORIZED(401),
	
	/** 参数校验失败 */ //3 参数校验失败，无法进入业务处�?
	UNVALIDATE(402),
	
	// 找不到资源
	NOTFOUND(404),

	// 服务器内部错误：所有未知异常
	INTERNAL_SERVER_ERROR(500);

	public int code;

	RetCode(int code) {
		this.code = code;
	}
}
