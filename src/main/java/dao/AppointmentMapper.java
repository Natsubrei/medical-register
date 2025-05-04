package dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AppointmentMapper {
    @Insert("INSERT INTO appointment (patient_name, doctor_name, department, booking_date, booking_time)" +
            "VALUES (#{patientName}, #{doctorName}, #{department}, #{bookingDate}, #{bookingTime})")
    Boolean insert(Appointment appointment);


    @Select("SELECT * FROM appointment WHERE id = #{appointmentId}")
    Appointment getById(Integer appointmentId);

    @Select("SELECT * FROM appointment WHERE patient_name = #{patientName}")
    List<Appointment> getByPatientName(String patientName);

    @Delete("DELETE FROM appointment WHERE id = #{appointmentId}")
    Boolean removeById(Integer appointmentId);
}
