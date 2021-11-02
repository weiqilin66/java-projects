## mybatis源码分析(结合深入浅出Mybatis注解版)
1. SqlSessionFactoryBuilder -> SqlSessionFactory -> SqlSession
2. SqlSessionFactoryBuilder: 局部变量,目的构造生成sqlSessionFactory
3. SqlSessionFactory: 一般单例,对应一个数据源,用于生产SqlSession
4. SqlSession: 一次数据库会话, 线程不安全, 互相隔离
5. Mapper生成代理类,由命令模式定位代理类具体方法sql,使用SqlSession执行Sql
6. SqlSession组织执行sql的4大组件
### 分析重点 SqlSession运行过程
##### 分析Mapper接口  
0. Mybatis01~03
1. mybatis00~01 (MapperProxy<T>) 根据xml命名空间与接口mapper全路径关联绑定,通过动态代理技术让接口跑起来
2. mybatis02 (MapperMethod)而后采用命令模式,根据上下文决定执行那个方法(sql) , 最后还是使用sqlSession接口的方法执行
3. 补充说明: SqlSession及Mapper关系
   * SqlSession为一次会话,如一个事务,整个流程为一个会话
   * 其中会包含多个Mapper接口,Mapper接口用于执行一条Sql

##### 插播设计模式builder模式
构造复杂对象,入SqlSessionFactory 在构造方法中传入一个configuration对象,从而将构造分层分步处理
在Configuration中一个重要方法newExecutor构建执行器 mybatis03(Configuration)
##### 分析Executor 执行器
1. mybatis04 (SimpleExecutor)
2. 执行器Executor组成:
  * statementHandler  step1预编译sql, step3数据库交互
  * ParameterHandler  step2参数处理
  * ResultHandler     step4结果处理

#### 分析statementHandler 
0. mybatis05 Executor根据Configuration生成statementHandler
1. mybatis06-> RoutingStatementHandler实现 statementHandler 接口 返回需要的Handler
2. mybatis07-> 举例SimpleStatementHandler 真正的sql数据库交互

#### 从插件理解责任链
1. interceptor 
  * plugin() 生成代理对象
  * intercept() 覆盖代理对象原有方法 入参为原有对象
