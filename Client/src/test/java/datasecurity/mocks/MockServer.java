package datasecurity.mocks;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

import javax.rmi.ssl.SslRMIServerSocketFactory;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
@SpringBootTest
@Component
public class MockServer implements Serializable {

  static  Registry registry;


    public void bindRemoteObject(Remote remoteInterFace, String objectName) throws RemoteException {

        registry.rebind(objectName,remoteInterFace);
    }


    public void initialize_With_SSL(int port) throws RemoteException {
        System.setProperty("javax.net.ssl.keyStorePassword", "group10");
        System.setProperty("javax.net.ssl.keyStore", "SSL_Test_files/keyStoreForTest.pfx");
        MockServerSim m = new MockServerSim();
        Thread t = new Thread(m);
        t.start();
        try {
            registry = LocateRegistry.createRegistry(port, null, new SslRMIServerSocketFactory());

       }catch (Exception e){
           e.printStackTrace();
           t.interrupt();
       }

    }



    public void initialize_Without_SSL(int port) throws RemoteException {
        registry =  LocateRegistry.createRegistry(port);
        MockServerSim m = new MockServerSim();
        Thread t = new Thread(m);
        t.start();
    }


    public void restart() throws RemoteException {
        if (registry!=null) {
            UnicastRemoteObject.unexportObject(registry, true);
            System.out.println("Server restarted");
        }

    }


    public static class MockServiceImplementation extends UnicastRemoteObject implements MockServiceForTest,Serializable {


        public MockServiceImplementation() throws RemoteException {
            super();
        }

        @Override
        public String testMethod(String testParameter) throws RemoteException, SQLException {
            System.out.println("Method invoked");

            return "Method invoked";
        }
    }




    @Component
    public static class MockServerSim implements Runnable {


        @Override
        public void run() {
            boolean active = true;

            while (active) {
                System.out.println("Server is started");

                if (Thread.interrupted()) {
                    active = false;
                    System.out.println("Server is stopped");
                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }
}
