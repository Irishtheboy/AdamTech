package za.co.admatech.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.admatech.domain.Address;
import za.co.admatech.domain.Customer;
import za.co.admatech.factory.AddressFactory;
import za.co.admatech.factory.CustomerFactory;
import za.co.admatech.repository.CustomerRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

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
    void create_ShouldReturnSavedCustomer_WhenValidCustomer() {
        // Given
        when(customerRepository.save(any(Customer.class))).thenReturn(testCustomer);

        // When
        Customer result = customerService.create(testCustomer);

        // Then
        assertNotNull(result);
        assertEquals(testCustomer.getCustomerId(), result.getCustomerId());
        assertEquals(testCustomer.getFirstName(), result.getFirstName());
        assertEquals(testCustomer.getLastName(), result.getLastName());
        assertEquals(testCustomer.getEmail(), result.getEmail());
        verify(customerRepository).save(testCustomer);
    }

    @Test
    void read_ShouldReturnCustomer_WhenCustomerExists() {
        // Given
        when(customerRepository.findById(1L)).thenReturn(Optional.of(testCustomer));

        // When
        Customer result = customerService.read(1L);

        // Then
        assertNotNull(result);
        assertEquals(testCustomer.getCustomerId(), result.getCustomerId());
        assertEquals(testCustomer.getFirstName(), result.getFirstName());
        verify(customerRepository).findById(1L);
    }

    @Test
    void read_ShouldReturnNull_WhenCustomerDoesNotExist() {
        // Given
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        // When
        Customer result = customerService.read(999L);

        // Then
        assertNull(result);
        verify(customerRepository).findById(999L);
    }

    @Test
    void update_ShouldReturnUpdatedCustomer_WhenValidCustomer() {
        // Given
        Customer updatedCustomer = CustomerFactory.buildCustomer(
                1L, "Jane", "Smith", "jane.smith@example.com", testAddress, null, "+0987654321"
        );
        when(customerRepository.save(any(Customer.class))).thenReturn(updatedCustomer);

        // When
        Customer result = customerService.update(updatedCustomer);

        // Then
        assertNotNull(result);
        assertEquals(updatedCustomer.getFirstName(), result.getFirstName());
        assertEquals(updatedCustomer.getLastName(), result.getLastName());
        assertEquals(updatedCustomer.getEmail(), result.getEmail());
        verify(customerRepository).save(updatedCustomer);
    }

    @Test
    void delete_ShouldReturnTrue_WhenCustomerExists() {
        // Given
        when(customerRepository.existsById(1L)).thenReturn(true);
        doNothing().when(customerRepository).deleteById(1L);

        // When
        boolean result = customerService.delete(1L);

        // Then
        assertTrue(result);
        verify(customerRepository).existsById(1L);
        verify(customerRepository).deleteById(1L);
    }

    @Test
    void delete_ShouldReturnFalse_WhenCustomerDoesNotExist() {
        // Given
        when(customerRepository.existsById(999L)).thenReturn(false);

        // When
        boolean result = customerService.delete(999L);

        // Then
        assertFalse(result);
        verify(customerRepository).existsById(999L);
        verify(customerRepository, never()).deleteById(anyLong());
    }

    @Test
    void getAll_ShouldReturnListOfCustomers() {
        // Given
        Customer customer2 = CustomerFactory.buildCustomer(
                2L, "Jane", "Smith", "jane.smith@example.com", testAddress, null, "+0987654321"
        );
        List<Customer> customers = Arrays.asList(testCustomer, customer2);
        when(customerRepository.findAll()).thenReturn(customers);

        // When
        List<Customer> result = customerService.getAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(testCustomer.getCustomerId(), result.get(0).getCustomerId());
        assertEquals(customer2.getCustomerId(), result.get(1).getCustomerId());
        verify(customerRepository).findAll();
    }

    @Test
    void getAll_ShouldReturnEmptyList_WhenNoCustomers() {
        // Given
        when(customerRepository.findAll()).thenReturn(Arrays.asList());

        // When
        List<Customer> result = customerService.getAll();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(customerRepository).findAll();
    }
}
