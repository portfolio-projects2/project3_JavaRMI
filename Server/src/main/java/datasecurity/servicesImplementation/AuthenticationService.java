package datasecurity.servicesImplementation;


import datasecurity.Persistence.DataBaseConnection;
import datasecurity.domain.RegistryBinder;
import datasecurity.domain.Session;
import datasecurity.model.User;
import datasecurity.services.IAuthenticationService;
import org.bouncycastle.crypto.generators.Argon2BytesGenerator;
import org.bouncycastle.crypto.params.Argon2Parameters;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.SecureRandom;
import java.util.Base64;

import static datasecurity.domain.CryptoUtility.encrypt;
import static datasecurity.domain.CryptoUtility.generateHash;

public class AuthenticationService extends UnicastRemoteObject implements IAuthenticationService, Serializable {
    int identifier;
    public AuthenticationService() throws RemoteException {
        super();
    }

    @Override
    public String authenticate(String username, String password) throws Exception {

        DataBaseConnection dataBaseConnection = new DataBaseConnection();

        String[] HashedPasswordAndSalt = dataBaseConnection.getHashedPasswordSalt(username);
        String hashedPassword = HashedPasswordAndSalt[1];
        String salt = HashedPasswordAndSalt[0];
        System.out.println("Start authenticating User:"+" "+username);

        if(generateHash(password, salt).equals(hashedPassword)){
            System.out.println("User :"+username+" is authenticated");

            //user authenticated
            User authenticatedUser = new User();
            authenticatedUser.setUsername(username);
            SecureRandom secureRandom =SecureRandom.getInstance("SHA1PRNG");
            String cookie= String.valueOf(secureRandom.nextInt());
            String encryptedCookie= encrypt(cookie,"MySecretKey123456789012345678901");
            Session session= new Session(authenticatedUser,cookie);
            RegistryBinder.bindPrintService(cookie);
            RegistryBinder.bindAccessControlService(cookie);
            return encryptedCookie;
        }

        //user not authenticated
        System.out.println("User :"+username+" is not authenticated");
        return "false";
    }





}
