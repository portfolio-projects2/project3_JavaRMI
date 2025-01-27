package datasecurity.session;

import datasecurity.ClientSecurity.UsersConfig;
import datasecurity.communication.RemoteObjectHandler;
import datasecurity.models.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

@Component
public class SessionDestroyhandler implements HttpSessionListener {
Server server;
UsersConfig usersConfig;
RemoteObjectHandler rm;
    @Autowired
    public SessionDestroyhandler(UsersConfig usersConfig,Server _server,RemoteObjectHandler _rm) {

        this.usersConfig = usersConfig;
        this.server=_server;
        this.rm=_rm;
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {


        //server.setStatus("stop");
        try {
            rm.getRemotePrintServiceObject().timOutSession();
        } catch (MalformedURLException | NotBoundException | RemoteException e) {
          e.printStackTrace();
        }
        usersConfig.seUsername("");
        usersConfig.set_sessionAuthCookie("");
        usersConfig._isActiveSession=false;
        System.out.println("\u001B[31m"+"Session is destroyed"+"\u001B[37m");
        SecurityContextHolder.clearContext();
       // System.out.println(event);
    }



}
