package com.PeterHausen.Clicker.controllers;

import com.PeterHausen.Clicker.models.Click;
import com.PeterHausen.Clicker.models.Game;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/game")
public class GameController {

    @PostMapping(produces = "application/json", consumes = "application/json")
    public Game start() {
        return new Game();
    }

    @GetMapping(value = "/{gameId}", produces = "application/json")
    public Game getGameState(@PathVariable UUID gameId){
        return new Game(gameId, 0);
    }

    @PostMapping(value = "/{gameId}", produces = "application/json", consumes = "application/json")
    public Game click(@PathVariable UUID gameId, @RequestBody Click click){
        return new Game(gameId, 0);
    }
}
