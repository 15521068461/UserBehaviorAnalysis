package com.haige.hr.xingwei.web.admin;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Picture
 */
@WebServlet("/open/common/captcha")
public class PictureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public String getString() {
		Random random = new Random();
		String str = "";
		for (int i = 0; i < 4; i++) {
			int num = random.nextInt(3);
			switch (num) {
			case 0:// ����
				int number = random.nextInt(10);
				str += number;
				break;
			case 1:// Сд��ĸ
				int lower = random.nextInt(26) + 97;
				str += (char) lower;
				break;
			case 2:// ��д��ĸ
				int upper = random.nextInt(26) + 65;
				str += (char) upper;
				break;
			default:
				System.out.println("error");
				break;
			}
		}
		return str;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// ����һ��ͼƬ
		int width = 120;
		int height = 25;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// ����һ֧����
		Graphics2D graphics = image.createGraphics();
		// �����������ɫ
		graphics.setColor(Color.white);
		// ������
		graphics.fillRect(0, 0, width, height);

		String str = this.getString();
		System.out.println(str + "��ζ��");                                                         
		System.out.println(request.getParameter("imgCodeToken"));

		// ������֤��Servlet ���ַ�������session
		HttpSession session = request.getSession();
		
		System.out.println(session.getId() + "��һ������");
		session.setAttribute("yz", str); // ���ɵ���֤��
		session.setAttribute("time", request.getParameter("imgCodeToken"));// ʱ���
        
		Random random = new Random();
		// ������֤�볤�������������(��ɫ�����λ��������������)
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			Font font = new Font("΢���ź�", Font.BOLD, 22);
			graphics.setFont(font);
			Color color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
			graphics.setColor(color);
			graphics.drawString(String.valueOf(c), 20 + i * 15, 20);
			int x1 = random.nextInt(width);
			int y1 = random.nextInt(height);
			int x2 = random.nextInt(width);
			int y2 = random.nextInt(height);
			graphics.drawLine(x1, y1, x2, y2);
		}
		// ��ͼ��ˢ��BufferedImage������
		graphics.dispose();
		// ��ͼ��д�� File����ָ��ͼƬ��ʽ

		ImageIO.write(image, "jpg", response.getOutputStream());

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
