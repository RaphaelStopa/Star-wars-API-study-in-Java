package com.example.demo.facade.dto;


import com.example.demo.domain.enumeration.Gender;
import lombok.*;
import java.io.Serializable;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
@Setter
public class ForceUserDTO implements Serializable {

    private Long id;

    private String name;

    private String stardateBirth;

    private String stardateDeath;

    private Boolean isLegends;

    private Gender gender;

    private SpeciesDTO species;

    private PlanetDTO planet;
}
