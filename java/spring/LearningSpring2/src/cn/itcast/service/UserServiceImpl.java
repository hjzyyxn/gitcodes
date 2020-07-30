package cn.itcast.service;

public class UserServiceImpl implements UserService {

	@Override
	public void save() {
		// TODO Auto-generated method stub
		System.out.println("保存用户");
		//int i = 1 / 0; 测试异常通知
	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub
		System.out.println("删除用户");
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		System.out.println("更新用户");
	}

	@Override
	public void find() {
		// TODO Auto-generated method stub
		System.out.println("查找用户");
	}

}
