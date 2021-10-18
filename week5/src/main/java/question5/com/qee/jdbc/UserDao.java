package question5.com.qee.jdbc;

import java.sql.SQLException;

/**
 * @ProjectName: learning
 * @Package: question5.com.qee.jdbc
 * @ClassName: UserDao
 * @Description:
 * @Date: 2021/10/18 7:24 下午
 * @Version: 1.0
 */
public interface UserDao {


    int add(User user) throws SQLException;


    int remove(Long id);


    int update(User user);

    User get(Long id);
}
