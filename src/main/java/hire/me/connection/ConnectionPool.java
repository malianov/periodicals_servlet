package hire.me.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {
    private static final Logger logger = LogManager.getLogger(ConnectionPool.class);
    private static volatile HikariDataSource dataSource;

    private static DataSource getDataSource() {
        if (dataSource == null) {
            synchronized (ConnectionPool.class) {
                if (dataSource == null) {
                    HikariConfig config = new HikariConfig("/db.properties");
                    logger.trace("Hikari pool created successfully");
                    dataSource = new HikariDataSource(config);
                }
            }
        }
        return dataSource;
    }

    public static Connection getConnection() {
        try {
            logger.trace("Connection providing");
            return getDataSource().getConnection();
        } catch (SQLException e) {
            logger.error("Hasn't found connection with database");
            throw new RuntimeException("Hasn't found connection with database");
        }
    }
}
