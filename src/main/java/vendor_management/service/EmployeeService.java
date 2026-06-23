package vendor_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vendor_management.dto.EmployeeRequest;
import vendor_management.entity.Employee;
import vendor_management.repository.EmployeeRepository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;

import vendor_management.entity.Role;
import vendor_management.entity.User;
import vendor_management.repository.UserRepository;

@Service
public class EmployeeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee createEmployee(EmployeeRequest request) {

        System.out.println("STEP 1");

        User user = new User();

        user.setUsername(request.getEmail());
        user.setPassword(passwordEncoder.encode("123456"));
        user.setRole(Role.USER);

        System.out.println("STEP 2");

        User savedUser = userRepository.save(user);

        System.out.println("USER ID = " + savedUser.getId());

        Employee employee = new Employee();

        employee.setUser(savedUser);

        employee.setEmployeeCode(
                request.getEmployeeCode());

        employee.setFirstName(
                request.getFirstName());

        employee.setLastName(
                request.getLastName());

        employee.setEmail(
                request.getEmail());

        employee.setMobile(
                request.getMobile());

        employee.setDepartment(
                request.getDepartment());

        employee.setDesignation(
                request.getDesignation());

        employee.setJoiningDate(
                request.getJoiningDate());

        employee.setSalary(
                request.getSalary());

        employee.setActive(true);

        employee.setCreatedDate(
                LocalDate.now());

        employee.setProfileImageUrl(
                request.getProfileImageUrl());

        return employeeRepository.save(employee);
    }
    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();
    }
    public Employee updateEmployee(
            Long id,
            EmployeeRequest request) {

        Employee employee =
                employeeRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee Not Found"));

        employee.setEmployeeCode(
                request.getEmployeeCode());

        employee.setFirstName(
                request.getFirstName());

        employee.setLastName(
                request.getLastName());

        employee.setEmail(
                request.getEmail());

        employee.setMobile(
                request.getMobile());

        employee.setDepartment(
                request.getDepartment());

        employee.setDesignation(
                request.getDesignation());

        employee.setJoiningDate(
                request.getJoiningDate());

        employee.setSalary(
                request.getSalary());

        employee.setProfileImageUrl(
                request.getProfileImageUrl());

        return employeeRepository.save(employee);
    }
    public String deleteEmployee(Long id) {

        Employee employee =
                employeeRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee Not Found"));

        employeeRepository.delete(employee);

        return "Employee Deleted Successfully";
    }
    public Employee activateEmployee(Long id) {

        Employee employee =
                employeeRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee Not Found"));

        employee.setActive(true);

        return employeeRepository.save(employee);
    }
    public Employee deactivateEmployee(Long id) {

        Employee employee =
                employeeRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee Not Found"));

        employee.setActive(false);

        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {

        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Employee Not Found"));
    }
}