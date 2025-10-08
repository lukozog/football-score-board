package com.lukozog.game.domain

import com.lukozog.game.domain.dto.FinishGameCommand
import com.lukozog.game.domain.exception.DomainValidationException
import com.lukozog.game.domain.exception.ResourceNotFoundException
import com.lukozog.game.domain.exception.ValidationException
import spock.lang.Specification

import static InMemoryGameRepository.GERMANY_VS_FRANCE

class FinishGameTest extends Specification {

    GameFacade scoreBoardFacade

    def setup() {
        scoreBoardFacade = new GameFacade(new InMemoryGameRepository(), new InMemoryTeamRepository())
    }

    def "should finish the game successfully"() {
        given:
        var finishGameCommand = new FinishGameCommand(GERMANY_VS_FRANCE.getId())

        when:
        scoreBoardFacade.finishGame(finishGameCommand)

        then:
        noExceptionThrown()
    }

    def "should not finish the game and throw exception when id equals null"() {
        when:
        new FinishGameCommand(null)

        then:
        Exception e = thrown(ValidationException.class)
    }

    def "should not finish the game and throw exception when game is already finished"() {
        given:
        var finishGameCommand = new FinishGameCommand(GERMANY_VS_FRANCE.getId())

        when:
        scoreBoardFacade.finishGame(finishGameCommand)

        then:
        Exception e = thrown(DomainValidationException.class)
    }

    def "should not finish the game and throw exception when game doesn't exist"() {
        given:
        var finishGameCommand = new FinishGameCommand(1231231232L)

        when:
        scoreBoardFacade.finishGame(finishGameCommand)

        then:
        Exception e = thrown(ResourceNotFoundException.class)
    }


}
