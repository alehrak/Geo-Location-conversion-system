package com.gmail.rak.aleh;

import com.gmail.rak.aleh.model.Address;
import com.gmail.rak.aleh.model.Coordinates;

public interface Geocoder {
    String getCoordinates(Address address);

    String getAddress(Coordinates coordinates);
}
