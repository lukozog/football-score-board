package com.lukozog.game.domain

import com.lukozog.game.domain.dto.FinishGameCommand
import com.lukozog.game.domain.dto.UpdateScoreCommand
import com.lukozog.game.domain.exception.DomainValidationException
import com.lukozog.game.domain.exception.ResourceNotFoundException
import com.lukozog.game.domain.exception.ValidationException
import spock.lang.Specification

import static InMemoryGameRepository.GERMANY_VS_FRANCE

class UpdateScoreTest extends Specification {

    GameFacade scoreBoardFacade

    def setup() {
        scoreBoardFacade = new GameFacade(new InMemoryGameRepository(), new InMemoryTeamRepository())
    }

    def "should update a score successfully"() {
        given:
        var updateScoreCommand = new UpdateScoreCommand(GERMANY_VS_FRANCE.getId(), 4, 5)

        when:
        scoreBoardFacade.updateScore(updateScoreCommand)

        then:
        noExceptionThrown()
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
        scoreBoardFacade.updateScore(updateScoreCommand)

        then:
        Exception e = thrown(ResourceNotFoundException.class)
    }

    def "should not update a score and throw exception when game has already finished"() {
        given:
        scoreBoardFacade.finishGame(new FinishGameCommand(GERMANY_VS_FRANCE.getId()))

        and:
        var updateScoreCommand = new UpdateScoreCommand(GERMANY_VS_FRANCE.getId(), 5, 5)

        when:
        scoreBoardFacade.updateScore(updateScoreCommand)

        then:
        Exception e = thrown(DomainValidationException.class)
    }

}
