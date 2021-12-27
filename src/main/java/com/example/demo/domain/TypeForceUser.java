package com.example.demo.domain;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Entity
@Table(name = "type_force_user")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
@Setter
public class TypeForceUser implements Serializable {
    //I could have made this entity together with the user of the force. However, the universe of star is so vast
    // that this information often doesn't exist, so i separated.

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //Boolean fields were marked with his current status because Siths were already Jedi a few times.
    @Column(name = "sith")
    private Boolean sith = Boolean.FALSE;

    @Column(name = "jedi")
    private Boolean jedi = Boolean.FALSE;

    @Column(name = "other")
    private Boolean other= Boolean.FALSE;

    @Column(name = "brief_history")
    private String briefHistory;

    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private ForceUser forceUser;
}
