package org.wayne.thief.config;

import cn.hutool.log.StaticLog;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @Description:
 * @author: lwq
 */
@Configuration
@MapperScan(basePackages = {"org.wayne.thief.mapper"})
public class MybatisConfig {
    @Autowired
    DataSource dataSource;

    @PostConstruct
    public void soutDataSource(){
        try {
            StaticLog.info("默认数据库链接地址:{}",dataSource.getConnection().getMetaData().getURL());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
