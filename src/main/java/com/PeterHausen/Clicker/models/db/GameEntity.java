package com.PeterHausen.Clicker.models.db;

import com.PeterHausen.Clicker.models.rest.Click;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "games")
public class GameEntity {
    @Id
    UUID id;

    String playerName;

    Long createdAt;

    boolean closed;
    @ToString.Exclude
    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<ClickEntity> clicks = new HashSet<>();
}
