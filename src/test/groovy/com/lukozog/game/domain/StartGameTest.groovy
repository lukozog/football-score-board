package com.lukozog.game.domain

import com.lukozog.game.domain.dto.StartGameCommand
import com.lukozog.game.domain.exception.DomainValidationException
import com.lukozog.game.domain.exception.ResourceNotFoundException
import com.lukozog.game.domain.exception.ValidationException
import spock.lang.Specification

import static com.lukozog.game.domain.InMemoryTeamRepository.*

class StartGameTest extends Specification {

    GameFacade gameFacade

    def setup() {
        gameFacade = new GameFacade(new InMemoryGameRepository(), new InMemoryTeamRepository())
    }

    def "should start a game successfully and throw exception if start another game for spain"() {
        given:
        var startGameCommand = new StartGameCommand(
                new StartGameCommand.TeamDto(SPAIN.getId()),
                new StartGameCommand.TeamDto(BRAZIL.getId())
        )

        when:
        def gameId = gameFacade.startGame(startGameCommand)

        then:
        noExceptionThrown()
        var game = gameFacade.findById(gameId)
        game != null
        game.id == gameId
        game.homeTeam == SPAIN
        game.awayTeam == BRAZIL
        game.score.homeScore == 0
        game.score.awayScore == 0
        game.score.totalScore == 0
        !game.finished
        game.addedAt != null

        and:
        def startAnotherGameForSpain = new StartGameCommand(
                new StartGameCommand.TeamDto(SPAIN.getId()),
                new StartGameCommand.TeamDto(MEXICO.getId())
        )

        when:
        gameFacade.startGame(startAnotherGameForSpain)

        then:
        Exception e = thrown(DomainValidationException.class)
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

    def "should not start a game and throw exception if team doesn't exist"() {
        given:
        var startGameCommand = new StartGameCommand(
                new StartGameCommand.TeamDto(123123123L),
                new StartGameCommand.TeamDto(MEXICO.getId())
        )
        when:
        gameFacade.startGame(startGameCommand)

        then:
        Exception e = thrown(ResourceNotFoundException.class)
    }

}
