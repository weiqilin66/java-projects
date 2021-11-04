package org.wayne.source.mybatis;


// sqlSession线程不安全原因: 执行sql是通过Executor调度生成Statement这个jdbc对象,这个对象是通过Connection链接的


public class Mybatis10 {
   /*
   public Connection getConnection() throws SQLException {
        // 这里只要有连接了就不重新打开连接了（从数据源中再次获取），说明只能有一个连接在一个org.apache.ibatis.transaction.Transaction中
        if (connection == null) {
            openConnection();
        }
        return connection;
    }*/
    }
