package datasecurity.integrationTests;

import datasecurity.mocks.MockServer;
import datasecurity.mocks.MockServiceForTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

// This class aims to measure the security of communication channels built in the solution
// It includes testing database and remote communication integration using ssl
//@ExtendWith(SpringExtension.class)

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {MockServiceForTest.class, MockServer.MockServerSim.class, MockServer.class, MockServer.MockServiceImplementation.class})

public final class clientServer_SSL_Verification implements Serializable {

@Autowired
MockServer mockServer;

@BeforeEach
void initializeServer() throws RemoteException, NotBoundException {
   mockServer.restart();
}


@Test
void TestLookUp_without_SSL() throws RemoteException, NotBoundException {
    //Run the server and create a registry without configuring tls
    mockServer.initialize_Without_SSL(1099);
    //bind a remote object on the registry
    MockServiceForTest mockImpl = new MockServer.MockServiceImplementation();
    mockServer.bindRemoteObject(mockImpl,"AuthObject");
    //Retrieve the registry
    Registry registry = LocateRegistry.getRegistry("localhost", 1099);
    //Assert the registry is initialized
    Assertions.assertNotEquals(null,registry);
    // Lookup in the registry
    MockServiceForTest returnedAuthenticationService =(MockServiceForTest) registry.lookup("AuthObject");
    // Assert the remote object exist
    Assertions.assertNotEquals(null,returnedAuthenticationService);
    //Ensure the skeleton returned is from the proxy server and not direct dependency to the server
    Assertions.assertTrue(returnedAuthenticationService.getClass().toString().contains("jdk.proxy"));

}


    @Test
    void TestLookUp_with_SSL() throws RemoteException, NotBoundException, MalformedURLException, SQLException {
        // Add ssl trust store to trust the encrypted socket
        System.setProperty("javax.net.ssl.trustStore", "SSL_Test_files/TrustStoreForTest.pfx");
        System.setProperty("javax.net.ssl.trustStorePassword", "group10");
        //Start the server with encrypted socket.
        // Note: We set the encryption store path in Mock server class methode initialize_With_SSL()
        mockServer.initialize_With_SSL(1099);
        //bind a remote object on the registry
        MockServiceForTest mockImpl = new MockServer.MockServiceImplementation();
        mockServer.bindRemoteObject(mockImpl,"testSSL");
        //Creating Ssl Rmi client and try getting the registry from the encrypted socket
        SslRMIClientSocketFactory s= new SslRMIClientSocketFactory();
        //Retrieve the registry
        Registry registry = LocateRegistry.getRegistry("localhost", 1099,s);
        //Assert the registry is initialized
        Assertions.assertNotEquals(null,registry);
        // Lookup in the registry
        MockServiceForTest returnedService =(MockServiceForTest) registry.lookup("testSSL");
        //Ensure the skeleton returned is from the proxy server and not direct dependency to the server
        Assertions.assertTrue(returnedService.getClass().toString().contains("jdk.proxy"));
        // Check that no exception is thrown when invoking the remote object
        Assertions.assertEquals("Method invoked",returnedService.testMethod("test"));

    }





}
