package org.wayne.card.config;

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
@MapperScan(basePackages = {"org.wayne.card.repository.mapper"})
public class DbConfig {
    @Autowired
    DataSource dataSource;

    @PostConstruct
    public void soutDataSource(){
        System.out.println("调试数据源");
        try {
            StaticLog.info("默认数据库链接地址:{}",dataSource.getConnection().getMetaData().getURL());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
