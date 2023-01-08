package com.PeterHausen.Clicker.repositories;

import com.PeterHausen.Clicker.models.db.GameEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface GameRepository extends CrudRepository<GameEntity, UUID> {

    @Modifying(clearAutomatically = true)
    @Query(value = "update \n" +
            "\tgames \n" +
            "set\n" +
            "\tclosed = true \n" +
            "where\n " +
            "\t closed = false" +
            "\t and created_at < :timestamp", nativeQuery = true)
    int closeExpired(@Param("timestamp") Long timestamp);
}
