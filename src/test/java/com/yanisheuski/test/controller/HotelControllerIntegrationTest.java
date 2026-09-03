package com.yanisheuski.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanisheuski.test.entity.Address;
import com.yanisheuski.test.entity.ArrivalTime;
import com.yanisheuski.test.entity.Contacts;
import com.yanisheuski.test.entity.Hotel;
import com.yanisheuski.test.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HotelControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {

        hotelRepository.deleteAll();

        Hotel hotel = new Hotel();

        hotel.setName(
                "DoubleTree by Hilton Minsk"
        );

        hotel.setDescription(
                "The DoubleTree by Hilton Hotel Minsk"
        );

        hotel.setBrand("Hilton");

        hotel.setAddress(
                new Address(
                        "9",
                        "Pobediteley Avenue",
                        "Minsk",
                        "Belarus",
                        "220004"
                )
        );

        hotel.setContacts(
                new Contacts(
                        "+375 17 309-80-00",
                        "doubletreeminsk.info@hilton.com"
                )
        );

        hotel.setArrivalTime(
                new ArrivalTime(
                        LocalTime.of(14, 0),
                        LocalTime.of(12, 0)
                )
        );

        hotel.setAmenities(
                List.of(
                        "Free parking",
                        "Free WiFi",
                        "Fitness center"
                )
        );

        hotelRepository.save(hotel);
    }

    @Test
    void getAllHotels_shouldReturnShortInformation()
            throws Exception {

        mockMvc.perform(
                        get("/property-view/hotels")
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$").isArray()
                )
                .andExpect(
                        jsonPath("$[0].name")
                                .value(
                                        "DoubleTree by Hilton Minsk"
                                )
                )
                .andExpect(
                        jsonPath("$[0].address")
                                .value(
                                        "9 Pobediteley Avenue, Minsk, 220004, Belarus"
                                )
                )
                .andExpect(
                        jsonPath("$[0].phone")
                                .value(
                                        "+375 17 309-80-00"
                                )
                );
    }

    @Test
    void getHotelById_shouldReturnDetails()
            throws Exception {

        Hotel hotel = hotelRepository.findAll()
                .get(0);

        mockMvc.perform(
                        get(
                                "/property-view/hotels/{id}",
                                hotel.getId()
                        )
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.id")
                                .value(
                                        hotel.getId().intValue()
                                )
                )
                .andExpect(
                        jsonPath("$.name")
                                .value(
                                        "DoubleTree by Hilton Minsk"
                                )
                )
                .andExpect(
                        jsonPath("$.brand")
                                .value("Hilton")
                )
                .andExpect(
                        jsonPath(
                                "$.address.city"
                        ).value("Minsk")
                )
                .andExpect(
                        jsonPath(
                                "$.address.country"
                        ).value("Belarus")
                )
                .andExpect(
                        jsonPath(
                                "$.contacts.phone"
                        ).value(
                                "+375 17 309-80-00"
                        )
                )
                .andExpect(
                        jsonPath(
                                "$.arrivalTime.checkIn"
                        ).value("14:00")
                )
                .andExpect(
                        jsonPath(
                                "$.arrivalTime.checkOut"
                        ).value("12:00")
                )
                .andExpect(
                        jsonPath("$.amenities[0]")
                                .value("Free parking")
                );
    }

    @Test
    void getHotelById_shouldReturn404ForMissingHotel()
            throws Exception {

        mockMvc.perform(
                        get(
                                "/property-view/hotels/{id}",
                                999999L
                        )
                )
                .andExpect(
                        status().isNotFound()
                )
                .andExpect(
                        jsonPath("$.status")
                                .value(404)
                );
    }

    @Test
    void searchByCity_shouldReturnMatchingHotels()
            throws Exception {

        mockMvc.perform(
                        get("/property-view/search")
                                .param("city", "minsk")
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$").isArray()
                )
                .andExpect(
                        jsonPath("$.length()")
                                .value(1)
                )
                .andExpect(
                        jsonPath("$[0].name")
                                .value(
                                        "DoubleTree by Hilton Minsk"
                                )
                );
    }

    @Test
    void searchByBrand_shouldReturnMatchingHotels()
            throws Exception {

        mockMvc.perform(
                        get("/property-view/search")
                                .param("brand", "hilton")
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.length()")
                                .value(1)
                )
                .andExpect(
                        jsonPath("$[0].name")
                                .value(
                                        "DoubleTree by Hilton Minsk"
                                )
                );
    }

    @Test
    void searchByAmenity_shouldReturnMatchingHotels()
            throws Exception {

        mockMvc.perform(
                        get("/property-view/search")
                                .param(
                                        "amenities",
                                        "Free WiFi"
                                )
                )
                .andExpect(status().isOk())
                .andExpect(
                        jsonPath("$.length()")
                                .value(1)
                );
    }

    @Test
    void createHotel_shouldReturnCreatedHotel()
            throws Exception {

        String requestBody = """
                {
                  "name": "Marriott Minsk",
                  "description": "New hotel",
                  "brand": "Marriott",
                  "address": {
                    "houseNumber": "20",
                    "street": "Independence Avenue",
                    "city": "Minsk",
                    "country": "Belarus",
                    "postCode": "220001"
                  },
                  "contacts": {
                    "phone": "+375 17 000-00-00",
                    "email": "marriott@example.com"
                  },
                  "arrivalTime": {
                    "checkIn": "15:00",
                    "checkOut": "11:00"
                  }
                }
                """;

        mockMvc.perform(
                        post("/property-view/hotels")
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content(requestBody)
                )
                .andExpect(
                        status().isCreated()
                )
                .andExpect(
                        jsonPath("$.id").exists()
                )
                .andExpect(
                        jsonPath("$.name")
                                .value("Marriott Minsk")
                )
                .andExpect(
                        jsonPath("$.brand")
                                .doesNotExist()
                )
                .andExpect(
                        jsonPath("$.address")
                                .value(
                                        "20 Independence Avenue, Minsk, 220001, Belarus"
                                )
                );
    }

    @Test
    void addAmenities_shouldAddAmenities()
            throws Exception {

        Hotel hotel = hotelRepository.findAll()
                .get(0);

        String requestBody = """
                [
                  "Free parking",
                  "Room service",
                  "Business center"
                ]
                """;

        mockMvc.perform(
                        post(
                                "/property-view/hotels/{id}/amenities",
                                hotel.getId()
                        )
                                .contentType(
                                        MediaType.APPLICATION_JSON
                                )
                                .content(requestBody)
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath("$.amenities")
                                .isArray()
                )
                .andExpect(
                        jsonPath(
                                "$.amenities[0]"
                        ).value("Free parking")
                )
                .andExpect(
                        jsonPath(
                                "$.amenities"
                        ).value(
                                org.hamcrest.Matchers.hasItems(
                                        "Free parking",
                                        "Free WiFi",
                                        "Room service",
                                        "Business center"
                                )
                        )
                );
    }

    @Test
    void histogramByCity_shouldReturnCounts()
            throws Exception {

        mockMvc.perform(
                        get(
                                "/property-view/histogram/city"
                        )
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath("$.Minsk")
                                .value(1)
                );
    }

    @Test
    void histogramByBrand_shouldReturnCounts()
            throws Exception {

        mockMvc.perform(
                        get(
                                "/property-view/histogram/brand"
                        )
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath("$.Hilton")
                                .value(1)
                );
    }

    @Test
    void histogramByAmenities_shouldReturnCounts()
            throws Exception {

        mockMvc.perform(
                        get(
                                "/property-view/histogram/amenities"
                        )
                )
                .andExpect(
                        status().isOk()
                )
                .andExpect(
                        jsonPath("$.Free WiFi")
                                .value(1)
                );
    }
}