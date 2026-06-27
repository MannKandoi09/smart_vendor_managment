package vendor_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vendor_management.dto.*;
import vendor_management.entity.Payment;
import vendor_management.entity.PurchaseOrder;
import vendor_management.repository.*;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public DashboardResponse getDashboard() {

        DashboardResponse response =
                new DashboardResponse();

        // Cards

        response.setTotalVendors(
                vendorRepository.count());

        response.setTotalEmployees(
                employeeRepository.count());

        response.setTotalPurchaseOrders(
                purchaseOrderRepository.count());

        response.setPendingOrders(
                purchaseOrderRepository.countByStatus("PENDING"));

        response.setApprovedOrders(
                purchaseOrderRepository.countByStatus("APPROVED"));

        response.setRejectedOrders(
                purchaseOrderRepository.countByStatus("REJECTED"));

        response.setTotalInvoices(
                invoiceRepository.count());

        response.setApprovedInvoices(
                invoiceRepository.countByStatus("APPROVED"));

        response.setRejectedInvoices(
                invoiceRepository.countByStatus("REJECTED"));

        response.setPendingInvoices(
                invoiceRepository.countByStatus("PENDING_REVIEW"));

        response.setTotalPayments(
                paymentRepository.count());

        response.setSuccessPayments(
                paymentRepository.countByStatus("SUCCESS"));

        response.setFailedPayments(
                paymentRepository.countByStatus("FAILED"));

        List<PurchaseOrder> orders =
                purchaseOrderRepository.findTop5ByOrderByIdDesc();

        List<RecentPurchaseOrderDTO> recentOrders =
                new ArrayList<>();

        for (PurchaseOrder po : orders) {

            RecentPurchaseOrderDTO dto =
                    new RecentPurchaseOrderDTO();

            dto.setPoNumber(po.getPoNumber());

            dto.setVendorName(
                    po.getVendor().getVendorName());

            dto.setAmount(
                    po.getGrandTotal());

            dto.setStatus(
                    po.getStatus());

            recentOrders.add(dto);
        }

        response.setRecentOrders(
                recentOrders);

        List<PurchaseOrder> allPurchaseOrders =
                purchaseOrderRepository.findAll();

        Map<String, Double> vendorAmountMap =
                new HashMap<>();

        for (PurchaseOrder po : allPurchaseOrders) {

            if (po.getVendor() == null) {
                continue;
            }

            String vendorName =
                    po.getVendor().getVendorName();

            Double amount =
                    po.getGrandTotal();

            vendorAmountMap.put(
                    vendorName,
                    vendorAmountMap.getOrDefault(
                            vendorName,
                            0.0
                    ) + amount
            );
        }

        List<VendorPerformanceDTO> vendorPerformance =
                new ArrayList<>();

        for (Map.Entry<String, Double> entry
                : vendorAmountMap.entrySet()) {

            VendorPerformanceDTO dto =
                    new VendorPerformanceDTO();

            dto.setVendorName(
                    entry.getKey());

            dto.setTotalAmount(
                    entry.getValue());

            vendorPerformance.add(dto);
        }

        response.setVendorPerformance(
                vendorPerformance);

        // ===============================
// Monthly Expense Chart
// ===============================

        List<Payment> payments =
                paymentRepository.findAll();

        Map<Integer, Double> monthAmountMap =
                new HashMap<>();

        for (Payment payment : payments) {

            if (payment.getStatus() == null ||
                    !payment.getStatus().equalsIgnoreCase("SUCCESS")) {
                continue;
            }

            int month =
                    payment.getPaymentDate().getMonthValue();

            Double amount =
                    payment.getAmount();

            monthAmountMap.put(
                    month,
                    monthAmountMap.getOrDefault(
                            month,
                            0.0
                    ) + amount
            );
        }

        List<MonthlyExpenseDTO> monthlyExpenses =
                new ArrayList<>();

        for (int month = 1; month <= 12; month++) {

            MonthlyExpenseDTO dto =
                    new MonthlyExpenseDTO();

            dto.setMonth(
                    Month.of(month).name().substring(0, 1)
                            + Month.of(month).name().substring(1, 3).toLowerCase());

            dto.setAmount(
                    monthAmountMap.getOrDefault(
                            month,
                            0.0
                    ));

            monthlyExpenses.add(dto);
        }

        response.setMonthlyExpensesChart(
                monthlyExpenses);

        double totalMonthlyExpense = 0.0;

        for (MonthlyExpenseDTO dto : monthlyExpenses) {
            totalMonthlyExpense += dto.getAmount();
        }

        response.setMonthlyExpenses(
                totalMonthlyExpense);

        List<Payment> pendingPayments =
                paymentRepository.findByStatus("PENDING");

        List<PendingPaymentDTO> pendingPaymentList =
                new ArrayList<>();

        for (Payment payment : pendingPayments) {

            PendingPaymentDTO dto =
                    new PendingPaymentDTO();

            dto.setVendorName(
                    payment.getVendor().getVendorName());

            dto.setInvoiceNumber(
                    payment.getInvoice().getInvoiceNumber());

            dto.setAmount(
                    payment.getAmount());

            dto.setDueDate(
                    payment.getInvoice().getDueDate());

            pendingPaymentList.add(dto);
        }

        response.setPendingPaymentList(
                pendingPaymentList);

        return response;
    }

}