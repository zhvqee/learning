package question5.com.qee.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @ProjectName: learning
 * @Package: question5.com.qee.jdbc
 * @ClassName: ConnectionFactory
 * @Description:
 * @Date: 2021/10/18 7:33 下午
 * @Version: 1.0
 */
public class ConnectionFactory {


    private ArrayBlockingQueue<Connection> connections = new ArrayBlockingQueue<>(4);

    private static final String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/test?useServerPrepStmts=true&zeroDateTimeBehavior=convertToNull&characterEncoding=utf8";

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void executeSelect() {
        connections.off

    }

    public static Connection getConnection() {


        try {
            return DriverManager.getConnection(jdbcUrl, "root", "root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void close(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
