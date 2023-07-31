package com.a606.jansori.domain.tag.repository;

import com.a606.jansori.domain.tag.domain.Tag;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Optional<Tag> findTagById(Long id);
}
