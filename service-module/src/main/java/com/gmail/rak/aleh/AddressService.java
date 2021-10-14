package com.gmail.rak.aleh;

import com.gmail.rak.aleh.model.AddressDTO;
import com.gmail.rak.aleh.model.CoordinatesDTO;

import java.io.IOException;

public interface AddressService {
    AddressDTO getAddressByCoordinates(CoordinatesDTO coordinates) throws IOException;

    CoordinatesDTO getCoordinatesByAddress(AddressDTO address) throws IOException;
}
