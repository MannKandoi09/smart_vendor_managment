package vendor_management.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import vendor_management.dto.VendorInvoiceRequest;
import vendor_management.dto.VendorInvoiceResponse;
import vendor_management.dto.VendorInvoiceViewResponse;
import vendor_management.service.VendorInvoiceService;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/vendor/invoices")
public class VendorInvoiceController {

    @Autowired
    private VendorInvoiceService service;

    @PostMapping(
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public VendorInvoiceResponse createInvoice(

            @ModelAttribute VendorInvoiceRequest request,

            @RequestParam("invoiceFile") MultipartFile invoiceFile){

        return service.createInvoice(
                request,
                invoiceFile);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadInvoice(
            @PathVariable("id") Long id){

        return service.downloadInvoice(id);
    }

    @GetMapping("/view/{purchaseOrderId}")
    public VendorInvoiceViewResponse
    viewInvoice(

            @PathVariable("purchaseOrderId")
            Long purchaseOrderId){

        return service.viewInvoice(
                purchaseOrderId);
    }

}