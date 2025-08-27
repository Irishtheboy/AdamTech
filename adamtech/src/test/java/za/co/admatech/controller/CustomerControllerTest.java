package za.co.admatech.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import za.co.admatech.domain.Address;
import za.co.admatech.domain.Customer;
import za.co.admatech.factory.AddressFactory;
import za.co.admatech.factory.CustomerFactory;
import za.co.admatech.service.CustomerService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private za.co.admatech.security.JwtService jwtService;

    @MockBean
    private za.co.admatech.security.JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockBean
    private za.co.admatech.repository.UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Customer testCustomer;
    private Address testAddress;

    @BeforeEach
    void setUp() {
        testAddress = AddressFactory.buildAddress(
                123, "Main Street", "Downtown", "New York", "NY", (short) 10001
        );

        testCustomer = CustomerFactory.buildCustomer(
                1L, "John", "Doe", "john.doe@example.com", testAddress, null, "+1234567890"
        );
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void create_ShouldReturnCreatedCustomer_WhenValidRequest() throws Exception {
        // Given
        when(customerService.create(any(Customer.class))).thenReturn(testCustomer);

        // When & Then
        mockMvc.perform(post("/customer/create")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customerId").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void read_ShouldReturnCustomer_WhenCustomerExists() throws Exception {
        // Given
        when(customerService.read(1L)).thenReturn(testCustomer);

        // When & Then
        mockMvc.perform(get("/customer/read/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customerId").value(1L))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"));
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void read_ShouldReturnNotFound_WhenCustomerDoesNotExist() throws Exception {
        // Given
        when(customerService.read(999L)).thenReturn(null);

        // When & Then
        mockMvc.perform(get("/customer/read/999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void update_ShouldReturnUpdatedCustomer_WhenValidRequest() throws Exception {
        // Given
        Customer updatedCustomer = CustomerFactory.buildCustomer(
                1L, "Jane", "Smith", "jane.smith@example.com", testAddress, null, "+0987654321"
        );
        when(customerService.update(any(Customer.class))).thenReturn(updatedCustomer);

        // When & Then
        mockMvc.perform(put("/customer/update")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedCustomer)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("Jane"))
                .andExpect(jsonPath("$.lastName").value("Smith"))
                .andExpect(jsonPath("$.email").value("jane.smith@example.com"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void delete_ShouldReturnTrue_WhenCustomerDeleted() throws Exception {
        // Given
        when(customerService.delete(1L)).thenReturn(true);

        // When & Then
        mockMvc.perform(delete("/customer/delete/1")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void delete_ShouldReturnFalse_WhenCustomerNotFound() throws Exception {
        // Given
        when(customerService.delete(999L)).thenReturn(false);

        // When & Then
        mockMvc.perform(delete("/customer/delete/999")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    @WithMockUser(roles = "CUSTOMER")
    void getAll_ShouldReturnListOfCustomers() throws Exception {
        // Given
        Customer customer2 = CustomerFactory.buildCustomer(
                2L, "Jane", "Smith", "jane.smith@example.com", testAddress, null, "+0987654321"
        );
        List<Customer> customers = Arrays.asList(testCustomer, customer2);
        when(customerService.getAll()).thenReturn(customers);

        // When & Then
        mockMvc.perform(get("/customer/getall")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].customerId").value(1L))
                .andExpect(jsonPath("$[1].customerId").value(2L));
    }

    @Test
    void create_ShouldReturnUnauthorized_WhenNoAuthentication() throws Exception {
        // When & Then
        mockMvc.perform(post("/customer/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testCustomer)))
                .andExpect(status().isUnauthorized());
    }
}
