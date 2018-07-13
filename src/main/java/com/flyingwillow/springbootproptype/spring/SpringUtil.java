package com.flyingwillow.springbootproptype.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 获得spring加载的bean
 * @author btshen @modified liuxuhui
 *
 */
public class SpringUtil {
	
	private static class SpringContextHolder{
		private static ApplicationContext context;
		
		static{
			context =  ApplicationHelper.getApplicationContext();
			if(context == null){
				context = new ClassPathXmlApplicationContext("spring/applicationContext_manual.xml");
				System.out.println("手工初始化spring环境！springContext.xml");
			}
		}
	}
	
	private SpringUtil(){
		
	}
    
	public static <T> T getBean(Class<T> clazz){
		return SpringContextHolder.context.getBean(clazz);
	}
	
	public static Object getBean(String name){
		return SpringContextHolder.context.getBean(name);
	}
	
	public static ApplicationContext getApplicationContext(){
		return SpringContextHolder.context;
	}
	
	public static ApplicationContext getApplicationContext(String[] path){
		return new ClassPathXmlApplicationContext(path);
	}
	
}
