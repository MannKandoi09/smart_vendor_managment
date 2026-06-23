package vendor_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vendor_management.entity.PurchaseOrderItem;

@Repository
public interface PurchaseOrderItemRepository
        extends JpaRepository<PurchaseOrderItem, Long> {
}