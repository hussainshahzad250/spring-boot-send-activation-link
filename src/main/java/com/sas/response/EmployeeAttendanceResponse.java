package com.sas.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class EmployeeAttendanceResponse {
    private Long employeeId;
    private String employeeName;
    private List<AttendanceResponse> attendance;
}
