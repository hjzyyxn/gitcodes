package cn.itcast.dao;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

public class AccountDaoImpl extends JdbcDaoSupport implements AccountDao {

	@Override
	public void increaseMoney(Integer id, Double money) {
		// TODO Auto-generated method stub
		getJdbcTemplate().update("update t_account set money = money + ? where id = ?", money, id);
	}

	@Override
	public void decreaseMoney(Integer id, Double money) {
		// TODO Auto-generated method stub
		getJdbcTemplate().update("update t_account set money = money - ? where id = ?", money, id);
	}

}
