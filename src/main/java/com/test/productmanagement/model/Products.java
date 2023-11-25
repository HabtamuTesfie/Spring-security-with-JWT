package com.test.productmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "entry_date")
    private Date entryDate;

    @Column(name = "item_code")
    private String itemCode;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_quantity")
    private int itemQuantity;

    @Column(name = "upc")
    private String upc;

    @Column(name = "ean")
    private String ean;

    @Column(name = "sku")
    private String sku;

    @Column(name = "isbn")
    private String isbn;

    @Column(name = "mpc")
    private String mpc;

    @Column(name = "s_status")
    private String sStatus;
}
