package com.yanisheuski.test;

import java.util.List;
import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Hotel {

    private @Id
    @GeneratedValue Long id;
    private String name;
    private Address address;
    private Contacts contacts;
    private String brand;
    private String description;
    private ArrivalTime arrivalTime;
    private Amenities amenities;

    public record Address {
        String houseNumber;
        String street;
        String city;
        String postCode;
    }
    public class Amenities{
        private List<String> amenities;
    }
    public class Amenities{
        private List<String> amenities;
    }
    public record ArrivalTime {
        private String checkIn,
        private String checkOut,
    }

}

