package com.gmail.rak.aleh.controller;

import com.gmail.rak.aleh.AddressService;
import com.gmail.rak.aleh.model.AddressDTO;
import com.gmail.rak.aleh.model.CoordinatesDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.gmail.rak.aleh.constants.URLConstants.*;

@Log4j2
@RestController
@RequestMapping(API_URL)
public class EncoderAPIController {
    private final AddressService addressService;

    public EncoderAPIController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping(ADDRESSES_URL)
    public AddressDTO getAddressByCoordinates(@RequestBody CoordinatesDTO coordinates) throws IOException {
        return addressService.getAddressByCoordinates(coordinates);
    }

    @GetMapping(COORDINATES_URL)
    public CoordinatesDTO getCoordinatesByAddress(@RequestBody AddressDTO address) throws IOException {
        return addressService.getCoordinatesByAddress(address);
    }
}
