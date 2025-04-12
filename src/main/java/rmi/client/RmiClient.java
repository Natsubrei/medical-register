package rmi.client;

import com.mysql.cj.util.StringUtils;
import entity.Appointment;
import rmi.service.AppointmentService;

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
            System.out.print("""
                    === 医疗挂号预约系统 ===
                    1 预约挂号
                    2 查询信息
                    3 取消预约
                    4 退出系统
                    请选择>\s
                    """);
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
                    System.out.println("请输入正确的数字！");
                    break;
            }
        }
    }

    private static void bookAppointment() throws RemoteException {
        Scanner sc = new Scanner(System.in);

        Appointment appointment;

        while (true) {
            try {
                System.out.print("""
                请按以下格式输入预约信息：
                [患者姓名] [医生姓名] [科室名称] [预约日期] [预约时间段]
                请输入>\s
                """);
                String line = sc.nextLine();
                String[] s = line.split(" ");

                appointment = Appointment.builder()
                        .patientName(s[0])
                        .doctorName(s[1])
                        .department(s[2])
                        .bookingDate(Date.valueOf(s[3]))
                        .bookingTime(Time.valueOf(s[4]))
                        .build();
                break;
            } catch (Exception e) {
                System.out.println("请输入正确的预约信息！");
            }
        }

        Boolean result = appointmentService.bookAppointment(appointment);
        System.out.println(result ? "预约成功！" : "预约失败！");
    }

    private static void queryAppointment() throws RemoteException {
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入预约id或患者姓名> ");
        String line = sc.nextLine();

        if (StringUtils.isStrictlyNumeric(line)) {
            Appointment appointment = appointmentService.queryById(Integer.valueOf(line));
            System.out.println("id\t\t患者姓名\t\t医生姓名\t\t科室名称\t\t预约日期\t\t预约时间段");
            System.out.println(appointment);
        } else {
            List<Appointment> appointments = appointmentService.queryByPatient(line);
            System.out.println("id\t\t患者姓名\t\t医生姓名\t\t科室名称\t\t预约日期\t\t预约时间段");
            appointments.forEach(System.out::println);
        }
    }

    private static void cancelAppointment() throws RemoteException {
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入预约id> ");
        String line = sc.nextLine();

        try {
            Boolean result = appointmentService.cancelAppointment(Integer.valueOf(line));
            System.out.println(result ? "取消成功！" : "取消失败！");
        } catch (NumberFormatException e) {
            System.out.println("请输入正确的预约id！");
        }
    }
}
