package org.myWeather.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.ejb.HibernatePersistence;
import org.myWeather.persistence.repository.DayEventRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
//The CRUD repository containing all operations
@EnableJpaRepositories(basePackages = "org.myWeather.persistence.repository")
@EnableTransactionManagement
public class JPAConfiguration {

	@Bean	
    public DataSource dataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
        //dataSource.setUrl("jdbc:oracle:thin:@http://90.225.88.116/:1521:xe");
        dataSource.setUsername("marcus");
        dataSource.setPassword("ronnang");       
        
        return dataSource;
    }
	
	@Bean	
    public EntityManagerFactory entityManagerFactory() {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	    vendorAdapter.setGenerateDdl(true);
		
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();	
        entityManagerFactoryBean.setDataSource(dataSource());	
        //entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactoryBean.setPackagesToScan("org.myWeather.persistence.domain");	
        entityManagerFactoryBean.setJpaProperties(hibProperties());		 
        entityManagerFactoryBean.afterPropertiesSet();

        return entityManagerFactoryBean.getObject();	
    }
	
	private Properties hibProperties() {		
        Properties properties = new Properties();		
        properties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");		
        properties.put("show_sql", "true");
        properties.put("hibernate.default_schema", "marcus");        

        return properties;		
	}

	@Bean
	public EntityManager entityManager(EntityManagerFactory entityManagerFactory) {
	  return entityManagerFactory.createEntityManager();
	}

	@Bean
	public PlatformTransactionManager transactionManager() throws SQLException {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory((EntityManagerFactory) entityManagerFactory());
		return txManager;
	}

	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}

}
