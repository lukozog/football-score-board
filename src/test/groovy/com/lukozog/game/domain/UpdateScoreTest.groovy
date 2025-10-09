package com.lukozog.game.domain

import com.lukozog.game.domain.dto.FinishGameCommand
import com.lukozog.game.domain.dto.StartGameCommand
import com.lukozog.game.domain.dto.UpdateScoreCommand
import com.lukozog.game.domain.exception.DomainValidationException
import com.lukozog.game.domain.exception.ResourceNotFoundException
import com.lukozog.game.domain.exception.ValidationException
import spock.lang.Specification

import static com.lukozog.game.domain.InMemoryTeamRepository.FRANCE
import static com.lukozog.game.domain.InMemoryTeamRepository.GERMANY

class UpdateScoreTest extends Specification {

    private static Game GERMANY_VS_FRANCE

    private GameFacade gameFacade

    def setup() {
        gameFacade = new GameFacade(new InMemoryGameRepository(), new InMemoryTeamRepository())
        def gameId = gameFacade.startGame(new StartGameCommand(
                new StartGameCommand.TeamDto(GERMANY.getId()),
                new StartGameCommand.TeamDto(FRANCE.getId())
        ))
        GERMANY_VS_FRANCE = gameFacade.findById(gameId)
    }

    def "should update a score successfully"() {
        given:
        var updateScoreCommand = new UpdateScoreCommand(GERMANY_VS_FRANCE.getId(), 4, 5)

        when:
        gameFacade.updateScore(updateScoreCommand)

        then:
        noExceptionThrown()
        def game = gameFacade.findById(GERMANY_VS_FRANCE.getId())
        game != null
        game.id == GERMANY_VS_FRANCE.getId()
        game.homeTeam == GERMANY
        game.awayTeam == FRANCE
        game.score.homeScore == 4
        game.score.awayScore == 5
        game.score.totalScore == 9
        !game.finished
        game.addedAt != null
    }

    def "should not update a score and throw exception when score is not filled or is negative"() {
        when:
        new UpdateScoreCommand(
                GERMANY_VS_FRANCE.getId(),
                homeScore,
                awayScore,
        )

        then:
        Exception e = thrown(ValidationException.class)

        where:
        homeScore | awayScore
        null      | 1
        1         | null
        -1        | 1
        1         | -1
    }


    def "should not update a score and throw exception when game id is null"() {
        when:
        new UpdateScoreCommand(null, 4, 5)

        then:
        Exception e = thrown(ValidationException.class)
    }


    def "should not update a score and throw exception when game doesn't exist"() {
        given:
        var updateScoreCommand = new UpdateScoreCommand(123123213L, 4, 5)

        when:
        gameFacade.updateScore(updateScoreCommand)

        then:
        Exception e = thrown(ResourceNotFoundException.class)
    }

    def "should not update a score and throw exception when game has already finished"() {
        given:
        gameFacade.finishGame(new FinishGameCommand(GERMANY_VS_FRANCE.getId()))

        and:
        var updateScoreCommand = new UpdateScoreCommand(GERMANY_VS_FRANCE.getId(), 5, 5)

        when:
        gameFacade.updateScore(updateScoreCommand)

        then:
        Exception e = thrown(DomainValidationException.class)
    }

}
