package com.yanisheuski.test.controller;

import com.yanisheuski.test.dto.response.HotelShortResponse;
import com.yanisheuski.test.service.HotelService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/property-view")
public class SearchController {

    private final HotelService hotelService;

    public SearchController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @Operation(
            summary = "Search hotels"
    )
    @GetMapping("/search")
    public List<HotelShortResponse> search(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String amenities
    ) {

        return hotelService.search(
                name,
                brand,
                city,
                country,
                amenities
        );
    }
}