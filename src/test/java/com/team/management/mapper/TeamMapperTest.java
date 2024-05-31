package com.team.management.mapper;

import com.team.management.domain.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.team.management.SpringBootRestApplication;
import com.team.management.domain.Team;
import com.team.management.domain.dto.PlayerDto;
import com.team.management.domain.dto.TeamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = SpringBootRestApplication.class)
public class TeamMapperTest {

    @Autowired
    private TeamMapper mapper;

    @Test
    public void givenTeamEntity_expectTeamDto(){
        Team team = new Team();
        team.setId(1);
        team.setName("name1");
        team.setAcronym("ac1");
        Player player1 = new Player();
        player1.setId(1);
        player1.setTeam(team);
        player1.setPosition(1);
        player1.setName("player1");
        List<Player> players = List.of(player1);
        team.setPlayerList(players);
        TeamDto teamDto = mapper.mapToDto(team);
        assertEquals(teamDto.getAcronym(), team.getAcronym());
        assertEquals(teamDto.getId(), team.getId());
        for (Player player : players) {
            assertEquals(player.getId(), player1.getId());
        }
    }

    @Test
    public void givenTeamDto_expectTeamEntity(){
        PlayerDto playerDto = PlayerDto.builder().id(1).position(1).name("player").build();
        List<PlayerDto> playerDtos = List.of(playerDto);
        TeamDto teamDto = TeamDto.builder().id(1).name("name1").acronym("ac1").playerDtoList(playerDtos).build();
        Team team = mapper.mapToEntity(teamDto);
        assertEquals(team.getAcronym(), teamDto.getAcronym());
        assertEquals(team.getId(), teamDto.getId());
        assertEquals(team.getName(), teamDto.getName());
        for (Player playerEntity : team.getPlayerList()) {
            assertEquals(playerEntity.getId(), playerDto.getId());
        }
    }
}
