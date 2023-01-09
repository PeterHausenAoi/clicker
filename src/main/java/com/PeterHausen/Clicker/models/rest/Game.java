package com.PeterHausen.Clicker.models.rest;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(ignoreUnknown = true)
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
