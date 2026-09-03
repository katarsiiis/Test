package com.yanisheuski.test.service;

import com.yanisheuski.test.exception.InvalidHistogramParameterException;
import com.yanisheuski.test.repository.HotelRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class HistogramService {

    private final HotelRepository hotelRepository;

    public HistogramService(
            HotelRepository hotelRepository
    ) {
        this.hotelRepository = hotelRepository;
    }

    public Map<String, Long> getHistogram(String parameter) {

        if (parameter == null || parameter.isBlank()) {
            throw new InvalidHistogramParameterException(parameter);
        }

        List<Object[]> rows = switch (
                parameter.toLowerCase(Locale.ROOT)
                ) {
            case "brand" ->
                    hotelRepository.histogramByBrand();

            case "city" ->
                    hotelRepository.histogramByCity();

            case "country" ->
                    hotelRepository.histogramByCountry();

            case "amenities" ->
                    hotelRepository.histogramByAmenities();

            default ->
                    throw new InvalidHistogramParameterException(
                            parameter
                    );
        };

        Map<String, Long> histogram =
                new LinkedHashMap<>();

        for (Object[] row : rows) {

            String key = String.valueOf(row[0]);

            Long count =
                    ((Number) row[1]).longValue();

            histogram.put(key, count);
        }

        return histogram;
    }
}