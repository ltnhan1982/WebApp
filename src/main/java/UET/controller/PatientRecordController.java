package UET.controller;

import UET.model.*;
import UET.model.DTO.PatientRecordDTO;
import UET.model.DTO.PatientRecordDTOList;
import UET.repository.PatientRecordRepository;
import UET.security.rbac.spring.ContextAwarePolicyEnforcement;
import UET.service.PatientService;
import UET.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
public class PatientRecordController {
    @Autowired
    PatientService patientService;
    @Autowired
    PatientRecordRepository patientRecordRepository;
    @Autowired
    UserService userService;
    @Autowired
    private ContextAwarePolicyEnforcement policy;

    @GetMapping("/patientRecord/read/doctor")
    @PreAuthorize("hasRole('DOCTOR')")
    public String showPatientRecordByDoctor(Model model) {
        PatientRecordDTOList patientRecordList = patientService.showPatientRecordByDoctor(model);
        if (ObjectUtils.isEmpty(patientRecordList)) {
            return "redirect:/";
        }
        policy.checkPermission(patientRecordList, "subject.employee.id == resource.employee.id");
        return "patientRecordDoctor";
    }

    @GetMapping("/patientRecord/read")
    @PreAuthorize("hasRole('PATIENT')")
    public String showPatientRecordForPatient(Model model) {
        User user = userService.getUserAth();
        Patient patient = new Patient();
        if (ObjectUtils.isEmpty(user)) {
            return "redirect:/";
        }
        if (!ObjectUtils.isEmpty(user.getPatient())) {
            patient = user.getPatient();
        }
        policy.checkPermission(patient, "subject.patient.id == resource.id");
        patientService.showRecordOfPatient(patient, model);
        return "patientRecord";
    }

    @GetMapping("/patientRecord/read/doctor/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public String editUser(@PathVariable("id") Integer id, Model model) {
        Patient patient = patientService.findById(id);
        if (ObjectUtils.isEmpty(patient)) {
            return "redirect:/";
        }
        policy.checkPermission(patient, "subject.employee.id == resource.employee.id");
        patientService.getPatientRecordDoctor(patient, model);
        return "editPatientRecord";
    }

    @PostMapping("/patientRecord/create")
    @PreAuthorize("hasRole('RECEPTIONIST')")
    public RedirectView createdPatientRecord(@ModelAttribute("patientRecordDTO") @Valid PatientRecordDTO patientRecordDTO,
                                             RedirectAttributes model) {
        patientService.createdPatientRecord(patientRecordDTO, model);
        return new RedirectView("/patientRecord/create");
    }

    @PostMapping("/patientRecord/update/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public String editPatientRecord(@ModelAttribute("patientRecord") @Valid PatientRecordDTO patientRecordDTO,
                                    @PathVariable("id") Integer id, Model model) {
        Patient patient = patientService.findById(id);
        policy.checkPermission(patient, "subject.employee.id == resource.employee.id");
        patientService.editPatientRecord(patientRecordDTO, patient, model);
        return "editPatientRecord";
    }

    @GetMapping("/patientRecord/update")
    @PreAuthorize("hasRole('PATIENT')")
    public String showPatientRecordForPatient(@ModelAttribute("patientRecord") @Valid PatientRecordDTO patientRecordDTO, Model model) {
        User user = userService.getUserAth();
        Patient patient = new Patient();
        if (ObjectUtils.isEmpty(user)) {
            return "redirect:/";
        }
        if (!ObjectUtils.isEmpty(user.getPatient())) {
            patient = user.getPatient();
        }
        policy.checkPermission(patient, "subject.patient.id == resource.id");
        patientService.editPatientRecord(patientRecordDTO, patient, model);
        return "patientRecord";
    }
}
