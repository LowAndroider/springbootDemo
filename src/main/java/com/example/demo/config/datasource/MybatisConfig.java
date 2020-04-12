package com.example.demo.config.datasource;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import lombok.Data;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.util.buf.StringUtils;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author Djh
 */
@Configuration
@MapperScan(basePackages = "com.example.demo.modules.dao")
@ConfigurationProperties("mybatis-plus")
@Data
public class MybatisConfig {

    private String typeAliasesPackage;

    private List<String> mapperLocations;

//  默认由MybatisPlusAutoConfiguration.java来配置
//    @Bean
//    public SqlSessionFactory sqlSessionFactory(DataSource routingSource) throws Exception {
//        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
//        bean.setDataSource(routingSource);
//        Optional.of(typeAliasesPackage).ifPresent(bean::setTypeAliasesPackage);
//        if (mapperLocations != null) {
//            String join = StringUtils.join(mapperLocations, ',');
//            Resource[] resources = new PathMatchingResourcePatternResolver().getResources(join);
//            bean.setMapperLocations(resources);
//        }
//        return bean.getObject();
//    }
}
