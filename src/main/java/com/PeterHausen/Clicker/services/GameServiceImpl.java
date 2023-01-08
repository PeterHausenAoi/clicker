package com.PeterHausen.Clicker.services;

import com.PeterHausen.Clicker.models.db.ClickEntity;
import com.PeterHausen.Clicker.models.db.GameEntity;
import com.PeterHausen.Clicker.models.rest.Click;
import com.PeterHausen.Clicker.models.rest.Game;
import com.PeterHausen.Clicker.repositories.ClickRepository;
import com.PeterHausen.Clicker.repositories.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class GameServiceImpl implements GameService {

    private static final Long GAME_LENGTH = 60 * 1000L;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    private final GameRepository gameRepository;
    private final ClickRepository clickRepository;

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
                .createdAt(sdf.format(timestamp));

        return gameBuilder.build();
    }

    @Override
    public List<Game> getTopGames() {
        return null;
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
