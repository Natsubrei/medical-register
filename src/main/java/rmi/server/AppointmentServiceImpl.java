package rmi.server;

import entity.Appointment;
import mapper.AppointmentMapper;
import org.apache.ibatis.session.SqlSession;
import rmi.service.AppointmentService;
import util.MapperUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class AppointmentServiceImpl extends UnicastRemoteObject implements AppointmentService {
    protected AppointmentServiceImpl() throws RemoteException {
        super();
    }

    /**
     * 预约挂号
     * @param appointment 预约信息
     * @return 是否成功
     */
    @Override
    public Boolean bookAppointment(Appointment appointment) throws RemoteException {
        try (SqlSession session = MapperUtil.openSession()) {
            AppointmentMapper appointmentMapper = MapperUtil.getMapper(AppointmentMapper.class, session);
            Boolean result = appointmentMapper.insert(appointment);
            session.commit();
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据预约id查询预约信息
     * @param appointmentId 预约id
     * @return 预约信息
     */
    @Override
    public Appointment queryById(Integer appointmentId) throws RemoteException {
        try (SqlSession session = MapperUtil.openSession()) {
            AppointmentMapper appointmentMapper = MapperUtil.getMapper(AppointmentMapper.class, session);
            return appointmentMapper.getById(appointmentId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据患者姓名查询所有预约
     * @param patientName 患者姓名
     * @return 患者的所有预约信息
     */
    @Override
    public List<Appointment> queryByPatient(String patientName) throws RemoteException {
        try (SqlSession session = MapperUtil.openSession()) {
            AppointmentMapper appointmentMapper = MapperUtil.getMapper(AppointmentMapper.class, session);
            return appointmentMapper.getByPatientName(patientName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 取消指定预约
     * @param appointmentId 预约id
     * @return 是否取消成功
     */
    @Override
    public Boolean cancelAppointment(Integer appointmentId) throws RemoteException {
        try (SqlSession session = MapperUtil.openSession()) {
            AppointmentMapper appointmentMapper = MapperUtil.getMapper(AppointmentMapper.class, session);
            Boolean result = appointmentMapper.removeById(appointmentId);
            session.commit();
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
