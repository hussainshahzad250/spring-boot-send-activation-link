package com.sas.service;

import com.sas.assembler.EmployeeAssembler;
import com.sas.request.EmployeeRequest;
import com.sas.response.EmployeeAttendanceResponse;
import com.sas.utils.TemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private TemplateUtil templateUtil;

    @Autowired
    private EmployeeAssembler employeeAssembler;

    @Override
    public void getAttendance(EmployeeRequest request) {
        Map<String, Object> details = new HashMap<>();
        EmployeeAttendanceResponse response = employeeAssembler.getEmployeeAttendance(request);
        details.put("empId", response.getEmployeeId());
        details.put("empName", response.getEmployeeName());
        details.put("details", response.getAttendance());

        String mailBody = templateUtil.fillData("hris-update", details);
        String[] to = {"shahzad.jdeveloper@gmail.com"};
        String[] cc = {"shahzad.hussain@sastechstudio.com", "shahzad.loandost@gmail.com"};

        try {
            emailService.sendEmail("Attendance details | " + response.getEmployeeName(), to, cc, mailBody);
        } catch (Exception e) {
            log.error("Exception occurs while sending attendance details on mail");
        }
    }
}
