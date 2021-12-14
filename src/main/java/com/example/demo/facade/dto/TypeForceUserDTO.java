package com.example.demo.facade.dto;

import lombok.*;

import java.io.Serializable;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
@Setter
public class TypeForceUserDTO implements Serializable {

    private Long id;

    private Boolean sith;

    private Boolean jedi;

    private Boolean other;

    private String briefHistory;

    private ForceUserDTO forceUser;

}
