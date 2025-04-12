package rmi.service;

import entity.Appointment;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface AppointmentService extends Remote {
    /**
     * 预约挂号
     * @param appointment 预约信息
     * @return 是否成功
     */
    Boolean bookAppointment(Appointment appointment) throws RemoteException;

    /**
     * 根据预约id查询预约信息
     * @param appointmentId 预约id
     * @return 预约信息
     */
    Appointment queryById(Integer appointmentId) throws RemoteException;

    /**
     * 根据患者姓名查询所有预约
     * @param patientName 患者姓名
     * @return 患者的所有预约信息
     */
    List<Appointment> queryByPatient(String patientName) throws RemoteException;

    /**
     * 取消指定预约
     * @param appointmentId 预约id
     * @return 是否取消成功
     */
    Boolean cancelAppointment(Integer appointmentId) throws RemoteException;
}
