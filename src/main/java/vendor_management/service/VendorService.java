package vendor_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vendor_management.dto.VendorRequest;
import vendor_management.dto.VendorResponse;
import vendor_management.entity.Role;
import vendor_management.entity.User;
import vendor_management.entity.Vendor;
import vendor_management.repository.UserRepository;
import vendor_management.repository.VendorRepository;

import java.time.LocalDate;
import java.util.List;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public VendorResponse addVendor(VendorRequest request) {

        if(userRepository.findByUsername(
                request.getUsername()).isPresent()) {

            throw new RuntimeException(
                    "Username already exists");
        }

        // Create User
        User user = new User();

        user.setUsername(request.getUsername());

        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()));

        user.setRole(Role.VENDOR);

        user = userRepository.save(user);

        // Create Vendor
        Vendor vendor = new Vendor();

        vendor.setVendorCode(request.getVendorCode());

        vendor.setVendorName(request.getVendorName());

        vendor.setCompany_name(request.getCompanyName());

        vendor.setEmail(request.getEmail());

        vendor.setMobile(request.getMobile());

        vendor.setGstNumber(request.getGstNumber());

        vendor.setAddress(request.getAddress());

        vendor.setCreatedDate(LocalDate.now());

        vendor.setActive(true);

        vendor.setUser(user);

        vendor = vendorRepository.save(vendor);

        // Create Response
        VendorResponse response = new VendorResponse();

        response.setId(vendor.getId());

        response.setVendorCode(vendor.getVendorCode());

        response.setVendorName(vendor.getVendorName());

        response.setCompanyName(vendor.getCompany_name());

        response.setEmail(vendor.getEmail());

        response.setMobile(vendor.getMobile());

        response.setGstNumber(vendor.getGstNumber());

        response.setAddress(vendor.getAddress());

        response.setCreatedDate(vendor.getCreatedDate());

        response.setActive(vendor.getActive());

        response.setUsername(vendor.getUser().getUsername());

        return response;
    }

    public Vendor getVendorById(Long id) {


        return vendorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendor not found"));
    }

    public Vendor updateVendor(
            Long id,
            VendorRequest request) {

        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Vendor not found"));

        vendor.setVendorName(
                request.getVendorName());

        vendor.setCompany_name(
                request.getCompanyName());

        vendor.setEmail(
                request.getEmail());

        vendor.setMobile(
                request.getMobile());

        vendor.setGstNumber(
                request.getGstNumber());

        vendor.setAddress(
                request.getAddress());

        return vendorRepository.save(vendor);
    }

    public void deleteVendor(Long id) {

        Vendor vendor =
                vendorRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Vendor not found"));

        User user = vendor.getUser();

        vendorRepository.delete(vendor);

        userRepository.delete(user);
    }

    public Vendor deactivateVendor(Long id){

        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Vendor not found"));

        vendor.setActive(false);

        vendorRepository.save(vendor);
        return vendor;
    }

    public List<Vendor> searchVendor(
            String vendorName){

        return vendorRepository
                .findByVendorNameContainingIgnoreCase(
                        vendorName);
    }

    public Long totalVendors(){

        return vendorRepository.count();
    }

    public Vendor activateVendor(Long id) {

        Vendor vendor = vendorRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Vendor not found"));

        vendor.setActive(true);

        return vendorRepository.save(vendor);
    }

    public List<Vendor> getAllVendors() {

        return vendorRepository.findByActiveTrue();
    }




}