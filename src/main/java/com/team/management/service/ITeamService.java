package com.team.management.service;

import com.team.management.domain.dto.TeamDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ITeamService {

    /**
     * Given size and page encapsulated in Pageable object, it searches for the team entity.
     * @param pageable
     * @return the found list of entities.
     */
    Page<TeamDto> findAll(Pageable pageable);

    /**
     * Create Team entity and save it to the Datasource
     * @param team
     * @return returns the created entity id.
     */
    Long create(TeamDto team);
}
