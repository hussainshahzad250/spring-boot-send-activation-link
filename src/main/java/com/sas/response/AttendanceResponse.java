package com.sas.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AttendanceResponse {

    public Long employeeId;

    public String attendanceDate;

    public String punchInTime;

    public String punchOutTime;

    public String status;

}
