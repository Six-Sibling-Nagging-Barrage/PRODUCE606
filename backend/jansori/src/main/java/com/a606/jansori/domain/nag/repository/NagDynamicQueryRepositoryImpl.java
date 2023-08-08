package com.a606.jansori.domain.nag.repository;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.todo.domain.Todo;
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

public class NagDynamicQueryRepositoryImpl implements NagDynamicQueryRepository {

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public Slice<Nag> findByNagsByMemberAndPages(Member member, Long cursor, Pageable pageable) {

    CriteriaBuilder cb = entityManager.getCriteriaBuilder();

    CriteriaQuery<Nag> cq = cb.createQuery(Nag.class);

    Root<Nag> nag = cq.from(Nag.class);

    nag.join("tag", JoinType.INNER);
    nag.join("member", JoinType.INNER);
    nag.join("todos", JoinType.LEFT);

    List<Predicate> predicates = new ArrayList<>();

    if(cursor != null) {
      predicates.add(cb.lessThan(nag.get("id"), cursor));
    }

    cq.where(predicates.toArray(new Predicate[0]));
    cq.orderBy(cb.desc(nag.get("createAt")));
    cq.select(nag);

    TypedQuery<Nag> query = entityManager.createQuery(cq);

    List<Nag> nags = query.setMaxResults(pageable.getPageSize() + 1).getResultList();

    Boolean hasNext = pageable.isPaged() && nags.size() > pageable.getPageSize();

    return new SliceImpl<>(hasNext ? nags.subList(0, pageable.getPageSize()) : nags, pageable,
        hasNext);
  }
}
