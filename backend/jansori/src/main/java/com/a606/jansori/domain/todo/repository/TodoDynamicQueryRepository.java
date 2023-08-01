package com.a606.jansori.domain.todo.repository;

import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.todo.domain.Todo;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoDynamicQueryRepository {

  Slice<Todo> findFeed(List<Tag> tags, Long cursor, Pageable pageable);

}
