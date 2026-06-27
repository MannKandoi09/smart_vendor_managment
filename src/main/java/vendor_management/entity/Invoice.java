package vendor_management.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String invoiceNumber;

    private LocalDate invoiceDate;

    private LocalDate dueDate;

    private Double amount;

    private String invoiceFileUrl;

    private String remarks;

    private String status;

    private Boolean active;

    private LocalDate createdDate;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @OneToOne
    @JoinColumn(name = "purchase_order_id")
    private PurchaseOrder purchaseOrder;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(
            String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(
            LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(
            LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(
            Double amount) {
        this.amount = amount;
    }

    public String getInvoiceFileUrl() {
        return invoiceFileUrl;
    }

    public void setInvoiceFileUrl(
            String invoiceFileUrl) {
        this.invoiceFileUrl = invoiceFileUrl;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(
            String remarks) {
        this.remarks = remarks;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(
            String status) {
        this.status = status;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(
            Boolean active) {
        this.active = active;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(
            LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(
            Vendor vendor) {
        this.vendor = vendor;
    }

    public PurchaseOrder getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(
            PurchaseOrder purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }
}