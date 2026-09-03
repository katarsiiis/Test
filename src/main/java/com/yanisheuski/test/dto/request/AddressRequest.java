package com.yanisheuski.test.dto.request;

import jakarta.validation.constraints.NotBlank;

public record AddressRequest(

        @NotBlank(message = "House number is required")
        String houseNumber,

        @NotBlank(message = "Street is required")
        String street,

        @NotBlank(message = "City is required")
        String city,

        @NotBlank(message = "Country is required")
        String country,

        @NotBlank(message = "Post code is required")
        String postCode
) {
}