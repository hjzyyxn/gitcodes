package tk.mybatis.simple.mapper;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;

import tk.mybatis.simple.model.SysPrivilege;
import tk.mybatis.simple.model.SysRole;

public class PrivilegeMapperTest extends BaseMapperTest {
	@Test
	public void testSelectById() {
		SqlSession sqlSession = getSqlSession();
		try {
			PrivilegeMapper privilegeMapper = sqlSession.getMapper(PrivilegeMapper.class);
			SysPrivilege privilege = privilegeMapper.selectById(1l);
			Assert.assertNotNull(privilege);
			Assert.assertEquals("用户管理", privilege.getPrivilegeName());
		} finally {
			sqlSession.close();
		}
	}
}
