package sk.kadlecek.productsemantizer.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.datasource.embedded.ConnectionProperties;
import org.springframework.jdbc.datasource.embedded.DataSourceFactory;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import sk.kadlecek.productsemantizer.constant.ApplicationConstant;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import java.text.SimpleDateFormat;
import java.util.List;

import static java.lang.System.getProperty;

@Configuration
@EnableWebMvc
@EnableScheduling
@EnableSpringDataWebSupport
@ComponentScan(basePackages = {
        "sk.kadlecek.productsemantizer.controller",
        "sk.kadlecek.productsemantizer.service",
        "sk.kadlecek.productsemantizer.dao",
        "sk.kadlecek.productsemantizer.bean"
})
@EnableJpaRepositories("sk.kadlecek.productsemantizer.dao")
@PropertySource(value = "file:${semantizer.home}/config/application.properties")
public class WebAppConfiguration extends WebMvcConfigurerAdapter {

    @Value("${database.uri:#{null}}")
    private String DB_URI;

    @Value("${database.username::#{null}}")
    private String DB_USERNAME;

    @Value("${database.password::#{null}}")
    private String DB_PASSWORD;

    @Value("${http.max.filesize.bytes}")
    private Long HTTP_MAX_FILESIZE_BYTES;

    private String getSemantizerHome() {
        return System.getProperty(ApplicationConstant.SEMANTIZER_HOME);
    }

    private String getDbUri() {
        String defaultUri = "jdbc:h2:" + getSemantizerHome() + ApplicationConstant.Default.DB_URI_PATH_SUFFIX +
                ";DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false";
        return (DB_URI != null) ? DB_URI : defaultUri;
    }

    private String getDbUsername() {
        return (DB_USERNAME != null) ? DB_USERNAME : "sa";
    }

    private String getDbPassword() {
        return (DB_PASSWORD != null) ? DB_PASSWORD : "";
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(mappingJackson2HttpMessageConverter());
    }

    @Bean(name = "mappingJackson2HttpMessageConverter")
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.indentOutput(true).dateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm"));
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        return new MappingJackson2HttpMessageConverter(builder.build());
    }

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver(){
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setMaxUploadSizePerFile(HTTP_MAX_FILESIZE_BYTES);
        return resolver;
    }

    @Bean
    public DataSource dataSource() {
        SimpleDriverDataSource simpleDriverDataSource = new SimpleDriverDataSource();
        simpleDriverDataSource.setDriverClass(org.h2.Driver.class);
        simpleDriverDataSource.setUrl(getDbUri());
        simpleDriverDataSource.setUsername(getDbUsername());
        simpleDriverDataSource.setPassword(getDbPassword());
        return simpleDriverDataSource;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter bean = new HibernateJpaVendorAdapter();
        bean.setDatabase(Database.H2);
        bean.setGenerateDdl(true);
        bean.setShowSql(true);
        return bean;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource, JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource);
        bean.setJpaVendorAdapter(jpaVendorAdapter);
        bean.setPackagesToScan("sk.kadlecek.productsemantizer");
        return bean;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}

