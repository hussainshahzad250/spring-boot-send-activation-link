package com.sas.controller;

import com.sas.entity.ConfirmationToken;
import com.sas.repository.ConfirmationTokenRepository;
import com.sas.entity.User;
import com.sas.repository.UserRepository;
import com.sas.request.EmployeeRequest;
import com.sas.service.AttendanceService;
import com.sas.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    private AttendanceService attendanceService;

    @GetMapping(value = {"/","/register"})
    public ModelAndView displayRegistration(ModelAndView modelAndView, User userEntity) {
        modelAndView.addObject("userEntity", userEntity);
        modelAndView.setViewName("register");
        attendanceService.getAttendance(new EmployeeRequest(1L));
        return modelAndView;
    }

    @PostMapping(value = "/register")
    public ModelAndView registerUser(ModelAndView modelAndView, User userEntity) {
        User existingUser = userRepository.findByEmailIdIgnoreCase(userEntity.getEmailId());
        if (existingUser != null) {
            modelAndView.addObject("message", "This email already exists!");
            modelAndView.setViewName("error");
        } else {
            userRepository.save(userEntity);
            ConfirmationToken confirmationToken = new ConfirmationToken(userEntity);
            confirmationTokenRepository.save(confirmationToken);
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(userEntity.getEmailId());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setText("To confirm your account, please click here : "
                    + "http://localhost:8080/confirm-account?token=" + confirmationToken.getConfirmationToken());
            emailService.sendEmail(mailMessage);
            modelAndView.addObject("emailId", userEntity.getEmailId());
            modelAndView.setViewName("successfulRegisteration");
        }
        return modelAndView;
    }


    @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token") String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if (token != null) {
            User user = userRepository.findByEmailIdIgnoreCase(token.getUserEntity().getEmailId());
            user.setEnabled(true);
            userRepository.save(user);
            modelAndView.setViewName("accountVerified");
        } else {
            modelAndView.addObject("message", "The link is invalid or broken!");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }
}
