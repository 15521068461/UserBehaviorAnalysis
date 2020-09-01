package com.haige.hr.xingwei.web;


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
			case 0:// 数字
				int number = random.nextInt(10);
				str += number;
				break;
			case 1:// 小写字母
				int lower = random.nextInt(26) + 97;
				str += (char) lower;
				break;
			case 2:// 大写字母
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

		// 创建一张图片
		int width = 120;
		int height = 25;
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 创建一支画笔
		Graphics2D graphics = image.createGraphics();
		// 给画笔添加颜色
		graphics.setColor(Color.white);
		// 填充矩形
		graphics.fillRect(0, 0, width, height);

		String str = this.getString();
		System.out.println(str + "的味道");                                                         
		System.out.println(request.getParameter("imgCodeToken"));

		// 生成验证码Servlet 将字符串存入session
		HttpSession session = request.getSession();
		
		System.out.println(session.getId() + "第一次请求");
		session.setAttribute("yz", str); // 生成的验证码
		session.setAttribute("time", request.getParameter("imgCodeToken"));// 时间戳
        
		Random random = new Random();
		// 根据验证码长度随机画干扰线(颜色随机，位置随机，长度随机)
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			Font font = new Font("微软雅黑", Font.BOLD, 22);
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
		// 把图像刷到BufferedImage对象中
		graphics.dispose();
		// 将图像写入 File，并指定图片格式

		ImageIO.write(image, "jpg", response.getOutputStream());

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
