package za.co.admatech.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.admatech.domain.Customer;
import za.co.admatech.repository.CustomerRepository;
import za.co.admatech.service.customer_domain_service.CustomerService;
import za.co.admatech.util.Helper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceUnitTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer.Builder()
                .customerId("1")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();
    }

    @Test
    void create_ValidCustomer_ReturnsCustomer() {
        try (MockedStatic<Helper> mockedHelper = mockStatic(Helper.class)) {
            mockedHelper.when(() -> Helper.isValidEmail(testCustomer.getEmail())).thenReturn(true);
            when(customerRepository.save(testCustomer)).thenReturn(testCustomer);

            Customer result = customerService.create(testCustomer);

            assertNotNull(result);
            assertEquals(testCustomer, result);
            mockedHelper.verify(() -> Helper.isValidEmail(testCustomer.getEmail()));
            verify(customerRepository).save(testCustomer);
        }
    }

    @Test
    void create_InvalidEmail_ThrowsIllegalArgumentException() {
        Customer customerWithInvalidEmail = new Customer.Builder()
                .customerId("1")
                .firstName("John")
                .lastName("Doe")
                .email("invalid-email")
                .build();

        try (MockedStatic<Helper> mockedHelper = mockStatic(Helper.class)) {
            mockedHelper.when(() -> Helper.isValidEmail("invalid-email")).thenReturn(false);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> customerService.create(customerWithInvalidEmail));
            assertEquals("Invalid email address", exception.getMessage());
            mockedHelper.verify(() -> Helper.isValidEmail("invalid-email"));
            verify(customerRepository, never()).save(any(Customer.class));
        }
    }

    @Test
    void read_ValidStringId_ReturnsCustomer() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));

        Customer result = customerService.read("1");

        assertNotNull(result);
        assertEquals(testCustomer, result);
        verify(customerRepository).findById(1L);
    }

    @Test
    void read_NonExistingId_ThrowsEntityNotFoundException() {
        when(customerRepository.findById(999L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> customerService.read("999"));
        assertEquals("Customer with ID 999 not found", exception.getMessage());
        verify(customerRepository).findById(999L);
    }

    @Test
    void read_InvalidIdFormat_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> customerService.read("invalid"));
        assertEquals("Invalid customer ID format: invalid", exception.getMessage());
        verify(customerRepository, never()).findById(anyLong());
    }

    @Test
    void update_ValidCustomer_ReturnsUpdatedCustomer() {
        try (MockedStatic<Helper> mockedHelper = mockStatic(Helper.class)) {
            mockedHelper.when(() -> Helper.isValidEmail(testCustomer.getEmail())).thenReturn(true);
            when(customerRepository.existsById(1L)).thenReturn(true);
            when(customerRepository.save(testCustomer)).thenReturn(testCustomer);

            Customer result = customerService.update(testCustomer);

            assertNotNull(result);
            assertEquals(testCustomer, result);
            mockedHelper.verify(() -> Helper.isValidEmail(testCustomer.getEmail()));
            verify(customerRepository).existsById(1L);
            verify(customerRepository).save(testCustomer);
        }
    }

    @Test
    void update_CustomerWithNullId_ThrowsIllegalArgumentException() {
        Customer customerWithoutId = new Customer.Builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();

        try (MockedStatic<Helper> mockedHelper = mockStatic(Helper.class)) {
            mockedHelper.when(() -> Helper.isValidEmail("john.doe@example.com")).thenReturn(true);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> customerService.update(customerWithoutId));
            assertEquals("Invalid customer data or missing ID", exception.getMessage());
            verify(customerRepository, never()).save(any(Customer.class));
        }
    }

    @Test
    void update_InvalidEmail_ThrowsIllegalArgumentException() {
        Customer customerWithInvalidEmail = new Customer.Builder()
                .customerId("1")
                .firstName("John")
                .lastName("Doe")
                .email("invalid-email")
                .build();

        try (MockedStatic<Helper> mockedHelper = mockStatic(Helper.class)) {
            mockedHelper.when(() -> Helper.isValidEmail("invalid-email")).thenReturn(false);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> customerService.update(customerWithInvalidEmail));
            assertEquals("Invalid customer data or missing ID", exception.getMessage());
            mockedHelper.verify(() -> Helper.isValidEmail("invalid-email"));
            verify(customerRepository, never()).save(any(Customer.class));
        }
    }

    @Test
    void update_NonExistingCustomer_ThrowsEntityNotFoundException() {
        try (MockedStatic<Helper> mockedHelper = mockStatic(Helper.class)) {
            mockedHelper.when(() -> Helper.isValidEmail(testCustomer.getEmail())).thenReturn(true);
            when(customerRepository.existsById(1L)).thenReturn(false);

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> customerService.update(testCustomer));
            assertEquals("Customer with ID 1 not found", exception.getMessage());
            mockedHelper.verify(() -> Helper.isValidEmail(testCustomer.getEmail()));
            verify(customerRepository).existsById(1L);
            verify(customerRepository, never()).save(any(Customer.class));
        }
    }

    @Test
    void update_InvalidIdFormat_ThrowsIllegalArgumentException() {
        Customer customerWithInvalidId = new Customer.Builder()
                .customerId("invalid")
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();

        try (MockedStatic<Helper> mockedHelper = mockStatic(Helper.class)) {
            mockedHelper.when(() -> Helper.isValidEmail("john.doe@example.com")).thenReturn(true);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> customerService.update(customerWithInvalidId));
            assertEquals("Invalid customer ID format: invalid", exception.getMessage());
            verify(customerRepository, never()).save(any(Customer.class));
        }
    }

    @Test
    void delete_ExistingCustomerId_ReturnsTrue() {
        when(customerRepository.existsById(1L)).thenReturn(true);
        doNothing().when(customerRepository).deleteById(1L);

        boolean result = customerService.delete("1");

        assertTrue(result);
        verify(customerRepository).existsById(1L);
        verify(customerRepository).deleteById(1L);
    }

    @Test
    void delete_NonExistingCustomerId_ReturnsFalse() {
        when(customerRepository.existsById(999L)).thenReturn(false);

        boolean result = customerService.delete("999");

        assertFalse(result);
        verify(customerRepository).existsById(999L);
        verify(customerRepository, never()).deleteById(anyLong());
    }

    @Test
    void delete_InvalidIdFormat_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> customerService.delete("invalid"));
        assertEquals("Invalid customer ID format: invalid", exception.getMessage());
        verify(customerRepository, never()).deleteById(anyLong());
    }

    @Test
    void getAll_CustomersExist_ReturnsListOfCustomers() {
        Customer customer2 = new Customer.Builder()
                .customerId("2")
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .build();

        List<Customer> expectedCustomers = Arrays.asList(testCustomer, customer2);
        when(customerRepository.findAll()).thenReturn(expectedCustomers);

        List<Customer> result = customerService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedCustomers, result);
        verify(customerRepository).findAll();
    }

    @Test
    void getAll_NoCustomers_ReturnsEmptyList() {
        when(customerRepository.findAll()).thenReturn(Arrays.asList());

        List<Customer> result = customerService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(customerRepository).findAll();
    }

    @Test
    void create_NullCustomer_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, () -> customerService.create(null));
        verify(customerRepository, never()).save(any(Customer.class));
    }
}
