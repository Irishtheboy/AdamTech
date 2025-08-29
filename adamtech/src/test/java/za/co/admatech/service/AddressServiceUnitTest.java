package za.co.admatech.service;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import za.co.admatech.domain.Address;
import za.co.admatech.repository.AddressRepository;
import za.co.admatech.service.address_domain_service.AddressService;
import za.co.admatech.util.Helper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressServiceUnitTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    private Address testAddress;

    @BeforeEach
    void setUp() {
        testAddress = new Address.Builder()
                .addressId(1L)
                .streetNumber("123")
                .streetName("Main St")
                .city("Cape Town")
                .province("Western Cape")
                .postalCode("8001")
                .houseNumber("5A")
                .build();
    }

    @Test
    void create_ValidAddress_ReturnsAddress() {
        try (MockedStatic<Helper> mockedHelper = mockStatic(Helper.class)) {
            mockedHelper.when(() -> Helper.isValidAddress(testAddress)).thenReturn(true);
            when(addressRepository.save(testAddress)).thenReturn(testAddress);

            Address result = addressService.create(testAddress);

            assertNotNull(result);
            assertEquals(testAddress, result);
            mockedHelper.verify(() -> Helper.isValidAddress(testAddress));
            verify(addressRepository).save(testAddress);
        }
    }

    @Test
    void create_InvalidAddress_ThrowsIllegalArgumentException() {
        try (MockedStatic<Helper> mockedHelper = mockStatic(Helper.class)) {
            mockedHelper.when(() -> Helper.isValidAddress(testAddress)).thenReturn(false);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> addressService.create(testAddress));
            assertEquals("Invalid address data", exception.getMessage());
            mockedHelper.verify(() -> Helper.isValidAddress(testAddress));
            verify(addressRepository, never()).save(any(Address.class));
        }
    }

    @Test
    void create_NullAddress_ThrowsIllegalArgumentException() {
        try (MockedStatic<Helper> mockedHelper = mockStatic(Helper.class)) {
            mockedHelper.when(() -> Helper.isValidAddress(null)).thenReturn(false);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> addressService.create(null));
            assertEquals("Invalid address data", exception.getMessage());
            mockedHelper.verify(() -> Helper.isValidAddress(null));
            verify(addressRepository, never()).save(any(Address.class));
        }
    }

    @Test
    void read_ExistingAddressId_ReturnsAddress() {
        when(addressRepository.findById(1L)).thenReturn(Optional.of(testAddress));

        Address result = addressService.read(1L);

        assertNotNull(result);
        assertEquals(testAddress, result);
        verify(addressRepository).findById(1L);
    }

    @Test
    void read_NonExistingAddressId_ThrowsEntityNotFoundException() {
        when(addressRepository.findById(999L)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
            () -> addressService.read(999L));
        assertEquals("Address with ID 999 not found", exception.getMessage());
        verify(addressRepository).findById(999L);
    }

    @Test
    void update_ValidAddressWithId_ReturnsUpdatedAddress() {
        try (MockedStatic<Helper> mockedHelper = mockStatic(Helper.class)) {
            mockedHelper.when(() -> Helper.isValidAddress(testAddress)).thenReturn(true);
            when(addressRepository.existsById(1L)).thenReturn(true);
            when(addressRepository.save(testAddress)).thenReturn(testAddress);

            Address result = addressService.update(testAddress);

            assertNotNull(result);
            assertEquals(testAddress, result);
            mockedHelper.verify(() -> Helper.isValidAddress(testAddress));
            verify(addressRepository).existsById(1L);
            verify(addressRepository).save(testAddress);
        }
    }

    @Test
    void update_InvalidAddress_ThrowsIllegalArgumentException() {
        try (MockedStatic<Helper> mockedHelper = mockStatic(Helper.class)) {
            mockedHelper.when(() -> Helper.isValidAddress(testAddress)).thenReturn(false);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> addressService.update(testAddress));
            assertEquals("Invalid address data or missing ID", exception.getMessage());
            mockedHelper.verify(() -> Helper.isValidAddress(testAddress));
            verify(addressRepository, never()).save(any(Address.class));
        }
    }

    @Test
    void update_AddressWithNullId_ThrowsIllegalArgumentException() {
        Address addressWithoutId = new Address.Builder()
                .streetNumber("123")
                .streetName("Main St")
                .city("Cape Town")
                .province("Western Cape")
                .postalCode("8001")
                .houseNumber("5A")
                .build();

        try (MockedStatic<Helper> mockedHelper = mockStatic(Helper.class)) {
            mockedHelper.when(() -> Helper.isValidAddress(addressWithoutId)).thenReturn(true);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> addressService.update(addressWithoutId));
            assertEquals("Invalid address data or missing ID", exception.getMessage());
            verify(addressRepository, never()).save(any(Address.class));
        }
    }

    @Test
    void update_NonExistingAddress_ThrowsEntityNotFoundException() {
        try (MockedStatic<Helper> mockedHelper = mockStatic(Helper.class)) {
            mockedHelper.when(() -> Helper.isValidAddress(testAddress)).thenReturn(true);
            when(addressRepository.existsById(1L)).thenReturn(false);

            EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> addressService.update(testAddress));
            assertEquals("Address with ID 1 not found", exception.getMessage());
            mockedHelper.verify(() -> Helper.isValidAddress(testAddress));
            verify(addressRepository).existsById(1L);
            verify(addressRepository, never()).save(any(Address.class));
        }
    }

    @Test
    void delete_ExistingAddressId_ReturnsTrue() {
        when(addressRepository.existsById(1L)).thenReturn(true);
        doNothing().when(addressRepository).deleteById(1L);

        boolean result = addressService.delete(1L);

        assertTrue(result);
        verify(addressRepository).existsById(1L);
        verify(addressRepository).deleteById(1L);
    }

    @Test
    void delete_NonExistingAddressId_ReturnsFalse() {
        when(addressRepository.existsById(999L)).thenReturn(false);

        boolean result = addressService.delete(999L);

        assertFalse(result);
        verify(addressRepository).existsById(999L);
        verify(addressRepository, never()).deleteById(anyLong());
    }

    @Test
    void getAll_AddressesExist_ReturnsListOfAddresses() {
        Address address2 = new Address.Builder()
                .addressId(2L)
                .streetNumber("456")
                .streetName("Second St")
                .city("Johannesburg")
                .province("Gauteng")
                .postalCode("2001")
                .houseNumber("10B")
                .build();

        List<Address> expectedAddresses = Arrays.asList(testAddress, address2);
        when(addressRepository.findAll()).thenReturn(expectedAddresses);

        List<Address> result = addressService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(expectedAddresses, result);
        verify(addressRepository).findAll();
    }

    @Test
    void getAll_NoAddresses_ReturnsEmptyList() {
        when(addressRepository.findAll()).thenReturn(Arrays.asList());

        List<Address> result = addressService.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(addressRepository).findAll();
    }
}
