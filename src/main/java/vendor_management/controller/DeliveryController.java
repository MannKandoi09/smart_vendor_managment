package vendor_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import vendor_management.dto.DeliveryRequest;
import vendor_management.dto.DeliveryResponse;
import vendor_management.dto.PurchaseOrderDropdownResponse;
import vendor_management.entity.Delivery;
import vendor_management.entity.Employee;
import vendor_management.entity.PurchaseOrder;
import vendor_management.service.DeliveryService;

import java.util.List;

@RestController
@RequestMapping("/admin/deliveries")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    @PostMapping
    public Delivery createDelivery(
            @RequestBody DeliveryRequest request) {

        return deliveryService
                .createDelivery(request);
    }
    @GetMapping("/{id}")
    public DeliveryResponse getDeliveryById(
            @PathVariable("id") Long id) {

        return deliveryService
                .getDeliveryById(id);
    }
    @PutMapping("/{id}")
    public Delivery updateDelivery(
            @PathVariable("id") Long id,
            @RequestBody DeliveryRequest request) {

        return deliveryService
                .updateDelivery(id, request);
    }
    @DeleteMapping("/{id}")
    public String deleteDelivery(
            @PathVariable("id") Long id) {

        return deliveryService
                .deleteDelivery(id);
    }
    @PutMapping("/{id}/delivered")
    public Delivery markDelivered(
            @PathVariable("id") Long id) {

        return deliveryService
                .markDelivered(id);
    }
    @PutMapping("/{id}/activate")
    public Delivery activateDelivery(
            @PathVariable("id") Long id) {

        return deliveryService
                .activateDelivery(id);
    }

    @PutMapping("/{id}/deactivate")
    public Delivery deactivateDelivery(
            @PathVariable("id") Long id) {

        return deliveryService
                .deactivateDelivery(id);
    }
    @GetMapping("/employee/{employeeId}")
    public List<Delivery> getDeliveriesByEmployee(
            @PathVariable("employeeId") Long employeeId) {

        return deliveryService
                .getDeliveriesByEmployee(employeeId);
    }

    @GetMapping("/status/{status}")
    public List<Delivery> getDeliveriesByStatus(
            @PathVariable("status") String status) {

        return deliveryService
                .getDeliveriesByStatus(status);
    }

    @GetMapping
    public List<DeliveryResponse> getAllDeliveries() {

        return deliveryService
                .getAllDeliveries();
    }

    @GetMapping("/purchase-order/{purchaseOrderId}")
    public List<Delivery>
    getDeliveriesByPurchaseOrder(
            @PathVariable("purchaseOrderId") Long purchaseOrderId) {

        return deliveryService
                .getDeliveriesByPurchaseOrder(
                        purchaseOrderId);
    }

    @GetMapping("/available-employees")
    public List<Employee> getAvailableEmployees() {
        return deliveryService.getAvailableEmployees();
    }

    @GetMapping("/available-purchase-orders")
    public List<PurchaseOrderDropdownResponse> getAvailablePurchaseOrders() {

        return deliveryService.getAvailablePurchaseOrders();
    }
}