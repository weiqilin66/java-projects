# 查询数据库默认存储引擎 default_storage_engine
show variables like '%storage_engine%';
# 查询表相关信息
SHOW TABLE STATUS like 'credit_apply';
# 修改表的存储引擎 效率低 生成使用新建表再insert select*操作 再修改表名
ALTER TABLE credit_apply ENGINE INNODB;
