package datasecurity.services;

import datasecurity.Enum.Role;

import java.io.Serializable;
import java.rmi.Remote;
import java.util.ArrayList;

public interface IAccessControlService extends Serializable, Remote {

    ArrayList<Role> getUserRoles(String username) throws Exception;
}
