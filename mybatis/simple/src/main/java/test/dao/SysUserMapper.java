package test.dao;

import java.util.List;
import test.model.SysUser;

public interface SysUserMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_user
	 * @mbg.generated
	 */
	int deleteByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_user
	 * @mbg.generated
	 */
	int insert(SysUser record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_user
	 * @mbg.generated
	 */
	SysUser selectByPrimaryKey(Long id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_user
	 * @mbg.generated
	 */
	List<SysUser> selectAll();

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table sys_user
	 * @mbg.generated
	 */
	int updateByPrimaryKey(SysUser record);
}