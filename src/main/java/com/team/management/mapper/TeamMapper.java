package com.team.management.mapper;

import com.team.management.domain.Player;
import com.team.management.domain.dto.PlayerDto;
import com.team.management.domain.dto.TeamDto;
import com.team.management.domain.Team;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {PlayerMapper.class})
public interface TeamMapper {

    @Mapping(target = "playerDtoList", expression = "java(mapPlayerList(team.getPlayerList()))")
    TeamDto mapToDto(Team team);

    @Mapping(target = "playerList", expression = "java(mapPlayerDtoList(team.getPlayerDtoList()))")
    Team mapToEntity(TeamDto team);

    default List<TeamDto> mapToDtos(List<Team> teams) {
        return teams.stream().map(team -> mapToDto(team)).collect(Collectors.toList());
    }

    default List<PlayerDto> mapPlayerList(List<Player> playerList) {
        return playerList != null ? playerList.stream().map(player -> mapToPlayerDto(player)).collect(Collectors.toList())
                : Collections.EMPTY_LIST;
    }

    default List<Player> mapPlayerDtoList(List<PlayerDto> playerList) {
        return playerList != null ? playerList.stream().map(player -> mapToPlayer(player)).collect(Collectors.toList())
                : Collections.EMPTY_LIST;
    }

    PlayerDto mapToPlayerDto(Player player);

    Player mapToPlayer(PlayerDto playerDto);
}
