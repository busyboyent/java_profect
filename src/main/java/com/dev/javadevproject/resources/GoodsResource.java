package com.dev.javadevproject.resources;

import com.dev.javadevproject.dto.addresses.CityRequest;
import com.dev.javadevproject.dto.addresses.DirectoryRequest;
import com.dev.javadevproject.dto.goods.ProductRequest;
import com.dev.javadevproject.dto.goods.ProductResponse;
import com.dev.javadevproject.entities.ProductEntity;
import com.dev.javadevproject.services.GoodsServiceImpl;
import com.dev.javadevproject.services.ImageService;
import io.swagger.models.Response;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/goods")
public class GoodsResource {

    @Autowired
    GoodsServiceImpl goodsService;

    @Autowired
    ImageService imageService;

    @GetMapping("")
    public ResponseEntity<?> getGoods(@RequestParam(required = false) String id,
                                      @RequestParam(required = false) String search,
                                      @RequestParam(required = false) String category) throws IOException {
        if (id != null && id.length() > 0) {
            ProductEntity item = goodsService.findById(id);
            if(item != null)
                return ResponseEntity.ok(new ProductResponse(Collections.singletonList(item)));
            return ResponseEntity.ok(new ProductResponse(Collections.emptyList()));
        }
        if (search != null && search.length() > 0) {
            ProductResponse productResponse = new ProductResponse(goodsService.findGoodsBySearchRequest(search));
            return ResponseEntity.ok(productResponse);
        }
        if (category != null && category.length() > 0) {
            ProductResponse productResponse = new ProductResponse(goodsService.findGoodsByCategoryRequest(category));
            return ResponseEntity.ok(productResponse);
        }
        ProductResponse productResponse = new ProductResponse(goodsService.getGoodsList());
        return ResponseEntity.ok(productResponse);
    }

    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createProduct(@RequestBody ProductRequest productRequest) throws IOException {
        HashMap<String, String> response = new HashMap<>();
        if (productRequest.name == null || productRequest.category == null || productRequest.price == null) {
            response.put("status", "error");
            response.put("message", "Not enough info");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        goodsService.Create(productRequest.name, productRequest.description, productRequest.category, productRequest.price, productRequest.discount);
        response.put("status", "ok");
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/excel", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createProductByExcel(@RequestPart("file") MultipartFile file) throws IOException {
        HashMap<String, String> response = new HashMap<>();
        HashMap<String, ArrayList<Object>> values = new HashMap<>();
        values.put("category", new ArrayList<>());
        values.put("discount", new ArrayList<>());
        values.put("price", new ArrayList<>());
        values.put("name", new ArrayList<>());
        values.put("description", new ArrayList<>());
        XSSFWorkbook workBook = null;
        try {
            workBook = new XSSFWorkbook(file.getInputStream());
        } catch (IOException e) {
            response.put("message", "Not correct path info");
            e.printStackTrace();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        Sheet sheet = workBook.getSheetAt(0);
        Iterator<Row> it = sheet.iterator();
        String flag = "";
        while (it.hasNext()) {
            Row row = it.next();
            Iterator<Cell> cells = row.iterator();
            while (cells.hasNext()) {
                Cell cell = cells.next();
                switch (cell.getCellTypeEnum()) {
                    case STRING:
                        switch (cell.getStringCellValue()) {
                            case "category":
                                flag = "category";
                                break;
                            case "description":
                                flag = "description";
                                break;
                            case "discount":
                                flag = "discount";
                                break;
                            case "name":
                                flag = "name";
                                break;
                            case "price":
                                flag = "price";
                                break;
                            default:
                                var temp = values.get(flag);
                                try {
                                    temp.add(cell.getStringCellValue());
                                } catch (NullPointerException e){
                                    temp.add("ignore");
                                }
                                values.put(flag, temp);
                                break;
                        }
                    case NUMERIC:
                        try{
                            var temp = values.get(flag);
                            temp.add(cell.getNumericCellValue());
                            values.put(flag, temp);
                        }
                        catch (IllegalStateException ignored) {}
                        break;
                    default:
                        var temp = values.get(flag);
                        temp.add("ignore");
                        values.put(flag, temp);
                        break;
                }
            }
        }
        for (int i = 0; i < values.get("name").size(); i++){
            if (values.get("category").get(i) == null || values.get("discount").get(i) == null ||
                    values.get("name").get(i) == null || values.get("description").get(i) == null ||
                    values.get("price").get(i) == null) {
                response.put("status", "error");
                response.put("message", "Not enough info");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
            try{
                var price = (int)Double.parseDouble(values.get("price").get(i).toString());
                var discount = (int)Double.parseDouble(values.get("discount").get(i).toString());
                goodsService.Create(values.get("name").get(i).toString(), values.get("description").get(i).toString() ,
                        values.get("category").get(i).toString(), price,
                        discount);
            } catch (Exception e){
                e.printStackTrace();
                response.put("status", "error");
                response.put("message", "Not correct values");
                return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
            }
        }
        response.put("message", "ok");
        return ResponseEntity.ok(response);
    }


    @GetMapping(value = "/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getCategoryList() throws IOException {
        HashMap<String, List<String>> response = new HashMap<>();
        response.put("items", goodsService.getCategoryList());
        return ResponseEntity.ok(response);
    }
}
