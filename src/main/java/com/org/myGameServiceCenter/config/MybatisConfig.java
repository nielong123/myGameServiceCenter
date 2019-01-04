package com.org.myGameServiceCenter.config;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = "com.org.myGameServiceCenter", sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MybatisConfig {


    @Bean(name = "masterSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("mainDataSource") DataSource mysqlDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(mysqlDataSource);
        sessionFactory.setPlugins(new Interceptor[]{pageHelpeSetting()});
        sessionFactory.setMapperLocations(
                new PathMatchingResourcePatternResolver().getResources("classpath*:/mapper/**Mapper.xml"));

        return sessionFactory.getObject();
    }


    private Interceptor pageHelpeSetting() {
        Interceptor interceptor = new PageInterceptor();
        Properties properties = new Properties();
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "true");
//        properties.setProperty("dialect", "mysql");       //4.0.0以后不要配置数据源
        properties.setProperty("pageSizeZero", "true");
        properties.setProperty("supportMethodsArguments", "false");
        properties.setProperty("returnPageInfo", "false");
        interceptor.setProperties(properties);
        return interceptor;
    }

    /*****
     * 如果不重写数据源，直接用下面的就可以使pageHelper生效
     * @return
     */
//    @Bean
//    public PageHelper pageHelper() {
//        PageHelper pageHelper = new PageHelper();
//        //添加配置，也可以指定文件路径
//        Properties p = new Properties();
//        p.setProperty("offsetAsPageNum", "true");
//        p.setProperty("rowBoundsWithCount", "true");
//        p.setProperty("reasonable", "true");
//        p.setProperty("dialect", "mysql");
//        p.setProperty("pageSizeZero", "true");
//        p.setProperty("supportMethodsArguments", "false");
//        p.setProperty("returnPageInfo", "false");
//        pageHelper.setProperties(p);
//        return pageHelper;
//    }

}
