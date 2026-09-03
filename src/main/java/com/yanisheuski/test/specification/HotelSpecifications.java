package com.yanisheuski.test.specification;

import com.yanisheuski.test.entity.Hotel;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.Locale;

public final class HotelSpecifications {

    private HotelSpecifications() {
    }

    public static Specification<Hotel> nameContains(String name) {
        return (root, query, cb) -> {

            if (isBlank(name)) {
                return cb.conjunction();
            }

            return cb.like(
                    cb.lower(root.get("name")),
                    "%" + normalize(name) + "%"
            );
        };
    }

    public static Specification<Hotel> brandContains(String brand) {
        return (root, query, cb) -> {

            if (isBlank(brand)) {
                return cb.conjunction();
            }

            return cb.like(
                    cb.lower(root.get("brand")),
                    "%" + normalize(brand) + "%"
            );
        };
    }

    public static Specification<Hotel> cityContains(String city) {
        return (root, query, cb) -> {

            if (isBlank(city)) {
                return cb.conjunction();
            }

            return cb.like(
                    cb.lower(root.get("address").get("city")),
                    "%" + normalize(city) + "%"
            );
        };
    }

    public static Specification<Hotel> countryContains(String country) {
        return (root, query, cb) -> {

            if (isBlank(country)) {
                return cb.conjunction();
            }

            return cb.like(
                    cb.lower(root.get("address").get("country")),
                    "%" + normalize(country) + "%"
            );
        };
    }

    public static Specification<Hotel> hasAmenity(String amenity) {
        return (root, query, cb) -> {

            if (isBlank(amenity)) {
                return cb.conjunction();
            }

            query.distinct(true);

            Join<Hotel, String> amenities =
                    root.join("amenities", JoinType.INNER);

            return cb.equal(
                    cb.lower(amenities),
                    normalize(amenity)
            );
        };
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private static String normalize(String value) {
        return value.trim().toLowerCase(Locale.ROOT);
    }
}