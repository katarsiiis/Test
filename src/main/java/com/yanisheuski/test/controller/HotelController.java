package com.yanisheuski.test.controller;

import com.yanisheuski.test.dto.request.CreateHotelRequest;
import com.yanisheuski.test.dto.response.HotelDetailsResponse;
import com.yanisheuski.test.dto.response.HotelShortResponse;
import com.yanisheuski.test.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/property-view/hotels")
public class HotelController {

    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Operation(
            summary = "Get all hotels",
            description = "Returns short information about all hotels"
    )
    @GetMapping
    public List<HotelShortResponse> getAllHotels() {
        return hotelService.getAllHotels();
    }

    @Operation(
            summary = "Get hotel by id",
            description = "Returns detailed information about a hotel"
    )
    @GetMapping("/{id}")
    public HotelDetailsResponse getHotelById(
            @PathVariable Long id
    ) {
        return hotelService.getHotelById(id);
    }

    @Operation(
            summary = "Create hotel"
    )
    @PostMapping
    public ResponseEntity<HotelShortResponse> createHotel(
            @Valid @RequestBody CreateHotelRequest request
    ) {

        HotelShortResponse response =
                hotelService.createHotel(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Operation(
            summary = "Add amenities to hotel"
    )
    @PostMapping("/{id}/amenities")
    public HotelDetailsResponse addAmenities(
            @PathVariable Long id,
            @RequestBody List<String> amenities
    ) {

        return hotelService.addAmenities(
                id,
                amenities
        );
    }
}