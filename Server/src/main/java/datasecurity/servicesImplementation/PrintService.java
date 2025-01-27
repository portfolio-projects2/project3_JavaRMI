package datasecurity.servicesImplementation;




import datasecurity.Exception.SessionTimedOutException;
import datasecurity.Exception.UnauthorizedException;
import datasecurity.Shared.ConsoleColors;
import datasecurity.domain.AccessManager;
import datasecurity.domain.RegistryBinder;
import datasecurity.domain.Session;
import datasecurity.model.PrintJob;
import datasecurity.model.Printer;
import datasecurity.services.IPrintService;
import datasecurity.Enum.Role;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class PrintService extends UnicastRemoteObject  implements IPrintService {
    private List<Printer> printers;
    private Map<String, String> configurations;
    private String log;
    private int printJobCount;
    String remoteObjectLocalReference;

    public  PrintService(String refCookie) throws RemoteException {
        super();
        this.remoteObjectLocalReference=refCookie;
        log="";
        printers = new ArrayList<>();
        configurations = new HashMap<>();
        printJobCount = 0;
        //Add printers
        printers.add(new Printer("Printer1"));
        printers.add(new Printer("Printer2"));
        printers.add(new Printer("Printer3"));
    }

    @Override
    public void start() throws Exception{
        ArrayList<Role> acceptedRoles= AccessManager.getAcceptedRoles("start");
        validateSession();
        validateAccess(acceptedRoles);
        String username = Session.getSession(this.remoteObjectLocalReference).getUser().getUsername();
        log="Print server started\n";
        System.out.println(ConsoleColors.GREEN + "Print server started by user :" + username );
        setAllPrinterStatus(Printer.Status.ON);
    }

    @Override
    public void stop() throws Exception {
        ArrayList<Role> acceptedRoles= AccessManager.getAcceptedRoles("stop");
        validateSession();
        validateAccess(acceptedRoles);
        String username = Session.getSession(this.remoteObjectLocalReference).getUser().getUsername();
        System.out.println(ConsoleColors.RED + "Print server stopped by user :" + username);
        setAllPrinterStatus(Printer.Status.OFF);
        log+="Print server stopped\n";
    }


    @Override
    public void restart() throws Exception {
        ArrayList<Role> acceptedRoles= AccessManager.getAcceptedRoles("restart");
        validateSession();
        validateAccess(acceptedRoles);
        String username = Session.getSession(this.remoteObjectLocalReference).getUser().getUsername();
        log = "Print server restarted";
        System.out.println(ConsoleColors.ORANGE + "Print server is restarting  by user :" + Session.getSession(this.remoteObjectLocalReference).getUser().getUsername());
        stop();
        setAllPrinterStatus(Printer.Status.RESTARTING);
        for (Printer printer : printers) {
            //Clear queue of all printers
            printer.clearQueue();
        }
        start();
    }

    @Override
    public void print(String filename, String printer) throws Exception {
        ArrayList<Role> acceptedRoles= AccessManager.getAcceptedRoles("print");
        validateSession();
        validateAccess(acceptedRoles);
        String username = Session.getSession(this.remoteObjectLocalReference).getUser().getUsername();
        printJobCount++;
        Printer p = findPrinter(printer);
        if(p != null)
            p.setStatus(Printer.Status.ON);
        p.addToQueue(new PrintJob(printJobCount, filename));

        log += printer+" starts printing file: "+filename+"\n";
        System.out.println(ConsoleColors.GREEN +printer+" starts printing file: "+filename +" by user :" + username);
    }


    @Override
    public void queue(String printer) throws Exception {
        ArrayList<Role> acceptedRoles= AccessManager.getAcceptedRoles("queue");
        validateSession();
        validateAccess(acceptedRoles);

        String username = Session.getSession(this.remoteObjectLocalReference).getUser().getUsername();
        Printer p = findPrinter(printer);
        if(p != null){
            log += "Queue for "+printer+":\n"+ p.getQueue();
        }
    }

    @Override
    public void topQueue(String printer, int job)throws Exception {
        ArrayList<Role> acceptedRoles= AccessManager.getAcceptedRoles("top_Queue");
        validateSession();
        validateAccess(acceptedRoles);
        String username = Session.getSession(this.remoteObjectLocalReference).getUser().getUsername();
        Printer p = findPrinter(printer);
        if(p != null){
            log += "Job: <"+ job + "> is put on the top of the queue for printer :"+printer+"\n";
            p.addToTopOfQueue(job);
        }
    }

    @Override
    public void status(String printer) throws Exception {
        ArrayList<Role> acceptedRoles= AccessManager.getAcceptedRoles("status");
        validateSession();
        validateAccess(acceptedRoles);

        String username = Session.getSession(this.remoteObjectLocalReference).getUser().getUsername();
        Printer p = findPrinter(printer);
        if(p != null){
            log += "The status of " + printer + " is: " + p.getStatus()+"\n";
            System.out.println(ConsoleColors.GREEN + "The status of " + printer + " is: " + p.getStatus());}
    }

    @Override
    public void readConfig(String parameter) throws Exception{
        ArrayList<Role> acceptedRoles= AccessManager.getAcceptedRoles("read_Config");
        validateSession();
        validateAccess(acceptedRoles);
        String username = Session.getSession(this.remoteObjectLocalReference).getUser().getUsername();
        log += "Configuration for Parameter: " +parameter + ", Value: " + configurations.get(parameter)+"\n";
        System.out.println(ConsoleColors.GREEN + "Parameter: " +parameter + ", Value: " + configurations.get(parameter)+". by user :" + username);
    }

    @Override
    public void setConfig(String parameter, String value) throws Exception {
        ArrayList<Role> acceptedRoles= AccessManager.getAcceptedRoles("set_Config");
        validateSession();
        validateAccess(acceptedRoles);

        String username = Session.getSession(this.remoteObjectLocalReference).getUser().getUsername();
        log += "Parameter: "+parameter+", Value: "+value+"\n";
        System.out.println("configurations for parameter"+parameter+". by user :" + username);
        configurations.put(parameter, value);
    }


    @Override
    public String getPrintLog(){
        return this.log;
    }

    @Override
    public void timOutSession() throws RemoteException {
        Session.getSession(this.remoteObjectLocalReference).isActive=false;
    }


    public Printer findPrinter(String printerName) {
        for (Printer p : printers) {
            if(p.getName().equals(printerName)) {
                return p;
            }
        }
        return null;
    }

    public void setAllPrinterStatus(Printer.Status status) {
        for (Printer printer : printers) {
            printer.setStatus(status);
        }
    }

    private void validateSession() throws Exception {
        Session session=Session.getSession(this.remoteObjectLocalReference);

        if (session.isTimedOut()){
            RegistryBinder.unBindPrintService(this.remoteObjectLocalReference);
            System.out.println("Session is timed out\n"+"Print service is down");
            throw new SessionTimedOutException("The session is timed out, please log-in again");
        }else {
            session.prolongSession();
        }
    }

    private void validateAccess(ArrayList<Role> acceptedRoles) throws Exception {
        ArrayList<Role>userRoles= new ArrayList<>(AccessManager.loadUserRoles(this.remoteObjectLocalReference));
        try {
            userRoles.retainAll(acceptedRoles);
        }catch (Exception e) {
            e.printStackTrace();
        }
        if (userRoles.isEmpty()){
            throw new UnauthorizedException("Your role does not permit you to perform this operation, please contact the service administrator");
        }
    }



}



