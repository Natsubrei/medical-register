create schema hospital;

use hospital;

create table appointment
(
    id           int auto_increment comment '预约id'
        primary key,
    patient_name varchar(16) not null comment '患者姓名',
    doctor_name  varchar(16) not null comment '医生姓名',
    department   varchar(16) not null comment '科室名称',
    booking_date date        not null comment '预约日期',
    booking_time time        not null comment '预约时间段'
)
    comment '预约表';