package com.yanisheuski.test.repository;

import com.yanisheuski.test.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HotelRepository extends JpaRepository
        <Hotel, Long>, JpaSpecificationExecutor<Hotel> {

    @Query("""
            SELECT h.brand, COUNT(h)
            FROM Hotel h
            GROUP BY h.brand
            ORDER BY h.brand
            """)
    List<Object[]> histogramByBrand();

    @Query("""
            SELECT h.address.city, COUNT(h)
            FROM Hotel h
            GROUP BY h.address.city
            ORDER BY h.address.city
            """)
    List<Object[]> histogramByCity();

    @Query("""
            SELECT h.address.country, COUNT(h)
            FROM Hotel h
            GROUP BY h.address.country
            ORDER BY h.address.country
            """)
    List<Object[]> histogramByCountry();

    @Query("""
            SELECT amenity, COUNT(DISTINCT h.id)
            FROM Hotel h
            JOIN h.amenities amenity
            GROUP BY amenity
            ORDER BY amenity
            """)
    List<Object[]> histogramByAmenities();
}