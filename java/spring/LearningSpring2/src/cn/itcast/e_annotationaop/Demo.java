package cn.itcast.e_annotationaop;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.bean.User;
import cn.itcast.service.UserService;
@RunWith(SpringJUnit4ClassRunner.class) //帮我们创建容器
//指定创建容器时使用哪个配置文件
@ContextConfiguration("classpath:cn/itcast/e_annotationaop/applicationContext.xml")
public class Demo {
	//将名为user的对象注入到u变量中
	@Resource(name="userService")
	private UserService us;
	
	@Test
	public void fun1() {
		us.save();
	}
	
}
