package datasecurity.services;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

public interface IAuthenticationService extends Serializable, Remote {

    String authenticate(String username, String password) throws Exception;

}
