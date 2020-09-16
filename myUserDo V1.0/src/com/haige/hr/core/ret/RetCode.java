package com.haige.hr.core.ret;

/**
 * @Description: 业务响应码枚举，从后端开发角度去定义，不是HTTP状�?�码（不能按HTTP语义去理解），不管有无异常，在发送响应对象时、HTTP状�?�码都是200
 * @author 灯芯科技 李远�?
 * @date 2018/4/19 09:42
 */
public enum RetCode {

	/** 业务处理成功 */ //业务处理成功，暂不细分，如需细分�?201,202,204等码
	SUCCESS(200),
	
	/** 资源校验失败 */ //1 资源校验失败，服务器无法匹配对应请求的路径�?�类型等
	NOTFOUND(404),
	
	/** 授权验证失败 */ //2 授权验证失败，用户没有权限进入，包括 接口签名验证、用户登录验证�?�业务权限验�? 等验�?
	UNAUTHORIZED(401),
	
	/** 参数校验失败 */ //3 参数校验失败，无法进入业务处�?
	UNVALIDATE(402),

	/** 业务处理失败 */ //4 业务处理失败，经过授权验证�?�参数校验后的业务处理失败，系统无异常，主要是业务判断产生的失败
	FAIL(400),
	
	/** 系统发生异常/错误 */ //5 系统发生异常/错误，包含所有能捕捉到的已知异常、未知异常（如果捕捉机制以外的，通过HTTP状�?�码500响应�?
	ERROR(500);

	public int code;

	RetCode(int code) {
		this.code = code;
	}
}

/*
	常见状�?�码
�?	200 OK - [GET]：服务器成功返回用户请求的数据，该操作是幂等的（Idempotent）�??
�?	201 CREATED - [POST/PUT/PATCH]：用户新建或修改数据成功�?
�?	202 Accepted - [*]：表示一个请求已经进入后台排队（异步任务�?
�?	204 NO CONTENT - [DELETE]：用户删除数据成功�??
�?	400 INVALID REQUEST - [POST/PUT/PATCH]：用户发出的请求有错误，服务器没有进行新建或修改数据的操作，该操作是幂等的�??
�?	401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）�?
�?	403 Forbidden - [*] 表示用户得到授权（与401错误相对），但是访问是被禁止的�??
�?	404 NOT FOUND - [*]：用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的�?
�?	406 Not Acceptable - [GET]：用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）�??
�?	410 Gone -[GET]：用户请求的资源被永久删除，且不会再得到的�??
�?	422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误�??
�?	500 INTERNAL SERVER ERROR - [*]：服务器发生错误，用户将无法判断发出的请求是否成功�??
	更多请看 http://www.w3.org/Protocols/rfc2616/rfc2616-sec10.html
*/