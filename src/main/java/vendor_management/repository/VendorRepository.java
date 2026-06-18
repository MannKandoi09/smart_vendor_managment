package vendor_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vendor_management.entity.Vendor;

public interface VendorRepository
        extends JpaRepository<Vendor, Long> {
}