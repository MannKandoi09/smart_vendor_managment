package vendor_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vendor_management.dto.PurchaseOrderDropdownResponse;
import vendor_management.dto.PurchaseOrderRequest;
import vendor_management.dto.PurchaseOrderResponse;
import vendor_management.entity.PurchaseOrder;
import vendor_management.service.PurchaseOrderService;

import java.util.List;

@RestController
@RequestMapping("/purchase-orders")
@CrossOrigin(origins = "*")
 // Agar frontend ports mismatch (CORS) block ho raha ho toh use karein
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    // 1. Create Purchase Order
    @PostMapping("/add")
    public ResponseEntity<PurchaseOrderResponse> addPurchaseOrder(@RequestBody PurchaseOrderRequest request) {
        PurchaseOrderResponse response = purchaseOrderService.addPurchaseOrder(request);
        return ResponseEntity.ok(response);
    }

    // 2. FIXED: Changed return type from List<PurchaseOrder> to List<PurchaseOrderResponse>
    @GetMapping
    public ResponseEntity<List<PurchaseOrderResponse>> getAllPurchaseOrders() {
        List<PurchaseOrderResponse> list = purchaseOrderService.getAllPurchaseOrders();
        return ResponseEntity.ok(list);
    }

    // 4. Delete Purchase Order
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePurchaseOrder(@PathVariable("id") Long id) { // FIXED: ("id") explicitly add kiya
        purchaseOrderService.deletePurchaseOrder(id);
        return ResponseEntity.ok().build();
    }

    // 5. FIXED: Changed return type from PurchaseOrder to PurchaseOrderResponse
    @PutMapping("/{id}/approve")
    public ResponseEntity<PurchaseOrderResponse> approvePurchaseOrder(
            @PathVariable("id") Long id) {

        PurchaseOrderResponse response =
                purchaseOrderService.approvePurchaseOrder(id);

        return ResponseEntity.ok(response);
    }

    // 6. FIXED: Changed return type from PurchaseOrder to PurchaseOrderResponse
    @PutMapping("/{id}/reject")
    public ResponseEntity<PurchaseOrderResponse> rejectPurchaseOrder(
            @PathVariable("id") Long id) {

        PurchaseOrderResponse response =
                purchaseOrderService.rejectPurchaseOrder(id);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseOrderResponse> updatePurchaseOrder(
            @PathVariable("id") Long id, // Fixed explicit naming
            @RequestBody PurchaseOrderRequest request) {
        PurchaseOrderResponse updatedPo = purchaseOrderService.updatePurchaseOrder(id, request);
        return ResponseEntity.ok(updatedPo);
    }

    // 1. Database ID ke base par fetch karne ke liye
    @GetMapping("/view/id/{id}")
    public ResponseEntity<PurchaseOrderResponse> getPurchaseOrderById(@PathVariable("id") Long id) {
        PurchaseOrderResponse response = purchaseOrderService.getPurchaseOrderById(id);
        return ResponseEntity.ok(response);
    }

    // 2. Alphanumeric PO Number ke base par fetch karne ke liye
    // ✅ FIXED CODE (Aapko is tarah likhna hai):
    @GetMapping("/view/po/{poNumber}")
    public ResponseEntity<PurchaseOrderResponse> getPurchaseOrderByPoNumber(@PathVariable("poNumber") String poNumber) {
        // Hamne explicitly ("poNumber") define kar diya hai
        PurchaseOrderResponse response = purchaseOrderService   .getPurchaseOrderByPoNumber(poNumber);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/approved")
    public List<PurchaseOrderDropdownResponse> getApprovedPurchaseOrders() {
        System.out.println("Approved API HIT");
        return purchaseOrderService
                .getApprovedPurchaseOrders();
    }
}