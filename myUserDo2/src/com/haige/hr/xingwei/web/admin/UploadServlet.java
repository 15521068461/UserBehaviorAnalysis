package com.haige.hr.xingwei.web.admin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.haige.hr.xingwei.service.UploadService;
import com.haige.hr.xingwei.service.UserService;
 

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/open/common/upload")
public class UploadServlet extends HttpServlet {
	private UserService userService = new UserService();
	private UploadService uploadService = new UploadService();
    // �ϴ��ļ��洢Ŀ¼
    private static final String UPLOAD_DIRECTORY = "upload";
 
    // �ϴ�����
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
 
    /**
     * �ϴ����ݼ������ļ�
     */
    protected void doPost(HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException {
        // ����Ƿ�Ϊ��ý���ϴ�
        if (!ServletFileUpload.isMultipartContent(request)) {
            // ���������ֹͣ
            PrintWriter writer = response.getWriter();
            writer.println("Error: ��������� enctype=multipart/form-data");
            writer.flush();
            return;
        }
 
        // �����ϴ�����
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // �����ڴ��ٽ�ֵ - �����󽫲�����ʱ�ļ����洢����ʱĿ¼��
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // ������ʱ�洢Ŀ¼
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
 
        ServletFileUpload upload = new ServletFileUpload(factory);
         
        // ��������ļ��ϴ�ֵ
        upload.setFileSizeMax(MAX_FILE_SIZE);
         
        // �����������ֵ (�����ļ��ͱ�����)
        upload.setSizeMax(MAX_REQUEST_SIZE);

        // ���Ĵ���
        upload.setHeaderEncoding("UTF-8"); 

        // ������ʱ·�����洢�ϴ����ļ�
        // ���·����Ե�ǰӦ�õ�Ŀ¼
        String uploadPath = request.getServletContext().getRealPath("./") + File.separator + UPLOAD_DIRECTORY;
       
         
        // ���Ŀ¼�������򴴽�
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
 
        try {
            // ���������������ȡ�ļ�����
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);
 
            if (formItems != null && formItems.size() > 0) {
                // ����������
                for (FileItem item : formItems) {
                    // �����ڱ��е��ֶ�
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        String  filePath = uploadPath + File.separator + fileName;
                       // userService.toDB(filePath);
                        File storeFile = new File(filePath);
                        // �ڿ���̨����ļ����ϴ�·��
                        System.out.println(filePath);
                        // �����ļ���Ӳ��
                        item.write(storeFile);
                        System.out.println("��������������������2");
                        String tokenValue = request.getHeader("token");
                        System.out.println(tokenValue);
                        uploadService.upload(Long.valueOf(tokenValue),filePath);                      
                        System.out.println("��������������������");
                        
                  String jsonString="{\"code\":200,\"msg\":\"�ļ��ϴ��ɹ�\",\"data\":{\"fileName\":"+ fileName +",\"filePath\":"+filePath+"}}";
                  doJsonResponse(jsonString,response);
    	
                    }
                }
            }
        } catch (Exception ex) {
        	String jsonString="{\"code\":204,\"msg\":\"�ļ��ϴ�ʧ��\",\"data\":null}";
            doJsonResponse(jsonString,response);
        }
 
    }

    
    private void doJsonResponse(String json,HttpServletResponse response)
			throws ServletException,IOException{		
		response.setCharacterEncoding("UTF-8");   
		response.setContentType("application/json; charset=UTF-8");  
		PrintWriter out = response.getWriter();
		out.println(json);
		out.close();
		
    }
}