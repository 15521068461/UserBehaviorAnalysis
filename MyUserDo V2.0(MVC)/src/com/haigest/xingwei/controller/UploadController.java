package com.haigest.xingwei.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.haigest.core.ajax.AjaxJsonUtil;
import com.haigest.core.mvc.Controller;
import com.haigest.core.mvc.URLMapping;
import com.haigest.xingwei.service.UploadService;
import com.haigest.xingwei.service.UserService;


@URLMapping(url = "/open/common/upload")
public class UploadController extends Controller {
	
	private UserService userService = new UserService();
	private UploadService uploadService = new UploadService();
    // 上传文件存储目录
    private static final String UPLOAD_DIRECTORY = "upload";
 
    // 上传配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
	
    /**
     * 上传数据及保存文件
     */
    @URLMapping()
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {
        // 检测是否为多媒体上传
        if (!ServletFileUpload.isMultipartContent(request)) {
            // 如果不是则停止
            PrintWriter writer = response.getWriter();
            writer.println("Error: 表单必须包含 enctype=multipart/form-data");
            writer.flush();
            return;
        }
 
        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
 
        ServletFileUpload upload = new ServletFileUpload(factory);
         
        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);
         
        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);

        // 中文处理
        upload.setHeaderEncoding("UTF-8"); 

        // 构造临时路径来存储上传的文件
        // 这个路径相对当前应用的目录
        String uploadPath = request.getServletContext().getRealPath("./") + File.separator + UPLOAD_DIRECTORY;
       
         
        // 如果目录不存在则创建
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
 
        try {
            // 解析请求的内容提取文件数据
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest((RequestContext) request);
 
            if (formItems != null && formItems.size() > 0) {
                // 迭代表单数据
                for (FileItem item : formItems) {
                    // 处理不在表单中的字段
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        String  filePath = uploadPath + File.separator + fileName;
                       // userService.toDB(filePath);
                        File storeFile = new File(filePath);
                        // 在控制台输出文件的上传路径
                        System.out.println(filePath);
                        // 保存文件到硬盘
                        item.write(storeFile);
                        System.out.println("顶顶顶顶顶顶顶顶顶顶2");
                        String tokenValue = request.getHeader("token");
                        System.out.println(tokenValue);
                        uploadService.upload(Long.valueOf(tokenValue),filePath);                      
                        System.out.println("顶顶顶顶顶顶顶顶顶顶");
                        
                  String jsonString="{\"code\":200,\"msg\":\"文件上传成功\",\"data\":{\"fileName\":"+ fileName +",\"filePath\":"+filePath+"}}";
                  AjaxJsonUtil.doJsonResponse(jsonString,response);
    	
                    }
                }
            }
        } catch (Exception ex) {
        	String jsonString="{\"code\":204,\"msg\":\"文件上传失败\",\"data\":null}";
        	 AjaxJsonUtil.doJsonResponse(jsonString,response);
        }
 
    }
    
}
