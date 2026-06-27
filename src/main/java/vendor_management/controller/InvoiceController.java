package vendor_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vendor_management.dto.InvoiceRequest;
import vendor_management.dto.InvoiceResponse;
import vendor_management.entity.Invoice;
import vendor_management.service.InvoiceService;

import java.util.List;

@RestController
@RequestMapping("/admin/invoices")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping
    public InvoiceResponse uploadInvoice(
            @RequestBody InvoiceRequest request) {

        return invoiceService
                .uploadInvoice(request);
    }

    @GetMapping
    public List<InvoiceResponse> getAllInvoices() {

        return invoiceService.getAllInvoices();
    }

    @GetMapping("/{id}")
    public InvoiceResponse getInvoiceById(
            @PathVariable ("id") Long id) {

        return invoiceService
                .getInvoiceById(id);
    }

    @PutMapping("/{id}")
    public InvoiceResponse updateInvoice(
            @PathVariable("id") Long id,
            @RequestBody InvoiceRequest request) {

        return invoiceService.updateInvoice(
                id,
                request);
    }

    @PutMapping("/{id}/approve")
    public InvoiceResponse approveInvoice(
            @PathVariable("id") Long id) {

        return invoiceService.approveInvoice(id);
    }

    @PutMapping("/{id}/reject")
    public InvoiceResponse rejectInvoice(
            @PathVariable("id") Long id) {

        return invoiceService.rejectInvoice(id);
    }

    @GetMapping("/vendor/{vendorId}")
    public List<Invoice> getInvoicesByVendor(
            @PathVariable("vendorId") Long vendorId) {

        return invoiceService
                .getInvoicesByVendor(vendorId);
    }

    @DeleteMapping("/{id}")
    public String deleteInvoice(
            @PathVariable("id") Long id) {

        return invoiceService
                .deleteInvoice(id);
    }

}