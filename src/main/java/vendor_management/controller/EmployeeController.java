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
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @PostMapping
    public Employee createEmployee(@RequestBody EmployeeRequest request) {
        return employeeService.createEmployee(request);
    }

    @GetMapping
    public List<Employee> getAllEmployees() {

        return employeeService
                .getAllEmployees();
    }

    // 1. GET SINGLE EMPLOYEE BY ID METHOD
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable(name = "id") Long id) { // 🚀 FIXED: explicit name map add kiya
        return employeeService.getEmployeeById(id);
    }

    // 2. PUT UPDATE EMPLOYEE METHOD
    @PutMapping("/{id}")
    public Employee updateEmployee(
            @PathVariable(name = "id") Long id, // 🚀 FIXED: explicit name map add kiya
            @RequestBody EmployeeRequest request) {
        return employeeService.updateEmployee(id, request);
    }

    // 3. DELETE EMPLOYEE METHOD
    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable(name = "id") Long id) { // 🚀 FIXED: explicit name map add kiya
        return employeeService.deleteEmployee(id);
    }



    @GetMapping("/employee/{employeeId}")
    public List<PurchaseOrder>
    getEmployeeOrders(
            @PathVariable Long employeeId){

        return purchaseOrderRepository
                .findByEmployeeId(employeeId);
    }

    @PutMapping("/{id}/activate")
    public Employee activateEmployee(@PathVariable("id") Long id) {
        return employeeService.activateEmployee(id);
    }

    @PutMapping("/{id}/deactivate")
    public Employee deactivateEmployee(@PathVariable("id") Long id) {
        return employeeService.deactivateEmployee(id);
    }

    @GetMapping("/active")
    public List<Employee> getActiveEmployees() {
        return employeeService.getAllActiveEmployees();
    }
}