/*
Database connection to project
Aun no hace nadaa

 */
package wolff_patient;

import java.sql.*;

public class DBConnection {

    private Connection dbLink;

    public Connection getConnection() {
        String dbName = "demo";
        String dbUser = "demoUser";
        String dbPassword = "demopass";
        String url = "jdbc:mysql://localhost/"+dbName;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            dbLink = DriverManager.getConnection(url, dbUser, dbPassword);
        }catch (Exception e){
            e.printStackTrace();
        }
        return dbLink;
    }

}
