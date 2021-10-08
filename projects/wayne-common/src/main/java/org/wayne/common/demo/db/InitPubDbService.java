package org.wayne.common.demo.db;

/**
 * @Description:
 * @author: lwq
 */
public class InitPubDbService {
//    private String dbId;
//    private String url;
//    private String username;
//    private String password;
//    private int maxActive;
//    private long maxWait;
//    private int minIdle;
//    private DataSource initDatasource;
//
//    public InitPubDbService() {
//    }
//
//
//    public void init() {
//        if (ObjectUtils.isEmpty(this.initDatasource)) {
//            MysqlDbUtilQ.initDb(this.url, this.password, this.username, this.dbId, this.maxActive, this.maxWait, this.minIdle);
//        } else {
//            MysqlDbUtilQ.addDb(this.dbId, this.initDatasource);
//        }
//
//        JdbcTemplate jdbcTemplate = MysqlDbUtilQ.getTemplate(this.dbId);
//        List<Map<String, Object>> list = jdbcTemplate.queryForList("select dbId,url,password,username from cis_db_config");
//        Iterator var4 = list.iterator();
//
//        while (var4.hasNext()) {
//            Map<String, Object> dbMap = (Map) var4.next();
//            String db_url = String.valueOf(dbMap.get("url"));
//            String db_username = String.valueOf(dbMap.get("username"));
//            String db_password = String.valueOf(dbMap.get("password"));
//            String db_dbId = String.valueOf(dbMap.get("dbId"));
//            MysqlDbUtilQ.initDb(db_url, db_password, db_username, db_dbId);
//        }
//
//    }
//
//    public String getUrl() {
//        return this.url;
//    }
//
//    public void setUrl(String url) {
//        this.url = url;
//    }
//
//    public String getDbId() {
//        return this.dbId;
//    }
//
//    public void setDbId(String dbId) {
//        this.dbId = dbId;
//    }
//
//    public String getUsername() {
//        return this.username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return this.password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public int getMaxActive() {
//        return this.maxActive;
//    }
//
//    public void setMaxActive(int maxActive) {
//        this.maxActive = maxActive;
//    }
//
//    public long getMaxWait() {
//        return this.maxWait;
//    }
//
//    public void setMaxWait(long maxWait) {
//        this.maxWait = maxWait;
//    }
//
//    public int getMinIdle() {
//        return this.minIdle;
//    }
//
//    public void setMinIdle(int minIdle) {
//        this.minIdle = minIdle;
//    }
//
//    public DataSource getInitDatasource() {
//        return this.initDatasource;
//    }
//
//    public void setInitDatasource(DataSource initDatasource) {
//        this.initDatasource = initDatasource;
//    }
}
