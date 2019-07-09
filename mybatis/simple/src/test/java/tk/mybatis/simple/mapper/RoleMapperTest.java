package tk.mybatis.simple.mapper;

import java.util.Date;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;


import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

public class RoleMapperTest extends BaseMapperTest {
	@Test
	public void testSelectById() {
		SqlSession sqlSession = getSqlSession();
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			SysRole role = roleMapper.selectById(1l);
			Assert.assertNotNull(role);
			Assert.assertEquals("管理员", role.getRoleName());
		} finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testSelectById2() {
		SqlSession sqlSession = getSqlSession();
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			SysRole role = roleMapper.selectById2(1l);
			Assert.assertNotNull(role);
			Assert.assertEquals("管理员", role.getRoleName());
		} finally {
			sqlSession.close();
		}
	}
	
	@Test
	public void testInsert() {
		SqlSession sqlSession = getSqlSession();
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			SysRole role = new SysRole();
			role.setRoleName("test1");
			role.setEnabled(1);
			role.setCreateBy(1l);
			role.setCreateTime(new Date());
			int result = roleMapper.insert(role);
			Assert.assertEquals(1, result);
			Assert.assertNull(role.getId());
		} finally {
			sqlSession.rollback();
			sqlSession.close();
		}
	}
	
	@Test
	public void testUpdateById() {
		SqlSession sqlSession = getSqlSession();
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			SysRole role = roleMapper.selectById(1L);
			Assert.assertEquals("管理员", role.getRoleName());
			role.setRoleName("admin");
			int result = roleMapper.updateById(role);
			Assert.assertEquals(1, result);
			role = roleMapper.selectById(1L);
			Assert.assertEquals("admin", role.getRoleName());
		} finally {
			sqlSession.rollback();
			sqlSession.close();
		}
	}
	
	@Test
	public void testDeleteById() {
		SqlSession sqlSession = getSqlSession();
		try {
			RoleMapper roleMapper = sqlSession.getMapper(RoleMapper.class);
			SysRole role = roleMapper.selectById(1L);
			Assert.assertNotNull(role);
			Assert.assertEquals(1, roleMapper.deleteById(1L));
			Assert.assertNull(roleMapper.selectById(1L));
		} finally {
			sqlSession.rollback();
			sqlSession.close();
		}
	}

}
