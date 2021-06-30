package com.stefanini.taskmanager.util.context;

import com.stefanini.taskmanager.util.ApplicationProperties;
import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan("com.stefanini.taskmanager")
@EnableTransactionManagement
@EnableJpaRepositories(value = "com.stefanini.taskmanager", entityManagerFactoryRef = "emf")
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
  public PlatformTransactionManager transactionManager(
      LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean) {
    JpaTransactionManager transactionManager = new JpaTransactionManager();
    transactionManager.setEntityManagerFactory(localContainerEntityManagerFactoryBean.getObject());
    return transactionManager;
  }

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

  @Bean("emf")
  public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
    LocalContainerEntityManagerFactoryBean entityManagerFactory =
        new LocalContainerEntityManagerFactoryBean();
    entityManagerFactory.setPersistenceProvider(new HibernatePersistenceProvider());
    entityManagerFactory.setDataSource(dataSource());
    entityManagerFactory.setPackagesToScan("com.stefanini.taskmanager");
    return entityManagerFactory;
  }
}
