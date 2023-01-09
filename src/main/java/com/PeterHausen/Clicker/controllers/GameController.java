package com.PeterHausen.Clicker.controllers;

import com.PeterHausen.Clicker.models.exceptions.NotFoundException;
import com.PeterHausen.Clicker.models.exceptions.ValidationException;
import com.PeterHausen.Clicker.models.rest.Click;
import com.PeterHausen.Clicker.models.rest.Game;
import com.PeterHausen.Clicker.services.GameService;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("api/game")
@AllArgsConstructor
public class GameController {
    private static final Logger logger = LoggerFactory.getLogger(GameController.class);
    private final GameService gameService;

    @PostMapping(produces = "application/json", consumes = "application/json")
    public Game start(@RequestBody Game game) throws ValidationException {
        logger.info("game start");
        if (Strings.isEmpty(game.getPlayerName())){
            logger.info("game start - no name");
            throw new ValidationException("No name");
        }

        return gameService.createGame(game.getPlayerName());
    }

    @GetMapping(value = "/{gameId}", produces = "application/json")
    public Game getGame(@PathVariable UUID gameId) throws NotFoundException {
        logger.info("game getGame");
        var game = gameService.getGame(gameId);

        if (Objects.isNull(game)) {
            logger.info("game getGame - No game home");
            throw new NotFoundException("No game home");
        }

        return game;
    }

    @PostMapping(value = "/{gameId}", produces = "application/json", consumes = "application/json")
    public Game click(@PathVariable UUID gameId, @RequestBody Click click) throws NotFoundException, ValidationException {
        logger.info("game click");
        if (Objects.isNull(click.getGameId())) {
            logger.info("No game home - invalid input");
            throw new ValidationException("No game home - invalid input");
        }

        var savedClick = gameService.click(click);

        if (Objects.isNull(savedClick)) {
            logger.info("No game home");
            throw new NotFoundException("No game home");
        }

        var game = gameService.getGame(gameId);

        if (Objects.isNull(game)) {
            logger.info("No game home nani ??");
            throw new NotFoundException("No game home nani ??");
        }

        return game;
    }
}
