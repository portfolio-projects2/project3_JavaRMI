package datasecurity.integrationTests;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;
import org.springframework.boot.test.context.SpringBootTest;
import java.sql.*;

// To perform tests in this class the database server should be up.
// The dockerCompose file will be run automatically when starting the server application
// If not:
// You need to execute the dockerCompose file in files directory
// The server runs on port 3306, make sure no other services running on the port

@SpringBootTest
public class Database_SSL_Verification {



    // In this test we try to connect to the database with setting the useSSL property to false
    // test result passed if the connection throws an exception
    @Test
    public void connect_Database_without_SSL(){
        Connection connection;
        try {
            Assertions.assertThrows(java.sql.SQLException.class,
                    (Executable) DriverManager.getConnection("jdbc:mysql://localhost:3306/db?" +
                            // Setting the SSL property to false
                                    "useSSL=false"
                            ,"root"
                            ,"root"));
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }


    @Test

    public void connect_Database_with_SSL()  {


        // Setting SSL configurations
        System.setProperty("javax.net.ssl.trustStore", "SSL_Test_files/DatabaseTrustStoreForTest.pfx");
        System.setProperty("javax.net.ssl.trustStorePassword", "group10");
        ;
        try {

            Connection connection= DriverManager.getConnection("jdbc:mysql://localhost:3306/db?" +
                            // Setting the SSL property to true
                            "useSSL=true"
                            ,"root"
                            ,"root");

            //get value from database
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM User");
            //Assert value returned
            Assertions.assertNotEquals(null,resultSet);
            System.out.println("Test2 passed");

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

}
