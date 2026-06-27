package vendor_management.dto;

import java.time.LocalDate;

public class PaymentRequest {

    private LocalDate paymentDate;

    private String paymentMethod;

    private String referenceNumber;

    private String remarks;

    private Long invoiceId;

    private Long vendorId;

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(
            LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(
            String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(
            String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(
            String remarks) {
        this.remarks = remarks;
    }

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(
            Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(
            Long vendorId) {
        this.vendorId = vendorId;
    }
}