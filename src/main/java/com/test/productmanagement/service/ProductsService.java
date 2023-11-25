package com.test.productmanagement.service;

import com.test.productmanagement.model.Products;
import com.test.productmanagement.model.dto.ProductRecordDTO;
import com.test.productmanagement.model.dto.ProductsRequestDTO;
import com.test.productmanagement.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductsService {

    private final ProductsRepository repository;

    @Autowired
    public ProductsService(ProductsRepository repository) {
        this.repository = repository;
    }

    public void addProducts(ProductsRequestDTO requestDTO) {
        List<ProductRecordDTO> records = requestDTO.getRecords();

        List<Products> products = convertToEntities(records);
        repository.saveAll(products);
    }

    public List<Products> getAllProducts() {
        return repository.findAll();
    }


    private List<Products> convertToEntities(List<ProductRecordDTO> records) {
        List<Products> products = new ArrayList<>();
        for (ProductRecordDTO record : records) {
            Products product = new Products();
            product.setEntryDate(parseDate(record.getEntry_date()));
            product.setItemCode(record.getItemcode());
            product.setItemName(record.getItemname());
            product.setItemQuantity(Integer.parseInt(record.getItemquantity()));
            product.setUpc(record.getUpc());
            product.setEan(record.getEan());
            product.setSku(record.getSku());
            product.setIsbn(record.getIsbn());
            product.setMpc(record.getMpc());
            product.setSStatus(record.getSStatus());

            products.add(product);
        }

        return products;
    }

    private Date parseDate(String dateStr) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing date: " + dateStr, e);
        }
    }
}
