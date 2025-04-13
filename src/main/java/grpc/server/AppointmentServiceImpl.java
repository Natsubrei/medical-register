package grpc.server;

import grpc.service.*;
import entity.Appointment;
import io.grpc.stub.StreamObserver;
import mapper.AppointmentMapper;
import org.apache.ibatis.session.SqlSession;
import util.MapperUtil;
import util.TypeUtil;

import java.util.List;

public class AppointmentServiceImpl extends AppointmentServiceGrpc.AppointmentServiceImplBase {
    @Override
    public void bookAppointment(BookAppointmentRequest request, StreamObserver<BookAppointmentResponse> responseObserver) {
        BookAppointmentResponse response;

        AppointmentMessage message = request.getMessage();
        Appointment appointment = TypeUtil.messageToEntity(message);

        try (SqlSession session = MapperUtil.openSession()) {
            AppointmentMapper appointmentMapper = MapperUtil.getMapper(AppointmentMapper.class, session);
            Boolean result = appointmentMapper.insert(appointment);
            session.commit();
            response = BookAppointmentResponse.newBuilder().setSuccess(result).build();
        } catch (Exception e) {
            responseObserver.onError(e);
            return;
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void queryById(QueryByIdRequest request, StreamObserver<QueryByIdResponse> responseObserver) {
        QueryByIdResponse response;

        Integer appointmentId = request.getAppointmentId();

        try (SqlSession session = MapperUtil.openSession()) {
            AppointmentMapper appointmentMapper = MapperUtil.getMapper(AppointmentMapper.class, session);
            Appointment appointment = appointmentMapper.getById(appointmentId);
            AppointmentMessage message = TypeUtil.entityToMessage(appointment);
            response = QueryByIdResponse.newBuilder().setMessage(message).build();
        } catch (Exception e) {
            responseObserver.onError(e);
            return;
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void queryByPatient(QueryByPatientRequest request, StreamObserver<QueryByPatientResponse> responseObserver) {
        QueryByPatientResponse response;

        String patientName = request.getPatientName();

        try (SqlSession session = MapperUtil.openSession()) {
            AppointmentMapper appointmentMapper = MapperUtil.getMapper(AppointmentMapper.class, session);
            List<Appointment> appointments = appointmentMapper.getByPatientName(patientName);
            List<AppointmentMessage> list = appointments.stream().map(TypeUtil::entityToMessage).toList();
            response = QueryByPatientResponse.newBuilder().addAllMessages(list).build();
        } catch (Exception e) {
            responseObserver.onError(e);
            return;
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void cancelAppointment(CancelAppointmentRequest request, StreamObserver<CancelAppointmentResponse> responseObserver) {
        CancelAppointmentResponse response;

        Integer appointmentId = request.getAppointmentId();

        try (SqlSession session = MapperUtil.openSession()) {
            AppointmentMapper appointmentMapper = MapperUtil.getMapper(AppointmentMapper.class, session);
            Boolean result = appointmentMapper.removeById(appointmentId);
            session.commit();
            response = CancelAppointmentResponse.newBuilder().setSuccess(result).build();
        } catch (Exception e) {
            responseObserver.onError(e);
            return;
        }

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
