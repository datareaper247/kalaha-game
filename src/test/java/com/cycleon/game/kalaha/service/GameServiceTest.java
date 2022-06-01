package com.cycleon.game.kalaha.service;

import com.cycleon.game.kalaha.commons.PlayerKey;
import com.cycleon.game.kalaha.domain.Game;
import com.cycleon.game.kalaha.domain.Movement;
import com.cycleon.game.kalaha.service.impl.GameServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @InjectMocks
    GameServiceImpl gameService;

    @Test
    public void whenGameIdNotExist_shouldReturnNewGameId() {
        assertNotEquals(gameService.getGame("random").getGameId(), "random");
    }


    @Test
    public void whenGameIdExists_shouldReturnExistOne() throws Exception {
        Game game = gameService.getGame("random");
        assertEquals(gameService.getGame(game.getGameId()).getGameId(), game.getGameId());
    }


    @Test
    public void whenMovementExists_shouldReturnProperResult() throws Exception {
        Movement movement = Movement.builder().gameId("random").player(PlayerKey.PLAYER1_KEY.getPlayerKey()).pitNumber(4).build();
        Game game = gameService.getMove(movement);
        assertEquals(game.getCurrentPlayer(), PlayerKey.PLAYER2_KEY.getPlayerKey());
        assertFalse(game.isWinnerExist);
        assertEquals(game.getPlayer1().getTreasury(), 1);

    }


}
