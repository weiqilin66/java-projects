package org.wayne.source.mybatis;

// line45 举例查询 stament执行预编译好的sql,resultSetHandler对结果集进行映射封装






public class Mybatis07 {
    /*public class SimpleStatementHandler extends BaseStatementHandler {

        public SimpleStatementHandler(Executor executor, MappedStatement mappedStatement, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, BoundSql boundSql) {
            super(executor, mappedStatement, parameter, rowBounds, resultHandler, boundSql);
        }

        @Override
        public int update(Statement statement) throws SQLException {
            String sql = boundSql.getSql();
            Object parameterObject = boundSql.getParameterObject();
            KeyGenerator keyGenerator = mappedStatement.getKeyGenerator();
            int rows;
            if (keyGenerator instanceof Jdbc3KeyGenerator) {
                statement.execute(sql, Statement.RETURN_GENERATED_KEYS);
                rows = statement.getUpdateCount();
                keyGenerator.processAfter(executor, mappedStatement, statement, parameterObject);
            } else if (keyGenerator instanceof SelectKeyGenerator) {
                statement.execute(sql);
                rows = statement.getUpdateCount();
                keyGenerator.processAfter(executor, mappedStatement, statement, parameterObject);
            } else {
                statement.execute(sql);
                rows = statement.getUpdateCount();
            }
            return rows;
        }

        @Override
        public void batch(Statement statement) throws SQLException {
            String sql = boundSql.getSql();
            statement.addBatch(sql);
        }

        @Override
        public <E> List<E> query(Statement statement, ResultHandler resultHandler) throws SQLException {
            String sql = boundSql.getSql();
            statement.execute(sql);
            return resultSetHandler.handleResultSets(statement);
        }

        @Override
        public <E> Cursor<E> queryCursor(Statement statement) throws SQLException {
            String sql = boundSql.getSql();
            statement.execute(sql);
            return resultSetHandler.handleCursorResultSets(statement);
        }

        @Override
        protected Statement instantiateStatement(Connection connection) throws SQLException {
            if (mappedStatement.getResultSetType() == ResultSetType.DEFAULT) {
                return connection.createStatement();
            } else {
                return connection.createStatement(mappedStatement.getResultSetType().getValue(), ResultSet.CONCUR_READ_ONLY);
            }
        }

        @Override
        public void parameterize(Statement statement) {
            // N/A
        }

    }*/
}
