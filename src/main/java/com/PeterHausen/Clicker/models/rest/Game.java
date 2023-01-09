package com.PeterHausen.Clicker.models.rest;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class Game {
    UUID id;

    String playerName;

    int count;

    String createdAt;

    boolean closed;

    String hostname;

    public Game(String playerName) {
        this.playerName = playerName;
    }
}
