package com.lukozog.scoreboard.domain

import spock.lang.Specification

class ScoreBoardFacadeTest extends Specification {

    ScoreBoardFacade scoreBoardFacade

    def setup() {
        scoreBoardFacade = new ScoreBoardFacade()
    }

    def "sample test"() {
        when:
        def result = scoreBoardFacade.getGamesSummary()

        then:
        result.isEmpty()
    }
}
