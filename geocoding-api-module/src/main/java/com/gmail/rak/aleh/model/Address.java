package com.gmail.rak.aleh.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Address {
    private String city;
    private String street;
    private Integer house;
}
