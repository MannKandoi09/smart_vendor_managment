package vendor_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vendor_management.entity.Payment;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository
        extends JpaRepository<Payment, Long> {

    boolean existsByPaymentNumber(
            String paymentNumber);

    List<Payment> findByStatus(
            String status);

    List<Payment> findByVendorId(
            Long vendorId);

    List<Payment> findByInvoiceId(
            Long invoiceId);

    List<Payment> findByActiveTrue();

    Optional<Payment> findTopByOrderByIdDesc();

    boolean existsByInvoiceId(Long invoiceId);

    long countByStatus(String status);
}