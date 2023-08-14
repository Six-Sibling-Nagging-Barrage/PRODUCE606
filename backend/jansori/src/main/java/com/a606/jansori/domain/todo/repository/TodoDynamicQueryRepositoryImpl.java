package com.a606.jansori.domain.todo.repository;

import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.domain.TodoTag;
import com.a606.jansori.domain.todo.domain.Todo;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

public class TodoDynamicQueryRepositoryImpl implements TodoDynamicQueryRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Slice<Todo> findTodoByTagsAndPages(List<Tag> tags, Long cursor, Pageable pageable,
      LocalDate date) {

    CriteriaBuilder cb = entityManager.getCriteriaBuilder();

    CriteriaQuery<Todo> cq = cb.createQuery(Todo.class);

    Root<Todo> todo = cq.from(Todo.class);

    todo.join("nag", JoinType.LEFT);
    todo.join("member", JoinType.INNER);
    Join<Todo, TodoTag> todoTags = todo.join("todoTags", JoinType.INNER);
    todoTags.join("tag", JoinType.INNER);

    List<Predicate> predicates = new ArrayList<>();

    if (cursor != null) {
      predicates.add(cb.lessThan(todo.get("id"), cursor));
    }

    predicates.add(cb.lessThanOrEqualTo(todo.get("todoAt"), date));

    predicates.add(cb.isTrue(todo.get("display")));

    if (tags != null && !tags.isEmpty()) {
      predicates.add(todoTags.get("tag").in(tags));
    }

    cq.where(predicates.toArray(new Predicate[0]));
    cq.orderBy(cb.desc(todo.get("id")));
    cq.select(todo).distinct(true);

    TypedQuery<Todo> query = entityManager.createQuery(cq);

    List<Todo> todos = query.setMaxResults(pageable.getPageSize() + 1).getResultList();

    Boolean hasNext = pageable.isPaged() && todos.size() > pageable.getPageSize();

    return new SliceImpl<>(hasNext ? todos.subList(0, pageable.getPageSize()) : todos, pageable,
        hasNext);
  }
}
