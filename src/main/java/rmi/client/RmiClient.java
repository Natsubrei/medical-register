package rmi.client;

import com.mysql.cj.util.StringUtils;
import entity.Appointment;
import rmi.service.AppointmentService;
import util.ConstantUtil;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Scanner;

public class RmiClient {
    public static final String RMI_PREFIX = "rmi://localhost/";
    public static final AppointmentService appointmentService;

    static {
        try {
            appointmentService = (AppointmentService) Naming.lookup(RMI_PREFIX + "AppointmentService");
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws RemoteException {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print(ConstantUtil.MENU);
            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    bookAppointment();
                    break;
                case "2":
                    queryAppointment();
                    break;
                case "3":
                    cancelAppointment();
                    break;
                case "4":
                    return;
                default:
                    System.out.println(ConstantUtil.INVALID_INPUT);
                    break;
            }
        }
    }

    private static void bookAppointment() throws RemoteException {
        Scanner sc = new Scanner(System.in);

        Appointment appointment;

        while (true) {
            try {
                System.out.print(ConstantUtil.INPUT_INFO);
                String line = sc.nextLine();
                String[] s = line.split(" ");

                appointment = Appointment.builder()
                        .patientName(s[0])
                        .doctorName(s[1])
                        .department(s[2])
                        .bookingDate(Date.valueOf(s[3]))
                        .bookingTime(Time.valueOf(s[4]))
                        .build();

                // 输入有效则跳出循环
                break;
            } catch (Exception e) {
                System.out.println(ConstantUtil.INVALID_INPUT);
            }
        }

        Boolean result = appointmentService.bookAppointment(appointment);
        System.out.println(result ? ConstantUtil.OPERATION_SUCCESS : ConstantUtil.OPERATION_FAILURE);
    }

    private static void queryAppointment() throws RemoteException {
        Scanner sc = new Scanner(System.in);
        System.out.print(ConstantUtil.INPUT_ID_OR_NAME);
        String line = sc.nextLine();

        // 根据输入是否是数字来判断输入的是预约id还是患者姓名
        if (StringUtils.isStrictlyNumeric(line)) {
            Appointment appointment = appointmentService.queryById(Integer.valueOf(line));
            System.out.println(ConstantUtil.BANNER);
            System.out.println(appointment);
        } else {
            List<Appointment> appointments = appointmentService.queryByPatient(line);
            System.out.println(ConstantUtil.BANNER);
            appointments.forEach(System.out::println);
        }
    }

    private static void cancelAppointment() throws RemoteException {
        Scanner sc = new Scanner(System.in);
        System.out.print(ConstantUtil.INPUT_ID);
        String line = sc.nextLine();

        try {
            Boolean result = appointmentService.cancelAppointment(Integer.valueOf(line));
            System.out.println(result ? ConstantUtil.OPERATION_SUCCESS : ConstantUtil.OPERATION_FAILURE);
        } catch (NumberFormatException e) {
            System.out.println(ConstantUtil.INVALID_INPUT);
        }
    }
}
