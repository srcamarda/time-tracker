package com.dev.timetracker.utility;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AddressAPITests {

    @Test
    public void mustReturnCorrectAddress() {
        AddressAPI addressAPI = new AddressAPI("76804496");

        Assertions.assertEquals(addressAPI.getAddrState(), "RO");
        Assertions.assertEquals(addressAPI.getAddrCity(), "Porto Velho");
        Assertions.assertEquals(addressAPI.getAddrStreet(), "Rua Goiás");
        Assertions.assertEquals(addressAPI.getAddrDistrict(), "Tucumanzal");
        Assertions.assertEquals(addressAPI.getAddrCountry(), "Brasil");
    }
}