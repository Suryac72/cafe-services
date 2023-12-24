package com.cafe.java.cafebackend.controllerImpl;

import com.cafe.java.cafebackend.constants.CafeConstants;
import com.cafe.java.cafebackend.controller.BillController;
import com.cafe.java.cafebackend.models.Bill;
import com.cafe.java.cafebackend.services.bill.BillService;
import com.cafe.java.cafebackend.utils.CafeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class BillControllerImpl implements BillController {

    @Autowired
    BillService billService;

    @Override
    public ResponseEntity<String> generateReport(Bill bill, BindingResult result) {
        try{
            return billService.generateReport(bill,result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Bill>> getBills() {
        try{
            return billService.getBills();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<Bill>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        try{
            return billService.getPdf(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(new byte[10],HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
