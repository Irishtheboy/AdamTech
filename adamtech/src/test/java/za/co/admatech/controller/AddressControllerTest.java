package za.co.admatech.controller;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import za.co.admatech.domain.Address;
import za.co.admatech.service.address_domain_service.AddressService;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AddressController.class)
class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    private Address sampleAddress;

    @BeforeEach
    void setUp() {
        sampleAddress = new Address();
        sampleAddress.setAddressId(12L);
        sampleAddress.setStreetNumber("123");
        sampleAddress.setStreetName("Main Street");
        sampleAddress.setCity("Cape Town");
        sampleAddress.setPostalCode("8001");
    }

    @Test
    void testGetAddressById() throws Exception {
        when(addressService.read(12L)).thenReturn(sampleAddress);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/address/ADDR001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.addressId").value("ADDR001"))
                .andExpect(jsonPath("$.city").value("Cape Town"));

        verify(addressService).read(12L);
    }

    @Test
    void testCreateAddress() throws Exception {
        when(addressService.create(any(Address.class))).thenReturn(sampleAddress);

        String json = """
                {
                  "addressId": "ADDR001",
                  "streetNumber": "123",
                  "streetName": "Main Street",
                  "city": "Cape Town",
                  "postalCode": "8001"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/address/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.streetName").value("Main Street"));
    }

    @Test
    void testDeleteAddress() throws Exception {
        doNothing().when(addressService).delete(12L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/address/delete/ADDR001"))
                .andExpect(status().isOk());

        verify(addressService).delete(12L);
    }
}
