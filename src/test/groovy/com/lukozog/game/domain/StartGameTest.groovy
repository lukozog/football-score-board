package com.lukozog.game.domain


import com.lukozog.game.domain.dto.StartGameCommand
import com.lukozog.game.domain.exception.DomainValidationException
import com.lukozog.game.domain.exception.ResourceNotFoundException
import com.lukozog.game.domain.exception.ValidationException
import spock.lang.Specification

import static InMemoryTeamRepository.*

class StartGameTest extends Specification {

    GameFacade scoreBoardFacade

    def setup() {
        scoreBoardFacade = new GameFacade(new InMemoryGameRepository(), new InMemoryTeamRepository())
    }

    def "should start a game successfully"() {
        given:
        var startGameCommand = new StartGameCommand(
                new StartGameCommand.TeamDto(SPAIN.getId()),
                new StartGameCommand.TeamDto(BRAZIL.getId())
        )

        when:
        scoreBoardFacade.startGame(startGameCommand)

        then:
        noExceptionThrown()
    }

    def "should not start a game and throw exception if team id is null"() {
        when:
        new StartGameCommand(
                new StartGameCommand.TeamDto(null),
                new StartGameCommand.TeamDto(ITALY.getId())
        )

        then:
        Exception e = thrown(ValidationException.class)
    }

    def "should not start a game and throw exception if teams are the same"() {
        when:
        new StartGameCommand(
                new StartGameCommand.TeamDto(ITALY.getId()),
                new StartGameCommand.TeamDto(ITALY.getId())
        )

        then:
        Exception e = thrown(ValidationException.class)
    }

    def "should not start a game and throw exception if team is involved in any other game"() {
        given:
        var startGameCommand = new StartGameCommand(
                new StartGameCommand.TeamDto(SPAIN.getId()),
                new StartGameCommand.TeamDto(MEXICO.getId())
        )

        when:
        scoreBoardFacade.startGame(startGameCommand)

        then:
        Exception e = thrown(DomainValidationException.class)
    }

    def "should not start a game and throw exception if team doesn't exist"() {
        given:
        var startGameCommand = new StartGameCommand(
                new StartGameCommand.TeamDto(123123123L),
                new StartGameCommand.TeamDto(MEXICO.getId())
        )
        when:
        scoreBoardFacade.startGame(startGameCommand)

        then:
        Exception e = thrown(ResourceNotFoundException.class)
    }

}
