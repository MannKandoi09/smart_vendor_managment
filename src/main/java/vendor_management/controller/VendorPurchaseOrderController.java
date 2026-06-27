package vendor_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vendor_management.dto.VendorPurchaseOrderResponse;
import vendor_management.dto.VendorPurchaseOrderViewResponse;
import vendor_management.service.VendorPurchaseOrderService;

import java.util.List;

@RestController
@RequestMapping("/vendor/purchase-orders")
public class VendorPurchaseOrderController {

    @Autowired
    private VendorPurchaseOrderService service;

    @GetMapping("/{vendorId}")
    public List<VendorPurchaseOrderResponse> getPurchaseOrders(
            @PathVariable("vendorId") Long vendorId){

        return service.getMyPurchaseOrders(vendorId);
    }

    @GetMapping("/view/{id}")
    public VendorPurchaseOrderViewResponse
    viewPurchaseOrder(
            @PathVariable("id") Long id){

        return service.getPurchaseOrderById(id);
    }

}