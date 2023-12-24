package com.cafe.java.cafebackend.services.bill;

import com.cafe.java.cafebackend.models.Bill;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface BillService {
    ResponseEntity<String> generateReport(Bill bill, BindingResult result);

    ResponseEntity<List<Bill>> getBills();

    ResponseEntity<byte[]> getPdf(Bill requestMap,BindingResult result);

    ResponseEntity<String> deleteBill(UUID billId);
}
