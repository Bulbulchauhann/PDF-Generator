package com.assignment.pdf_generator.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class InvoiceRequest {
    private String seller;
    private String sellerGstin;
    private String sellerAddress;
    private String buyer;
    private String buyerGstin;
    private String buyerAddress; // Corrected to buyerAddress for clarity
    private List<Item> items;
}
