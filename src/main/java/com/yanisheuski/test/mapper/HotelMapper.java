package com.yanisheuski.test.mapper;

import com.yanisheuski.test.dto.request.AddressRequest;
import com.yanisheuski.test.dto.request.ArrivalTimeRequest;
import com.yanisheuski.test.dto.request.ContactsRequest;
import com.yanisheuski.test.dto.request.CreateHotelRequest;
import com.yanisheuski.test.dto.response.AddressResponse;
import com.yanisheuski.test.dto.response.ArrivalTimeResponse;
import com.yanisheuski.test.dto.response.ContactsResponse;
import com.yanisheuski.test.dto.response.HotelDetailsResponse;
import com.yanisheuski.test.dto.response.HotelShortResponse;
import com.yanisheuski.test.entity.Address;
import com.yanisheuski.test.entity.ArrivalTime;
import com.yanisheuski.test.entity.Contacts;
import com.yanisheuski.test.entity.Hotel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HotelMapper {

    public Hotel toEntity(CreateHotelRequest request) {
        Hotel hotel = new Hotel();

        hotel.setName(request.name());
        hotel.setDescription(request.description());
        hotel.setBrand(request.brand());

        hotel.setAddress(toEntity(request.address()));
        hotel.setContacts(toEntity(request.contacts()));
        hotel.setArrivalTime(toEntity(request.arrivalTime()));

        return hotel;
    }

    public HotelShortResponse toShortResponse(Hotel hotel) {
        return new HotelShortResponse(
                hotel.getId(),
                hotel.getName(),
                hotel.getDescription(),
                formatAddress(hotel.getAddress()),
                hotel.getContacts() != null
                        ? hotel.getContacts().getPhone()
                        : null
        );
    }

    public HotelDetailsResponse toDetailsResponse(Hotel hotel) {
        return new HotelDetailsResponse(
                hotel.getId(),
                hotel.getName(),
                hotel.getDescription(),
                hotel.getBrand(),
                toResponse(hotel.getAddress()),
                toResponse(hotel.getContacts()),
                toResponse(hotel.getArrivalTime()),
                hotel.getAmenities() == null
                        ? List.of()
                        : List.copyOf(hotel.getAmenities())
        );
    }

    private Address toEntity(AddressRequest request) {
        if (request == null) {
            return null;
        }

        return new Address(
                request.houseNumber(),
                request.street(),
                request.city(),
                request.country(),
                request.postCode()
        );
    }

    private Contacts toEntity(ContactsRequest request) {
        if (request == null) {
            return null;
        }

        return new Contacts(
                request.phone(),
                request.email()
        );
    }

    private ArrivalTime toEntity(ArrivalTimeRequest request) {
        if (request == null) {
            return null;
        }

        return new ArrivalTime(
                request.checkIn(),
                request.checkOut()
        );
    }

    private AddressResponse toResponse(Address address) {
        if (address == null) {
            return null;
        }

        return new AddressResponse(
                address.getHouseNumber(),
                address.getStreet(),
                address.getCity(),
                address.getCountry(),
                address.getPostCode()
        );
    }

    private ContactsResponse toResponse(Contacts contacts) {
        if (contacts == null) {
            return null;
        }

        return new ContactsResponse(
                contacts.getPhone(),
                contacts.getEmail()
        );
    }

    private ArrivalTimeResponse toResponse(ArrivalTime arrivalTime) {
        if (arrivalTime == null) {
            return null;
        }

        return new ArrivalTimeResponse(
                arrivalTime.getCheckIn(),
                arrivalTime.getCheckOut()
        );
    }

    private String formatAddress(Address address) {
        if (address == null) {
            return null;
        }

        return String.format(
                "%s %s, %s, %s, %s",
                address.getHouseNumber(),
                address.getStreet(),
                address.getCity(),
                address.getPostCode(),
                address.getCountry()
        );
    }
}