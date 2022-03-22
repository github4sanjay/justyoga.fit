package com.justyoga.collection.domain.dao;

import static org.hibernate.jpa.QueryHints.HINT_FETCH_SIZE;

import com.justyoga.collection.domain.model.mysql.Collection;
import java.util.UUID;
import java.util.stream.Stream;
import javax.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

public interface CollectionRepo extends JpaRepository<Collection, UUID> {

    @QueryHints(value = @QueryHint(name = HINT_FETCH_SIZE, value = "" + Integer.MIN_VALUE))
    @Query(value = "select t from Collection t ")
    Stream<Collection> streamAll();
}
