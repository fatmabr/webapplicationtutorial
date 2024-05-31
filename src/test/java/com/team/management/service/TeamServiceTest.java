package com.team.management.service;

import com.team.management.domain.dto.TeamDto;
import com.team.management.repository.TeamRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.team.management.domain.Team;
import com.team.management.mapper.TeamMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {
    @InjectMocks
    private TeamService teamService;
    @Mock
    private TeamRepository repository;
    @Mock
    private TeamMapper mapper;

    @Test
    void givenNull_findAll_ReturnEmptyList() {
        Mockito.when(repository.findAll(Pageable.unpaged())).thenReturn(Page.empty());
        Page<TeamDto> teamDtos = teamService.findAll(Pageable.unpaged());
        Assertions.assertEquals(teamDtos.getContent(), Collections.EMPTY_LIST);
    }

    @Test
    void findAll_ReturnTeamList() {
        Team team = new Team();
        team.setName("team");
        Mockito.when(repository.findAll(Mockito.any(Pageable.class))).thenReturn(new PageImpl<>(List.of(team)));
        Page<TeamDto> page = teamService.findAll(PageRequest.of(0, 1, Sort.by("name")));
        Assertions.assertEquals(1, page.getContent().size());
    }

    @Test
    void create_returnNegativeId_resourceIsNull(){

    }
}
