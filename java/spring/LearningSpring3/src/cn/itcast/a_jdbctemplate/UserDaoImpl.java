package cn.itcast.a_jdbctemplate;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import cn.itcast.bean.User;
//使用JDBC模板实现增删改查
//继承JdbcDaoSupport根据连接池创建JDBC模板，不需要手动准备JDBC模板
//从父类方法中获得即可
public class UserDaoImpl extends JdbcDaoSupport implements UserDao {

	//private JdbcTemplate jt;
	
	@Override
	public void save(User u) {
		// TODO Auto-generated method stub
		String sql = "insert into users values(?,null)";
		super.getJdbcTemplate().update(sql, u.getId());
		//jt.update(sql, u.getId());
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		String sql = "delete from users where id = ?";
		super.getJdbcTemplate().update(sql, id);
	}

	@Override
	public void update(User u) {
		// TODO Auto-generated method stub
		String sql = "update users set name = ? where id = ?";
		super.getJdbcTemplate().update(sql, u.getName(), u.getId());
	}

	@Override
	public User getById(Integer id) {
		// TODO Auto-generated method stub
		String sql = "select * from users where id = ?";
		
		return super.getJdbcTemplate().queryForObject(sql, new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setName(rs.getString("name"));
				return u;
			}}, id);
	}

	@Override
	public int getTotalCount() {
		// TODO Auto-generated method stub
		String sql = "select count(*) from users";
		Integer count = super.getJdbcTemplate().queryForObject(sql, Integer.class);
		return count;
	}

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		String sql = "select * from users";
		List<User> list = super.getJdbcTemplate().query(sql, new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setName(rs.getString("name"));
				return u;
			}});
		return list;
	}

	/*public JdbcTemplate getJt() {
		return jt;
	}

	public void setJt(JdbcTemplate jt) {
		this.jt = jt;
	}*/

	
}
