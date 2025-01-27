package datasecurity.Persistence;

import java.sql.*;
import java.util.Enumeration;

public class DataBaseConnection {
    Connection connection;

    public DataBaseConnection() throws SQLException {


    }



    public String[] getHashedPasswordSalt(String username) throws SQLException {
        String hashedPassword="";
        String salt="";

        try {

            System.setProperty("javax.net.ssl.trustStore", "certificate/dbTrustStore.pfx");
            System.setProperty("javax.net.ssl.trustStorePassword", "group10");
            String dbhost = System.getenv("dbhostIp");
            connection = DriverManager.getConnection("jdbc:mysql://"+dbhost+":3306/db" +
                    "?verifyServerCertificate=true" +
                    "&useSSL=true&" +
                    "requireSSL=true","root","root");
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM User WHERE username =? ");
            ps.setString(1, username);

            ResultSet resset = ps.executeQuery();
            //  System.out.println("Certificate Name: " + connection.getClientInfo());


            while(resset.next()){
                //Code to get hashed password for user username
                hashedPassword = resset.getString("hashedPassword");
                salt = resset.getString("salt");
            }


        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
        connection.endRequest();

        //System.out.println(salt+hashedPassword);
        String[] result = {salt, hashedPassword};
        return result;
    }



}
