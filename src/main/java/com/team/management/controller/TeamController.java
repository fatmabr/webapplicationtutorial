package com.team.management.controller;

import com.team.management.config.SortField;
import com.team.management.domain.dto.TeamDto;
import com.team.management.service.ITeamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teams")
class TeamController {

    private static final Logger logger = LoggerFactory.getLogger(TeamController.class);

    @Autowired
    private ITeamService service;

    @GetMapping(params = {"page", "size"})
    public Page<TeamDto> findAll(@RequestParam("page") int page, @RequestParam("size") int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(SortField.NAME.getDatabaseFieldName(),
                SortField.ACRONYM.getDatabaseFieldName(), SortField.BUDGET.getDatabaseFieldName()));
        logger.debug(String.format("findAll GET request is done with parameters: " + pageRequest));
        return service.findAll(pageRequest);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Long> create(@RequestBody TeamDto resource) {
        logger.debug(String.format("create POST request is done with parameters: " + resource));
        Long team = service.create(resource);
        return ResponseEntity.ok(team);
    }
}