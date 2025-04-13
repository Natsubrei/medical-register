package rmi.server;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class RmiServer {
    public static final String RMI_PREFIX = "rmi://localhost/";
    public static final int PORT = 1099;

    public static void main(String[] args) {
        try {
            // 启动RMI注册表
            LocateRegistry.createRegistry(PORT);

            // 创建AppointmentService接口的实现对象
            AppointmentServiceImpl appointmentService = new AppointmentServiceImpl();

            // 将实现对象绑定到RMI注册表中，使客户端可以通过网络访问
            Naming.rebind(RMI_PREFIX + "AppointmentService", appointmentService);

            System.out.println("🚀 RMI Server started on port " + PORT);
        } catch (RemoteException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
}
