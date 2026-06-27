package vendor_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vendor_management.dto.InvoiceDropdownResponse;
import vendor_management.dto.PaymentRequest;
import vendor_management.dto.PaymentResponse;
import vendor_management.entity.Invoice;
import vendor_management.entity.Payment;
import vendor_management.entity.Vendor;
import vendor_management.repository.InvoiceRepository;
import vendor_management.repository.PaymentRepository;
import vendor_management.repository.VendorRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private VendorRepository vendorRepository;

    public PaymentResponse createPayment(PaymentRequest request) {

        // Payment Number Validation
        String paymentNumber = generatePaymentNumber();

        if (paymentRepository.existsByPaymentNumber(paymentNumber)) {
            throw new RuntimeException("Payment Number Already Exists");
        }

        // Invoice Validation
        Invoice invoice = invoiceRepository
                .findById(request.getInvoiceId())
                .orElseThrow(() ->
                        new RuntimeException("Invoice Not Found"));

        // Vendor Validation
        Vendor vendor = vendorRepository
                .findById(request.getVendorId())
                .orElseThrow(() ->
                        new RuntimeException("Vendor Not Found"));

        // Invoice Status Validation
        if (!"APPROVED".equalsIgnoreCase(invoice.getStatus())) {
            throw new RuntimeException(
                    "Invoice must be approved before payment.");
        }

        // Duplicate Payment Validation
        if (paymentRepository.existsByInvoiceId(request.getInvoiceId())) {
            throw new RuntimeException(
                    "Payment has already been created for this invoice.");
        }

        // Create Payment
        Payment payment = new Payment();

        payment.setPaymentNumber(paymentNumber);
        payment.setPaymentDate(request.getPaymentDate());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setReferenceNumber(request.getReferenceNumber());
        payment.setRemarks(request.getRemarks());

        payment.setInvoice(invoice);
        payment.setVendor(vendor);

        payment.setAmount(invoice.getAmount());

        payment.setStatus("PENDING");
        payment.setActive(true);
        payment.setCreatedDate(LocalDate.now());

        Payment savedPayment =
                paymentRepository.save(payment);

        // Response
        PaymentResponse response =
                new PaymentResponse();

        response.setId(savedPayment.getId());

        response.setPaymentNumber(
                savedPayment.getPaymentNumber());

        response.setInvoiceNumber(
                savedPayment.getInvoice().getInvoiceNumber());

        response.setVendorName(
                savedPayment.getVendor().getVendorName());

        response.setAmount(
                savedPayment.getAmount());

        response.setPaymentDate(
                savedPayment.getPaymentDate());

        response.setPaymentMethod(
                savedPayment.getPaymentMethod());

        response.setReferenceNumber(
                savedPayment.getReferenceNumber());

        response.setStatus(
                savedPayment.getStatus());

        return response;
    }

    public List<PaymentResponse> getAllPayments() {

        List<Payment> payments =
                paymentRepository.findByActiveTrue();

        List<PaymentResponse> responseList =
                new ArrayList<>();

        for (Payment payment : payments) {
            responseList.add(mapToResponse(payment));
        }

        return responseList;
    }

    public PaymentResponse getPaymentById(Long id) {

        Payment payment =
                paymentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Payment Not Found"));

        return mapToResponse(payment);
    }

    public PaymentResponse updatePayment(
            Long id,
            PaymentRequest request) {

        Payment payment =
                paymentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Payment Not Found"));

        payment.setPaymentDate(request.getPaymentDate());
        payment.setPaymentMethod(request.getPaymentMethod());
        payment.setReferenceNumber(request.getReferenceNumber());
        payment.setRemarks(request.getRemarks());

        payment = paymentRepository.save(payment);

        return mapToResponse(payment);
    }

    public PaymentResponse markSuccess(Long id) {

        Payment payment =
                paymentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Payment Not Found"));

        payment.setStatus("SUCCESS");

        Invoice invoice = payment.getInvoice();
        invoice.setStatus("PAID");
        invoiceRepository.save(invoice);

        payment = paymentRepository.save(payment);

        return mapToResponse(payment);
    }

    public PaymentResponse markFailed(Long id) {

        Payment payment =
                paymentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException("Payment Not Found"));

        payment.setStatus("FAILED");

        payment = paymentRepository.save(payment);

        return mapToResponse(payment);
    }

    public List<PaymentResponse> getPaymentsByVendor(Long vendorId) {

        List<Payment> payments =
                paymentRepository.findByVendorId(vendorId);

        List<PaymentResponse> responseList =
                new ArrayList<>();

        for (Payment payment : payments) {
            responseList.add(mapToResponse(payment));
        }

        return responseList;
    }

    public List<PaymentResponse> getPaymentsByInvoice(Long invoiceId) {

        List<Payment> payments =
                paymentRepository.findByInvoiceId(invoiceId);

        List<PaymentResponse> responseList =
                new ArrayList<>();

        for (Payment payment : payments) {
            responseList.add(mapToResponse(payment));
        }

        return responseList;
    }
    public String deletePayment(
            Long id) {

        Payment payment =
                paymentRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Payment Not Found"));

        payment.setActive(false);

        paymentRepository.save(
                payment);

        return "Payment Deleted Successfully";
    }

    private PaymentResponse mapToResponse(Payment payment) {

        PaymentResponse response = new PaymentResponse();

        response.setId(payment.getId());

        response.setPaymentNumber(
                payment.getPaymentNumber());

        response.setInvoiceNumber(
                payment.getInvoice()
                        .getInvoiceNumber());

        response.setPoNumber(
                payment.getInvoice()
                        .getPurchaseOrder()
                        .getPoNumber());

        response.setVendorName(
                payment.getVendor()
                        .getVendorName());

        response.setAmount(
                payment.getAmount());

        response.setPaymentDate(
                payment.getPaymentDate());

        response.setPaymentMethod(
                payment.getPaymentMethod());

        response.setReferenceNumber(
                payment.getReferenceNumber());

        response.setRemarks(
                payment.getRemarks());

        response.setStatus(
                payment.getStatus());

        response.setActive(
                payment.getActive());

        return response;
    }

    private String generatePaymentNumber() {

        Optional<Payment> lastPayment =
                paymentRepository.findTopByOrderByIdDesc();

        int nextNumber = 1;

        if (lastPayment.isPresent()) {

            String lastNumber =
                    lastPayment.get().getPaymentNumber();

            if (lastNumber != null && !lastNumber.isBlank()) {

                String[] parts = lastNumber.split("-");

                if (parts.length == 3) {

                    nextNumber =
                            Integer.parseInt(parts[2]) + 1;

                } else if (parts.length == 2) {

                    nextNumber =
                            Integer.parseInt(parts[1]) + 1;
                }
            }
        }

        return String.format(
                "PAY-%d-%04d",
                LocalDate.now().getYear(),
                nextNumber
        );
    }

    public List<InvoiceDropdownResponse> getAvailableInvoices() {

        List<Invoice> invoices =
                invoiceRepository.findByStatus("APPROVED");

        List<InvoiceDropdownResponse> responseList =
                new ArrayList<>();

        for (Invoice invoice : invoices) {

            if (!paymentRepository.existsByInvoiceId(invoice.getId())) {

                InvoiceDropdownResponse response =
                        new InvoiceDropdownResponse();

                response.setId(invoice.getId());
                response.setInvoiceNumber(invoice.getInvoiceNumber());

                response.setVendorId(invoice.getVendor().getId());
                response.setVendorName(invoice.getVendor().getVendorName());

                response.setPurchaseOrderId(invoice.getPurchaseOrder().getId());
                response.setPoNumber(invoice.getPurchaseOrder().getPoNumber());

                response.setAmount(invoice.getAmount());
                response.setInvoiceDate(invoice.getInvoiceDate());

                responseList.add(response);
            }
        }

        return responseList;
    }
}