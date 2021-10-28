package com.gmail.rak.aleh.converter.impl;

import com.gmail.rak.aleh.converter.CoordinatesAndAddressConverter;
import com.gmail.rak.aleh.model.Address;
import com.gmail.rak.aleh.model.AddressDTO;
import com.gmail.rak.aleh.model.Coordinates;
import com.gmail.rak.aleh.model.CoordinatesDTO;
import org.springframework.stereotype.Component;

@Component
public class CoordinatesAndAddressConverterImpl implements CoordinatesAndAddressConverter {
    @Override
    public Coordinates convert(CoordinatesDTO coordinatesDTOs) {
        Coordinates coordinates = new Coordinates();
        Double latitude = coordinatesDTOs.getLatitude();
        coordinates.setLatitude(latitude);
        Double longitude = coordinatesDTOs.getLongitude();
        coordinates.setLongitude(longitude);
        return coordinates;
    }

    @Override
    public Address convert(AddressDTO addressDTOs) {
        Address address = new Address();
        String city = addressDTOs.getCity();
        address.setCity(city);
        String street = addressDTOs.getStreet();
        address.setStreet(street);
        Integer house = addressDTOs.getHouse();
        address.setHouse(house);
        return address;
    }
}
