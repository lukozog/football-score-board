Assumptions

- I assumed that once the game is finished, it won't be visible on the scoreboard, but I am not certain about it.
  Scoreboard may also contain historical games

Improvements

- GameFacade.getGamesSummary(...) is a logic that should be handled by db query, without fetching all the data
  into app memory.
- From my point of view I would probably add to the Game startDateTime/finishDateTime, but it wasn't necessary for
  current requirements
- Exceptions should contain more specific information about ids of resources for which the exception was thrown, as
  well as tests should check not only the type of exception but also the message 
