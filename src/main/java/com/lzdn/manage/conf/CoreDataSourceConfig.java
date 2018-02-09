package com.lzdn.manage.conf;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;

@Configuration
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = CoreDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "coreSqlSessionFactory")
public class CoreDataSourceConfig {

	// 精确到 core 目录，以便跟其他数据源隔离
	static final String PACKAGE = "com.lzdn.manage.mapper.core";
	static final String MAPPER_LOCATION = "classpath:mapper/core/*.xml";

	@Value("${core.datasource.url}")
	private String url;

	@Value("${core.datasource.username}")
	private String user;

	@Value("${core.datasource.password}")
	private String password;

	@Value("${core.datasource.driverClassName}")
	private String driverClass;

	@Value("${core.datasource.type}")
	private String type;
	
	@Bean(name = "coreDataSource")
	@Primary
	public DataSource coreDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setDriverClassName(driverClass);
		dataSource.setUrl(url);
		dataSource.setUsername(user);
		dataSource.setPassword(password);
		dataSource.setDbType(type);
		return dataSource;
	}

	@Bean(name = "coreTransactionManager")
	@Primary
	public DataSourceTransactionManager coreTransactionManager() {
		return new DataSourceTransactionManager(coreDataSource());
	}

	@Bean(name = "coreSqlSessionFactory")
	@Primary
	public SqlSessionFactory coreSqlSessionFactory(@Qualifier("coreDataSource") DataSource coreDataSource)
			throws Exception {
		final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
		sessionFactory.setDataSource(coreDataSource);
		sessionFactory.setTypeAliasesPackage("com.lzdn.manage.domain.core");
		//sessionFactory.setConfigLocation(configLocation);
		  //分页插件
//        PageHelper pageHelper = new PageHelper();
//        Properties properties = new Properties();
//        properties.setProperty("reasonable", "true");
//        properties.setProperty("supportMethodsArguments", "true");
//        properties.setProperty("returnPageInfo", "check");
//        properties.setProperty("params", "count=countSql");
//        pageHelper.setProperties(properties);

        //添加插件
       // bean.setPlugins(new Interceptor[]{pageHelper});

		sessionFactory.setMapperLocations(
				new PathMatchingResourcePatternResolver().getResources(CoreDataSourceConfig.MAPPER_LOCATION));
		return sessionFactory.getObject();
	}
}
