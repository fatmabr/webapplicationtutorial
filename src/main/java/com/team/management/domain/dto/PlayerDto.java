package com.team.management.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Setter
@Builder
@Jacksonized
public class PlayerDto {
    private long id;
    private String name;
    private Integer position;
}
