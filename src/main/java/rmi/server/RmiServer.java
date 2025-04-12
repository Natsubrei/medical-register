package rmi.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RmiServer {
    public static final String RMI_PREFIX = "rmi://localhost/";

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            AppointmentServiceImpl appointmentService = new AppointmentServiceImpl();
            Naming.rebind(RMI_PREFIX + "AppointmentService", appointmentService);
            System.out.println("🚀 RMI Server started on port 1099...");
        } catch (RemoteException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
