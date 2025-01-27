package datasecurity.domain;

import datasecurity.model.User;

import java.util.HashMap;
import java.util.Map;

public class Session {

    static HashMap<String,Session> sessionMapper= new HashMap<>();
    User user;
    public boolean isActive = false;

    //how long is the session
    int duration = 2*60*1000;
    long activationTime;

    private datasecurity.model.User User;

    public Session(User user, String refCookie) {
        sessionMapper.put(refCookie,this);
        this.user =user;
        this.activationTime=System.currentTimeMillis();
    }


    public static Session getSession(String cookie){
        for (Map.Entry<String,Session> e:sessionMapper.entrySet()) {
            if (e.getKey().equals(cookie)){
                return e.getValue();
            }
        }
        return null;
    }

    public boolean isTimedOut(){
        //needs session validation here and one step before - rehashing session from client
        long currentTime = System.currentTimeMillis();
        long timeElapsed = currentTime-this.activationTime;
        if(timeElapsed < this.duration){
            this.isActive = true;
            return false;
        }

        return true;
    }

    public void prolongSession(){
        this.activationTime=System.currentTimeMillis();
    }


    public User getUser() {
        return this.user;
    }
}
