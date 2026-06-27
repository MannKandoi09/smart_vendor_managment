package vendor_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vendor_management.dto.InvoiceRequest;
import vendor_management.dto.InvoiceResponse;
import vendor_management.entity.Invoice;
import vendor_management.entity.PurchaseOrder;
import vendor_management.entity.Vendor;
import vendor_management.repository.InvoiceRepository;
import vendor_management.repository.PurchaseOrderRepository;
import vendor_management.repository.VendorRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    public InvoiceResponse uploadInvoice(
            InvoiceRequest request) {

        if (invoiceRepository.existsByPurchaseOrderId(
                request.getPurchaseOrderId())) {

            throw new RuntimeException(
                    "Invoice already exists for this Purchase Order");
        }

        Vendor vendor =
                vendorRepository.findById(
                                request.getVendorId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Vendor Not Found"));

        PurchaseOrder purchaseOrder =
                purchaseOrderRepository.findById(
                                request.getPurchaseOrderId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Purchase Order Not Found"));

        Invoice invoice = new Invoice();

        invoice.setInvoiceNumber(
                generateInvoiceNumber());

        invoice.setInvoiceDate(
                request.getInvoiceDate());

        invoice.setDueDate(
                request.getDueDate());

        invoice.setInvoiceFileUrl(
                request.getInvoiceFileUrl());

        invoice.setRemarks(
                request.getRemarks());

        invoice.setVendor(vendor);

        invoice.setPurchaseOrder(
                purchaseOrder);

        invoice.setAmount(
                purchaseOrder.getGrandTotal());

        invoice.setStatus(
                "PENDING_REVIEW");

        invoice.setActive(true);

        invoice.setCreatedDate(
                LocalDate.now());

        invoice = invoiceRepository.save(invoice);

        InvoiceResponse response =
                new InvoiceResponse();

        response.setId(invoice.getId());
        response.setInvoiceNumber(invoice.getInvoiceNumber());
        response.setPoNumber(invoice.getPurchaseOrder().getPoNumber());
        response.setVendorName(invoice.getVendor().getVendorName());
        response.setInvoiceDate(invoice.getInvoiceDate());
        response.setDueDate(invoice.getDueDate());
        response.setAmount(invoice.getAmount());
        response.setStatus(invoice.getStatus());
        response.setInvoiceFileUrl(invoice.getInvoiceFileUrl());
        response.setRemarks(invoice.getRemarks());
        response.setActive(invoice.getActive());

        return response;
    }

    public List<InvoiceResponse> getAllInvoices() {

        List<Invoice> invoices =
                invoiceRepository.findAll();

        List<InvoiceResponse> responseList =
                new ArrayList<>();

        for (Invoice invoice : invoices) {

            InvoiceResponse response =
                    new InvoiceResponse();

            response.setId(invoice.getId());

            response.setInvoiceNumber(
                    invoice.getInvoiceNumber());

            response.setPoNumber(
                    invoice.getPurchaseOrder().getPoNumber());

            response.setVendorName(
                    invoice.getVendor().getVendorName());

            response.setInvoiceDate(
                    invoice.getInvoiceDate());

            response.setDueDate(
                    invoice.getDueDate());

            response.setAmount(
                    invoice.getAmount());

            response.setStatus(
                    invoice.getStatus());

            response.setInvoiceFileUrl(
                    invoice.getInvoiceFileUrl());

            response.setRemarks(
                    invoice.getRemarks());

            response.setActive(
                    invoice.getActive());

            responseList.add(response);
        }

        return responseList;
    }

    public InvoiceResponse getInvoiceById(
            Long id) {

        Invoice invoice =
                invoiceRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invoice Not Found"));

        InvoiceResponse response =
                new InvoiceResponse();

        response.setId(
                invoice.getId());

        response.setInvoiceNumber(
                invoice.getInvoiceNumber());

        response.setPoNumber(
                invoice.getPurchaseOrder()
                        .getPoNumber());

        response.setVendorName(
                invoice.getVendor()
                        .getVendorName());

        response.setInvoiceDate(
                invoice.getInvoiceDate());

        response.setDueDate(
                invoice.getDueDate());

        response.setAmount(
                invoice.getAmount());

        response.setStatus(
                invoice.getStatus());

        response.setInvoiceFileUrl(
                invoice.getInvoiceFileUrl());

        response.setRemarks(
                invoice.getRemarks());

        response.setActive(
                invoice.getActive());

        return response;
    }

    public InvoiceResponse updateInvoice(
            Long id,
            InvoiceRequest request) {

        Invoice invoice =
                invoiceRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invoice Not Found"));

        // Read Only Fields
        // Invoice Number
        // Vendor
        // Purchase Order
        // Amount

        invoice.setInvoiceDate(
                request.getInvoiceDate());

        invoice.setDueDate(
                request.getDueDate());

        invoice.setInvoiceFileUrl(
                request.getInvoiceFileUrl());

        invoice.setRemarks(
                request.getRemarks());

        invoice = invoiceRepository.save(invoice);

        InvoiceResponse response =
                new InvoiceResponse();

        response.setId(
                invoice.getId());

        response.setInvoiceNumber(
                invoice.getInvoiceNumber());

        response.setPoNumber(
                invoice.getPurchaseOrder()
                        .getPoNumber());

        response.setVendorName(
                invoice.getVendor()
                        .getVendorName());

        response.setInvoiceDate(
                invoice.getInvoiceDate());

        response.setDueDate(
                invoice.getDueDate());

        response.setAmount(
                invoice.getAmount());

        response.setStatus(
                invoice.getStatus());

        response.setInvoiceFileUrl(
                invoice.getInvoiceFileUrl());

        response.setRemarks(
                invoice.getRemarks());

        response.setActive(
                invoice.getActive());

        return response;
    }

    public InvoiceResponse approveInvoice(Long id) {

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Invoice Not Found"));

        invoice.setStatus("APPROVED");

        invoice = invoiceRepository.save(invoice);

        InvoiceResponse response = new InvoiceResponse();

        response.setId(invoice.getId());
        response.setInvoiceNumber(invoice.getInvoiceNumber());
        response.setPoNumber(invoice.getPurchaseOrder().getPoNumber());
        response.setVendorName(invoice.getVendor().getVendorName());
        response.setInvoiceDate(invoice.getInvoiceDate());
        response.setDueDate(invoice.getDueDate());
        response.setAmount(invoice.getAmount());
        response.setStatus(invoice.getStatus());
        response.setInvoiceFileUrl(invoice.getInvoiceFileUrl());
        response.setRemarks(invoice.getRemarks());
        response.setActive(invoice.getActive());

        return response;
    }
    public InvoiceResponse rejectInvoice(Long id) {

        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Invoice Not Found"));

        invoice.setStatus("REJECTED");

        invoice = invoiceRepository.save(invoice);

        InvoiceResponse response = new InvoiceResponse();

        response.setId(invoice.getId());
        response.setInvoiceNumber(invoice.getInvoiceNumber());
        response.setPoNumber(invoice.getPurchaseOrder().getPoNumber());
        response.setVendorName(invoice.getVendor().getVendorName());
        response.setInvoiceDate(invoice.getInvoiceDate());
        response.setDueDate(invoice.getDueDate());
        response.setAmount(invoice.getAmount());
        response.setStatus(invoice.getStatus());
        response.setInvoiceFileUrl(invoice.getInvoiceFileUrl());
        response.setRemarks(invoice.getRemarks());
        response.setActive(invoice.getActive());

        return response;
    }

    public List<Invoice> getInvoicesByVendor(
            Long vendorId) {

        return invoiceRepository
                .findByVendorId(vendorId);
    }

    public String deleteInvoice(
            Long id) {

        Invoice invoice =
                invoiceRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Invoice Not Found"));

        invoice.setActive(false);

        invoiceRepository.save(invoice);

        return "Invoice Deleted Successfully";
    }

    private String generateInvoiceNumber() {

        Optional<Invoice> lastInvoice =
                invoiceRepository.findTopByOrderByIdDesc();

        int nextNumber = 1;

        if (lastInvoice.isPresent()) {

            String lastNumber =
                    lastInvoice.get().getInvoiceNumber();

            if (lastNumber != null &&
                    lastNumber.contains("-")) {

                String[] parts =
                        lastNumber.split("-");

                nextNumber =
                        Integer.parseInt(parts[2]) + 1;
            }
        }

        return String.format(
                "INV-%d-%04d",
                LocalDate.now().getYear(),
                nextNumber
        );
    }
}
