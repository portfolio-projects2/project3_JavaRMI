package datasecurity.models;

import org.springframework.stereotype.Component;

@Component
public class Server {
    String status;
public Server(){
    status="stop";
}
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
