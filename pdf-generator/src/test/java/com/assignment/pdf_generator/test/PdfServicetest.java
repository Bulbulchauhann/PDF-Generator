package com.assignment.pdf_generator.test;

import com.assignment.pdf_generator.model.InvoiceRequest;
import com.assignment.pdf_generator.model.Item;
import com.assignment.pdf_generator.service.PdfService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class PdfServiceTest {

    @InjectMocks
    private PdfService pdfService;

    @BeforeEach
    void setUp() {}

    @Test
    void testGeneratePdf_ValidInput() throws IOException {
        Item item = new Item("Product 1", "12 Nos", 567.00, 1234.00);
        InvoiceRequest invoiceRequest = new InvoiceRequest(
                "ABC Pvt. Ltd.",
                "28AABBCCDD121FG",
                "123 Main St",
                "XYZ Computers",
                "@#$HHIIJJKKLL678",
                "456 Secondary St",
                Collections.singletonList(item)
        );

        byte[] pdfBytes = pdfService.generatePdf(invoiceRequest);
        assertNotNull(pdfBytes, "PDF should not be null");
        assertTrue(pdfBytes.length > 0, "PDF content should not be empty");
    }

    @Test
    void testGeneratePdf_EmptyItems() throws IOException {
        InvoiceRequest invoiceRequest = new InvoiceRequest(
                "ABC Pvt. Ltd.",
                "28AABBCCDD121FG",
                "123 Main St",
                "XYZ Computers",
                "@#$HHIIJJKKLL678",
                "456 Secondary St",
                Collections.emptyList()
        );

        byte[] pdfBytes = pdfService.generatePdf(invoiceRequest);
        assertNotNull(pdfBytes, "PDF should not be null");
        assertTrue(pdfBytes.length > 0, "PDF content should not be empty");
    }

    @Test
    void testGeneratePdf_NullItems() {
        InvoiceRequest invoiceRequest = new InvoiceRequest(
                "ABC Pvt. Ltd.",
                "28AABBCCDD121FG",
                "123 Main St",
                "XYZ Computers",
                "@#$HHIIJJKKLL678",
                "456 Secondary St",
                null
        );

        assertThrows(IllegalArgumentException.class, () -> pdfService.generatePdf(invoiceRequest));
    }

    @Test
    void testGeneratePdf_NullInvoiceRequest() {
        assertThrows(IllegalArgumentException.class, () -> pdfService.generatePdf(null));
    }
}
