package vendor_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import vendor_management.dto.VendorInvoiceRequest;
import vendor_management.dto.VendorInvoiceResponse;
import vendor_management.dto.VendorInvoiceViewResponse;
import vendor_management.entity.Invoice;
import vendor_management.entity.PurchaseOrder;
import vendor_management.repository.InvoiceRepository;
import vendor_management.repository.PurchaseOrderRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class VendorInvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    public VendorInvoiceResponse createInvoice(
            VendorInvoiceRequest request,
            MultipartFile invoiceFile) {

        // Purchase Order Validation
        PurchaseOrder purchaseOrder =
                purchaseOrderRepository.findById(
                                request.getPurchaseOrderId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Purchase Order Not Found"));

        // Purchase Order Status Validation
        if (!purchaseOrder.getStatus()
                .equalsIgnoreCase("APPROVED")) {

            throw new RuntimeException(
                    "Purchase Order is not Approved.");
        }

        // Duplicate Invoice Validation
        if (invoiceRepository.existsByPurchaseOrderId(
                request.getPurchaseOrderId())) {

            throw new RuntimeException(
                    "Invoice already exists for this Purchase Order.");
        }

        // Amount Validation
        if (request.getAmount() >
                purchaseOrder.getGrandTotal()) {

            throw new RuntimeException(
                    "Invoice Amount cannot exceed Purchase Order Amount.");
        }

        // PDF Validation
        if (invoiceFile == null || invoiceFile.isEmpty()) {

            throw new RuntimeException(
                    "Invoice PDF is required.");
        }

        String originalFileName =
                invoiceFile.getOriginalFilename();

        if (originalFileName == null ||
                !originalFileName.toLowerCase().endsWith(".pdf")) {

            throw new RuntimeException(
                    "Only PDF files are allowed.");
        }

        if (invoiceFile.getSize() >
                (5 * 1024 * 1024)) {

            throw new RuntimeException(
                    "Maximum file size is 5 MB.");
        }

        // Create Upload Folder
        Path uploadPath =
                Paths.get("uploads/invoices");

        try {

            if (!Files.exists(uploadPath)) {

                Files.createDirectories(uploadPath);
            }

            // Generate Unique File Name
            String uniqueFileName =
                    System.currentTimeMillis()
                            + "_"
                            + originalFileName;

            // Save File
            Files.copy(
                    invoiceFile.getInputStream(),
                    uploadPath.resolve(uniqueFileName),
                    StandardCopyOption.REPLACE_EXISTING
            );

            // Create Invoice
            Invoice invoice =
                    new Invoice();

            invoice.setInvoiceNumber(
                    request.getInvoiceNumber());

            invoice.setInvoiceDate(
                    request.getInvoiceDate());

            invoice.setDueDate(
                    request.getDueDate());

            invoice.setAmount(
                    request.getAmount());

            invoice.setRemarks(
                    request.getRemarks());

            invoice.setStatus(
                    "PENDING_REVIEW");

            invoice.setActive(
                    true);

            invoice.setCreatedDate(
                    LocalDate.now());

            invoice.setVendor(
                    purchaseOrder.getVendor());

            invoice.setPurchaseOrder(
                    purchaseOrder);

            // Save PDF Path
            invoice.setInvoiceFileUrl(
                    "uploads/invoices/"
                            + uniqueFileName);

            // Save Invoice
            Invoice savedInvoice =
                    invoiceRepository.save(invoice);

            // Response
            VendorInvoiceResponse response =
                    new VendorInvoiceResponse();

            response.setId(
                    savedInvoice.getId());

            response.setInvoiceNumber(
                    savedInvoice.getInvoiceNumber());

            response.setPoNumber(
                    purchaseOrder.getPoNumber());

            response.setVendorName(
                    purchaseOrder.getVendor()
                            .getVendorName());

            response.setAmount(
                    savedInvoice.getAmount());

            response.setStatus(
                    savedInvoice.getStatus());

            response.setPdfUrl(
                    savedInvoice.getInvoiceFileUrl());

            return response;

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to upload Invoice PDF.");
        }
    }

    public ResponseEntity<Resource> downloadInvoice(Long id) {

        Invoice invoice =
                invoiceRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Invoice Not Found"));

        if (invoice.getInvoiceFileUrl() == null ||
                invoice.getInvoiceFileUrl().isBlank()) {

            throw new RuntimeException("Invoice PDF not available.");
        }

        try {

            Path path =
                    Paths.get(invoice.getInvoiceFileUrl());

            Resource resource =
                    new UrlResource(path.toUri());

            if (!resource.exists()) {
                throw new RuntimeException("Invoice PDF not found.");
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(
                            HttpHeaders.CONTENT_DISPOSITION,
                            ContentDisposition
                                    .attachment()
                                    .filename(resource.getFilename())
                                    .build()
                                    .toString()
                    )
                    .body(resource);

        } catch (MalformedURLException e) {

            throw new RuntimeException(
                    "Unable to download invoice.");
        }
    }

    public VendorInvoiceViewResponse
    viewInvoice(Long purchaseOrderId){

        PurchaseOrder purchaseOrder =
                purchaseOrderRepository
                        .findById(purchaseOrderId)
                        .orElseThrow(()->
                                new RuntimeException(
                                        "Purchase Order Not Found"));

        Invoice invoice =
                invoiceRepository
                        .findByPurchaseOrderId(
                                purchaseOrderId)
                        .orElseThrow(()->
                                new RuntimeException(
                                        "Invoice Not Found"));

        VendorInvoiceViewResponse response =
                new VendorInvoiceViewResponse();

        response.setId(
                invoice.getId());

        response.setInvoiceNumber(
                invoice.getInvoiceNumber());

        response.setInvoiceDate(
                invoice.getInvoiceDate());

        response.setDueDate(
                invoice.getDueDate());

        response.setAmount(
                invoice.getAmount());

        response.setRemarks(
                invoice.getRemarks());

        response.setStatus(
                invoice.getStatus());

        response.setPdfUrl(
                invoice.getInvoiceFileUrl());

        response.setPoNumber(
                purchaseOrder.getPoNumber());

        response.setOrderDate(
                purchaseOrder.getOrderDate());

        response.setExpectedDeliveryDate(
                purchaseOrder.getExpectedDeliveryDate());

        response.setPaymentTerms(
                purchaseOrder.getPaymentTerms());

        response.setVendorName(
                purchaseOrder.getVendor()
                        .getVendorName());

        response.setBillingAddress(
                purchaseOrder.getBillingAddress());

        response.setDeliveryAddress(
                purchaseOrder.getDeliveryAddress());

        response.setSubTotal(
                purchaseOrder.getSubTotal());

        response.setTaxAmount(
                purchaseOrder.getTaxAmount());

        response.setGrandTotal(
                purchaseOrder.getGrandTotal());

        return response;
    }


}
