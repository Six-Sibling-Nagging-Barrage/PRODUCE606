package com.a606.jansori.domain.todo.repository;

import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.todo.domain.Todo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public class TodoDynamicQueryRepositoryImpl implements TodoDynamicQueryRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Slice<Todo> findFeed(List<Tag> tags, Long cursor, Pageable pageable) {

    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

    CriteriaQuery<Todo> criteriaQuery = criteriaBuilder.createQuery(Todo.class);

    

    return null;
  }
}
