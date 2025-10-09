package com.lukozog.game.domain

import com.lukozog.game.domain.dto.FinishGameCommand
import com.lukozog.game.domain.dto.StartGameCommand
import com.lukozog.game.domain.exception.DomainValidationException
import com.lukozog.game.domain.exception.ResourceNotFoundException
import com.lukozog.game.domain.exception.ValidationException
import spock.lang.Specification

import static com.lukozog.game.domain.InMemoryTeamRepository.getFRANCE
import static com.lukozog.game.domain.InMemoryTeamRepository.getGERMANY

class FinishGameTest extends Specification {

    private static Game GERMANY_VS_FRANCE

    GameFacade gameFacade

    def setup() {
        gameFacade = new GameFacade(new InMemoryGameRepository(), new InMemoryTeamRepository())
        def gameId = gameFacade.startGame(new StartGameCommand(
                new StartGameCommand.TeamDto(GERMANY.getId()),
                new StartGameCommand.TeamDto(FRANCE.getId())
        ))
        GERMANY_VS_FRANCE = gameFacade.findById(gameId)
    }

    def "should finish the game successfully and throw exception with another try"() {
        given:
        var finishGameCommand = new FinishGameCommand(GERMANY_VS_FRANCE.getId())

        when:
        gameFacade.finishGame(finishGameCommand)

        then:
        noExceptionThrown()

        when:
        gameFacade.finishGame(finishGameCommand)

        then:
        Exception e = thrown(DomainValidationException.class)

    }

    def "should not finish the game and throw exception when id equals null"() {
        when:
        new FinishGameCommand(null)

        then:
        Exception e = thrown(ValidationException.class)
    }

    def "should not finish the game and throw exception when game doesn't exist"() {
        given:
        var finishGameCommand = new FinishGameCommand(1231231232L)

        when:
        gameFacade.finishGame(finishGameCommand)

        then:
        Exception e = thrown(ResourceNotFoundException.class)
    }


}
