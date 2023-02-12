package com.sas.assembler;

import com.sas.request.EmployeeRequest;
import com.sas.response.AttendanceResponse;
import com.sas.response.EmployeeAttendanceResponse;
import com.sas.response.EmployeeResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EmployeeAssembler {

    public List<AttendanceResponse> getAttendance() {
        return List.of(
                new AttendanceResponse(1L, "10-01-2023", "09:00", "18:00", "P"),
                new AttendanceResponse(1L, "11-01-2023", "09:00", "18:00", "P"),
                new AttendanceResponse(1L, "12-01-2023", "09:00", "18:00", "P"),
                new AttendanceResponse(1L, "13-01-2023", "09:00", "18:00", "P"),
                new AttendanceResponse(1L, "14-01-2023", "09:00", "18:00", "P"),
                new AttendanceResponse(1L, "15-01-2023", "00:00", "00:00", "A"),
                new AttendanceResponse(2L, "10-01-2023", "09:00", "18:00", "P"),
                new AttendanceResponse(2L, "11-01-2023", "09:00", "18:00", "P"),
                new AttendanceResponse(2L, "12-01-2023", "09:00", "18:00", "P"),
                new AttendanceResponse(2L, "13-01-2023", "09:00", "18:00", "P"),
                new AttendanceResponse(2L, "14-01-2023", "09:00", "18:00", "P"),
                new AttendanceResponse(2L, "15-01-2023", "00:00", "00:00", "A")
        );
    }


    public List<EmployeeResponse> getEmployee() {
        return List.of(new EmployeeResponse(1L, "Shahzad Hussain"),
                new EmployeeResponse(2L, "Ejaz Hussain"),
                new EmployeeResponse(3L, "Sahil Hussain"));

    }


    public EmployeeAttendanceResponse getEmployeeAttendance(EmployeeRequest request) {
        Optional<EmployeeResponse> optional = getEmployee().stream().filter(o -> o.getEmployeeId().equals(request.getEmployeeId())).findFirst();
        if(optional.isPresent()) {
            EmployeeAttendanceResponse response = new EmployeeAttendanceResponse();
            EmployeeResponse employeeResponse = optional.get();
            response.setEmployeeId(employeeResponse.getEmployeeId());
            response.setEmployeeName(employeeResponse.getEmployeeName());
            response.setAttendance(getAttendance().stream().filter(o->o.getEmployeeId().equals(request.getEmployeeId())).collect(Collectors.toList()));
            return response;
        }
        return null;
    }
}
