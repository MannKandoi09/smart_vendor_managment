package vendor_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vendor_management.dto.PurchaseOrderRequest;
import vendor_management.dto.PurchaseOrderResponse;
import vendor_management.entity.PurchaseOrder;
import vendor_management.service.PurchaseOrderService;

import java.util.List;

@RestController
@RequestMapping("/purchase-orders")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @PostMapping("/add")
    public PurchaseOrderResponse addPurchaseOrder(
            @RequestBody PurchaseOrderRequest request) {

        return purchaseOrderService
                .addPurchaseOrder(request);
    }

    @GetMapping
    public List<PurchaseOrder> getAllPurchaseOrders() {

        return purchaseOrderService
                .getAllPurchaseOrders();
    }

    @GetMapping("/{id}")
    public PurchaseOrder getPurchaseOrderById(
            @PathVariable Long id) {

        return purchaseOrderService
                .getPurchaseOrderById(id);
    }

    @PutMapping("/{id}")
    public PurchaseOrder updatePurchaseOrder(
            @PathVariable Long id,
            @RequestBody PurchaseOrderRequest request) {

        return purchaseOrderService
                .updatePurchaseOrder(id,
                        request);
    }

    @DeleteMapping("/{id}")
    public String deletePurchaseOrder(
            @PathVariable Long id) {

        purchaseOrderService
                .deletePurchaseOrder(id);

        return "Purchase Order deleted successfully";
    }

    @PutMapping("/{id}/approve")
    public PurchaseOrder approvePurchaseOrder(
            @PathVariable Long id) {

        return purchaseOrderService
                .approvePurchaseOrder(id);
    }

    @PutMapping("/{id}/reject")
    public PurchaseOrder rejectPurchaseOrder(
            @PathVariable Long id) {

        return purchaseOrderService
                .rejectPurchaseOrder(id);
    }

    @GetMapping("/search")
    public List<PurchaseOrder> searchPurchaseOrder(
            @RequestParam String poNumber) {

        return purchaseOrderService
                .searchPurchaseOrder(poNumber);
    }
}