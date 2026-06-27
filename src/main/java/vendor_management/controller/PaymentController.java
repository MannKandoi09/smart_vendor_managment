package vendor_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import vendor_management.dto.InvoiceDropdownResponse;
import vendor_management.dto.PaymentRequest;
import vendor_management.dto.PaymentResponse;
import vendor_management.entity.Payment;
import vendor_management.service.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/admin/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public PaymentResponse createPayment(
            @RequestBody PaymentRequest request) {

        System.out.println("PAYMENT CONTROLLER CALLED");

        return paymentService.createPayment(request);
    }

    @GetMapping
    public List<PaymentResponse> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    public PaymentResponse getPaymentById(
            @PathVariable("id") Long id) {

        return paymentService.getPaymentById(id);
    }

    @PutMapping("/{id}")
    public PaymentResponse updatePayment(
            @PathVariable("id") Long id,
            @RequestBody PaymentRequest request) {

        return paymentService
                .updatePayment(id, request);
    }

    @PutMapping("/{id}/success")
    public PaymentResponse markSuccess(
            @PathVariable("id") Long id) {

        return paymentService.markSuccess(id);
    }

    @PutMapping("/{id}/failed")
    public PaymentResponse markFailed(
            @PathVariable("id") Long id) {

        return paymentService.markFailed(id);
    }

    @GetMapping("/vendor/{vendorId}")
    public List<PaymentResponse> getPaymentsByVendor(
            @PathVariable("vendorId") Long vendorId) {

        return paymentService
                .getPaymentsByVendor(vendorId);
    }

    @GetMapping("/invoice/{invoiceId}")
    public List<PaymentResponse> getPaymentsByInvoice(
            @PathVariable("invoiceId") Long invoiceId) {

        return paymentService
                .getPaymentsByInvoice(invoiceId);
    }

    @DeleteMapping("/{id}")
    public String deletePayment(
            @PathVariable("id") Long id) {

        return paymentService
                .deletePayment(id);
    }

    @GetMapping("/available-invoices")
    public List<InvoiceDropdownResponse> getAvailableInvoices() {

        return paymentService.getAvailableInvoices();
    }
}