package com.haigest.xingwei.controller;

import java.util.List;

import com.haigest.core.ajax.AjaxJsonUtil;
import com.haigest.core.mvc.Controller;
import com.haigest.core.mvc.URLMapping;
import com.haigest.core.ret.RetResponse;
import com.haigest.core.ret.RetResult;
import com.haigest.xingwei.controller.param.AddLabelParam;
import com.haigest.xingwei.controller.param.UpdataParam;
import com.haigest.xingwei.service.SystemService;
import com.haigest.xingwei.service.bean.VO4Label;


@URLMapping(url = "/auth/system/label")
public class SystemController extends Controller {
	private SystemService SystemService = new SystemService();
	
	
	 //添加标签
	@URLMapping(url = "/add")
	public RetResult<?> doAdd(){
		// 1.获取参数
				String tokenValue = request.getHeader("token");

				AddLabelParam addLabelParam = AjaxJsonUtil.parsePayload2Object(request, AddLabelParam.class);

				String labelName = addLabelParam.getLabelName();
				String labelExplain = addLabelParam.getLabelExplain();
				// 2.参数校验
				if (labelName == null || labelName.isEmpty()) {
					return RetResponse.makeUnvalidateRsp("参数校验失败：标签名必填");
				}
				// 3.业务调用
				if (SystemService.addLabel(Long.valueOf(tokenValue), labelName, labelExplain)) {
					return RetResponse.makeOKRsp();
				
				} else {
					return RetResponse.makeFailRsp("失败，标签名已存在");
				}
	} 
	
	//更新标签
	@URLMapping(url = "/update")
	public RetResult<?> doUpdate(){
		// 1.获取参数

				UpdataParam updataParam = AjaxJsonUtil.parsePayload2Object(request, UpdataParam.class);

				long id = updataParam.getId();
				String labelExplain = updataParam.getLabelExplain();
				String tokenValue = request.getHeader("token");

				// 2.参数校验
				// 3.业务调用
				SystemService.updateLabel(Long.valueOf(tokenValue), labelExplain, id);
				return RetResponse.makeOKRsp();
				
	}
	 //删除标签
	@URLMapping(url = "/delete")
	public RetResult<?> doDelete(){
		// 1.获取参数
				String tokenValue = request.getHeader("token");
				String id = request.getParameter("id");

				// 2.参数校验
				// 3.业务调用
				if (SystemService.deleteLabel(Long.valueOf(tokenValue), Long.valueOf(id))) {
					return RetResponse.makeOKRsp();
				} else {
					return RetResponse.makeUnauthorizedRsp("业务处理失败：获取权限失败");
				}
	}
	
	//查看标签列表（或者单个标签）
	@URLMapping(url = "/getone")
	public RetResult<?> doGetone(){
		// 1.参数获取
				String tokenValue = request.getHeader("token");
				String id = request.getParameter("id");
				// 2.参数校验
				// 3.调用
				if (id != null) {// 项目V.0中并没有使用这个方法里的接口
					VO4Label rtn = SystemService.showOneLabel(Long.valueOf(tokenValue), Long.valueOf(id));
					if (rtn == null) {
						return RetResponse.makeFailRsp("业务处理失败：获取失败");
					} else {
						return RetResponse.makeOKRspWithMsg("查询基本信息成功", rtn);
					}
				} else {
					List<VO4Label> t = SystemService.showLabelInfo();
					if (t.size() == 0) {
						return  RetResponse.makeUnauthorizedRsp("业务处理失败：获取权限失败");
					} else {
						System.out.println(t.get(0).getDataUpdateAt()+"还是之后");
						return RetResponse.makeOKRspWithMsg("查询基本a信息成功", t);
						
					}
				}
	}
}
