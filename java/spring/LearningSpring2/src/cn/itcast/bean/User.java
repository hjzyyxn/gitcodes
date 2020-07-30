package cn.itcast.bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("user")
//<bean name="user" class="cn.itcast.bean.User" />
@Scope(scopeName="singleton")
//指定对象的作用范围
public class User {
	@Value("tom") //通过反射Field赋值，破坏封装性 
	private String name;
	@Value(value="18")
	private Integer age;
	
	//@Autowired //自动装配，扫描容器
	//问题：如果匹配多个类型一致的对象，将无法选择具体注入哪一个对象
	//@Qualifier("car2") //使用@Qualifier注解告诉spring容器自动装配哪个名称的对象
	
	@Resource(name="car2") //手动注入，指定注入哪个名称的对象，推荐
	private Car car;
	
	public Car getCar() {
		return car;
	}
	public void setCar(Car car) {
		this.car = car;
	}
	public String getName() {
		return name;
	}
	//@Value("tom") 通过set方法赋值
	public void setName(String name) {
		this.name = name;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	@PostConstruct //在对象被创建后调用,init-method
	public void init() {
		System.out.println("init");
	}
	@PreDestroy //在销毁对象之前调用，destroy-method
	public void destroy() {
		System.out.println("destroy");
	}
	@Override
	public String toString() {
		return "User [name=" + name + ", age=" + age + ", car=" + car + "]";
	}
	
	

}
