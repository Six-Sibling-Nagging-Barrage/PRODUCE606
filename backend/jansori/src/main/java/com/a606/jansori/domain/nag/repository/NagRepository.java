package com.a606.jansori.domain.nag.repository;

import com.a606.jansori.domain.nag.domain.Nag;
import java.util.List;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NagRepository extends JpaRepository<Nag, Long> {

  @Query(value = "select n from nag n order by n.likeCount desc ")
  List<Nag> findByRandomNagsNotInTopRank(Pageable pageable);

  @Query(value = "select n from nag n join fetch n.member order by n.likeCount desc ")
  List<Nag> findByNagsOfNagBox(Pageable pageable);
}
