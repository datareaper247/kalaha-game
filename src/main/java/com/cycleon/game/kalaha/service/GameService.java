package com.cycleon.game.kalaha.service;

import com.cycleon.game.kalaha.domain.Game;
import com.cycleon.game.kalaha.domain.Movement;


public interface GameService {

    Game getGame(String gameId);

    Game getMove(Movement movement);
}
