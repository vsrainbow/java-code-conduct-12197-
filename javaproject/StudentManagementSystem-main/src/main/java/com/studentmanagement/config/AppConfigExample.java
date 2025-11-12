/**
 * CONFIGURATION TEMPLATE
 *
 * Copy this file as AppConfig.java and update:
 * - MySQL password in dataSource() method
 * - Database name if different
 */


package com.studentmanagement.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.studentmanagement")
public class AppConfigExample {

    /**
     * DataSource Bean - Using HikariCP for connection pooling
     * Demonstrates Dependency Injection
     */
    @Bean
    public DataSource dataSource() {
        HikariDataSource dataSource = new HikariDataSource();

        // Database connection properties
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/student_management_db?createDatabaseIfNotExist=true");
        dataSource.setUsername("root");
        dataSource.setPassword("YOUR_MYSQL_PASSWORD_HERE"); // Change this to your MySQL password
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        // HikariCP specific properties
        dataSource.setMaximumPoolSize(10);
        dataSource.setMinimumIdle(5);
        dataSource.setConnectionTimeout(30000);
        dataSource.setIdleTimeout(600000);
        dataSource.setPoolName("StudentMgmtHikariPool");

        return dataSource;
    }

    /**
     * SessionFactory Bean - Core Hibernate component
     * Injected with DataSource (demonstrates DI)
     */
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();

        // Inject DataSource dependency
        sessionFactory.setDataSource(dataSource());

        // Scan entities
        sessionFactory.setPackagesToScan("com.studentmanagement.model");

        // Hibernate properties
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    /**
     * Hibernate properties configuration
     */
    private Properties hibernateProperties() {
        Properties properties = new Properties();

        // Hibernate dialect for MySQL
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");

        // Show SQL queries in console
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");

        // Auto DDL - create tables automatically
        properties.setProperty("hibernate.hbm2ddl.auto", "update");

        // Enable Hibernate statistics
        properties.setProperty("hibernate.generate_statistics", "false");

        // Connection pool settings
        properties.setProperty("hibernate.connection.pool_size", "10");

        // Enable batch processing
        properties.setProperty("hibernate.jdbc.batch_size", "20");

        // Enable second level cache (optional)
        properties.setProperty("hibernate.cache.use_second_level_cache", "false");

        return properties;
    }

    /**
     * Transaction Manager Bean
     * Enables @Transactional annotation support
     */
    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();

        // Inject SessionFactory dependency
        transactionManager.setSessionFactory(sessionFactory().getObject());

        return transactionManager;
    }
}