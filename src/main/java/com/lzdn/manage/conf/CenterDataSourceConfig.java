package com.lzdn.manage.conf;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
@MapperScan(basePackages = CenterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "centerSqlSessionFactory")
public class CenterDataSourceConfig {

	// 精确到 center 目录，以便跟其他数据源隔离
	static final String PACKAGE = "com.lzdn.manage.mapper.center";
	static final String MAPPER_LOCATION = "classpath:mapper/center/*.xml";

	@Value("${center.datasource.url}")
	private String url;

	@Value("${center.datasource.username}")
	private String user;

	@Value("${center.datasource.password}")
	private String password;

	@Value("${center.datasource.driverClassName}")
	private String driverClass;

	@Value("${center.datasource.type}")
	private String type;
	
	@Bean(name = "centerDataSource")
	public DataSource centerDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(driverClass);
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(password);
		dataSource.setDbType(type);
		return dataSource;
	}

	@Bean(name = "centerTransactionManager")
	public DataSourceTransactionManager centerTransactionManager() {
		return new DataSourceTransactionManager(centerDataSource());
	}

	@Bean(name = "centerSqlSessionFactory")
	public SqlSessionFactory centerSqlSessionFactory(@Qualifier("centerDataSource") DataSource centerDataSource)
			throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(centerDataSource);
		sessionFactory.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources(CenterDataSourceConfig.MAPPER_LOCATION));
		return sessionFactory.getObject();
	}
}
