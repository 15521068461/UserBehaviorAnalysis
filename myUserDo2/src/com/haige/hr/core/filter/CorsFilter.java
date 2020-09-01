package com.haige.hr.core.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class CorsFilter
 */
@WebFilter("/*")
public class CorsFilter implements Filter {

    /**
     * Default constructor. 
     */
    public CorsFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse res=(HttpServletResponse)response;
//		/* 允许跨域的主机地址 */
		res.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
//		/* 允许跨域的请求方法GET, POST, HEAD 等 */
		res.setHeader("Access-Control-Allow-Methods", "POST,GET,PUT,DELETE,OPTION");
//		/* 重新预检验跨域的缓存时间 (s) */
//		res.setHeader("Access-Control-Max-Age", "3600");
//		/* 允许跨域的请求头 	*/
		res.setHeader("Access-Control-Allow-Headers", "User-Agent,Origin,Cache-Control,Content-type,x-zd,Date,Server,withCredentials,Authorization,Origin,X-Requested-With,Content-Type,Accept,"
                + "content-Type,origin,x-requested-with,content-type,accept,authorization,token,id,X-Custom-Header,X-Cookie,Connection,User-Agent,Cookie,*");
//		/* 是否携带cookie */
		res.setHeader("Access-Control-Allow-Credentials", "true");
		
		if (req.getMethod().equals("OPTIONS")) {
			res.setStatus(200);
            return;
        }
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
