package com.yanisheuski.test.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateHotelRequest(

        @NotBlank(message = "Name is required")
        String name,

        String description,

        @NotBlank(message = "Brand is required")
        String brand,

        @NotNull(message = "Address is required")
        @Valid
        AddressRequest address,

        @NotNull(message = "Contacts are required")
        @Valid
        ContactsRequest contacts,

        @NotNull(message = "Arrival time is required")
        @Valid
        ArrivalTimeRequest arrivalTime
) {
}