package cn.itcast.c_injection;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.bean.User;

public class Demo {
	@Test
	public void fun1() {
		//创建容器对象
		ApplicationContext ac = new ClassPathXmlApplicationContext("cn/itcast/c_injection/applicationContext.xml");
		//单例
		User u = (User)ac.getBean("user");
		
		System.out.println(u);
	}
	
	@Test
	public void fun2() {
		//创建容器对象
		ApplicationContext ac = new ClassPathXmlApplicationContext("cn/itcast/c_injection/applicationContext.xml");
		//单例
		User u = (User)ac.getBean("user2");
		
		System.out.println(u);
	}
	
	@Test
	public void fun3() {
		//创建容器对象
		ApplicationContext ac = new ClassPathXmlApplicationContext("cn/itcast/c_injection/applicationContext.xml");
		//单例
		User u = (User)ac.getBean("user3");
		
		System.out.println(u);
	}
	
	@Test
	public void fun4() {
		//创建容器对象
		ApplicationContext ac = new ClassPathXmlApplicationContext("cn/itcast/c_injection/applicationContext.xml");
		//单例
		User u = (User)ac.getBean("user4");
		
		System.out.println(u);
	}
	
	@Test
	public void fun5() {
		//创建容器对象
		ApplicationContext ac = new ClassPathXmlApplicationContext("cn/itcast/c_injection/applicationContext.xml");
		//单例
		CollectionBean cb = (CollectionBean)ac.getBean("cb");
		
		System.out.println(cb);
	}
	
}
