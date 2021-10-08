package org.wayne.common.demo.db;

import cn.hutool.Hutool;
import cn.hutool.db.DbUtil;
import cn.hutool.db.Entity;
import cn.hutool.db.Session;
import cn.hutool.db.StatementUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.wayne.common.qlexpress.entity.DemoQl;

import java.sql.SQLException;

/**
 * @Description: hutoolDb 比jdbcTemplate好用
 * @author: lwq
 */
public class HtDbDemo {
    public static void main(String[] args) {
        Hutool.printAllUtils();
        final Session session = DbUtil.newSession();
        try {
            session.insert(Entity.parse(new DemoQl()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
