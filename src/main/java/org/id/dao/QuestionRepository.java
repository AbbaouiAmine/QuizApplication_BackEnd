package org.id.dao;

import java.util.List;

import org.id.entities.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface QuestionRepository extends JpaRepository<Question, Long>{
}
