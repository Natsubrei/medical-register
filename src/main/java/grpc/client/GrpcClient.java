package grpc.client;

import com.mysql.cj.util.StringUtils;
import grpc.service.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import util.ConstantUtil;
import util.TypeUtil;

import java.util.Scanner;

public class GrpcClient {
    private static final String HOST = "localhost";
    private static final int PORT = 9999;
    public static final ManagedChannel managedChannel;
    public static final AppointmentServiceGrpc.AppointmentServiceBlockingStub appointmentService;

    static {
        try {
            managedChannel = ManagedChannelBuilder.forAddress(HOST, PORT).usePlaintext().build();
            appointmentService = AppointmentServiceGrpc.newBlockingStub(managedChannel);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            managedChannel.shutdown();
        }
    }

    private static void bookAppointment() {
        Scanner sc = new Scanner(System.in);

        AppointmentMessage message;

        while (true) {
            try {
                System.out.print(ConstantUtil.INPUT_INFO);
                String line = sc.nextLine();
                String[] s = line.split(" ");

                message = AppointmentMessage.newBuilder()
                        .setPatientName(s[0])
                        .setDoctorName(s[1])
                        .setDepartment(s[2])
                        .setBookingDate(s[3])
                        .setBookingTime(s[4])
                        .build();

                // 输入有效则跳出循环
                break;
            } catch (Exception e) {
                System.out.println(ConstantUtil.INVALID_INPUT);
            }
        }

        BookAppointmentRequest request = BookAppointmentRequest.newBuilder().setMessage(message).build();
        BookAppointmentResponse response = appointmentService.bookAppointment(request);
        System.out.println(response.getSuccess() ? ConstantUtil.OPERATION_SUCCESS : ConstantUtil.OPERATION_FAILURE);
    }

    private static void queryAppointment() {
        Scanner sc = new Scanner(System.in);
        System.out.print(ConstantUtil.INPUT_ID_OR_NAME);
        String line = sc.nextLine();

        // 根据输入是否是数字来判断输入的是预约id还是患者姓名
        if (StringUtils.isStrictlyNumeric(line)) {
            QueryByIdRequest request = QueryByIdRequest.newBuilder().setAppointmentId(Integer.parseInt(line)).build();
            QueryByIdResponse response = appointmentService.queryById(request);
            System.out.println(ConstantUtil.BANNER);
            System.out.println(TypeUtil.messageToEntity(response.getMessage()));
        } else {
            QueryByPatientRequest request = QueryByPatientRequest.newBuilder().setPatientName(line).build();
            QueryByPatientResponse response = appointmentService.queryByPatient(request);
            System.out.println(ConstantUtil.BANNER);
            response.getMessagesList().forEach(message -> System.out.println(TypeUtil.messageToEntity(message)));
        }
    }

    private static void cancelAppointment() {
        Scanner sc = new Scanner(System.in);
        System.out.print(ConstantUtil.INPUT_ID);
        String line = sc.nextLine();

        try {
            CancelAppointmentRequest request = CancelAppointmentRequest.newBuilder().setAppointmentId(Integer.parseInt(line)).build();
            CancelAppointmentResponse response = appointmentService.cancelAppointment(request);
            System.out.println(response.getSuccess() ? ConstantUtil.OPERATION_SUCCESS : ConstantUtil.OPERATION_FAILURE);
        } catch (NumberFormatException e) {
            System.out.println(ConstantUtil.INVALID_INPUT);
        }
    }
}
