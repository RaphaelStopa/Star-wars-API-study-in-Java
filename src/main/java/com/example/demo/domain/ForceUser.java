package com.example.demo.domain;

import com.example.demo.domain.enumeration.Gender;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity
@Table(name = "force_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
@Setter
public class ForceUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", unique = true, nullable = false)
    private String name;

    //Star Wars has its own system for dates that include letters. For simplicity, it was done this way.
    @Column(name = "stardate_birth")
    private String stardateBirth;

    @Column(name = "stardate_death")
    private String stardateDeath;

    @Column(name = "is_legends")
    private Boolean isLegends= Boolean.FALSE;

    @NotNull
    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ManyToOne
    @JoinColumn
    private Species species;

    @ManyToOne
    @JoinColumn
    private Planet planet;
}
