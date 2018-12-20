package top.poul.utils;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC
 * 
 * @author 杨霄鹏
 * @version 1.0, 2016-11-25
 * @since
 */
public class JDBCUtil {


    /**
     *  快速删除数据库中所有表的方法
     *    use `hrtop_paas`;
     *    set @database_name = 'hrtop_paas';
     *    SELECT
     *    CONCAT('drop table `',@database_name,'`.`', table_name, '`;')
     *    FROM
     *    information_schema.`TABLES`
     *    WHERE
     *    table_schema = @database_name;
     */

//    public static final String ORACLE_CLASS = "oracle.jdbc.driver.OracleDriver";
//    public static final String MYSQL_CLASS = "com.mysql.jdbc.Driver";

    /**
     * 获取一个connection连接
     * @param url        数据库连接地址
     * @param user       数据库账号
     * @param password   数据库密码
     * @param className  数据库class全限定名
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    public static Connection getConnection(String url,String user, String password, DataBaseClassName className)
            throws ClassNotFoundException,SQLException {
        Class.forName(className.getQualifiedName());
        return DriverManager.getConnection(url, user, password);
    }

    /**
     *  获取一个connection连接从druid连接池
     * @param url        数据库连接地址
     * @param user       数据库账号
     * @param password   数据库密码
     * @param className  数据库class全限定名
     * @return
     * @throws SQLException
     */
    public static Connection getConnectionFromDruid(String url,String user, String password,DataBaseClassName className)
            throws SQLException{
        DruidDataSource druidDataSource = getDruidDataSource(url, user, password, className);
        return druidDataSource.getConnection();
    }

    /**
     * 获取druid数据库连接池
     * @param url        数据库连接地址
     * @param user       数据库账号
     * @param password   数据库密码
     * @param className  数据库驱动class全限定名
     * @return
     */
    public static DruidDataSource getDruidDataSource(String url,String user, String password,DataBaseClassName className) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(className.getQualifiedName());
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(user);
        druidDataSource.setPassword(password);

