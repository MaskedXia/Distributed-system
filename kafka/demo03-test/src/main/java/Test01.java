import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Test01 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://192.168.80.128:3306/atguigudb?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull";
        String user = "root";
        String password = "123456";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            if (connection != null) {
                System.out.println("Connection successful!");
            } else {
                System.out.println("Failed to connect!");
            }
        } catch (SQLException e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }
}
