package vendor_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vendor_management.dto.PurchaseOrderItemResponse;
import vendor_management.dto.VendorPurchaseOrderResponse;
import vendor_management.dto.VendorPurchaseOrderViewResponse;
import vendor_management.entity.Invoice;
import vendor_management.entity.PurchaseOrder;
import vendor_management.entity.PurchaseOrderItem;
import vendor_management.repository.InvoiceRepository;
import vendor_management.repository.PurchaseOrderRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VendorPurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    public List<VendorPurchaseOrderResponse> getMyPurchaseOrders(Long vendorId) {

        List<PurchaseOrder> purchaseOrders =
                purchaseOrderRepository
                        .findByVendorIdAndStatus(vendorId, "APPROVED");

        List<VendorPurchaseOrderResponse> responseList =
                new ArrayList<>();

        for (PurchaseOrder purchaseOrder : purchaseOrders) {

            VendorPurchaseOrderResponse response =
                    new VendorPurchaseOrderResponse();

            response.setId(
                    purchaseOrder.getId());

            response.setPoNumber(
                    purchaseOrder.getPoNumber());

            response.setVendorName(
                    purchaseOrder.getVendor().getVendorName());

            response.setEmployeeName(
                    purchaseOrder.getEmployee().getFirstName()
                            + " "
                            + purchaseOrder.getEmployee().getLastName());

            response.setOrderDate(
                    purchaseOrder.getOrderDate());

            response.setExpectedDeliveryDate(
                    purchaseOrder.getExpectedDeliveryDate());

            response.setAmount(
                    purchaseOrder.getGrandTotal());

            response.setStatus(
                    purchaseOrder.getStatus());

            // ==========================
            // Invoice Details
            // ==========================

            Optional<Invoice> invoice =
                    invoiceRepository.findByPurchaseOrderId(
                            purchaseOrder.getId());

            if (invoice.isPresent()) {

                response.setInvoiceId(
                        invoice.get().getId());     // ⭐ IMPORTANT

                response.setInvoiceStatus(
                        invoice.get().getStatus());

            } else {

                response.setInvoiceId(null);

                response.setInvoiceStatus(
                        "NOT_CREATED");
            }

            responseList.add(response);
        }

        return responseList;
    }

    public VendorPurchaseOrderViewResponse getPurchaseOrderById(Long id) {

        PurchaseOrder purchaseOrder =
                purchaseOrderRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Purchase Order Not Found"));

        VendorPurchaseOrderViewResponse dto =
                new VendorPurchaseOrderViewResponse();

        dto.setId(
                purchaseOrder.getId());

        dto.setPoNumber(
                purchaseOrder.getPoNumber());

        dto.setVendorName(
                purchaseOrder.getVendor().getVendorName());

        dto.setEmployeeName(
                purchaseOrder.getEmployee().getFirstName()
                        + " "
                        + purchaseOrder.getEmployee().getLastName());

        dto.setOrderDate(
                purchaseOrder.getOrderDate());

        dto.setExpectedDeliveryDate(
                purchaseOrder.getExpectedDeliveryDate());

        dto.setPaymentTerms(
                purchaseOrder.getPaymentTerms());

        dto.setDeliveryAddress(
                purchaseOrder.getDeliveryAddress());

        dto.setBillingAddress(
                purchaseOrder.getBillingAddress());

        dto.setSubTotal(
                purchaseOrder.getSubTotal());

        dto.setTaxAmount(
                purchaseOrder.getTaxAmount());

        dto.setGrandTotal(
                purchaseOrder.getGrandTotal());

        dto.setNotes(
                purchaseOrder.getNotes());

        dto.setStatus(
                purchaseOrder.getStatus());

        Optional<Invoice> invoice =
                invoiceRepository.findByPurchaseOrderId(
                        purchaseOrder.getId());

        if (invoice.isPresent()) {

            dto.setInvoiceStatus(
                    invoice.get().getStatus());

        } else {

            dto.setInvoiceStatus(
                    "NOT_CREATED");
        }

        List<PurchaseOrderItemResponse> items =
                new ArrayList<>();

        for (PurchaseOrderItem item : purchaseOrder.getItems()) {

            PurchaseOrderItemResponse response =
                    new PurchaseOrderItemResponse();

            response.setItemName(
                    item.getItemName());

            response.setQuantity(
                    item.getQuantity());

            response.setUnitPrice(
                    item.getUnitPrice());

            response.setTotalPrice(
                    item.getTotal());

            items.add(response);
        }

        dto.setItems(items);

        return dto;
    }
}