        return druidDataSource;
    }

    /**
     *  关闭一个数据库连接
     * @param connection
     * @throws SQLException
     */
    public static void closeConnection(Connection connection) throws SQLException{
        if (connection != null)
            connection.close();
    }

    /*
     * jdbc工作原理 1. 加载，建立连接 2. 创建语句对象 3. 执行sql语句 4. 处理结果集 5. 关闭连接
     */
    public static void JDBCToDB(String url, String user, String password, DataBaseClassName className) {

        Connection connection = null;
        Statement cs = null;
        PreparedStatement ps = null;
//        String sql = "select * from eo_c_order t where t.eoor_order_no = 'AD16071300023768'";
        String dmlSql = "update EO_C_ORDER set MODIFY_TIME = sysdate where EOOR_ORDER_NO = 'AD16071300023768'";
        Boolean autoCommit = null;
        try {
            Class.forName(className.getQualifiedName());

            connection = DriverManager.getConnection(url, user, password);
            // 设置本连接中事务的隔离级别
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            autoCommit = connection.getAutoCommit();
            System.out.println(autoCommit);
            // 设置是否自动提交
//            connection.setAutoCommit(false);
            autoCommit = connection.getAutoCommit();
            cs = connection.createStatement();
//             
//            boolean execute = cs.execute(sql);
            int executeUpdate = cs.executeUpdate(dmlSql);
            System.out.println(executeUpdate);
//            ResultSet rs = cs.getResultSet();
//            ResultSetMetaData md = rs.getMetaData();
//            int columnCount = md.getColumnCount();
//            ps = connection.prepareStatement(sql);
//            ResultSet rs = ps.executeQuery();

//            int i = 0;
            
//            for (; rs.next(); ){
//                for (int c = 1; c <= columnCount; c++) {
//                    String columnName = md.getColumnName(c);
//                    System.out.print(columnName + "\t");
//                    String string = rs.getString(c);
//                    System.out.println(string);
//                }
//            }
            // 设置保存点
            Savepoint s1 = connection.setSavepoint("s1");
            
            // 会滚到某个保存点
            connection.rollback(s1);
            
            
            
            if (autoCommit )
                throw new RuntimeException("我是故意的");
            if (autoCommit != null && !autoCommit)
                connection.commit();
        } catch (Exception e) { 
            e.printStackTrace();
            try {
                if (autoCommit != null && !autoCommit && connection != null)
                    connection.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }

                if (cs != null) {
                    cs.close();
                }

                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }

    /**
     * 使用jdbc的数据库连接池技术（apache dbcp2）
     * 
     * @param url
     * @param username
     * @param password
     * @param dataClass
     */
    public static void JDBCPToDB(String url, String username, String password, String dataClass) {
        BasicDataSource basicDataSource = null;
        Connection connection = null;
        String sql = "desc ET_HD_ORDER";
        ResultSet rs = null;
        try {
            // 获取连接池
            basicDataSource = new BasicDataSource();

            // 设置连接池属性
            basicDataSource.setDriverClassName(dataClass);
            basicDataSource.setUrl(url);
            basicDataSource.setUsername(username);
            basicDataSource.setPassword(password);

            // The initial number of connections that are created when the pool
            // is started.
            basicDataSource.setInitialSize(1);
            // The maximum number of active connections that can be allocated
            // from this pool at the same time, or negative for no limit.
            basicDataSource.setMaxTotal(20);
            // The maximum number of connections that can remain idle in the
            // pool, without extra ones being released, or negative for no
            // limit.
            basicDataSource.setMaxIdle(19);
            // The minimum number of connections that can remain idle in the
            // pool, without extra ones being created, or zero to create none.
            basicDataSource.setMinIdle(1);
            /*
             * The maximum number of milliseconds that the pool will wait (when
             * there are no available connections) for a connection to be
             * returned before throwing an exception, or -1 to wait
             * indefinitely.
             */
            basicDataSource.setMaxWaitMillis(6000);
            // The default auto-commit state of connections created by this
            // pool. If not set then the setAutoCommit method will not be
            // called.
            // basicDataSource.setDefaultAutoCommit(false);

            connection = basicDataSource.getConnection();

            Statement sc = connection.createStatement();
            rs = sc.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();
            int s = 0;
            List<String> listHeadBuffer = new ArrayList<String>();

            List<List<String>> listOuter = new ArrayList<List<String>>();
            while (rs.next()) {
                List<String> listBodyBuffer = new ArrayList<String>();
                for (int i = 1, length = metaData.getColumnCount(); i <= length; i++) {
                    // 获得所有列的实际数目
                    // 获取指定的列明
                    String columnName = metaData.getColumnName(i);
                    if (s == 0) {
                        listHeadBuffer.add(columnName);
                    }
//                    String columnLabel = metaData.getColumnLabel(i);
//                    int columnDisplaySize = metaData.getColumnDisplaySize(i);
//                    String columnTypeName = metaData.getColumnTypeName(i);
//                    int precision = metaData.getPrecision(i);
//                    int columnType = metaData.getColumnType(i);
//                    int scale = metaData.getScale(i);
//                    String columnClassName = metaData.getColumnClassName(i);

                    System.out.println(columnName);

                    String string = rs.getString(i);
                    listBodyBuffer.add(string);
                    System.out.println(string);
                }
                if (listHeadBuffer != null && s == 0) {
                    listOuter.add(listHeadBuffer);
                }
                if (listBodyBuffer != null && listBodyBuffer.size() > 0) {
                    listOuter.add(listBodyBuffer);
                }

                s++;
                if (s > 10) {
                    System.out.println(listOuter);
                    break;
                }
            }
            
            listHeadBuffer.clear();
            listHeadBuffer = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (basicDataSource != null) {
                    basicDataSource.close();
                }
                if (connection != null) {
                    connection.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {
        String url = "jdbc:oracle:thin:@10.0.254.13:1521:orcl";
        String user = "YAJOMS2C";
        String password = "w1PAJ3";
        JDBCToDB(url, user, password, DataBaseClassName.ORACLE);
    }
    
    
    /**
     * 获取ResultSet中的数据信息
     * @param rs
     * @return
     * @throws SQLException
     * @since 2017年11月9日 下午9:14:38
     */
    public static List<List<String>> extractFromRs(ResultSet rs) throws SQLException {
		ResultSetMetaData metaData = rs.getMetaData();
		int s = 0;
		List<String> listHeadBuffer = new ArrayList<String>();
		List<List<String>> listOuter = new ArrayList<List<String>>();
		String value;
		String columnName;
		// 获取返回的所有的字段名
		int length = metaData.getColumnCount();
		for (int i = 1; i <= length; i++) {
			// 获得所有列的实际数目
			// 获取指定的列明
			columnName = metaData.getColumnName(i);
			listHeadBuffer.add(columnName);

		}
		if (listHeadBuffer != null && s == 0) {
			listOuter.add(listHeadBuffer);
		}
		while (rs.next()) {
			List<String> listBodyBuffer = new ArrayList<String>();
			for (int i = 1; i <= length; i++) {
				value = rs.getString(i);
				listBodyBuffer.add(value);
			}
			if (listBodyBuffer != null && listBodyBuffer.size() > 0) {
				listOuter.add(listBodyBuffer);
			}
		}
		
		return listOuter;
	}

}
