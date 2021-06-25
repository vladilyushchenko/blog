package com.leverx.blog.config.db;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.*;

@Configuration
@EnableTransactionManagement
@PropertySource({"classpath:db.properties"})
@ComponentScan({"com.leverx.blog"})
@EnableJpaRepositories(basePackages = "com.leverx.blog.repositories")
public class JpaConfig {
    private final Environment env;

    public JpaConfig(Environment env) {
        super();
        this.env = env;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan("com.leverx.blog.entities");

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactoryBean.setJpaProperties(jpaProperties());

        return entityManagerFactoryBean;
    }

    final Properties jpaProperties() {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(DIALECT,
                Objects.requireNonNull(env.getProperty(DIALECT)));
        hibernateProperties.setProperty(USE_SECOND_LEVEL_CACHE,
                Objects.requireNonNull(env.getProperty(USE_SECOND_LEVEL_CACHE)));
        hibernateProperties.setProperty(USE_QUERY_CACHE,
                Objects.requireNonNull(env.getProperty(USE_QUERY_CACHE)));
        return hibernateProperties;
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty(DRIVER)));
        dataSource.setUrl(Objects.requireNonNull(env.getProperty(JPA_JDBC_URL)));
        dataSource.setUsername(Objects.requireNonNull(env.getProperty(JPA_JDBC_USER)));
        dataSource.setPassword(Objects.requireNonNull(env.getProperty(JPA_JDBC_PASSWORD)));
        return dataSource;
    }

    @Bean
    public JpaTransactionManager transactionManager(final EntityManagerFactory emf) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }
}