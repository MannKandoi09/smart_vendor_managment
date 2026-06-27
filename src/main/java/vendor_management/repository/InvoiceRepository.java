package vendor_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vendor_management.entity.Delivery;
import vendor_management.entity.Invoice;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository
        extends JpaRepository<Invoice, Long> {

    boolean existsByInvoiceNumber(
            String invoiceNumber);

    List<Invoice> findByStatus(
            String status);

    List<Invoice> findByVendorId(
            Long vendorId);

    Optional<Invoice> findTopByOrderByIdDesc();
    long countByStatus(String status);

    Optional<Invoice>
    findByPurchaseOrderId(Long purchaseOrderId);


    List<Invoice> findByActiveTrue();

    boolean existsByPurchaseOrderId(
            Long purchaseOrderId);



}