package vendor_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vendor_management.dto.PurchaseOrderItemRequest;
import vendor_management.dto.PurchaseOrderItemResponse;
import vendor_management.dto.PurchaseOrderRequest;
import vendor_management.dto.PurchaseOrderResponse;
import vendor_management.entity.Employee;
import vendor_management.entity.PurchaseOrder;
import vendor_management.entity.PurchaseOrderItem;
import vendor_management.entity.Vendor;
import vendor_management.repository.EmployeeRepository;
import vendor_management.repository.PurchaseOrderItemRepository;
import vendor_management.repository.PurchaseOrderRepository;
import vendor_management.repository.VendorRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PurchaseOrderItemRepository purchaseOrderItemRepository;

    @Transactional
    public PurchaseOrderResponse addPurchaseOrder(PurchaseOrderRequest request) {

        if (purchaseOrderRepository.existsByPoNumber(request.getPoNumber())) {
            throw new RuntimeException("PO Number already exists");
        }

        Vendor vendor = vendorRepository.findById(request.getVendorId())
                .orElseThrow(() -> new RuntimeException("Vendor not found"));

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        PurchaseOrder po = new PurchaseOrder();
        po.setPoNumber(request.getPoNumber());
        po.setEmployee(employee);
        po.setOrderDate(request.getOrderDate());
        po.setReferenceNumber(request.getReferenceNumber());
        po.setPaymentTerms(request.getPaymentTerms());
        po.setExpectedDeliveryDate(request.getExpectedDeliveryDate());
        po.setDeliveryAddress(request.getDeliveryAddress());
        po.setBillingAddress(request.getBillingAddress());
        po.setNotes(request.getNotes());
        po.setStatus("PENDING");
        po.setCreatedDate(LocalDate.now());
        po.setActive(true);
        po.setVendor(vendor);

        double subTotal = 0;
        double taxAmount = 0;

        // Calculations Loop
        for (PurchaseOrderItemRequest item : request.getItems()) {
            double itemTotal = item.getQuantity() * item.getUnitPrice();
            subTotal += itemTotal;
            taxAmount += (itemTotal * item.getTax()) / 100;
        }

        po.setSubTotal(subTotal);
        po.setTaxAmount(taxAmount);
        po.setGrandTotal(subTotal + taxAmount);

        // Save Master Parent First
        final PurchaseOrder savedPo = purchaseOrderRepository.save(po);

        // Save Nested Child Items
        for (PurchaseOrderItemRequest itemRequest : request.getItems()) {
            PurchaseOrderItem item = new PurchaseOrderItem();
            item.setItemName(itemRequest.getItemName());
            item.setDescription(itemRequest.getDescription());
            item.setQuantity(itemRequest.getQuantity());
            item.setUnitPrice(itemRequest.getUnitPrice());
            item.setTax(itemRequest.getTax());
            item.setTotal(itemRequest.getQuantity() * itemRequest.getUnitPrice());
            item.setPurchaseOrder(savedPo);
            purchaseOrderItemRepository.save(item);
        }

        return mapToResponse(savedPo);
    }

    // FIXED: Returns mapped DTO arrays to perfectly feed React states without rendering crashes
    public List<PurchaseOrderResponse> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public PurchaseOrderResponse getPurchaseOrderById(Long id) {
        PurchaseOrder po = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase Order not found"));
        return mapToResponse(po);
    }

    private PurchaseOrderResponse mapToResponse(PurchaseOrder po) {
        PurchaseOrderResponse response = new PurchaseOrderResponse();
        response.setId(po.getId());
        response.setPoNumber(po.getPoNumber());
        response.setOrderDate(po.getOrderDate());
        response.setReferenceNumber(po.getReferenceNumber());
        response.setPaymentTerms(po.getPaymentTerms());
        response.setExpectedDeliveryDate(po.getExpectedDeliveryDate());
        response.setDeliveryAddress(po.getDeliveryAddress());
        response.setBillingAddress(po.getBillingAddress());
        response.setNotes(po.getNotes());
        response.setStatus(po.getStatus());

        response.setSubTotal(po.getSubTotal());
        response.setTaxAmount(po.getTaxAmount());
        response.setGrandTotal(po.getGrandTotal());

        if (po.getVendor() != null) {
            response.setVendorName(po.getVendor().getVendorName());
        }
        if (po.getEmployee() != null) {
            response.setEmployeeName(po.getEmployee().getFirstName() + " " + po.getEmployee().getLastName());
        }

        if (po.getItems() != null) {
            List<PurchaseOrderItemResponse> mappedItems = po.getItems().stream().map(dbItem -> {
                PurchaseOrderItemResponse itemResp = new PurchaseOrderItemResponse();

                itemResp.setItemName(dbItem.getItemName());
                itemResp.setDescription(dbItem.getDescription());
                itemResp.setQuantity(dbItem.getQuantity());
                itemResp.setUnitPrice(dbItem.getUnitPrice());
                itemResp.setTax(dbItem.getTax());

                // Agar dynamic totals field structured hai toh use bhi map karein:
                itemResp.setTotal(dbItem.getTotal());

                return itemResp;
            }).collect(java.util.stream.Collectors.toList());

            // Optional: Reversing order list if required
            java.util.Collections.reverse(mappedItems);

            // Now this will match perfectly!
            response.setItems(mappedItems);
        }

        return response;
    }

    @Transactional
    public void deletePurchaseOrder(Long id) {
        purchaseOrderRepository.deleteById(id);
    }

    @Transactional
    public PurchaseOrderResponse approvePurchaseOrder(Long id) {
        PurchaseOrder po = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase Order not found"));
        po.setStatus("APPROVED");
        return mapToResponse(purchaseOrderRepository.save(po));
    }

    @Transactional
    public PurchaseOrderResponse rejectPurchaseOrder(Long id) {
        PurchaseOrder po = purchaseOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Purchase Order not found"));
        po.setStatus("REJECTED");
        return mapToResponse(purchaseOrderRepository.save(po));
    }

    public PurchaseOrderResponse getPurchaseOrderByPoNumber(String poNumber) {
        // Database Repository layer lookup check call mapping directly from alphanumeric value
        PurchaseOrder po = purchaseOrderRepository.findByPoNumber(poNumber)
                .orElseThrow(() -> new RuntimeException("Purchase Order not found with PO Number: " + poNumber));
        return mapToResponse(po);
    }

    public Long totalPurchaseOrders() { return purchaseOrderRepository.count(); }
    public Long approvedPurchaseOrders() { return purchaseOrderRepository.countByStatus("APPROVED"); }
    public Long rejectedPurchaseOrders() { return purchaseOrderRepository.countByStatus("REJECTED"); }
}