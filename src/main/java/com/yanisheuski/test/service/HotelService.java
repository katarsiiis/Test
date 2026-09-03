package com.yanisheuski.test.service;

import com.yanisheuski.test.dto.request.CreateHotelRequest;
import com.yanisheuski.test.dto.response.HotelDetailsResponse;
import com.yanisheuski.test.dto.response.HotelShortResponse;
import com.yanisheuski.test.entity.Hotel;
import com.yanisheuski.test.exception.HotelNotFoundException;
import com.yanisheuski.test.mapper.HotelMapper;
import com.yanisheuski.test.repository.HotelRepository;
import com.yanisheuski.test.specification.HotelSpecifications;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class HotelService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;

    public HotelService(
            HotelRepository hotelRepository,
            HotelMapper hotelMapper
    ) {
        this.hotelRepository = hotelRepository;
        this.hotelMapper = hotelMapper;
    }

    public List<HotelShortResponse> getAllHotels() {
        return hotelRepository.findAll()
                .stream()
                .map(hotelMapper::toShortResponse)
                .toList();
    }

    public HotelDetailsResponse getHotelById(Long id) {
        Hotel hotel = findHotelById(id);

        return hotelMapper.toDetailsResponse(hotel);
    }

    public List<HotelShortResponse> search(
            String name,
            String brand,
            String city,
            String country,
            String amenities
    ) {

        Specification<Hotel> specification = Specification
                .where(HotelSpecifications.nameContains(name))
                .and(HotelSpecifications.brandContains(brand))
                .and(HotelSpecifications.cityContains(city))
                .and(HotelSpecifications.countryContains(country))
                .and(HotelSpecifications.hasAmenity(amenities));

        return hotelRepository.findAll(specification)
                .stream()
                .map(hotelMapper::toShortResponse)
                .toList();
    }

    @Transactional
    public HotelShortResponse createHotel(
            CreateHotelRequest request
    ) {

        Hotel hotel = hotelMapper.toEntity(request);

        Hotel savedHotel = hotelRepository.save(hotel);

        return hotelMapper.toShortResponse(savedHotel);
    }

    @Transactional
    public HotelDetailsResponse addAmenities(
            Long hotelId,
            List<String> amenities
    ) {

        Hotel hotel = findHotelById(hotelId);

        LinkedHashSet<String> uniqueAmenities =
                new LinkedHashSet<>();

        if (hotel.getAmenities() != null) {
            uniqueAmenities.addAll(
                    hotel.getAmenities()
            );
        }

        if (amenities != null) {
            amenities.stream()
                    .filter(amenity ->
                            amenity != null
                                    && !amenity.isBlank()
                    )
                    .map(String::trim)
                    .forEach(uniqueAmenities::add);
        }

        hotel.setAmenities(
                new ArrayList<>(uniqueAmenities)
        );

        return hotelMapper.toDetailsResponse(hotel);
    }

    private Hotel findHotelById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(
                        () -> new HotelNotFoundException(id)
                );
    }
}