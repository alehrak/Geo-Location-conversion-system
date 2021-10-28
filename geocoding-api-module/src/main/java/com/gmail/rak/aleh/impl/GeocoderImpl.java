package com.gmail.rak.aleh.impl;

import com.gmail.rak.aleh.Geocoder;
import com.gmail.rak.aleh.exception.GeocoderException;
import com.gmail.rak.aleh.model.Address;
import com.gmail.rak.aleh.model.Coordinates;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import static com.gmail.rak.aleh.constants.HEREApiConstants.*;

@Log4j2
@Component
public class GeocoderImpl implements Geocoder {

    public String getCoordinates(Address address) {
        HttpClient httpClient = HttpClient.newHttpClient();
        String city = address.getCity();
        String street = address.getStreet();
        Integer house = address.getHouse();
        String query = city + ", " + street + ", " + house;
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String requestUri = GEOCODING_RESOURCE + "?apiKey=" + API_KEY + "&q=" + encodedQuery;
        return sendRequestAndReceivingResponse(httpClient, requestUri);
    }

    public String getAddress(Coordinates coordinates) {
        HttpClient httpClient = HttpClient.newHttpClient();
        Double latitude = coordinates.getLatitude();
        Double longitude = coordinates.getLongitude();
        String query = latitude + "," + longitude;
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String requestUri = REVERSE_GEOCODING_RESOURCE + "?apiKey=" + API_KEY + "&at=" + encodedQuery + "&lang=en-US";
        return sendRequestAndReceivingResponse(httpClient, requestUri);
    }

    private String sendRequestAndReceivingResponse(HttpClient httpClient, String requestUri) {
        HttpRequest geocodingRequest = HttpRequest.newBuilder().GET().uri(URI.create(requestUri))
                .timeout(Duration.ofMillis(MAX_TIME_WAITING_REQUEST_IN_MILLSEC)).build();
        HttpResponse<String> geocodingResponse;
        try {
            geocodingResponse = httpClient.send(geocodingRequest,
                    HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException e) {
            log.error(e.getMessage(), e);
            throw new GeocoderException("Interrupting a thread during a request");
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new GeocoderException("The request could not be formed correctly");
        }
        return geocodingResponse.body();
    }
}
