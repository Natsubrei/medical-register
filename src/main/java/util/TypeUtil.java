package util;

import entity.Appointment;
import grpc.service.AppointmentMessage;

import java.sql.Date;
import java.sql.Time;

public class TypeUtil {
    public static Appointment messageToEntity(AppointmentMessage message) {
        if (message.getId() == -1) {
            // 若消息id为-1，则返回空对象
            return null;
        }

        try {
            return Appointment.builder()
                    .id(message.getId())
                    .patientName(message.getPatientName())
                    .doctorName(message.getDoctorName())
                    .department(message.getDepartment())
                    .bookingDate(Date.valueOf(message.getBookingDate()))
                    .bookingTime(Time.valueOf(message.getBookingTime()))
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static AppointmentMessage entityToMessage(Appointment appointment) {
        if (appointment == null) {
            // 若实体类为空，则返回id为-1的空消息
            return AppointmentMessage.newBuilder().setId(-1).build();
        }

        try {
            return AppointmentMessage.newBuilder()
                    .setId(appointment.getId())
                    .setPatientName(appointment.getPatientName())
                    .setDoctorName(appointment.getDoctorName())
                    .setDepartment(appointment.getDepartment())
                    .setBookingDate(appointment.getBookingDate().toString())
                    .setBookingTime(appointment.getBookingTime().toString())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
