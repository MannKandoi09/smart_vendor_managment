package vendor_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vendor_management.entity.Vendor;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendorRepository
        extends JpaRepository<Vendor, Long> {

    Optional<Vendor>
    findByVendorCode(
            String vendorCode);

    List<Vendor>
    findAllByActiveTrue();

    List<Vendor> findByVendorNameContainingIgnoreCase(
            String vendorName);

    long count();

    List<Vendor> findByActiveTrue();
}