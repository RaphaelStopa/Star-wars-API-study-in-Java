package com.example.demo.facade.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(toBuilder = true)
@Getter
@Setter
public class KnownMasterDTO implements Serializable {

    private Long id;

    @NotBlank
    private ForceUserDTO master;

    @NotBlank
    private ForceUserDTO forceUser;
}
