**PDF Generator API**

This project is a Spring Boot application that generates PDF invoices using a Java template engine. The API accepts invoice details, including seller and buyer information, and produces a well-formatted PDF document.


**Built With**

1. Java: 17
2. Spring Boot: 3.3.4
3. Maven: Project Management
4. iText PDF: For PDF generation
5. Lombok: To reduce boilerplate code
6. JUnit 5 & Mockito: For unit testing
   

**Prerequisites**

Java 17: Ensure you have JDK 17 installed.

Maven: Make sure you have Maven installed for project management.


**Installation**

Clone the repository: git clone https://github.com/yourusername/pdf-generator.git

cd pdf-generator


**Build the project using Maven**: mvn clean install


**Run the application**: mvn spring-boot:run


**The application will start** on http://localhost:8080.


**API Endpoints**

Generate Invoice PDF

Endpoint: POST /api/invoice

Request Body: JSON object containing invoice details.


**Example Request:**

{

  "seller": "ABC Pvt. Ltd.",
  
  "sellerGstin": "28AABBCCDD121FG",
  
  "sellerAddress": "Bengaluru, Karnataka, India",
  
  "buyer": "XYZ Computers",
  
  "buyerGstin": "@#$HHIIJJKKLL678",
  
  "address": "123 Tech Street, Silicon Valley",
  
  "items": [
  
    {
      "name": "Product 1",
      "quantity": "12 Nos",
      "rate": 567.00,
      "amount": 1234.00
    }
    
  ]
  
}


**How to Test with Postman**

1. Open Postman.
2. Create a new POST request.
3. Set the URL to http://localhost:8080/api/invoice.
4. Select Body and choose raw. Set the format to JSON.
5. Paste the example request body from above.
6. Click Send.
*If successful, the server will respond with a PDF document generated from the provided invoice details.*


**Running Tests**
To run the tests, use the following Maven command: mvn test
