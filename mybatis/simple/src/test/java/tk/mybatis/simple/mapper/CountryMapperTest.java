package tk.mybatis.simple.mapper;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import tk.mybatis.simple.model.Country;
import tk.mybatis.simple.model.CountryExample;

public class CountryMapperTest extends BaseMapperTest {
	
	/*@Test
	public void testSelectAll() {
		SqlSession sqlSession = getSqlSession();
		try {
			List<Country> countryList = sqlSession.selectList("tk.mybatis.simple.mapper"
					+ ".CountryMapper.selectAll");
			printCountryList(countryList);
		} finally {
			sqlSession.close();
		}
	}*/
	
	private void printCountryList(List<Country> countryList) {
		for(Country country : countryList) {
			System.out.printf("%-4d%4s%4s\n", 
					country.getId(), country.getCountryname(), country.getCountrycode());
		}
	}
	
	@Test
	public void testExample() {
		SqlSession sqlSession = getSqlSession();
		try {
			CountryMapper countryMapper = 
					sqlSession.getMapper(CountryMapper.class);
			CountryExample example = new CountryExample();
			example.setOrderByClause("id desc, countryname asc");
			example.setDistinct(true);
			CountryExample.Criteria criteria = example.createCriteria();
			criteria.andIdGreaterThanOrEqualTo(1);
			criteria.andIdLessThan(4);
			criteria.andCountrycodeLike("%U%");
			CountryExample.Criteria or = example.or();
			or.andCountrycodeEqualTo("JP");
			//or.andCountrynameEqualTo("中国");
			List<Country> countryList = countryMapper.selectByExample(example);
			printCountryList(countryList);
		} finally {
			sqlSession.close();
		}
	}

}
