package cn.itcast.a_annotation;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.bean.User;

public class Demo {
	@Test
	public void fun1() {
		//创建容器对象
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		//单例
		User u = (User)ac.getBean("user");
		User u2 = (User)ac.getBean("user");
		
		System.out.println(u);
		System.out.println(u == u2);
		
		ac.close();
	}
	
}
