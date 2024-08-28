package com.paytonkawa.customer_service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;

import com.paytonkawa.customer_service.dto.CustomerResponse;
import com.paytonkawa.customer_service.dto.LoginRequest;
import com.paytonkawa.customer_service.entity.Adress;
import com.paytonkawa.customer_service.entity.Customer;
import com.paytonkawa.customer_service.repo.CustomerRepo;
import com.paytonkawa.customer_service.services.CustomerServices;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CustomerServiceApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private ObjectMapper objectMapper;

    private CustomerServices customerServices;



    @BeforeEach
    void setUp() {
        customerServices = new CustomerServices(customerRepo);
        customerRepo.deleteAll();
    }

    // Service Layer Tests
    @Test
    void testCreateCustomer() {
        Customer customer = new Customer("testuser", "testuserlastname", "test.test@gmail.com", new Adress(54682, "Country"));
        ResponseEntity<Map<String, String>> response = customerServices.createCustomer(customer);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertTrue(response.getBody().containsKey("message"));

        java.util.Optional<Customer> savedCustomer = customerRepo.findById(customer.getId());
        assertTrue(savedCustomer.isPresent());
        assertEquals("testuser", savedCustomer.get().getFirstname());
    }

    @Test
    void testUpdateCustomer() {
        Customer customer = new Customer("John", "Doe", "john.doe@example.com", new Adress(54682, "Country"));
        Customer savedCustomer = customerRepo.save(customer);

        Customer updatedCustomer = new Customer("Jane", "Doe", "jane.doe@example.com", new Adress(54682, "Country"));
        ResponseEntity<Map<String, String>> response = customerServices.updateCustomer(savedCustomer.getId(), updatedCustomer);

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertTrue(response.getBody().containsKey("message"));

        Optional<Customer> savedCustomer1 = customerRepo.findById(savedCustomer.getId());
        assertTrue(savedCustomer1.isPresent());
        assertEquals("Jane", savedCustomer1.get().getFirstname());
    }
    
    @Test
    void failedUpdateCustomer() {
        Customer customer = new Customer("John", "Doe", "john.doe@example.com", new Adress(54682, "Country"));
        Customer savedCustomer = customerRepo.save(customer);

        Customer updatedCustomer = new Customer("Jane", "Doe", "jane.doe@example.com", new Adress(54682, "Country"));
        ResponseEntity<Map<String, String>> response = customerServices.updateCustomer(-1, updatedCustomer);

        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void testDeleteCustomer() {
        Customer customer = new Customer("John", "Doe", "john.doe@example.com", new Adress(54682, "Country"));
        customerRepo.save(customer);

        ResponseEntity<Map<String, String>> response = customerServices.deleteCustomer(customer.getId());

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertTrue(response.getBody().containsKey("message"));

        Optional<Customer> deletedCustomer = customerRepo.findById(customer.getId());
        assertFalse(deletedCustomer.isPresent());
    }
    
    @Test
    void testFindCustomerByEmail() {
    	Customer customer = new Customer("adam", "adam", "adam@gmail.com", new Adress(54682, "Country"));
    	customerRepo.save(customer);
    	ResponseEntity<Customer> findCustomerByEmail = customerServices.customerByEmail("adam@gmail.com");
    	assertTrue(findCustomerByEmail.getStatusCode().is2xxSuccessful());
    	ResponseEntity<Customer> findCustomerByNonExistantEmail = customerServices.customerByEmail("marie@gmail.com");
    	assertTrue(findCustomerByNonExistantEmail.getStatusCode().is4xxClientError());
    }
    
    @Test
    void FailedtestDeleteCustomer() {


        ResponseEntity<Map<String, String>> response = customerServices.deleteCustomer(-1);

        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void testCustomerById() {
        Customer customer = new Customer("John", "Doe", "john.doe@example.com", new Adress(54682, "Country"));
        customerRepo.save(customer);

        ResponseEntity<Customer> response = customerServices.CustomerById(customer.getId());

        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertNotNull(response.getBody());
        assertEquals("John", response.getBody().getFirstname());
    }

    @Test
    void CustomerByIdNotFound() {
    	ResponseEntity<Customer> response = customerServices.CustomerById(-1);
        assertTrue(response.getStatusCode().is4xxClientError());
    }
    // Controller Layer Tests
    @Test
    void testCreateCustomerController() throws Exception {
        Customer customer = new Customer("testuser", "testuserlastname", "test.test@gmail.com", new Adress(54682, "Country"));

        mockMvc.perform(post("/customer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("customer with email test.test@gmail.com saved succefully"));

        // Verify customer is saved in the repository
        assertEquals(1, customerRepo.count());
    }

    @Test
    void testGetCustomerByIdController() throws Exception {
        Customer customer = new Customer("John", "Doe", "john.doe@example.com", new Adress(54682, "Country"));
        Customer savedCustomer = customerRepo.save(customer);

        mockMvc.perform(get("/customer/{id}", savedCustomer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstname").value("John"))
                .andExpect(jsonPath("$.lastname").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    void testUpdateCustomerController() throws Exception {
        Customer customer = new Customer("John", "Doe", "john.doe@example.com", new Adress(54682, "Country"));
        Customer savedCustomer = customerRepo.save(customer);

        Customer updatedCustomer = new Customer("Jane", "Doe", "jane.doe@example.com", new Adress(54682, "Country"));

        mockMvc.perform(put("/customer/{id}", savedCustomer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCustomer)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("customer with email jane.doe@example.com updated succefully"));

        // Verify that the customer is updated in the repository
        Customer foundCustomer = customerRepo.findById(savedCustomer.getId()).get();
        assertEquals("Jane", foundCustomer.getFirstname());
        assertEquals("jane.doe@example.com", foundCustomer.getEmail());
    }
    
    @Test
    void testDeleteCustomerController() throws Exception {
        Customer customer = new Customer("John", "Doe", "john.doe@example.com", new Adress(54682, "Country"));
        Customer savedCustomer = customerRepo.save(customer);
        mockMvc.perform(delete("/customer/{id}", savedCustomer.getId())
                .contentType(MediaType.APPLICATION_JSON)) 
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("customer with email "+savedCustomer.getEmail()+" deleted succefully"));
        Optional<Customer> deletedCustomer = customerRepo.findById(savedCustomer.getId());
        assertFalse(deletedCustomer.isPresent());
    }
    
    @Test
    void testNoArgsConstructorCustomerResponse() {
        CustomerResponse customerResponse = new CustomerResponse();
        assertNotNull(customerResponse);
    }
    
    @Test
    void testCreateCustomerThrowsException() {

        var response = customerServices.createCustomer(null);
        assertTrue(response.getStatusCode().is4xxClientError());
    }

    @Test
    void testAllArgsConstructorCustomerResponse() {
        CustomerResponse customerResponse = new CustomerResponse(1, "test@example.com", "USER");

        assertEquals(1, customerResponse.getId());
        assertEquals("test@example.com", customerResponse.getEmail());
        assertEquals("USER", customerResponse.getRole());
    }

    @Test
    void testGettersAndSettersCustomerResponse() {
        CustomerResponse customerResponse = new CustomerResponse();

        customerResponse.setId(2);
        customerResponse.setEmail("test2@example.com");
        customerResponse.setRole("ADMIN");

        assertEquals(2, customerResponse.getId());
        assertEquals("test2@example.com", customerResponse.getEmail());
        assertEquals("ADMIN", customerResponse.getRole());
    }

    @Test
    void testMapFromCustomerCustomerResponse() {
        Customer customer = new Customer();
        customer.setId(3);
        customer.setEmail("customer@example.com");
        customer.setRole("USER");

        CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.mapFromCustomer(customer);

        assertEquals(3, customerResponse.getId());
        assertEquals("customer@example.com", customerResponse.getEmail());
        assertEquals("USER", customerResponse.getRole());
    }

    @Test
    void testToStringCustomerResponse() {
        CustomerResponse customerResponse = new CustomerResponse(4, "test4@example.com", "GUEST");

        String expectedString = "CustomerResponse [id=4, email=test4@example.com, role=GUEST]";
        assertEquals(expectedString, customerResponse.toString());
    }
    
    @Test
    void testNoArgsConstructorLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();
        assertNotNull(loginRequest);
    }

    @Test
    void testAllArgsConstructorLoginRequest() {
        LoginRequest loginRequest = new LoginRequest("testUser", "testPassword");

        assertEquals("testUser", loginRequest.getUsername());
        assertEquals("testPassword", loginRequest.getPassword());
    }

    @Test 
    void testingApplicationIsStarting() {
    	System.out.println("application started");
    }
    @Test
    void testGettersAndSettersLoginRequest() {
        LoginRequest loginRequest = new LoginRequest();

        loginRequest.setUsername("newUser");
        loginRequest.setPassword("newPassword");

        assertEquals("newUser", loginRequest.getUsername());
        assertEquals("newPassword", loginRequest.getPassword());
    }

    @Test
    void testToStringLoginRequest() {
        LoginRequest loginRequest = new LoginRequest("testUser", "testPassword");

        String expectedString = "loginRequest [username=testUser, password=testPassword]";
        assertEquals(expectedString, loginRequest.toString());
    }
    

}
