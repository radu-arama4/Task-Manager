package com.stefanini.taskmanager.util;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan("com.stefanini.taskmanager")
//@EnableTransactionManagement
public class ApplicationContextConfiguration {
  @Bean
  public LocalSessionFactoryBean sessionFactory() {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(dataSource());
    sessionFactory.setPackagesToScan("com.stefanini.taskmanager");

    sessionFactory.setHibernateProperties(getHibernateProperties());

    return sessionFactory;
  }

  private Properties getHibernateProperties() {
    Properties hibernateProperties = new Properties();
    hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
    hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
    return hibernateProperties;
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    HibernateTransactionManager transactionManager = new HibernateTransactionManager();
    transactionManager.setSessionFactory(sessionFactory().getObject());
    return transactionManager;
  }

  //  @Bean
  //  public PlatformTransactionManager transactionManager(DataSource dataSource) {
  //    HibernateTransactionManager txManager = new HibernateTransactionManager();
  //    txManager.setNestedTransactionAllowed(true);
  //    txManager.setDataSource(dataSource);
  //    return txManager;
  //  }

  //  @Bean
  //  public DataSource dataSource() {
  //    MysqlDataSource dataSource = new MysqlDataSource();
  //
  //
  //    dataSource.setPassword(applicationProperties.getPassword());
  //    dataSource.setUser(applicationProperties.getUser());
  //    dataSource.setUrl(applicationProperties.getUrl());
  //    try {
  //      dataSource.setAllowPublicKeyRetrieval(true);
  //    } catch (SQLException throwables) {
  //      throwables.printStackTrace();
  //    }
  //
  //    return dataSource;
  //  }

  @Bean
  public DataSource dataSource() {
    ApplicationProperties applicationProperties = ApplicationProperties.getInstance();

    BasicDataSource dataSource = new BasicDataSource();
    dataSource.setDriverClassName(applicationProperties.getDriverClass());
    dataSource.setUrl(applicationProperties.getUrl());
    dataSource.setUsername(applicationProperties.getUser());
    dataSource.setPassword(applicationProperties.getPassword());

    return dataSource;
  }

  //
  //  @Override
  //  public PlatformTransactionManager annotationDrivenTransactionManager() {
  //    return transactionManager();
  //  }
}
