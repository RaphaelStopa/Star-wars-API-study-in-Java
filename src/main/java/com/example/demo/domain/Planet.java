package com.example.demo.domain;

import com.example.demo.domain.enumeration.Climate;
import com.example.demo.domain.enumeration.PlanetType;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;


@Entity
@Table(name = "planet")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
@Setter
@ToString
public class Planet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @NotNull
    @Column( name = "name", unique = true)
    private String name;

    @Column(name = "number_suns")
    private Integer numberSuns;

    @Column(name = "number_moons")
    private Integer numberMoons;

    @Column(name = "approximate_region")
    private String approximateRegion;

    @Column(name = "is_breathable")
    private Boolean isBreathable = Boolean.FALSE;

    @Column(name = "still_exist")
    private Boolean stillExist = Boolean.TRUE;

    @Column(name = "planet_type")
    @Enumerated(EnumType.STRING)
    private PlanetType planetType;

    @Column(name = "climate")
    @Enumerated(EnumType.STRING)
    private Climate climate;

}
