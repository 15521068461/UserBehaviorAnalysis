package com.haigest.core.ret;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;

/**
 * @Description: 返回对象实体
 * @author 灯芯科技 李远念
 * @date 2018/4/19 09:43
 */
//@ApiModel(value="RetResult",description="统一返回对象")
public class RetResult<T> implements Serializable {

	private static final long serialVersionUID = 3758864789222317092L;
	
	//@ApiModelProperty(value="状态码")
	public int code;
	//@ApiModelProperty(value="响应信息")
	private String msg;
	//@ApiModelProperty(value="返回数据")
	private T data;

	public RetResult<T> setCode(RetCode retCode) {
		this.code = retCode.code;
		return this;
	}

	public int getCode() {
		return code;
	}

	public RetResult<T> setCode(int code) {
		this.code = code;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public RetResult<T> setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public T getData() {
		return data;
	}

	public RetResult<T> setData(T data) {
		this.data = data;
		return this;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}
}