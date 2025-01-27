package datasecurity.services;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IPrintService extends Serializable,Remote {

    /**
     * Starts the print server
     */
    void start()  throws RemoteException, Exception;
    /**
     * Stops the print server
     */
    void stop()  throws RemoteException, Exception;
    /**
     * Stops the print server, clears the print queue and starts the print server again
     */
    void restart()  throws RemoteException, Exception;
    /**
     * Prints file 'filename' on the specified 'printer'
     * @param filename the file name
     * @param printer the printer name
     */
    void print(String filename, String printer)  throws RemoteException, Exception;
    /**
     * Lists the print queue for a given printer on the user's display in lines of the form '< job number >< file name >'
     * @param printer the printer name
     */
    void queue(String printer)  throws RemoteException, Exception;
    /**
     * Moves job to the top of the queue
     * @param printer the printer name
     * @param job the job number
     */
    void topQueue(String printer, int job)  throws RemoteException, Exception;
    /**
     * Prints status of printer on the user's display
     * @param printer the printer name
     */
    void status(String printer)  throws RemoteException, Exception;
    /**
     * Prints the value of the parameter
     * @param parameter value to be printed
     */
    void readConfig(String parameter)  throws RemoteException, Exception;
    /**
     * Sets the parameter on the print server to 'value'
     * @param parameter the parameter to be set
     * @param value the value to be set to the parameter
     */
    void setConfig(String parameter, String value) throws RemoteException, NotBoundException, Exception;


    String getPrintLog() throws  RemoteException;

    void timOutSession() throws  RemoteException;
}

