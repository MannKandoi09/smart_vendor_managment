package vendor_management.dto;

import java.time.LocalDate;

public class InvoiceRequest {

    private LocalDate invoiceDate;

    private LocalDate dueDate;

    private String invoiceFileUrl;

    private String remarks;

    private Long vendorId;

    private Long purchaseOrderId;

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getInvoiceFileUrl() {
        return invoiceFileUrl;
    }

    public void setInvoiceFileUrl(String invoiceFileUrl) {
        this.invoiceFileUrl = invoiceFileUrl;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public Long getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(Long purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }
}
