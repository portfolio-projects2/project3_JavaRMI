package datasecurity.session;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.http.HttpSessionListener;
@Configuration
public class SessionManagement {

SessionDestroyhandler sessionDestroyhandler;
    @Autowired
    public SessionManagement(SessionDestroyhandler sessionDestroyhandler) {
        this.sessionDestroyhandler = sessionDestroyhandler;
    }

    @Bean
    public ServletListenerRegistrationBean<HttpSessionListener> sessionListener() {
        ServletListenerRegistrationBean<HttpSessionListener> listenerBean =
                new ServletListenerRegistrationBean<>();
        listenerBean.setListener(sessionDestroyhandler);
        return listenerBean;
    }
}