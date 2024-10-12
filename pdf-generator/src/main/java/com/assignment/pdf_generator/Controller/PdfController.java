package com.assignment.pdf_generator.Controller;

import com.assignment.pdf_generator.model.InvoiceRequest;
import com.assignment.pdf_generator.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/pdf")
public class PdfController {

    @Autowired
    public PdfService pdfService;

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generatePdf(@RequestBody InvoiceRequest invoiceRequest) throws IOException {
        byte[] pdfBytes = pdfService.generatePdf(invoiceRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);  // Set Content-Type for PDF
        headers.add("Content-Disposition", "inline; filename=invoice_" + invoiceRequest.getBuyer() + ".pdf"); // Keep inline for preview, change to "attachment" for download

        return ResponseEntity
                .status(HttpStatus.OK)
                .headers(headers)
                .body(pdfBytes);
    }
}
