package com.haigest.core.mvc;
/**
 *    用来存储URL到JAVA后台方法对应关系的基础数据类型
 * @author 灯芯科技 李远念
 *
 */
public class MVCBase {
	private String url; //我们将来要访问的URL
    private String controller; //这个URL对应的后台JAVA类
    private String method; //这个URL对应的后台方法名称
        
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getController() {
        return controller;
    }
    public void setController(String controller) {
        this.controller = controller;
    }
    public String getMethod() {
        return method;
    }
    public void setMethod(String method) {
        this.method = method;
    } 
}
