package com.a606.jansori.domain.tag.repository;

import com.a606.jansori.domain.tag.domain.Tag;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

  Optional<Tag> findTagById(Long id);

  List<Tag> findTagsByNameContainingIgnoreCaseOrderByFollowCountDesc(
      String name,
      Pageable pageable);
}
