package datasecurity.domain;

import datasecurity.Shared.ConsoleColors;
import datasecurity.model.User;
import datasecurity.services.IAccessControlService;
import datasecurity.services.IAuthenticationService;
import datasecurity.servicesImplementation.AccessControlService;
import datasecurity.servicesImplementation.AuthenticationService;
import datasecurity.servicesImplementation.PrintService;

import javax.rmi.ssl.SslRMIServerSocketFactory;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RegistryBinder {

    static Registry registry;

    static {
        try {
            System.setProperty("javax.net.ssl.keyStore", "certificate/RMI_ServerKeyStore.pfx");
            System.setProperty("javax.net.ssl.keyStorePassword", "group10");

            registry = LocateRegistry.createRegistry(1099,null,new SslRMIServerSocketFactory());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public static void bindAuthenticationService() throws RemoteException {
        IAuthenticationService remoteAuthenticationServiceObject = new AuthenticationService();
        registry.rebind("authenticationObject", remoteAuthenticationServiceObject);
        System.out.println("Authentication service is started. You can access it using a JRMP-TLS channel on port 1099\n");

    }

    public static void bindPrintService(String referenceCookie) throws RemoteException {
        PrintService remotePrintServiceObject = new PrintService(referenceCookie);
        registry.rebind("printServiceObject"+referenceCookie, remotePrintServiceObject);
        System.out.println("Print service is ready to use.");

    }

    public static void unBindPrintService(String referenceCookie) throws NotBoundException, RemoteException {
        registry.unbind("printServiceObject"+referenceCookie);
        System.out.println("print service is stopped");
    }

    public static void bindAccessControlService(String referenceCookie) throws IOException {
        IAccessControlService accessControlServiceObject = new AccessControlService();
        registry.rebind("accessControlServiceObject"+referenceCookie, accessControlServiceObject);
        System.out.println("RBAC Access Control Service started,"+ ConsoleColors.ORANGE+"\n Access list is updated dynamically at run-time from \"AccessControlList.json\" file" );
    }
}
