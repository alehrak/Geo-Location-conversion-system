package com.gmail.rak.aleh.model;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AddressDTO implements Serializable {
    private String city;
    private String street;
    private Integer house;
}
