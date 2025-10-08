package com.lukozog.game.domain

import java.util.concurrent.ConcurrentHashMap

class InMemoryTeamRepository implements TeamRepository {

    static final Team MEXICO = new Team(TestIdGenerator.generateId(), "Mexico");
    static final Team CANADA = new Team(TestIdGenerator.generateId(), "Canada");
    static final Team SPAIN = new Team(TestIdGenerator.generateId(), "Spain");
    static final Team BRAZIL = new Team(TestIdGenerator.generateId(), "Brazil");
    static final Team GERMANY = new Team(TestIdGenerator.generateId(), "Germany");
    static final Team FRANCE = new Team(TestIdGenerator.generateId(), "France");
    static final Team URUGUAY = new Team(TestIdGenerator.generateId(), "Uruguay");
    static final Team ITALY = new Team(TestIdGenerator.generateId(), "Italy");
    static final Team ARGENTINA = new Team(TestIdGenerator.generateId(), "Argentina");
    static final Team AUSTRALIA = new Team(TestIdGenerator.generateId(), "Australia");

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


}

