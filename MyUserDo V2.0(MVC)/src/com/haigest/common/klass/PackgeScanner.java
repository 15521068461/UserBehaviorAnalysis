package com.haigest.common.klass;

import java.io.File;
import java.lang.annotation.Annotation;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PackgeScanner {
	private final static Log logger = LogFactory.getLog(PackgeScanner.class);
	/**
     * 从包package中获取所有的Class
     * @param packageName
     * @return 类集合
     */
    private static Set<Class<?>> getClasses(String packageName){
        // 存储class的集合
        Set<Class<?>> classes = new HashSet<>();
        // 是否循环迭代
        boolean recursive = true;
        // 获取包的名字 并进行替换
        String packageDirName = packageName.replace('.', '/');
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);         
            // 循环迭代下去
            while (dirs.hasMoreElements()) {
                // 获取下一个元素
                URL url = dirs.nextElement();
                // 得到协议的名称
                String protocol = url.getProtocol();
                // 如果是以class文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    // 获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    addClass(classes, filePath, packageName);
                } else if ("jar".equals(protocol)) {
                    // 如果是jar包文件，定义一个JarFile
                    JarFile jar;
                        // 获取jar
                        jar = ((JarURLConnection) url.openConnection()).getJarFile();
                        // 从此jar包 得到一个枚举类
                        Enumeration<JarEntry> entries = jar.entries();
                        // 同样的进行循环迭代
                        while (entries.hasMoreElements()) {
                            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            // 如果是以/开头的
                            if (name.charAt(0) == '/') {
                                // 获取后面的字符串
                                name = name.substring(1);
                            }
                            // 如果前半部分和定义的包名相同
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                // 如果以"/"结尾 是一个包
                                if (idx != -1) {
                                    // 获取包名 把"/"替换成"."
                                    packageName = name.substring(0, idx).replace('/', '.');
                                }
                                // 如果可以迭代下去 并且是一个包
                                if ((idx != -1) || recursive) {
                                    // 如果是一个.class文件 而且不是目录
                                    if (name.endsWith(".class") && !entry.isDirectory()) {
                                        // 去掉后面的".class" 获取真正的类名
                                        String className = name.substring(packageName.length() + 1, name.length() - 6);
                                        // 添加到classes
                                        classes.add(Class.forName(packageName + '.' + className));                                       
                                    }
                                }
                            }
                        }
                }
            }
        } catch (Exception e) {
        	logger.fatal("包扫描类检索失败", e);
        }

        return classes;
    }
    private static void addClass(Set<Class<?>> classes, String filePath, String packageName) throws Exception {
        File[] files = new File(filePath).listFiles(file -> (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory());
        assert files != null;
        for (File file : files) {
        	//如果是目录，递归扫描
        	if(file.isDirectory()) {
            	addClass(classes,file.getPath(),packageName+"."+file.getName());
            }
            String fileName = file.getName();
            if (file.isFile()) {
                String classsName = fileName.substring(0, fileName.lastIndexOf("."));
                if (!packageName.isEmpty()) {
                    classsName = packageName + "." + classsName;
                }               
                doAddClass(classes, classsName);
            }

        }
    }
    private static void doAddClass(Set<Class<?>> classes, final String classsName) throws Exception {
    	//System.out.println("目标包中检索到一个类："+classsName);
        classes.add(Class.forName(classsName));
    }

    /**
     * 	
     *
     * @param Hashmap的键是扫描的包名，值是注解的类信息
     * @return 类集合
     */
	public static <A extends Annotation> Set<Class<?>> getAnnotationClasses(String[] scanArray,Class<A> annotationClass) throws Exception {

        //存储用到annotationClass注解的类集合
        Set<Class<?>> controllers = new HashSet<>();
        //获取目标包中所有类
        for(String packageName : scanArray) {
	        Set<Class<?>> clsList = getClasses(packageName);
	        if (clsList != null && clsList.size() > 0) {
	            for (Class<?> cls : clsList) {
	                if (cls.getAnnotation(annotationClass) != null) {
	                	System.out.println("目标包中检索到一个目标类："+cls.getName());
	                    controllers.add(cls);
	                }
	            }
	        }
        }
        return controllers;
    }
}
