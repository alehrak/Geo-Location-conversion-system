package com.gmail.rak.aleh.converter;

import com.gmail.rak.aleh.model.Address;
import com.gmail.rak.aleh.model.AddressDTO;
import com.gmail.rak.aleh.model.Coordinates;
import com.gmail.rak.aleh.model.CoordinatesDTO;

public interface CoordinatesAndAddressConverter {
    Coordinates convert(CoordinatesDTO coordinatesDTOs);

    Address convert(AddressDTO addressDTOs);
}
