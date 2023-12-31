package com.a606.jansori.dummy;

import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.repository.TagRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("local")
public class TagDummy {

  private final DummyProperty dummyProperty;
  private final TagRepository tagRepository;

  private final List<Tag> tags = new ArrayList<>();


  public List<Tag> createTags() {

    List<String> tagDummies = dummyProperty.getTagDummies();

    for (String tagName : tagDummies) {
      tags.add(Tag.builder().name(tagName).build());
    }
    return tagRepository.saveAll(tags);
  }

}
