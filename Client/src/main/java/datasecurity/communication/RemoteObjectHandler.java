package datasecurity.communication;

import datasecurity.ClientSecurity.ServerAuthentication;
import datasecurity.ClientSecurity.UsersConfig;
import datasecurity.Enum.Role;
import datasecurity.services.IAccessControlService;
import datasecurity.services.IAuthenticationService;
import datasecurity.services.IPrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;


import javax.rmi.ssl.SslRMIClientSocketFactory;
import java.io.*;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMIClientSocketFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static datasecurity.ClientSecurity.AES_Crypto.encrypt;


@Component
public class RemoteObjectHandler {
    Registry registry;
    IAuthenticationService authenticationService;
    IPrintService printService ;
    IAccessControlService accessControlService;

    @Autowired
    UsersConfig userDetails;

    public RemoteObjectHandler( ) throws IOException, NotBoundException, RemoteException {

        System.setProperty("javax.net.ssl.trustStore", "certificate/RMI_ClientTrustStore.pfx");
        System.setProperty("javax.net.ssl.trustStorePassword", "group10");
        String host = System.getenv("hostIp");
        RMIClientSocketFactory clientFactory = new SslRMIClientSocketFactory();
        registry = LocateRegistry.getRegistry(host, 1099,clientFactory);
        authenticationService =(IAuthenticationService) registry.lookup("authenticationObject");
    }

    public IAuthenticationService getRemoteAuthenticationObject() throws MalformedURLException, NotBoundException, RemoteException {

        return authenticationService;

    }
    public IPrintService getRemotePrintServiceObject() throws MalformedURLException, NotBoundException, RemoteException {
        printService= (IPrintService)registry.lookup("printServiceObject"+userDetails.get_sessionAuthCookie());

        return printService;

    }

    public IAccessControlService getAccessControlServiceObject() throws NotBoundException, RemoteException {
        accessControlService = (IAccessControlService)registry.lookup("accessControlServiceObject"+userDetails.get_sessionAuthCookie());
        return accessControlService;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() throws Exception {
        ArrayList<Role> accessList;
        try {

            accessList = getAccessControlServiceObject().getUserRoles(encrypt(userDetails.get_sessionAuthCookie()));
            userDetails.userRole= Role.fromString(accessList.get(0).toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();

        for (Role role : accessList) {
            authorities.add(new SimpleGrantedAuthority(role.toString()));
        }
        return authorities;
    }
}



