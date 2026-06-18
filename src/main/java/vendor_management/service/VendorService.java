package vendor_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vendor_management.entity.Vendor;
import vendor_management.repository.VendorRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    public Vendor addVendor(Vendor vendor) {

        vendor.setCreatedDate(LocalDate.now());
        vendor.setStatus("ACTIVE");

        return vendorRepository.save(vendor);
    }
    public Vendor getVendorById(Long id) {

        return vendorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Vendor Not Found"));
    }
    public Vendor updateVendor(Long id,
                               Vendor updatedVendor) {

        Vendor vendor =
                vendorRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Vendor Not Found"));

        vendor.setVendorName(
                updatedVendor.getVendorName());

        vendor.setCompanyName(
                updatedVendor.getCompanyName());

        vendor.setEmail(
                updatedVendor.getEmail());

        vendor.setPhone(
                updatedVendor.getPhone());

        vendor.setAddress(
                updatedVendor.getAddress());

        return vendorRepository.save(vendor);
    }
    public String deleteVendor(Long id) {

        Vendor vendor =
                vendorRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Vendor Not Found"));

        vendorRepository.delete(vendor);

        return "Vendor Deleted Successfully";
    }
    public Vendor activateVendor(Long id) {

        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Vendor Not Found"));

        vendor.setStatus("ACTIVE");

        return vendorRepository.save(vendor);
    }

    public Vendor deactivateVendor(Long id) {

        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Vendor Not Found"));

        vendor.setStatus("INACTIVE");

        return vendorRepository.save(vendor);
    }

    public List<Vendor> getAllVendors() {

        return vendorRepository.findAll();
    }
}