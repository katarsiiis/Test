package com.yanisheuski.test.dto.response;

public record AddressResponse(
        String houseNumber,
        String street,
        String city,
        String country,
        String postCode
) {
}