package org.com.Priyansh;


import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    static Connection connection = null;

    public static Connection getConnection() {
        if(connection!=null){
            return connection;
        }
        String db = "sql12579765";
        String user = "sql12579765";
        String pwd = "FRF7cuVGuU";
        return getConnection(db, user, pwd);
    }
    private static Connection getConnection(String db, String user, String pwd){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://sql12.freesqldatabase.com/"+db+"?user="+user+"&password="+pwd);
        }
        catch(Exception exception){
            exception.printStackTrace();
        }
        return connection;
    }

}
