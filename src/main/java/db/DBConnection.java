package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {





        private static final String url="jdbc:mysql://localhost:3306/smart_atm";
        private static final String user="root";
        private static final String password="Juned@2003";
        public static Connection getConnection(){

            Connection con=null;
            try{
                Class.forName("com.mysql.cj.jdbc.Driver");
                con= DriverManager.getConnection(url,user,password);

            }
            catch(ClassNotFoundException | SQLException e){
                e.printStackTrace();
            }
            return con;
        }
}
