package com.haigest.core.mvc;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.haigest.core.constant.MvcProperties;
import com.haigest.common.klass.PackgeScanner;
/**
 *   监听器在tomcat启动的时候收集URL到JAVA后台方法的对应关系并存储起来
 * @author 灯芯科技 李远念
 * 
 */
public class URLMappingCollection implements ServletContextListener {
	private final static Log logger = LogFactory.getLog(URLMappingCollection.class);
	/*
	 * mvcBases就是将来我们要将URL到JAVA后台方法对应关系存储到的地方。我们将他定义为静态方法，以便后续我们直接访问获取。
	 * controllerList是用来告诉监听器，我们要从哪些类中获取URL到JAVA后台方法的对应关系。因为在程序设计期我们就已经知道了，所以定义为final属性。
	 *    然后在监听器初始化的时候，循环controllerList中的class，根据注解的信息来收集URL到JAVA后台方法的对应关系并存储到mvcBases中
	 */
	
	
    //被注解了URLMapper的类方法列表
    private static List<MVCBase> mvcBases;

    //直接定义目标类的方案（我们要扫描的Controller类列表）
    //private final String[] scanArray = MvcProperties.MVC_CONTROLLERS.split(",");
    //基于包扫描器的方案（我们要扫描的包列表）
    private final String[] scanArray=MvcProperties.MVC_CONTROLLERS_PACKAGE.split(",");
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
    
    //tomcat启动时初始化
    @Override
    public void contextInitialized(ServletContextEvent sce) {    	
        try {
        	ArrayList<String> controllerList=new ArrayList<String>();
        	Set<Class<?>> set = PackgeScanner.getAnnotationClasses(scanArray, URLMapping.class);
        	System.out.println(set.size()+"你懂吗");
        	for (Class<?> c : set) {
        		controllerList.add(c.getName());
        	}        	
            mvcBases = new ArrayList<MVCBase>();
            //循环所有需要扫描的Controller
            for (int i = 0; i < controllerList.size(); i++) {
                String controllerName = controllerList.get(i); 
                
                String classURL = "";//定义并且初始化一个classURL
                String methodURL = "";//定义并且初始化一个methodURL
                
                Class<?> clazz = Class.forName(controllerName); //获取Controller类
                if (clazz.isAnnotationPresent(URLMapping.class)) { //class被标记了URLMapping注解
                    classURL = ((URLMapping) clazz.getAnnotation(URLMapping.class)).url();  //则获取其注解上url的值           
                }
                //获取method列表
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(URLMapping.class)) { 
                    //method被标记了URLMapping注解
                        methodURL = ((URLMapping) method.getAnnotation(URLMapping.class)).url(); 
                        
                        MVCBase mvcBase = new MVCBase();
                        mvcBase.setUrl(classURL+methodURL);
                        mvcBase.setController(controllerName);
                        mvcBase.setMethod(method.getName());
                        
                        mvcBases.add(mvcBase);
                    }
                }
            }
        }
        catch (Exception e) {
        	logger.fatal("接口初始化失败", e);
        }
        
       System.out.println(URLMappingCollection.getMvcBases().size()+"你懂的"); 
    }
    
    public static List<MVCBase> getMvcBases() {
        return mvcBases;
    }
    public static void setMvcBases(List<MVCBase> list) {
        mvcBases = list;
    }
}
