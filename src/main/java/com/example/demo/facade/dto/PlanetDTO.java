package com.example.demo.facade.dto;

import com.example.demo.domain.enumeration.Climate;
import com.example.demo.domain.enumeration.PlanetType;
import lombok.*;


import java.io.Serializable;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
@Setter
public class PlanetDTO implements Serializable {

    private Long id;

    private String name;

    private Integer numberSuns;

    private Integer numberMoons;

    private String approximateRegion;

    private Boolean isBreathable;

    private Boolean stillExist;

    private PlanetType planetType;

    private Climate climate;
}
