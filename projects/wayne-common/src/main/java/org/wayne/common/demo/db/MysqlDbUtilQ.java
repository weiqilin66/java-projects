package org.wayne.common.demo.db;

/**
 * @Description: 多数据源表配置设计
 * @author: lwq
 */
public class MysqlDbUtilQ {
//    private static final Logger log = LoggerFactory.getLogger(MysqlDbUtilQ.class);
//    public static Map<Object, Object> dbSource = new HashMap();
//    public static ConcurrentHashMap<String, JdbcTemplate> jdbcTemplateMap = new ConcurrentHashMap();
//
//    public MysqlDbUtilQ() {
//    }
//
//    public static void initDb(String url, String password, String userName, String dbId, int maxActive, long maxWait, int minIdle) {
//        if (!dbSource.containsKey(dbId)) {
//            DruidDataSource druidDataSource = buildDb(url, password, userName);
//            dbSource.put(dbId, druidDataSource);
//        }
//
//    }
//
//    public static void initDb(String url, String password, String userName, String dbId) {
//        if (!dbSource.containsKey(dbId)) {
//            DruidDataSource druidDataSource = buildDb(url, password, userName);
//            dbSource.put(dbId, druidDataSource);
//        }
//
//    }
//
//    public static void addDb(String dbId, DataSource dataSource) {
//        if (!dbSource.containsKey(dbId)) {
//            dbSource.put(dbId, dataSource);
//        }
//
//    }
//
//    public static JdbcTemplate getTemplate(String dbId) {
//        if (!jdbcTemplateMap.containsKey(dbId)) {
//            if (dbId.indexOf("&") > -1) {
//                String[] dbArr = dbId.split("&");
//                boolean hasDbArr = true;
//                Map<String, DataSource> conactDb = new HashMap();
//                String[] var7 = dbArr;
//                int var6 = dbArr.length;
//
//                for (int var5 = 0; var5 < var6; ++var5) {
//                    String dbArrId = var7[var5];
//                    if (!dbSource.containsKey(dbArrId)) {
//                        hasDbArr = false;
//                        break;
//                    }
//
//                    conactDb.put(dbArrId, (DataSource) dbSource.get(dbArrId));
//                }
//
//                if (!hasDbArr) {
//                    log.error("获取联合数据源配置失败，请检查配置信息表[cis_db_config]，dbId【" + dbId + "】");
//                    return null;
//                }
//
//                DataSource dataSource;
//                // 占时只走else单个数据库,不做分库分表
////                    TableRuleConfiguration tableRuleConfiguration = new TableRuleConfiguration("sys_parametes");
////                    ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
////                    shardingRuleConfiguration.getTableRuleConfigs().add(tableRuleConfiguration);
////                    dataSource = ShardingDataSourceFactory.createDataSource(conactDb, shardingRuleConfiguration, new Properties());
//                dataSource = (DataSource) dbSource.get("");
//                JdbcTemplate JdbcTemplate = new JdbcTemplate();
//                JdbcTemplate.setDataSource(dataSource);
//                jdbcTemplateMap.put(dbId, JdbcTemplate);
//            } else {
//                JdbcTemplate JdbcTemplate = new JdbcTemplate();
//                JdbcTemplate.setDataSource((DruidDataSource) dbSource.get(dbId));
//                jdbcTemplateMap.put(dbId, JdbcTemplate);
//            }
//        }
//
//        return (JdbcTemplate) jdbcTemplateMap.get(dbId);
//    }
//
//    private static DruidDataSource buildDb(String url, String password, String userName, int maxActive, long maxWait, int minIdle) {
//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setUrl(url);
//        druidDataSource.setPassword(password);
//        druidDataSource.setUsername(userName);
//        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        druidDataSource.setMaxActive(maxActive);
//        druidDataSource.setMaxWait(maxWait);
//        druidDataSource.setMinIdle(minIdle);
//        return druidDataSource;
//    }
//
//    private static DruidDataSource buildDb(String url, String password, String userName) {
//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setUrl(url);
//        druidDataSource.setPassword(password);
//        druidDataSource.setUsername(userName);
//        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
//        return druidDataSource;
//    }

}
