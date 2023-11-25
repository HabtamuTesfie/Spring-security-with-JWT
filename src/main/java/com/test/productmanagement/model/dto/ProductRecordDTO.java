package com.test.productmanagement.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRecordDTO {
    private String entry_date;
    private String itemcode;
    private String itemname;
    private String itemquantity;
    private String upc;
    private String ean;
    private String sku;
    private String isbn;
    private String mpc;
    @JsonProperty("sStatus")
    private String sStatus;
}
