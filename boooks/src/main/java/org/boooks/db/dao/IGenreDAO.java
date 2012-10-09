package org.boooks.db.dao;

import org.boooks.db.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IGenreDAO extends JpaRepository<Genre, Long> { }