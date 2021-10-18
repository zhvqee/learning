package question5.com.qee.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @ProjectName: learning
 * @Package: question5.com.qee.jdbc
 * @ClassName: Test
 * @Description:
 * @Date: 2021/10/15 3:57 下午
 * @Version: 1.0
 */
public class Test {

    private static final String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/test?useServerPrepStmts=true&zeroDateTimeBehavior=convertToNull&characterEncoding=utf8";

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(jdbcUrl, "root", "root");
        Statement statement = connection.createStatement();
        PreparedStatement preparedStatement = connection.prepareStatement();
        preparedStatement

    }
}
