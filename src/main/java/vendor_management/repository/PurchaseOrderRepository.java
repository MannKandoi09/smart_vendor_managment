package vendor_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
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

    long countByStatus(String status);


}
