package com.log.reader.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.log.reader.db.model.Event;

@Repository
public interface LogEventRepository extends JpaRepository<Event, Long> {

}
