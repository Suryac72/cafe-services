package com.cafe.java.cafebackend.impl.bill;

import com.cafe.java.cafebackend.constants.CafeConstants;
import com.cafe.java.cafebackend.mappers.BillMapper;
import com.cafe.java.cafebackend.models.Bill;
import com.cafe.java.cafebackend.repo.BillRepository;
import com.cafe.java.cafebackend.services.auth.JwtFilter;
import com.cafe.java.cafebackend.services.bill.BillService;
import com.cafe.java.cafebackend.utils.CafeUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.print.Doc;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

@Service
@Slf4j
public class BillServiceImpl implements BillService {

    @Autowired
    BillRepository billRepository;

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    BillMapper billMapper;

    @Override
    public ResponseEntity<String> generateReport(Bill bill, BindingResult result) {
        log.info("Inside Generate Report : {}",bill.toString());
        try {
            String fileName;
            if(Objects.isNull(bill)){
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_REQUEST_PAYLOAD,
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
            if(!result.hasErrors()){
                if(bill.getIsGenerated() != null && !Boolean.parseBoolean(bill.getIsGenerated().toLowerCase())){
                    fileName =  bill.getBillId().toString();
                }else {
                    fileName = CafeUtils.generateFileName();
                    bill.setBillUUID(fileName);
                    bill.setCreatedBy(jwtFilter.getCurrentUser());
                    bill.setCreatedAt(LocalDate.now().toString());
                    billRepository.save(bill);
                }
                String data = "Name:" + bill.getCustomerName() + "\n" + "Contact Number:" + bill.getContactNumber() + "\n"
                        + "Email:" + bill.getCustomerEmail() + "\n" + "Payment Method:" + bill.getPaymentMethod();

                Document document = new Document();
                String userHome = System.getProperty("user.home");
                String downloadsFolder = userHome + System.getProperty("file.separator") + "Downloads";
                PdfWriter.getInstance(document,new FileOutputStream(downloadsFolder + System.getProperty("file.separator") + fileName + ".pdf"));
                document.open();
                setRectangleInPdf(document);
                Paragraph chunk = new Paragraph("Cafe Management System",getFont("Header"));
                chunk.setAlignment(Element.ALIGN_CENTER);
                document.add(chunk);

                Paragraph paragraph = new Paragraph(data + "\n \n",getFont("Data"));
                document.add(paragraph);
                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                addTableHeader(table);
                JSONArray jsonArray = CafeUtils.getJsonArrayFromString(bill.getProductDetails());
                for(int i = 0;i<jsonArray.length();i++){
                    addRows(table,CafeUtils.getMapFromJson(jsonArray.getString(i)));
                }
                document.add(table);
                Paragraph footer = new Paragraph("Total : " + bill.getTotalAmount() + "\n" + "Thank you for visiting, Please visit again!!",getFont("Data"));
                document.add(footer);
                document.close();
                return new ResponseEntity<>("{\"billUUID\":\"" + fileName + "\"}",HttpStatus.OK);
            }
            else {
                return CafeUtils.handleValidationErrors(result);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Bill>> getBills() {
       try{
           List<Bill> list = new ArrayList<>();
           if(jwtFilter.isAdmin()){
               list = billRepository.getAllBills();
           }else {
               list = billRepository.getBillByUserName(jwtFilter.getCurrentUser());
           }
           return new ResponseEntity<>(list,HttpStatus.OK);
       }catch (Exception e){
           e.printStackTrace();
       }
        return new ResponseEntity<List<Bill>>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<byte[]> getPdf(Bill requestMap,BindingResult result) {
        log.info("Inside getPdf : requestMap {}",requestMap.toString());
        try {
            byte[] byteArray = new byte[0];
            if(requestMap.getBillUUID() == null && !result.hasErrors()){
                return new ResponseEntity<>(byteArray,HttpStatus.BAD_REQUEST);
            }
            String downloadsFolder = System.getProperty("file.separator") + "Downloads";
            String fileName = downloadsFolder + System.getProperty("file.separator") + requestMap.getBillUUID() + ".pdf";
            if(CafeUtils.isFileExists(fileName)){
                byteArray = getByteArray(fileName);
                return new ResponseEntity<>(byteArray,HttpStatus.OK);
            }else {
                requestMap.setCreatedAt(LocalDate.now().toString());
                requestMap.setCreatedBy(jwtFilter.getCurrentUser());
                generateReport(requestMap,result);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<byte[]>(new byte[10],HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteBill(UUID billId) {
        try{
            Optional<Bill> foundBill = billRepository.findById(billId);
            if(foundBill.isEmpty()){
                return CafeUtils.getResponseEntity(CafeConstants.BILL_NOT_FOUND,HttpStatus.BAD_REQUEST);
            }
            else {
                billRepository.deleteById(billId);
                return CafeUtils.getResponseEntity(CafeConstants.BILL_DELETED_SUCCESSFULLY,HttpStatus.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<String>(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * Helper Methods for Impl
     * @param requestMap
     * @return
     */
    private boolean validateRequestMap(Map<String,Object> requestMap){
        return requestMap.containsKey("billUUID") && requestMap.containsKey("customerName")
                && requestMap.containsKey("customerEmail") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("paymentMethod") && requestMap.containsKey("totalAmount")
                && requestMap.containsKey("productDetails");
    }

    private byte[] getByteArray(String fileName) throws Exception {
        log.info("Inside getByteArray : {}",fileName);
        File initialFile = new File(fileName);
        InputStream targetStream = new FileInputStream(initialFile);
        byte[] byteArray = IOUtils.toByteArray(targetStream);
        targetStream.close();
        return byteArray;
    }

    private String convertMapToJsonString(String productDetails) {
        try {
            return new ObjectMapper().writeValueAsString(productDetails);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting Map to JSON", e);
        }
    }

    private void addRows(PdfPTable table, Map<String, Object> data) {
        log.info("Inside addRows");
        table.addCell(data.get("name").toString());
        table.addCell(data.get("category").toString());
        table.addCell(data.get("quantity").toString());
        table.addCell(data.get("price").toString());
        table.addCell(data.get("total").toString());
    }

    private void addTableHeader(PdfPTable table) {
        log.info("Inside addTableHeader");
        Stream.of("Name","Category","Quantity","Price","Total").forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(columnTitle));
            header.setBackgroundColor(BaseColor.YELLOW);
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setVerticalAlignment(Element.ALIGN_CENTER);
            table.addCell(header);
        });
    }

    private void setRectangleInPdf(Document document) throws DocumentException{
        Rectangle rect = new Rectangle(577,825,18,15);
        rect.enableBorderSide(1);
        rect.enableBorderSide(2);
        rect.enableBorderSide(4);
        rect.enableBorderSide(8);
        rect.setBorderColor(BaseColor.BLACK);
        rect.setBorderWidth(1);
        document.add(rect);
    }

    private Font getFont(String type){
        switch (type) {
            case "Header":
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE,18,BaseColor.BLACK);
                headerFont.setStyle(Font.BOLD);
                return headerFont;
            case "Data":
                Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN,18,BaseColor.BLACK);
                dataFont.setStyle(Font.BOLD);
                return dataFont;
            default:
                return new Font();
        }
    }
}
