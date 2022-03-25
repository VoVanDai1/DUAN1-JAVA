package helper;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;

/**
 *
 * @author ACER
 */
public class DatabaseHelper {

//    public static void main(String[] args) throws Exception {
//        Connection conn = openConnection();
//            if (conn != null) {
//                System.out.println("Connected");
//                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
//                System.out.println("Driver name: " + dm.getDriverName());
//                System.out.println("Driver version: " + dm.getDriverVersion());
//                System.out.println("Product name: " + dm.getDatabaseProductName());
//                System.out.println("Product version: " + dm.getDatabaseProductVersion());
//            }
//    }
    /**
     * Kết nối CSDL trả về Connection
     * @return 
     * @throws java.lang.Exception
     */
    public static Connection openConnection() throws Exception {
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        String connectionUrl = "jdbc:sqlserver://localhost;database=DUAN1";
        String username = "sa";
        String password = "sa";
        Connection conn = DriverManager.getConnection(connectionUrl, username, password);
        return conn;
    }
}
