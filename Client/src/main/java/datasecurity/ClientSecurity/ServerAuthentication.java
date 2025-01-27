package datasecurity.ClientSecurity;

import datasecurity.communication.RemoteObjectHandler;
import datasecurity.models.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

import static datasecurity.ClientSecurity.AES_Crypto.decrypt;


public class ServerAuthentication implements AuthenticationProvider {
    @Autowired
    RemoteObjectHandler remoteObjectHandler;
    @Autowired
    UsersConfig userDetails;
    @Autowired
    Server server;


    //@lombok.SneakyThrows
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        boolean authStatus=false;
        try {
            authStatus=isAuthenticationValid(username,password);
        }catch (Exception e){
            e.printStackTrace();
        }

        if (authStatus) {
            try {
                return new UsernamePasswordAuthenticationToken(userDetails, password, remoteObjectHandler.getAuthorities());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            // Authentication failed
            throw new UsernameNotFoundException("Authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

    private boolean isAuthenticationValid(String username, String password) throws Exception {
        String authStatus= remoteObjectHandler.getRemoteAuthenticationObject().authenticate(username,password);

        if(!authStatus.equals("false")){
            userDetails.set_sessionAuthCookie(decrypt(authStatus));
            userDetails.seUsername(username);
            userDetails._isActiveSession=true;

            return true;
        }
        return false;

    }




}
