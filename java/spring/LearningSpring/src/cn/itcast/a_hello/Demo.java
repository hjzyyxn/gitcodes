package cn.itcast.a_hello;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.bean.User;

public class Demo {
	@Test
	public void fun1() {
		//创建容器对象
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		//单例
		User u = (User)ac.getBean("user");
		User u2 = (User)ac.getBean("user");
		
		System.out.println(u);
		System.out.println(u == u2);
	}
	
	@Test
	public void fun2() {
		//创建容器对象
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		User u = (User)ac.getBean("user2");
		
		System.out.println(u);
	}
	
	@Test
	public void fun3() {
		//创建容器对象
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		User u = (User)ac.getBean("user3");
		
		System.out.println(u);
	}

	@Test
	public void fun4() {
		//创建容器对象
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		User u = (User)ac.getBean("user");
		User u2 = (User)ac.getBean("user");
		//prototype
		System.out.println(u);
		System.out.println(u == u2);
	}
	
	@Test
	//生命周期
	public void fun5() {
		//创建容器对象
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		
		User u = (User)ac.getBean("user");

		System.out.println(u);
		
		ac.close();

	}
}
