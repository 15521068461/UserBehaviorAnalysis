package com.haigest.common.algorithm;

import java.util.Random;

/**
 *	 随机数工具类
 * 	@author 灯芯科技 李远念
 */
public class RandomUtil {

    /**
     * 获取多少位随机数
     * @param num
     * @return
     */
    public static String getNumStringRandom(int num){
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        //随机生成数字，并添加到字符串
        for(int i = 0;i<num;i++){
            str.append(random.nextInt(10));
        }
        return  str.toString();
    }

    /**
     * 获取区间内的随机数
     * @param min
     * @param max
     * @return
     */
    public static int getRandomBetween(int min, int max){
        Random random = new Random();
        int s = random.nextInt(max)%(max-min+1) + min;
        return s;
    }
	/** 根据时间戳获取到getToken()
	 *  
	 * @return
	 */
	public static long getToken(){		
        return System.currentTimeMillis();
    }
	
	//获取图形验证码字符串
	public static String getString() {
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
    
    public static void main(String args[]) {
    }
}
