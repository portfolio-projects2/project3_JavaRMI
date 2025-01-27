package datasecurity.servicesImplementation;

import datasecurity.Enum.Role;
import datasecurity.domain.AccessManager;
import datasecurity.services.IAccessControlService;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import static datasecurity.domain.CryptoUtility.decrypt;

public class AccessControlService extends UnicastRemoteObject implements IAccessControlService, Serializable {

    public AccessControlService() throws IOException {
        super();
    }

    @Override
    public ArrayList<Role> getUserRoles(String encryptedUserCookie) throws Exception {
        return AccessManager.loadUserRoles(decrypt(encryptedUserCookie,"MySecretKey123456789012345678901"));

    }



}
