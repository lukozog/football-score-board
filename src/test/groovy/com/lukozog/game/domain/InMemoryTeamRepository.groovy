package com.lukozog.game.domain

import java.util.concurrent.ConcurrentHashMap
import java.util.stream.Collectors

class InMemoryTeamRepository implements TeamRepository {

    static final Team MEXICO = new Team("Mexico");
    static final Team CANADA = new Team("Canada");
    static final Team SPAIN = new Team("Spain");
    static final Team BRAZIL = new Team("Brazil");
    static final Team GERMANY = new Team("Germany");
    static final Team FRANCE = new Team("France");
    static final Team URUGUAY = new Team("Uruguay");
    static final Team ITALY = new Team("Italy");
    static final Team ARGENTINA = new Team("Argentina");
    static final Team AUSTRALIA = new Team("Australia");

    private final Map<Long, Team> teamIdToTeam;

    InMemoryTeamRepository() {
        teamIdToTeam = new ConcurrentHashMap<>();
        teamIdToTeam.put(MEXICO.getId(), MEXICO);
        teamIdToTeam.put(CANADA.getId(), CANADA);
        teamIdToTeam.put(SPAIN.getId(), SPAIN);
        teamIdToTeam.put(BRAZIL.getId(), BRAZIL);
        teamIdToTeam.put(GERMANY.getId(), GERMANY);
        teamIdToTeam.put(FRANCE.getId(), FRANCE);
        teamIdToTeam.put(URUGUAY.getId(), URUGUAY);
        teamIdToTeam.put(ITALY.getId(), ITALY);
        teamIdToTeam.put(ARGENTINA.getId(), ARGENTINA);
        teamIdToTeam.put(AUSTRALIA.getId(), AUSTRALIA);
    }

    @Override
    Map<Long, Team> findByIds(List<Long> teamIds) {
        return teamIdToTeam.entrySet().stream()
                .filter(teamIdToTeam -> teamIds.contains(teamIdToTeam.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}

