package vendor_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vendor_management.entity.PurchaseOrder;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseOrderRepository
        extends JpaRepository<PurchaseOrder, Long> {

    boolean existsByPoNumber(String poNumber);

    Optional<PurchaseOrder> findByPoNumber(String poNumber);

    List<PurchaseOrder> findByActiveTrue();

    List<PurchaseOrder> findByStatus(String status);

    List<PurchaseOrder> findByPoNumberContainingIgnoreCase(
            String poNumber);

    List<PurchaseOrder> findByEmployeeId(Long employeeId);

    List<PurchaseOrder> findByStatusAndActiveTrue(String status);

    long countByStatus(String status);

    List<PurchaseOrder> findTop5ByOrderByIdDesc();

    List<PurchaseOrder>
    findByVendorIdAndStatusOrderByCreatedDateDesc(
            Long vendorId,
            String status);

    @Query("""
    SELECT p
    FROM PurchaseOrder p
    WHERE p.id NOT IN (
        SELECT d.purchaseOrder.id
        FROM Delivery d
        WHERE d.purchaseOrder IS NOT NULL
    )
""")
    List<PurchaseOrder> findUnassignedPurchaseOrders();

    @Query("""
SELECT p
FROM PurchaseOrder p
WHERE p.status = 'APPROVED'
AND p.active = true
AND p.id NOT IN (
    SELECT d.purchaseOrder.id
    FROM Delivery d
    WHERE d.active = true
)
ORDER BY p.id DESC
""")
    List<PurchaseOrder> findAvailablePurchaseOrders();


    List<PurchaseOrder> findByVendorIdAndStatus(Long vendorId, String approved);
}
