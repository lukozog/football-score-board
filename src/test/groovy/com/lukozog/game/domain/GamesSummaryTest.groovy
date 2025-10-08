package com.lukozog.game.domain


import spock.lang.Specification

class GamesSummaryTest extends Specification {

    GameFacade scoreBoardFacade

    def setup() {
        scoreBoardFacade = new GameFacade(new InMemoryGameRepository(), new InMemoryTeamRepository())
    }

    def "should return not finished games summaries properly ordered"() {
        when:
        def result = scoreBoardFacade.getGamesSummary()

        then:
        !result.isEmpty()
    }

}
