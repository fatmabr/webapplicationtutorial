package com.team.management.service;

import com.team.management.domain.Team;
import com.team.management.domain.dto.TeamDto;
import com.team.management.mapper.TeamMapper;
import com.team.management.repository.TeamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class TeamService implements ITeamService {
    private final TeamMapper teamMapper;
    private final TeamRepository teamRepository;

    public TeamService(TeamMapper mapper, TeamRepository teamRepository) {
        this.teamMapper = mapper;
        this.teamRepository = teamRepository;
    }

    @Override
    public Page<TeamDto> findAll(Pageable pageable) {
        Page<Team> all = teamRepository.findAll(pageable);
        return all.map(x -> teamMapper.mapToDto(x));
    }

    @Override
    public Long create(TeamDto team) {
        Team entity = teamMapper.mapToEntity(team);
        return teamRepository.save(entity).getId();
    }
}
