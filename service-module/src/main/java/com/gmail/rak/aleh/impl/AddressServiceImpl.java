package com.gmail.rak.aleh.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.rak.aleh.AddressService;

import com.gmail.rak.aleh.Geocoder;
import com.gmail.rak.aleh.converter.CoordinatesAndAddressConverter;
import com.gmail.rak.aleh.exception.ServiceException;
import com.gmail.rak.aleh.model.Address;
import com.gmail.rak.aleh.model.AddressDTO;
import com.gmail.rak.aleh.model.Coordinates;
import com.gmail.rak.aleh.model.CoordinatesDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Objects;

import static com.gmail.rak.aleh.constants.AddressServiceConstant.*;

@Service
@Log4j2
public class AddressServiceImpl implements AddressService {
    private final CoordinatesAndAddressConverter converter;
    private final Geocoder geocoder;
    private final ObjectMapper mapper;

    public AddressServiceImpl(CoordinatesAndAddressConverter converter, Geocoder geocoder, ObjectMapper mapper) {
        this.converter = converter;
        this.geocoder = geocoder;
        this.mapper = mapper;
    }

    @Cacheable(value = NAME_CACHE_COORDINATES)
    @Override
    public AddressDTO getAddressByCoordinates(CoordinatesDTO coordinatesDTOs) throws IOException {
        log.info("Request to a third-party api, there is no request with such coordinates in the cache");
        Coordinates coordinates = converter.convert(coordinatesDTOs);
        String response = geocoder.getAddress(coordinates);
        JsonNode responseJsonNode = mapper.readTree(response);
        JsonNode items = responseJsonNode.get(NAME_SEARCH_OBJECT_IN_RESPONSE);
        AddressDTO address = new AddressDTO();
        if (Objects.nonNull(items)) {
            for (JsonNode item : items) {
                JsonNode addressJson = item.get(KEY_ADDRESS);
                String city = addressJson.get(KEY_CITY).asText();
                address.setCity(city);
                String street = addressJson.get(KEY_STREET).asText();
                address.setStreet(street);
                String houseNumberString = addressJson.get(KEY_HOUSE_NUMBER).asText();
                Integer houseNumber = Integer.valueOf(houseNumberString);
                address.setHouse(houseNumber);
            }
            return address;
        } else {
            throw new ServiceException("Address with coordinates :" + coordinatesDTOs.toString() + " not found");
        }
    }

    @Cacheable(value = NAME_CACHE_ADDRESS)
    @Override
    public CoordinatesDTO getCoordinatesByAddress(AddressDTO addressDTOs) throws IOException {
        log.info("Request to a third-party api, there is no request in the cache at this address");
        Address address = converter.convert(addressDTOs);
        String response = geocoder.getCoordinates(address);
        JsonNode responseJsonNode = mapper.readTree(response);
        JsonNode items = responseJsonNode.get(NAME_SEARCH_OBJECT_IN_RESPONSE);
        CoordinatesDTO coordinates = new CoordinatesDTO();
        if (Objects.nonNull(items)) {
            for (JsonNode item : items) {
                JsonNode location = item.get(KEY_LOCATION);
                String latitudeString = location.get(KEY_LATITUDE).asText();
                Double latitude = Double.valueOf(latitudeString);
                coordinates.setLatitude(latitude);
                String longitudeString = location.get(KEY_LONGITUDE).asText();
                Double longitude = Double.valueOf(longitudeString);
                coordinates.setLongitude(longitude);
            }
            return coordinates;
        } else {
            throw new ServiceException("Address :" + addressDTOs.toString() + " was entered incorrectly");
        }
    }
}
