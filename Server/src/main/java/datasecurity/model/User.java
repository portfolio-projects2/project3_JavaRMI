package datasecurity.model;

import datasecurity.Enum.Role;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String username;

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }


}
