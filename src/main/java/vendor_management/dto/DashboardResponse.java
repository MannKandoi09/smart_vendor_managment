package vendor_management.dto;

import java.util.List;

public class DashboardResponse {

    // Cards
    private Long totalVendors;
    private Long totalEmployees;
    private Long totalPurchaseOrders;
    private Long pendingOrders;
    private Double pendingPayments;
    private Double monthlyExpenses;

    // Purchase Order Status
    private Long approvedOrders;
    private Long pendingPurchaseOrders;
    private Long rejectedOrders;

    // Invoice
    private Long totalInvoices;
    private Long approvedInvoices;
    private Long rejectedInvoices;
    private Long pendingInvoices;

    // Payment
    private Long totalPayments;
    private Long successPayments;
    private Long failedPayments;

    // Charts
    private List<VendorPerformanceDTO> vendorPerformance;

    private List<MonthlyExpenseDTO> monthlyExpensesChart;

    private List<RecentPurchaseOrderDTO> recentOrders;

    private List<PendingPaymentDTO> pendingPaymentList;

    public Long getTotalVendors() {
        return totalVendors;
    }

    public void setTotalVendors(Long totalVendors) {
        this.totalVendors = totalVendors;
    }

    public Long getTotalEmployees() {
        return totalEmployees;
    }

    public void setTotalEmployees(Long totalEmployees) {
        this.totalEmployees = totalEmployees;
    }

    public Long getTotalPurchaseOrders() {
        return totalPurchaseOrders;
    }

    public void setTotalPurchaseOrders(Long totalPurchaseOrders) {
        this.totalPurchaseOrders = totalPurchaseOrders;
    }

    public Long getPendingOrders() {
        return pendingOrders;
    }

    public void setPendingOrders(Long pendingOrders) {
        this.pendingOrders = pendingOrders;
    }

    public Double getPendingPayments() {
        return pendingPayments;
    }

    public void setPendingPayments(Double pendingPayments) {
        this.pendingPayments = pendingPayments;
    }

    public Double getMonthlyExpenses() {
        return monthlyExpenses;
    }

    public void setMonthlyExpenses(Double monthlyExpenses) {
        this.monthlyExpenses = monthlyExpenses;
    }

    public Long getApprovedOrders() {
        return approvedOrders;
    }

    public void setApprovedOrders(Long approvedOrders) {
        this.approvedOrders = approvedOrders;
    }

    public Long getPendingPurchaseOrders() {
        return pendingPurchaseOrders;
    }

    public void setPendingPurchaseOrders(Long pendingPurchaseOrders) {
        this.pendingPurchaseOrders = pendingPurchaseOrders;
    }

    public Long getRejectedOrders() {
        return rejectedOrders;
    }

    public void setRejectedOrders(Long rejectedOrders) {
        this.rejectedOrders = rejectedOrders;
    }

    public Long getTotalInvoices() {
        return totalInvoices;
    }

    public void setTotalInvoices(Long totalInvoices) {
        this.totalInvoices = totalInvoices;
    }

    public Long getApprovedInvoices() {
        return approvedInvoices;
    }

    public void setApprovedInvoices(Long approvedInvoices) {
        this.approvedInvoices = approvedInvoices;
    }

    public Long getRejectedInvoices() {
        return rejectedInvoices;
    }

    public void setRejectedInvoices(Long rejectedInvoices) {
        this.rejectedInvoices = rejectedInvoices;
    }

    public Long getPendingInvoices() {
        return pendingInvoices;
    }

    public void setPendingInvoices(Long pendingInvoices) {
        this.pendingInvoices = pendingInvoices;
    }

    public Long getTotalPayments() {
        return totalPayments;
    }

    public void setTotalPayments(Long totalPayments) {
        this.totalPayments = totalPayments;
    }

    public Long getSuccessPayments() {
        return successPayments;
    }

    public void setSuccessPayments(Long successPayments) {
        this.successPayments = successPayments;
    }

    public Long getFailedPayments() {
        return failedPayments;
    }

    public void setFailedPayments(Long failedPayments) {
        this.failedPayments = failedPayments;
    }

    public List<VendorPerformanceDTO> getVendorPerformance() {
        return vendorPerformance;
    }

    public void setVendorPerformance(List<VendorPerformanceDTO> vendorPerformance) {
        this.vendorPerformance = vendorPerformance;
    }

    public List<MonthlyExpenseDTO> getMonthlyExpensesChart() {
        return monthlyExpensesChart;
    }

    public void setMonthlyExpensesChart(List<MonthlyExpenseDTO> monthlyExpensesChart) {
        this.monthlyExpensesChart = monthlyExpensesChart;
    }

    public List<RecentPurchaseOrderDTO> getRecentOrders() {
        return recentOrders;
    }

    public void setRecentOrders(List<RecentPurchaseOrderDTO> recentOrders) {
        this.recentOrders = recentOrders;
    }

    public List<PendingPaymentDTO> getPendingPaymentList() {
        return pendingPaymentList;
    }

    public void setPendingPaymentList(List<PendingPaymentDTO> pendingPaymentList) {
        this.pendingPaymentList = pendingPaymentList;
    }
}
