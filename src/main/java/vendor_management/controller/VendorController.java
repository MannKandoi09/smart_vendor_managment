package vendor_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import vendor_management.dto.VendorRequest;
import vendor_management.dto.VendorResponse;
import vendor_management.entity.Vendor;
import vendor_management.service.VendorService;

import java.util.List;

@RestController
@RequestMapping("/admin/vendors")
// Agar aapka frontend localhost:5173 par chal raha hai toh CORS allow karna safe rahega
@CrossOrigin(origins = "http://localhost:5173", allowedHeaders = "*", allowCredentials = "true")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @PostMapping
    public VendorResponse addVendor(@RequestBody VendorRequest request) {
        return vendorService.addVendor(request);
    }

    // 1. FIXED: Added explicit variable name mapping "id"
    @GetMapping("/{id}")
    public ResponseEntity<Vendor> getVendorById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(vendorService.getVendorById(id));
    }

    // 2. FIXED: Added explicit variable name mapping "id"
    @PutMapping("/{id}")
    public ResponseEntity<Vendor> updateVendor(
            @PathVariable("id") Long id,
            @RequestBody VendorRequest request) {
        return ResponseEntity.ok(vendorService.updateVendor(id, request));
    }

    // 3. FIXED: Added explicit variable name mapping "id"
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteVendor(@PathVariable("id") Long id) {
        vendorService.deleteVendor(id);
        return ResponseEntity.ok("Vendor deleted successfully");
    }

    // 4. FIXED: Added explicit variable name mapping "id"
    @PutMapping("/{id}/activate")
    public Vendor activateVendor(@PathVariable("id") Long id) {
        return vendorService.activateVendor(id);
    }

    // 5. FIXED: Added explicit variable name mapping "id"
    @PutMapping("/{id}/deactivate")
    public Vendor deactivateVendor(@PathVariable("id") Long id) {
        return vendorService.deactivateVendor(id);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Vendor>> searchVendor(@RequestParam String vendorName) {
        return ResponseEntity.ok(vendorService.searchVendor(vendorName));
    }

    @GetMapping
    public List<Vendor> getAllVendors() {
        return vendorService.getAllVendors();
    }
}