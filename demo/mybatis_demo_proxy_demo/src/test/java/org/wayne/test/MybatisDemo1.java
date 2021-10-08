package org.wayne.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.wayne.example.Blog;
import org.wayne.example.BlogMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Description: 原生builder->sqlSessionFactory->sqlSession->mapper->执行sql
 * @author: lwq
 */
public class MybatisDemo1 {
    /**
     * 每个基于 MyBatis 的应用都是以一个 SqlSessionFactory 的实例为核心的。SqlSessionFactory 的实例可以通过 SqlSessionFactoryBuilder 获得
     */
    @Test
    public void init() throws IOException {
        String resource = "org/wayne/example/mybatis-config.xml";
        // 使用mybatis的Resources工具类加载配置
        InputStream inputStream = Resources.getResourceAsStream(resource);
        // SqlSessionFactoryBuilder获得 sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 打开一个sqlSession
        try(final SqlSession sqlSession = sqlSessionFactory.openSession()){

            final BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
            final Blog blog = mapper.selectBlog(1);
            sqlSession.commit();
            sqlSession.rollback();
            System.out.printf("查询到的标题为:%s\n",blog.getTitle());
        }
    }

    @Before
    public void beforeMethod() {
        System.out.println("junit4 started by wayne");


    }

    @After
    public void afterMethod() {

    }
}
