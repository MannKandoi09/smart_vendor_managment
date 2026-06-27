package vendor_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vendor_management.entity.Delivery;

import java.util.List;

@Repository
public interface DeliveryRepository
        extends JpaRepository<Delivery, Long> {



    List<Delivery> findByEmployeeId(Long employeeId);

    List<Delivery> findByStatus(String status);

    List<Delivery> findByActiveTrue();

    List<Delivery> findByPurchaseOrderId(
            Long purchaseOrderId);

    boolean existsByDeliveryCode(
            String deliveryCode);

    boolean existsByEmployeeId(Long employeeId);

    boolean existsByPurchaseOrderId(Long purchaseOrderId);

}