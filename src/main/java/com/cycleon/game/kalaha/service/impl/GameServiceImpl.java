package com.cycleon.game.kalaha.service.impl;

import com.cycleon.game.kalaha.commons.Pit;
import com.cycleon.game.kalaha.commons.PlayerKey;
import com.cycleon.game.kalaha.commons.Seed;
import com.cycleon.game.kalaha.domain.Game;
import com.cycleon.game.kalaha.domain.Movement;
import com.cycleon.game.kalaha.domain.Player;
import com.cycleon.game.kalaha.service.GameService;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

@Service
@Slf4j
public class GameServiceImpl implements GameService {
    private HashMap<String, Game> games = new HashMap<>();

    @Override
    public Game getGame(String gameId) {
        if (games.containsKey(gameId)) {
            return games.get(gameId);
        }
        return games.get(createNewGame());
    }

    @Override
    public Game getMove(Movement movement) {
        Game game = this.getGame(movement.getGameId());
        if (!game.getCurrentPlayer().equals(movement.getPlayer())) return game;

        Game tempGame = game;
        int pitNumber = movement.getPitNumber();

        boolean isCurrentsTurn = false;
        switch (movement.getPlayer()) {
            case "player1":

                isCurrentsTurn = calculateMovements(tempGame, pitNumber);
                if (!isCurrentsTurn) {
                    tempGame.setCurrentPlayer(PlayerKey.PLAYER2_KEY.getPlayerKey());
                }
                log.info("Movement of " + game.getGameId() + " Turn: "+ PlayerKey.PLAYER1_KEY.getPlayerKey());
                break;
            case "player2":
                Player player1 = game.getPlayer1();
                Player player2 = game.getPlayer2();
                tempGame.setPlayer1(player2);
                tempGame.setPlayer2(player1);
                isCurrentsTurn = calculateMovements(tempGame, pitNumber);
                if (!isCurrentsTurn) {
                    tempGame.setCurrentPlayer(PlayerKey.PLAYER1_KEY.getPlayerKey());
                }
                player1 = tempGame.getPlayer2();
                player2 = tempGame.getPlayer1();
                tempGame.setPlayer1(player1);
                tempGame.setPlayer2(player2);
                log.info("Movement of " + game.getGameId() + " Turn: "+ PlayerKey.PLAYER2_KEY.getPlayerKey());
                break;
            default:
                return game;
        }
        boolean isWinnerExist = checkWinner(tempGame);
        tempGame.setWinnerExist(isWinnerExist);
        game = tempGame;
        return game;
    }

    private String createNewGame() {
        UUID uuid = UUID.randomUUID();
        String gameId = uuid.toString();
        games.put(gameId, this.initiateResultSet(gameId));
        log.info("New Game is created. Game ID:" + gameId);
        return gameId;
    }


    private boolean calculateMovements(Game game, int pitNumber) {
        Player currentPlayer = game.getPlayer1();
        Player counterPlayer = game.getPlayer2();

        int[] ownPits = currentPlayer.getPits();
        int stones = ownPits[pitNumber];
        boolean isMyTurn = false;


        ownPits[pitNumber] = Seed.BLANK.getSeeds();
        if (stones == Seed.BLANK.getSeeds()) return true;
        while (stones != Seed.BLANK.getSeeds()) {
            for (int i = pitNumber + 1; i < Pit.INIT_PLAYER_PIT_SIZE.getPitSize(); i++) {
                if (stones == Seed.BLANK.getSeeds()) break;
                ownPits[i]++;
                stones--;
            }

            if (stones != Seed.BLANK.getSeeds()) {
                isMyTurn = true;
                currentPlayer.treasury++;
                stones--;
            }

            if (stones != Seed.BLANK.getSeeds()) {
                isMyTurn = false;
                int[] counterPits = counterPlayer.getPits();
                int i = 0;
                int targetIndex = (stones >= Pit.INIT_PLAYER_PIT_SIZE.getPitSize()) ? Pit.INIT_PLAYER_PIT_SIZE.getPitSize() : stones;

                for (i = 0; i < targetIndex; i++) {
                    if (stones == Seed.BLANK.getSeeds()) break;
                    counterPits[i]++;
                    stones--;
                }
                if (counterPits[i - 1] % 2 == 0 && counterPits[i - 1] > 0) {
                    currentPlayer.treasury += counterPits[i];
                    currentPlayer.treasury += ownPits[Pit.INIT_PLAYER_PIT_SIZE.getPitSize() - i];
                    ownPits[Pit.INIT_PLAYER_PIT_SIZE.getPitSize() - i] = 0;
                    counterPits[i - 1] = 0;

                }
                counterPlayer.setPits(counterPits);
            }
            currentPlayer.setPits(ownPits);
        }

        game.setPlayer1(currentPlayer);
        game.setPlayer2(counterPlayer);
        return isMyTurn;
    }

    private Game initiateResultSet(String gameId) {
        int[] pits1 = new int[Pit.INIT_PLAYER_PIT_SIZE.getPitSize()];
        int[] pits2 = new int[Pit.INIT_PLAYER_PIT_SIZE.getPitSize()];

        Arrays.fill(pits1, Seed.INITIAL_PIT_SEED.getSeeds());
        Arrays.fill(pits2, Seed.INITIAL_PIT_SEED.getSeeds());

        return Game.builder().
                gameId(gameId).
                currentPlayer(PlayerKey.PLAYER1_KEY.getPlayerKey()).
                player1(Player.builder().pits(pits1).build()).
                player2(Player.builder().pits(pits2).build()).
                build();
    }

    private boolean checkWinner(Game game) {

        boolean isWinnerExist = isAllStonesSpent(game.getPlayer1().getPits())
                || isAllStonesSpent(game.getPlayer2().getPits());

        if (isWinnerExist) {
            decideWinner(game);
        }
        return isWinnerExist;
    }

    private void decideWinner(Game game) {
        int player1Treasury = game.getPlayer1().treasury +
                Arrays.stream(game.getPlayer1().getPits()).sum();
        int player2Treasury = game.getPlayer2().treasury +
                Arrays.stream(game.getPlayer2().getPits()).sum();
        if (player1Treasury > player2Treasury) {
            log.info("Winner of " + game.getGameId() + " is " + PlayerKey.PLAYER1_KEY.getPlayerKey());
            game.setWinner(PlayerKey.PLAYER1_KEY.getPlayerKey());
        } else {
            log.info("Winner of " + game.getGameId() + " is " + PlayerKey.PLAYER2_KEY.getPlayerKey());
            game.setWinner(PlayerKey.PLAYER2_KEY.getPlayerKey());
        }
    }

    private static boolean isAllStonesSpent(int[] pits) {
        for (int pit : pits) if (pit != 0) return false;
        return true;
    }
}
