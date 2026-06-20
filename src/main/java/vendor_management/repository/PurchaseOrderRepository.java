package vendor_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vendor_management.entity.PurchaseOrder;

import java.util.List;

@Repository
public interface PurchaseOrderRepository
        extends JpaRepository<PurchaseOrder, Long> {

    List<PurchaseOrder> findByActiveTrue();

    List<PurchaseOrder> findByStatus(String status);

    List<PurchaseOrder> findByPoNumberContainingIgnoreCase(
            String poNumber);

    long countByStatus(String status);
}
