import java.sql.*;

public class Application {
    public static void main(String[] args) throws SQLException {
        System.out.println("Hello World! 2");


            //create connection for a server installed in localhost, with a user "root" with no password
            try (Connection conn = DriverManager.getConnection("jdbc:mariadb://localhost/", "root", "teste123")) {
                // create a Statement
                try (Statement stmt = conn.createStatement()) {
                    //execute query
                    try(ResultSet rs = stmt.executeQuery("USE test ")){

                    };

                    try (ResultSet rs = stmt.executeQuery("SELECT * FROM `HELLO`")) {
                        //position result to first
                        rs.first();
                        System.out.println(rs.getString(1)); //result is "Hello World!"
                    }
                }
            }
        }
    }


