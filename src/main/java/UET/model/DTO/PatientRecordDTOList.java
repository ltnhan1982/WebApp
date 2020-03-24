package UET.model.DTO;

import UET.model.Department;
import UET.model.Employee;

import java.util.List;

public class PatientRecordDTOList {
    private List<PatientRecordDTO> patientRecordDTOs;
    private Department department;
    private Employee employee;

    public PatientRecordDTOList() {
    }

    public List<PatientRecordDTO> getPatientRecordDTOs() {
        return patientRecordDTOs;
    }

    public void setPatientRecordDTOs(List<PatientRecordDTO> patientRecordDTOs) {
        this.patientRecordDTOs = patientRecordDTOs;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
