package com.gmail.rak.aleh.model;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class CoordinatesDTO implements Serializable {
    private Double latitude;
    private Double longitude;
}
