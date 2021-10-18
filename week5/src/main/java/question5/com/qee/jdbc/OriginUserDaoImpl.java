package question5.com.qee.jdbc;

import java.sql.Connection;
import java.sql.Statement;

/**
 * @ProjectName: learning
 * @Package: question5.com.qee.jdbc
 * @ClassName: OriginUserDaoImpl
 * @Description:
 * @Date: 2021/10/18 7:25 下午
 * @Version: 1.0
 */
public class OriginUserDaoImpl implements UserDao {


    @Override
    public int add(User user) {
        Connection connection = ConnectionFactory.getConnection();
        Statement statement = connection.createStatement();
        String insertSql = "insert into tb_user(name,feature) values('#name','#feature')";
        insertSql = insertSql.replaceFirst("#name", user.getName());
        insertSql.replaceFirst("#feature", user.getFeature());
        boolean ret = false;
        try {
            ret = statement.execute(insertSql);
            statement.close();
        } catch (Exception e) {

        }finally {
           statement.close();
           ConnectionFactory.close(connection);

        }
        return ret ? 1 : 0;
    }

    @Override
    public int remove(Long id) {
        Connection connection = ConnectionFactory.getConnection();
        Statement statement = connection.createStatement();

        return 0;
    }

    @Override
    public int update(User user) {
        return 0;
    }

    @Override
    public User get(Long id) {
        return null;
    }
}
