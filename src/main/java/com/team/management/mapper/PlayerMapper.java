package com.team.management.mapper;

import com.team.management.domain.Player;
import com.team.management.domain.dto.PlayerDto;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface PlayerMapper {
    PlayerDto mapToDto(Player player);

    Player mapToEntity(PlayerDto playerDto);

    default List<PlayerDto> mapToDtos(List<Player> players){
        return players.stream().map(player -> mapToDto(player
        )).collect(Collectors.toList());
    }
}
