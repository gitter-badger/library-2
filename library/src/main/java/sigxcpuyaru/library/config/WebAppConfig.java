package sigxcpuyaru.library.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.http.MediaType;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import liquibase.integration.spring.SpringLiquibase;

import sigxcpuyaru.library.dao.BookDAO;
import sigxcpuyaru.library.dao.UserDAO;
import sigxcpuyaru.library.dao.JdbcTemplateBookDAO;
import sigxcpuyaru.library.dao.JdbcTemplateUserDAO;
import sigxcpuyaru.library.service.Service;
import sigxcpuyaru.library.service.BookBatcher;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages="sigxcpuyaru")
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    // @Bean
    // public SpringLiquibase liquibase() {
    //     SpringLiquibase liquibase = new SpringLiquibase();
    //     liquibase.setChangeLog("classpath:liquibase-changeLog.xml");
    //     liquibase.setDataSource(dataSource());
    //     return liquibase;
    // }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("classpath:schema.sql")
                .addScript("classpath:test-data.sql")
                .build();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/");
    }

    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public Service service() {
        return new Service(bookDAO(), userDAO());
    }

    @Bean
    public BookBatcher bookBatcher() {
        return new BookBatcher(bookDAO(), userDAO());
    }

    @Bean
    public BookDAO bookDAO() {
        return new JdbcTemplateBookDAO(jdbcTemplate());
    }

    @Bean
    public UserDAO userDAO() {
        return new JdbcTemplateUserDAO(jdbcTemplate());
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }
}
