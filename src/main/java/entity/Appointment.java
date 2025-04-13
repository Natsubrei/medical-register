package entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

/**
 * 预约类
 */
@Data
@Builder
public class Appointment implements Serializable {
    /**
     * 预约id
     */
    private Integer id;

    /**
     * 患者姓名
     */
    private String patientName;

    /**
     * 医生姓名
     */
    private String doctorName;

    /**
     * 科室名称
     */
    private String department;

    /**
     * 预约日期
     */
    private Date bookingDate;

    /**
     * 预约时间段
     */
    private Time bookingTime;

    @Override
    public String toString() {
        return id + "\t\t" + patientName + "\t\t" + doctorName + "\t\t" +
                department + "\t\t" + bookingDate + "\t" + bookingTime;
    }
}
