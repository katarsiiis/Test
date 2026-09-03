package com.yanisheuski.test.service;

import com.yanisheuski.test.exception.InvalidHistogramParameterException;
import com.yanisheuski.test.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HistogramServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    private HistogramService histogramService;

    @BeforeEach
    void setUp() {
        histogramService =
                new HistogramService(hotelRepository);
    }

    @Test
    void histogramByBrand_shouldReturnCorrectResult() {

        when(hotelRepository.histogramByBrand())
                .thenReturn(
                        List.of(
                                new Object[]{"Hilton", 3L},
                                new Object[]{"Marriott", 2L}
                        )
                );

        Map<String, Long> result =
                histogramService.getHistogram("brand");

        assertThat(result)
                .containsEntry("Hilton", 3L)
                .containsEntry("Marriott", 2L);
    }

    @Test
    void histogramByCity_shouldReturnCorrectResult() {

        when(hotelRepository.histogramByCity())
                .thenReturn(
                        List.of(
                                new Object[]{"Minsk", 5L},
                                new Object[]{"Mogilev", 2L}
                        )
                );

        Map<String, Long> result =
                histogramService.getHistogram("city");

        assertThat(result)
                .containsEntry("Minsk", 5L)
                .containsEntry("Mogilev", 2L);
    }

    @Test
    void histogramByCountry_shouldReturnCorrectResult() {

        when(hotelRepository.histogramByCountry())
                .thenReturn(
                        List.of(
                                new Object[]{"Belarus", 10L},
                                new Object[]{"Poland", 4L}
                        )
                );

        Map<String, Long> result =
                histogramService.getHistogram("country");

        assertThat(result)
                .containsEntry("Belarus", 10L)
                .containsEntry("Poland", 4L);
    }

    @Test
    void histogramByAmenities_shouldReturnCorrectResult() {

        when(hotelRepository.histogramByAmenities())
                .thenReturn(
                        List.of(
                                new Object[]{"Free WiFi", 20L},
                                new Object[]{"Free parking", 15L}
                        )
                );

        Map<String, Long> result =
                histogramService.getHistogram(
                        "amenities"
                );

        assertThat(result)
                .containsEntry("Free WiFi", 20L)
                .containsEntry("Free parking", 15L);
    }

    @Test
    void histogram_shouldBeCaseInsensitive() {

        when(hotelRepository.histogramByCity())
                .thenReturn(
                        List.of(
                                new Object[]{"Minsk", 5L}
                        )
                );

        Map<String, Long> result =
                histogramService.getHistogram("CITY");

        assertThat(result)
                .containsEntry("Minsk", 5L);
    }

    @Test
    void histogram_shouldThrowExceptionForInvalidParameter() {

        assertThatThrownBy(
                () -> histogramService.getHistogram(
                        "unknown"
                )
        )
                .isInstanceOf(
                        InvalidHistogramParameterException.class
                );
    }

    @Test
    void histogram_shouldThrowExceptionForBlankParameter() {

        assertThatThrownBy(
                () -> histogramService.getHistogram(" ")
        )
                .isInstanceOf(
                        InvalidHistogramParameterException.class
                );
    }
}