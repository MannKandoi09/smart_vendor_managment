package vendor_management.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vendor_management.dto.DeliveryRequest;
import vendor_management.dto.DeliveryResponse;
import vendor_management.dto.PurchaseOrderDropdownResponse;
import vendor_management.entity.Delivery;
import vendor_management.entity.Employee;
import vendor_management.entity.PurchaseOrder;
import vendor_management.repository.DeliveryRepository;
import vendor_management.repository.EmployeeRepository;
import vendor_management.repository.PurchaseOrderRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public Delivery createDelivery(
            DeliveryRequest request) {

        if(deliveryRepository.existsByEmployeeId(request.getEmployeeId())) {
            throw new RuntimeException("Employee already assigned to a delivery");
        }

        if(deliveryRepository.existsByPurchaseOrderId(request.getPurchaseOrderId())) {
            throw new RuntimeException("Purchase Order already assigned to a delivery");
        }

        PurchaseOrder purchaseOrder =
                purchaseOrderRepository.findById(
                                request.getPurchaseOrderId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Purchase Order Not Found"));

        Employee employee =
                employeeRepository.findById(
                                request.getEmployeeId())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Employee Not Found"));

        Delivery delivery = new Delivery();

        delivery.setDeliveryCode(
                request.getDeliveryCode());

        delivery.setDispatchDate(
                request.getDispatchDate());

        delivery.setExpectedDate(
                request.getExpectedDate());

        delivery.setCurrentLocation(
                request.getCurrentLocation());

        delivery.setRemarks(
                request.getRemarks());

        delivery.setProofImageUrl(
                request.getProofImageUrl());

        delivery.setStatus("PENDING");

        delivery.setActive(true);

        delivery.setCreatedDate(
                LocalDate.now());

        delivery.setLastUpdatedDate(
                LocalDate.now());

        delivery.setPurchaseOrder(
                purchaseOrder);

        delivery.setEmployee(
                employee);

        if(deliveryRepository.existsByDeliveryCode(
                request.getDeliveryCode())) {

            throw new RuntimeException(
                    "Delivery Code Already Exists");
        }

        return deliveryRepository.save(delivery);
    }
    public DeliveryResponse getDeliveryById(Long id) {

        Delivery delivery =
                deliveryRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Delivery Not Found"));

        DeliveryResponse response =
                new DeliveryResponse();

        response.setId(
                delivery.getId());

        response.setDeliveryCode(
                delivery.getDeliveryCode());

        response.setPoNumber(
                delivery.getPurchaseOrder()
                        .getPoNumber());

        response.setVendorName(
                delivery.getPurchaseOrder()
                        .getVendor()
                        .getVendorName());

        response.setEmployeeCode(
                delivery.getEmployee()
                        .getEmployeeCode());

        response.setEmployeeName(
                delivery.getEmployee()
                        .getFirstName()
                        + " "
                        + delivery.getEmployee()
                        .getLastName());

        response.setEmployeeProfileImageUrl(
                delivery.getEmployee().getProfileImageUrl());

        response.setDispatchDate(
                delivery.getDispatchDate());

        response.setExpectedDate(
                delivery.getExpectedDate());

        response.setDeliveryDate(
                delivery.getDeliveryDate());

        response.setCurrentLocation(
                delivery.getCurrentLocation());

        response.setStatus(
                delivery.getStatus());

        response.setRemarks(
                delivery.getRemarks());

        response.setProofImageUrl(
                delivery.getProofImageUrl());

        response.setPurchaseOrderId(
                delivery.getPurchaseOrder().getId());

        response.setEmployeeId(
                delivery.getEmployee().getId());



        return response;
    }

    public Delivery updateDelivery(
            Long id,
            DeliveryRequest request) {


        Delivery delivery =
                deliveryRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Delivery Not Found"));

        delivery.setDeliveryCode(
                request.getDeliveryCode());

        delivery.setDispatchDate(
                request.getDispatchDate());

        delivery.setExpectedDate(
                request.getExpectedDate());

        delivery.setCurrentLocation(
                request.getCurrentLocation());

        delivery.setRemarks(
                request.getRemarks());

        delivery.setProofImageUrl(
                request.getProofImageUrl());

        delivery.setLastUpdatedDate(
                LocalDate.now());

        delivery.setStatus(
                request.getStatus());

        System.out.println("STATUS = "
                + request.getStatus());

        return deliveryRepository.save(delivery);
    }
    public String deleteDelivery(Long id) {

        Delivery delivery   =
                deliveryRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Delivery Not Found"));

        delivery.setActive(false);

        deliveryRepository.save(delivery);

        return "Delivery Deleted Successfully";
    }
    public Delivery markDelivered(Long id) {

        Delivery delivery =
                deliveryRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Delivery Not Found"));

        delivery.setStatus("DELIVERED");

        delivery.setDeliveryDate(
                LocalDate.now());

        delivery.setLastUpdatedDate(
                LocalDate.now());

        return deliveryRepository.save(delivery);
    }
    public Delivery activateDelivery(Long id) {

        Delivery delivery =
                deliveryRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Delivery Not Found"));

        delivery.setActive(true);

        return deliveryRepository.save(delivery);
    }

    public Delivery deactivateDelivery(Long id) {

        Delivery delivery =
                deliveryRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Delivery Not Found"));

        delivery.setActive(false);

        return deliveryRepository.save(delivery);
    }
    public List<Delivery> getDeliveriesByEmployee(
            Long employeeId) {

        return deliveryRepository
                .findByEmployeeId(employeeId);
    }

    public List<Delivery> getDeliveriesByStatus(
            String status) {

        return deliveryRepository
                .findByStatus(status);
    }

    public List<DeliveryResponse> getAllDeliveries() {

        List<Delivery> deliveries =
                deliveryRepository.findAll();

        List<DeliveryResponse> responseList =
                new ArrayList<>();

        for (Delivery delivery : deliveries) {

            DeliveryResponse response =
                    new DeliveryResponse();

            response.setId(
                    delivery.getId());

            response.setDeliveryCode(
                    delivery.getDeliveryCode());

            response.setPoNumber(
                    delivery.getPurchaseOrder()
                            .getPoNumber());

            response.setVendorName(
                    delivery.getPurchaseOrder()
                            .getVendor()
                            .getVendorName());

            response.setEmployeeCode(
                    delivery.getEmployee()
                            .getEmployeeCode());

            response.setEmployeeName(
                    delivery.getEmployee()
                            .getFirstName()
                            + " "
                            + delivery.getEmployee()
                            .getLastName());

            response.setEmployeeProfileImageUrl(
                    delivery.getEmployee().getProfileImageUrl());

            response.setDispatchDate(
                    delivery.getDispatchDate());

            response.setExpectedDate(
                    delivery.getExpectedDate());

            response.setDeliveryDate(
                    delivery.getDeliveryDate());

            response.setCurrentLocation(
                    delivery.getCurrentLocation());

            response.setStatus(
                    delivery.getStatus());

            response.setRemarks(
                    delivery.getRemarks());

            response.setProofImageUrl(
                    delivery.getProofImageUrl());

            responseList.add(response);
        }

        return responseList;
    }

    public List<Delivery> getActiveDeliveries() {

        return deliveryRepository
                .findByActiveTrue();
    }

    public List<Delivery>
    getDeliveriesByPurchaseOrder(
            Long purchaseOrderId) {

        return deliveryRepository
                .findByPurchaseOrderId(
                        purchaseOrderId);
    }

    public List<Employee> getAvailableEmployees() {
        return employeeRepository.findAvailableEmployees();
    }

    public List<PurchaseOrderDropdownResponse> getAvailablePurchaseOrders() {

        List<PurchaseOrder> purchaseOrders =
                purchaseOrderRepository.findAvailablePurchaseOrders();

        List<PurchaseOrderDropdownResponse> response =
                new ArrayList<>();

        for (PurchaseOrder po : purchaseOrders) {

            PurchaseOrderDropdownResponse dto =
                    new PurchaseOrderDropdownResponse();

            dto.setId(po.getId());
            dto.setPoNumber(po.getPoNumber());

            response.add(dto);
        }

        return response;
    }
}

