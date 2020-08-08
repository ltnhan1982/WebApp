package UET.controller;

import UET.model.*;
import UET.model.DTO.PasswordDTO;
import UET.repository.UserRepository;
import UET.service.PatientService;
import UET.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    ModelMapper modelMapperl;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PatientService patientService;

    @PostMapping("/user/updatePassword")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('SECRETARY')or hasRole('NURSE')")
    public String updatePassword(@ModelAttribute("passwordDTO") PasswordDTO passwordDto, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName());
        if (!userService.checkPassword(user.getPassword(), passwordDto.getOldPassword())) {
            model.addAttribute("erroOldPassword", true);
            return "profile";
        }
        if (!passwordDto.getNewPassword().equals(passwordDto.getConfirmonPassword())) {
            model.addAttribute("erroNewPassword", true);
            return "profile";
        }
        if (passwordDto.getNewPassword().length() < 6) {
            model.addAttribute("erroLengthPassword", true);
            return "profile";
        } else {
            userService.updatePassword(user, passwordDto.getNewPassword());
            return "profile";
        }
    }

    @GetMapping("/patientRecord/create/")
    @PreAuthorize("hasRole('RECEPTIONIST')")
    public String getFormPatientRecord(Model model) {
        userService.showDoctors(model);
        return "createPatientRecord";
    }
}
