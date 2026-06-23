package vendor_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import vendor_management.dto.EmployeeRequest;
import vendor_management.entity.Employee;
import vendor_management.entity.PurchaseOrder;
import vendor_management.repository.PurchaseOrderRepository;
import vendor_management.service.EmployeeService;

import java.util.List;

@RestController
@RequestMapping("/admin/employees")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @PostMapping
    public Employee createEmployee(
            @RequestBody EmployeeRequest request) {

        return employeeService
                .createEmployee(request);
    }

    @GetMapping
    public List<Employee> getAllEmployees() {

        return employeeService
                .getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(
            @PathVariable Long id) {

        return employeeService
                .getEmployeeById(id);
    }
    @PutMapping("/{id}")
    public Employee updateEmployee(
            @PathVariable Long id,
            @RequestBody EmployeeRequest request) {

        return employeeService
                .updateEmployee(id, request);
    }
    @DeleteMapping("/{id}")
    public String deleteEmployee(
            @PathVariable Long id) {

        return employeeService
                .deleteEmployee(id);
    }
    @PutMapping("/{id}/activate")
    public Employee activateEmployee(
            @PathVariable Long id) {

        return employeeService
                .activateEmployee(id);
    }
    @PutMapping("/{id}/deactivate")
    public Employee deactivateEmployee(
            @PathVariable Long id) {

        return employeeService
                .deactivateEmployee(id);
    }

    @GetMapping("/employee/{employeeId}")
    public List<PurchaseOrder>
    getEmployeeOrders(
            @PathVariable Long employeeId){

        return purchaseOrderRepository
                .findByEmployeeId(employeeId);
    }
}