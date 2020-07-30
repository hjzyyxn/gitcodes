package tk.mybatis.simple.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import tk.mybatis.simple.model.SysRole;
import tk.mybatis.simple.model.SysUser;

public interface UserMapper {
	SysUser selectedById(Long id);
	List<SysUser> selectAll();
	List<SysRole> selectRolesByUserId(Long userId);
	int insert(SysUser sysUser);
	int insert2(SysUser sysUser);
	int insert3(SysUser sysUser);
	int updateById(SysUser sysUser);
	int deleteById(Long id);
	int deleteById(SysUser sysUser);
	List<SysRole> selectRolesByUserIdAndRoleEnabled(
			@Param("userId")Long userId,
			@Param("enabled")Integer enabled);
	List<SysUser> selectByUser(SysUser sysUser);
	int updateByIdSelective(SysUser sysUser);
	SysUser selectByIdOrUserName(SysUser sysUser);
	List<SysUser> selectByIdList(List<Long> idList);
	int insertList(List<SysUser> userList);
	int updateByMap(Map<String, Object> map);

}
