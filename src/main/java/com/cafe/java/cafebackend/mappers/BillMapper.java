package com.cafe.java.cafebackend.mappers;

import com.cafe.java.cafebackend.models.Bill;
import com.cafe.java.cafebackend.utils.ProductUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Service
public class BillMapper {

    public void toPersistence(Map<String, Object> requestMap, Bill bill){
        bill.setBillUUID(requestMap.get("billUUID").toString());
        bill.setProductDetails(requestMap.get("productDetails").toString());
        bill.setCreatedAt(LocalDate.now().toString());
        bill.setTotalAmount(requestMap.get("totalAmount").toString());
        bill.setPaymentMethod(requestMap.get("paymentMethod").toString());
        bill.setContactNumber(requestMap.get("contactNumber").toString());
        bill.setCustomerEmail(requestMap.get("customerEmail").toString());
    }
}
