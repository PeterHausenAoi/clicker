package com.PeterHausen.Clicker.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class Game {
    UUID gameId;
    int count;

    public Game(){
        gameId = UUID.randomUUID();
    }
}
