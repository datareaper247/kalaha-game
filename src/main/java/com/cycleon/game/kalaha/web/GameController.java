package com.cycleon.game.kalaha.web;

import com.cycleon.game.kalaha.domain.Game;
import com.cycleon.game.kalaha.domain.Movement;
import com.cycleon.game.kalaha.service.GameService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class GameController {

    @Autowired
    private GameService gameService;

    @GetMapping("/start")
    @ResponseBody
    public Game getStart(@RequestParam String gameId) {
        return gameService.getGame(gameId);
    }

    @PostMapping("/move")
    public Game getMovement(@Valid @RequestBody Movement movement) {
        return gameService.getMove(movement);
    }


}
