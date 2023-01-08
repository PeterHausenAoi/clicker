package com.PeterHausen.Clicker.services;

import com.PeterHausen.Clicker.models.rest.Click;
import com.PeterHausen.Clicker.models.rest.Game;

import java.util.List;
import java.util.UUID;

public interface GameService {
    Game createGame(String playerName);
    List<Game> getTopGames();

    Game getGame(UUID gameId);

    Click click(Click click);

    int closeExpired();
}
