package datasecurity.mocks;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.SQLException;

    public interface MockServiceForTest extends Serializable, Remote {

        String testMethod(String testParameter) throws RemoteException, SQLException;


}
