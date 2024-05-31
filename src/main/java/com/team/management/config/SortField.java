package com.team.management.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SortField {
    ID("id"),
    NAME("name"),
    ACRONYM("acronym"),
    BUDGET("budget");

    private final String databaseFieldName;
}