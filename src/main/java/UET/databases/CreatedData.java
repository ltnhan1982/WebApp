package UET.databases;

import UET.Enum.EDepartment;
import UET.model.*;
import UET.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreatedData implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent arg0) {
        // Roles
        if (roleRepository.findByName("ROLE_NURSE") == null) {
            roleRepository.save(new Role("ROLE_NURSE"));
        }
        if (roleRepository.findByName("ROLE_RECEPTIONIST") == null) {
            roleRepository.save(new Role("ROLE_RECEPTIONIST"));
        }

        if (roleRepository.findByName("ROLE_DOCTOR") == null) {
            roleRepository.save(new Role("ROLE_DOCTOR"));
        }

        if (roleRepository.findByName("ROLE_PATIENT") == null) {
            roleRepository.save(new Role("ROLE_PATIENT"));
        }

        if (roleRepository.findByName("ROLE_ADMIN") == null) {
            roleRepository.save(new Role("ROLE_ADMIN"));
        }

        if (departmentRepository.findByType(EDepartment.CARDIOLOGY) == null) {
            Department department = new Department();
            department.setType(EDepartment.CARDIOLOGY);
            departmentRepository.save(department);
        }
        if (departmentRepository.findByType(EDepartment.DENTISTRY) == null) {
            Department department = new Department();
            department.setType(EDepartment.DENTISTRY);
            departmentRepository.save(department);
        }
        if (departmentRepository.findByType(EDepartment.ENDOCRINOLOGY) == null) {
            Department department = new Department();
            department.setType(EDepartment.ENDOCRINOLOGY);
            departmentRepository.save(department);
        }
        if (departmentRepository.findByType(EDepartment.GASTROENTEROLOGY) == null) {
            Department department = new Department();
            department.setType(EDepartment.GASTROENTEROLOGY);
            departmentRepository.save(department);
        }
        if (departmentRepository.findByType(EDepartment.INTERNAL_MEDICINE) == null) {
            Department department = new Department();
            department.setType(EDepartment.INTERNAL_MEDICINE);
            departmentRepository.save(department);
        }
        if (departmentRepository.findByType(EDepartment.NEUROSURGERY) == null) {
            Department department = new Department();
            department.setType(EDepartment.NEUROSURGERY);
            departmentRepository.save(department);
        }
        if (departmentRepository.findByType(EDepartment.SURGERY) == null) {
            Department department = new Department();
            department.setType(EDepartment.SURGERY);
            departmentRepository.save(department);
        }

        if (userRepository.findByUsername("admin") == null) {
            User admin = new User();
            Employee employee = new Employee();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("123456"));
            List<Role> roles = new ArrayList<>();
            roles.add(roleRepository.findByName("ROLE_ADMIN"));
            admin.setRoles(roles);
            employee.setFullName("admin");
            employee.setUser(admin);
            employeeRepository.save(employee);
            userRepository.save(admin);
        }

        if (userRepository.findByUsername("admin1") == null) {
            User admin = new User();
            Employee employee = new Employee();
            employee.setFullName("admin1");
            admin.setPassword(passwordEncoder.encode("123456"));
            List<Role> roles = new ArrayList<>();
            roles.add(roleRepository.findByName("ROLE_ADMIN"));
            admin.setRoles(roles);
            admin.setUsername("admin1");
            admin.setEmployee(employee);
            employee.setUser(admin);
            employeeRepository.save(employee);
            userRepository.save(admin);
        }
    }
}
