package vendor_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vendor_management.dto.PurchaseOrderRequest;
import vendor_management.dto.PurchaseOrderResponse;
import vendor_management.entity.PurchaseOrder;
import vendor_management.entity.Vendor;
import vendor_management.repository.PurchaseOrderRepository;
import vendor_management.repository.VendorRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private VendorRepository vendorRepository;

    public PurchaseOrderResponse addPurchaseOrder(
            PurchaseOrderRequest request) {

        Vendor vendor = vendorRepository.findById(
                        request.getVendorId())
                .orElseThrow(() ->
                        new RuntimeException("Vendor not found"));

        PurchaseOrder po = new PurchaseOrder();

        po.setPoNumber(request.getPoNumber());

        po.setOrderDate(request.getOrderDate());

        po.setTotalAmount(request.getTotalAmount());

        po.setDescription(request.getDescription());

        po.setStatus("PENDING");

        po.setCreatedDate(LocalDate.now());

        po.setActive(true);

        po.setVendor(vendor);

        po = purchaseOrderRepository.save(po);

        PurchaseOrderResponse response =
                new PurchaseOrderResponse();

        response.setId(po.getId());

        response.setPoNumber(po.getPoNumber());

        response.setOrderDate(po.getOrderDate());

        response.setTotalAmount(po.getTotalAmount());

        response.setDescription(po.getDescription());

        response.setStatus(po.getStatus());

        response.setVendorName(
                po.getVendor().getVendorName());

        return response;
    }

    public List<PurchaseOrder> getAllPurchaseOrders() {

        return purchaseOrderRepository.findAll();
    }

    public PurchaseOrder getPurchaseOrderById(
            Long id) {

        return purchaseOrderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Purchase Order not found"));
    }

    public PurchaseOrder updatePurchaseOrder(
            Long id,
            PurchaseOrderRequest request) {

        PurchaseOrder po =
                purchaseOrderRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Purchase Order not found"));

        po.setPoNumber(request.getPoNumber());

        po.setOrderDate(request.getOrderDate());

        po.setTotalAmount(request.getTotalAmount());

        po.setDescription(
                request.getDescription());

        return purchaseOrderRepository.save(po);
    }

    public void deletePurchaseOrder(
            Long id) {

        purchaseOrderRepository.deleteById(id);
    }

    public PurchaseOrder approvePurchaseOrder(
            Long id) {

        PurchaseOrder po =
                purchaseOrderRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Purchase Order not found"));

        po.setStatus("APPROVED");

        return purchaseOrderRepository.save(po);
    }

    public PurchaseOrder rejectPurchaseOrder(
            Long id) {

        PurchaseOrder po =
                purchaseOrderRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Purchase Order not found"));

        po.setStatus("REJECTED");

        return purchaseOrderRepository.save(po);
    }

    public List<PurchaseOrder> searchPurchaseOrder(
            String poNumber) {

        return purchaseOrderRepository
                .findByPoNumberContainingIgnoreCase(
                        poNumber);
    }

    public List<PurchaseOrder>
    getActivePurchaseOrders() {

        return purchaseOrderRepository
                .findByActiveTrue();
    }

    public Long totalPurchaseOrders() {

        return purchaseOrderRepository.count();
    }

    public Long approvedPurchaseOrders() {

        return purchaseOrderRepository
                .countByStatus("APPROVED");
    }

    public Long rejectedPurchaseOrders() {

        return purchaseOrderRepository
                .countByStatus("REJECTED");
    }
}
