package com.PeterHausen.Clicker.repositories;


import com.PeterHausen.Clicker.models.db.ClickEntity;
import org.springframework.data.repository.CrudRepository;

public interface ClickRepository extends CrudRepository<ClickEntity, Long> {
}
