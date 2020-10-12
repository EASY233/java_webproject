package Data;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

public class Config {
    static final String jdburl="jdbc:mysql://localhost/dvwa?useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai";
    static final  String jdbuser = "root";
    static final String jdbpassword = "EASYEASY12345";
    public static DataSource getConnection(){
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(jdburl);
        config.setUsername(jdbuser);
        config.setPassword(jdbpassword);
        config.addDataSourceProperty("cachePrepStmts","true");
        config.addDataSourceProperty("prepStmtCacheSize", "100");
        config.addDataSourceProperty("maximumPoolSize", "100");
        try{
            return (DataSource) new HikariDataSource(config);
        }
        catch (RuntimeException ignored){
            return (DataSource) new HikariDataSource(config);
        }
    }
}
