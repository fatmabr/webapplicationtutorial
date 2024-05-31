package com.team.management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team.management.SpringBootRestApplication;
import com.team.management.domain.Player;
import com.team.management.domain.dto.TeamDto;
import com.team.management.repository.PlayerRepository;
import com.team.management.repository.TeamRepository;
import com.team.management.service.TeamService;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.team.management.domain.Team;
import com.team.management.mapper.TeamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = SpringBootRestApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
public class TeamControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamMapper teamMapper;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamService teamService;

    private void createTeams() {
        Team entity = new Team();
        entity.setId(8);
        teamRepository.delete(entity);
        createTeam(1, "Team1", "aT1", 1000);
        createTeam(2, "Team2", "bT1", 1000);
        createTeam(3, "Team4", "cT1", 1000);
        createTeam(4, "Team5", "yT1", 1000);
        createTeam(5, "Team3", "zT1", 1000);
        createPlayer("Player1", 1L, 0);
    }

    private void createPlayer(String name, Long teamId, int position) {
        Team team = new Team();
        team.setId(teamId);
        Player player = new Player();
        player.setName(name);
        player.setPosition(position);
        player.setTeam(team);
        playerRepository.save(player);
    }

    private Long createTeam(int id, String name, String acronym, int budget) {
        TeamDto team = TeamDto.builder().id(id).name(name).acronym(acronym).budget(BigDecimal.valueOf(budget)).build();
        return teamService.create(team);
    }

    @Test
    public void givenTeams_whenGetPage0AndSize2_thenStatus200()
            throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/teams")
                        .param("page", "0")
                        .param("size", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name", CoreMatchers.is("Team1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].playerDtoList[0].name", CoreMatchers.is("Player1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].name", CoreMatchers.is("Team2")));
    }

    @Test
    public void givenTeams_whenGetPage1AndSize2_thenStatus200()
            throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/teams")
                        .param("page", "1")
                        .param("size", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name", CoreMatchers.is("Team3")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].playerDtoList", CoreMatchers.anyOf(Matchers.empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[1].name", CoreMatchers.is("Team4")));
    }

    @Test
    public void givenTeams_whenGetPage2AndSize2_thenStatus200()
            throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/teams")
                        .param("page", "2")
                        .param("size", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name", CoreMatchers.is("Team5")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].playerDtoList", CoreMatchers.anyOf(Matchers.empty())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(Matchers.hasSize(1))));
    }

    @Test
    public void givenTeams_whenGetPage3AndSize2_thenStatus200()
            throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/teams")
                        .param("page", "3")
                        .param("size", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", CoreMatchers.is(Matchers.empty())));
    }

    @Test
    public void create_Team_thenStatus200()
            throws Exception {

        TeamDto teamDto = TeamDto.builder().id(8).name("name").build();
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(teamDto);
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/api/teams")
                        .content(s)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
        resultActions.andDo(x -> {
            Team team = new Team();
            team.setId(Long.valueOf(x.getResponse().getContentAsString()));
            teamRepository.delete(team);
        });
    }
}