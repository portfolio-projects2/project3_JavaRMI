package datasecurity.unitTests;

import datasecurity.mocks.MockServer;
import datasecurity.mocks.MockServiceForTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.rmi.RemoteException;

@SpringBootTest(classes = {MockServiceForTest.class, MockServer.MockServerSim.class, MockServer.class, MockServer.MockServiceImplementation.class})
public class Authentication_Validation {

@Autowired
MockServer mockServer;


/*
    @Test
    public void invoke_Print_Without_Auth() throws RemoteException {
        mockServer.initialize_Without_SSL(1099);


    }
*/
}

