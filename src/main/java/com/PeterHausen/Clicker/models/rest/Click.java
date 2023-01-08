package com.PeterHausen.Clicker.models.rest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class Click {
    UUID gameId;
    Long id;
    String timestamp;
}
