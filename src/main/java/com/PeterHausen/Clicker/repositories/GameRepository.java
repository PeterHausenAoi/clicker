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

    @Query(value = "select \n" +
            "\tg2.*\n" +
            "from\n" +
            "\t(\n" +
            "\t\tselect \n" +
            "\t\t\tg.id,\n" +
            "\t\t\tcount(*) as count\n" +
            "\t\tfrom \n" +
            "\t\t\tgames g\n" +
            "\t\t\tleft join clicks c on(g.id=c.game_id)\n" +
            "\t\tgroup by \n" +
            "\t\t\tg.id\n" +
            "\t\torder by \n" +
            "\t\t\tcount(*) desc\n" +
            "\t\tlimit\n" +
            "\t\t\t10\n" +
            "\t)g\n" +
            "\tinner join \n" +
            "\t\tgames g2 on(g.id = g2.id)\n" +
            "\torder by \n" +
            "\t\tg.count desc", nativeQuery = true)
    Iterable<GameEntity> getTop10();
}
