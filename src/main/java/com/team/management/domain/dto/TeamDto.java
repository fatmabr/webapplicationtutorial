package com.team.management.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@Jacksonized
public class TeamDto {
    private long id;
    private String name;
    private String acronym;
    private BigDecimal budget;
    private List<PlayerDto> playerDtoList;
}
