package com.flyingwillow.springbootproptype.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.flyingwillow.springbootproptype.spring.multipledatasource.MultipleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@AutoConfigureAfter(DataSourcesConfiguration.class)
public class DataSourceConfig {

    @Value("${spring.datasource.druidLoginName}")
    private String druidLoginName;

    @Value("${spring.datasource.druidPassword}")
    private String druidPassword;

    @Autowired
    @Qualifier("masterDataSource")
    private DataSource masterDataSource;

    @Autowired
    @Qualifier("slaveDataSource")
    private DataSource slaveDataSource;

    @Bean(name = "multipleDataSource")
    @Primary
    public MultipleDataSource multipleDataSource(){

        MultipleDataSource multipleDataSource = new MultipleDataSource();
        multipleDataSource.setDefaultTargetDataSource(masterDataSource);
        Map<Object,Object> targetDataSources = new HashMap<>(4);
        targetDataSources.put("master",masterDataSource);
        targetDataSources.put("slave",slaveDataSource);
        multipleDataSource.setTargetDataSources(targetDataSources);
        return multipleDataSource;
    }

    /////////  下面是druid 监控访问的设置  /////////////////
    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean reg = new ServletRegistrationBean();
        reg.setServlet(new StatViewServlet());
        reg.addUrlMappings("/druid/*");  //url 匹配
        reg.addInitParameter("allow", "192.168.16.110,127.0.0.1"); // IP白名单 (没有配置或者为空，则允许所有访问)
        reg.addInitParameter("deny", "192.168.16.111"); //IP黑名单 (存在共同时，deny优先于allow)
        reg.addInitParameter("loginUsername", this.druidLoginName);//登录名
        reg.addInitParameter("loginPassword", this.druidPassword);//登录密码
        reg.addInitParameter("resetEnable", "false"); // 禁用HTML页面上的“Reset All”功能
        return reg;
    }

    @Bean(name="druidWebStatFilter")
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*"); //忽略资源
        filterRegistrationBean.addInitParameter("profileEnable", "true");
        filterRegistrationBean.addInitParameter("principalCookieName", "USER_COOKIE");
        filterRegistrationBean.addInitParameter("principalSessionName", "USER_SESSION");
        return filterRegistrationBean;
    }
}
