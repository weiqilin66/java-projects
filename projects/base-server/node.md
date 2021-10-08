### tip

1. 所有http请求都会先进入DispatcherServlet 判断是否登录再进入SecurityMetadataSourceFilter ,CustomUrlDecisionManager判断路径是否有权限
2. 服务端真正500的时候要全局异常处理

### 开发文档

1. department调用存储过程增加(一次请求数据库执行了多条sql逻辑)和删除
2. EmpBasicController 分页查询
3. @JsonFormat(pattern="yyyy-MM-dd",timezone="Asia/Shanghai")
    * 后端返回给前端时注解的字段是 如此 格式
    * 前端给后端传Json时 被注解字段也必须是这个格式
4. 循环列表插入 EmployeeMapper.xml

## 内容转移到