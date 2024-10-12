package com.assignment.pdf_generator.service;

import com.assignment.pdf_generator.model.InvoiceRequest;
import com.assignment.pdf_generator.model.Item;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

@Service
public class PdfService {

    public byte[] generatePdf(InvoiceRequest invoiceRequest) throws IOException {
        validateRequest(invoiceRequest);

        String directoryPath = "invoices";
        String filePath = directoryPath + "/invoice_" + invoiceRequest.getBuyer().replaceAll(" ", "_") + ".pdf";

        createDirectoryIfNotExists(directoryPath);
        File file = new File(filePath);

        if (file.exists()) return Files.readAllBytes(file.toPath());

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); // Declare here
        try (PdfWriter writer = new PdfWriter(byteArrayOutputStream);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {
            addInvoiceContent(document, invoiceRequest);
        }

        writeFile(file, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void validateRequest(InvoiceRequest request) {
        if (request == null || request.getItems() == null) {
            throw new IllegalArgumentException("InvoiceRequest or its items cannot be null");
        }
    }

    private void createDirectoryIfNotExists(String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        if (!directory.exists() && !directory.mkdir()) {
            throw new IOException("Failed to create directory: " + directoryPath);
        }
    }

    private void addInvoiceContent(Document document, InvoiceRequest request) {
        // Add Seller and Buyer Information Side by Side
        float[] columnWidths = {2, 2}; // Two equal columns for seller and buyer
        Table headerTable = new Table(columnWidths);

        // Seller Information
        Cell sellerCell = new Cell();
        sellerCell.add(new Paragraph("Seller:").setBold());
        sellerCell.add(new Paragraph(request.getSeller()));
        sellerCell.add(new Paragraph(request.getSellerAddress()));
        sellerCell.add(new Paragraph("GSTIN: " + request.getSellerGstin()));
        headerTable.addCell(sellerCell);

        // Buyer Information
        Cell buyerCell = new Cell();
        buyerCell.add(new Paragraph("Buyer:").setBold());
        buyerCell.add(new Paragraph(request.getBuyer()));
        buyerCell.add(new Paragraph(request.getBuyerAddress()));
        buyerCell.add(new Paragraph("GSTIN: " + request.getBuyerGstin()));
        headerTable.addCell(buyerCell);

        document.add(headerTable);
        document.add(new Paragraph(" ")); // Adding space between sections

        // Add Item Table
        Table itemTable = new Table(new float[]{4, 2, 2, 2}); // Table for items
        itemTable.addHeaderCell(new Cell().add(new Paragraph("Item").setBold()));
        itemTable.addHeaderCell(new Cell().add(new Paragraph("Quantity").setBold()));
        itemTable.addHeaderCell(new Cell().add(new Paragraph("Rate").setBold()));
        itemTable.addHeaderCell(new Cell().add(new Paragraph("Amount").setBold()));

        populateTableWithItems(itemTable, request.getItems());

        document.add(itemTable);
    }

    private void populateTableWithItems(Table table, List<Item> items) {
        items.forEach(item -> {
            table.addCell(new Cell().add(new Paragraph(item.getName())));
            table.addCell(new Cell().add(new Paragraph(item.getQuantity())));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getRate()))));
            table.addCell(new Cell().add(new Paragraph(String.valueOf(item.getAmount()))));
        });
    }

    private void writeFile(File file, ByteArrayOutputStream byteArrayOutputStream) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            fileOutputStream.write(byteArrayOutputStream.toByteArray());
        }
    }
}
