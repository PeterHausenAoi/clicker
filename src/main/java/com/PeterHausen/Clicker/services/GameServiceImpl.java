package com.PeterHausen.Clicker.services;

import com.PeterHausen.Clicker.controllers.GameController;
import com.PeterHausen.Clicker.models.db.ClickEntity;
import com.PeterHausen.Clicker.models.db.GameEntity;
import com.PeterHausen.Clicker.models.rest.Click;
import com.PeterHausen.Clicker.models.rest.Game;
import com.PeterHausen.Clicker.repositories.ClickRepository;
import com.PeterHausen.Clicker.repositories.GameRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class GameServiceImpl implements GameService {
    private static final Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);
    private static final Long GAME_LENGTH = 60 * 1000L;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    private final GameRepository gameRepository;
    private final ClickRepository clickRepository;

    private String getHostname() {
        var hostname = "";

        try {
            hostname = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException ex) {
            logger.info("host error: " + ex.getMessage());
        }

        return hostname;
    }

    @Override
    public Game createGame(String playerName) {
        var builder = GameEntity.builder();

        builder.id(UUID.randomUUID())
                .playerName(playerName)
                .closed(false)
                .createdAt(System.currentTimeMillis());

        var savedObj = gameRepository.save(builder.build());

        var gameBuilder = Game.builder();

        Date timestamp = new Date(savedObj.getCreatedAt());

        gameBuilder.id(savedObj.getId())
                .playerName(savedObj.getPlayerName())
                .count(0)
                .hostname(getHostname())
                .createdAt(sdf.format(timestamp));

        return gameBuilder.build();
    }

    @Override
    public List<Game> getTopGames() {
        var gameEntities = gameRepository.getTop10();

        var games = new ArrayList<Game>();

        gameEntities.forEach(gameEntity -> {
            var builder = Game.builder();

            Date timestamp = new Date(gameEntity.getCreatedAt());

            builder.id(gameEntity.getId())
                    .playerName(gameEntity.getPlayerName())
                    .count(gameEntity.getClicks().size())
                    .closed(gameEntity.isClosed())
                    .hostname(getHostname())
                    .createdAt(sdf.format(timestamp));

            games.add(builder.build());
        });

        return games;
    }

    @Override
    public Game getGame(UUID gameId) {
        var savedObjOptional = gameRepository.findById(gameId);

        if (savedObjOptional.isEmpty()){
            return null;
        }

        var savedObj = savedObjOptional.get();

        var gameBuilder = Game.builder();

        Date timestamp = new Date(savedObj.getCreatedAt());

        gameBuilder.id(savedObj.getId())
                .playerName(savedObj.getPlayerName())
                .count(savedObj.getClicks().size())
                .closed(savedObj.isClosed())
                .hostname(getHostname())
                .createdAt(sdf.format(timestamp));

        return gameBuilder.build();
    }

    @Override
    public Click click(Click click) {
        var savedObjOptional = gameRepository.findById(click.getGameId());

        if (savedObjOptional.isEmpty()){
            return null;
        }

        var savedGame = savedObjOptional.get();

        if (savedGame.isClosed() || savedGame.getCreatedAt() < System.currentTimeMillis() - GAME_LENGTH){
            return click;
        }

        var builder = ClickEntity.builder();
        builder.game(savedGame)
                .createdAt(System.currentTimeMillis());

        var newClick = builder.build();

        var savedClick = clickRepository.save(newClick);

        var clickBuilder = Click.builder();

        Date timestamp = new Date(savedClick.getCreatedAt());

        clickBuilder.gameId(savedClick.getGame().getId())
                .timestamp(sdf.format(timestamp))
                .id(click.getId());

        return clickBuilder.build();
    }

    @Transactional(rollbackOn = {SQLException.class})
    @Override
    public int closeExpired() {
        return gameRepository.closeExpired(System.currentTimeMillis() - GAME_LENGTH);
    }

}
