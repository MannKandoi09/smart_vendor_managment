package vendor_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import vendor_management.entity.Vendor;
import vendor_management.service.VendorService;

import java.util.List;

@RestController
@RequestMapping("/admin/vendors")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @PostMapping
    public Vendor addVendor(
            @RequestBody Vendor vendor) {

        return vendorService.addVendor(vendor);
    }
    @GetMapping("/{id}")
    public Vendor getVendorById(
            @PathVariable Long id) {

        return vendorService.getVendorById(id);
    }
    @PutMapping("/{id}")
    public Vendor updateVendor(
            @PathVariable Long id,
            @RequestBody Vendor vendor) {

        return vendorService
                .updateVendor(id, vendor);
    }
    @DeleteMapping("/{id}")
    public String deleteVendor(
            @PathVariable Long id) {

        return vendorService
                .deleteVendor(id);
    }
    @PutMapping("/{id}/activate")
    public Vendor activateVendor(
            @PathVariable Long id) {

        return vendorService.activateVendor(id);
    }

    @PutMapping("/{id}/deactivate")
    public Vendor deactivateVendor(@PathVariable Long id) {
        return vendorService.deactivateVendor(id);
    }

    @GetMapping
    public List<Vendor> getAllVendors() {

        return vendorService.getAllVendors();
    }
}