package com.lukozog.game.domain

import com.lukozog.game.domain.dto.FinishGameCommand
import com.lukozog.game.domain.dto.StartGameCommand
import com.lukozog.game.domain.dto.UpdateScoreCommand
import spock.lang.Specification

import static com.lukozog.game.domain.InMemoryTeamRepository.*

class GamesSummaryTest extends Specification {

    GameFacade gameFacade

    def setup() {
        gameFacade = new GameFacade(new InMemoryGameRepository(), new InMemoryTeamRepository())
    }

    def "should return not finished games summaries properly ordered"() {
        given: "start games according to the example data from file"
        def mexicoCanadaId = gameFacade.startGame(new StartGameCommand(
                new StartGameCommand.TeamDto(MEXICO.getId()),
                new StartGameCommand.TeamDto(CANADA.getId())
        ))
        def spainBrazilId = gameFacade.startGame(new StartGameCommand(
                new StartGameCommand.TeamDto(SPAIN.getId()),
                new StartGameCommand.TeamDto(BRAZIL.getId())
        ))
        def germanyFranceId = gameFacade.startGame(new StartGameCommand(
                new StartGameCommand.TeamDto(GERMANY.getId()),
                new StartGameCommand.TeamDto(FRANCE.getId())
        ))
        def uruguayItalyId = gameFacade.startGame(new StartGameCommand(
                new StartGameCommand.TeamDto(URUGUAY.getId()),
                new StartGameCommand.TeamDto(ITALY.getId())
        ))
        def argentinaAustraliaId = gameFacade.startGame(new StartGameCommand(
                new StartGameCommand.TeamDto(ARGENTINA.getId()),
                new StartGameCommand.TeamDto(AUSTRALIA.getId())
        ))

        and: "set proper scores"
        gameFacade.updateScore(new UpdateScoreCommand(mexicoCanadaId, 0, 5))
        gameFacade.updateScore(new UpdateScoreCommand(spainBrazilId, 10, 2))
        gameFacade.updateScore(new UpdateScoreCommand(germanyFranceId, 2, 2))
        gameFacade.updateScore(new UpdateScoreCommand(uruguayItalyId, 6, 6))
        gameFacade.updateScore(new UpdateScoreCommand(argentinaAustraliaId, 3, 1))

        when:
        def result = gameFacade.getGamesSummary()

        then:
        result.size() == 5
        result[0].gameId() == uruguayItalyId
        result[1].gameId() == spainBrazilId
        result[2].gameId() == mexicoCanadaId
        result[3].gameId() == argentinaAustraliaId
        result[4].gameId() == germanyFranceId

        and:
        gameFacade.updateScore(new UpdateScoreCommand(argentinaAustraliaId, 3, 2))
        gameFacade.updateScore(new UpdateScoreCommand(germanyFranceId, 3, 2))

        when:
        result = gameFacade.getGamesSummary()

        then:
        result.size() == 5
        result[0].gameId() == uruguayItalyId
        result[1].gameId() == spainBrazilId
        result[2].gameId() == argentinaAustraliaId
        result[3].gameId() == germanyFranceId
        result[4].gameId() == mexicoCanadaId

        and:
        gameFacade.finishGame(new FinishGameCommand(mexicoCanadaId))
        gameFacade.finishGame(new FinishGameCommand(spainBrazilId))

        when:
        result = gameFacade.getGamesSummary()

        then:
        result.size() == 3
        result[0].gameId() == uruguayItalyId
        result[1].gameId() == argentinaAustraliaId
        result[2].gameId() == germanyFranceId
    }

}
