package com.a606.jansori.domain.tag.repository;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.domain.TagFollow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagFollowRepository extends JpaRepository<TagFollow, Long> {

    Optional<TagFollow> findTagFollowByTagAndMember(Tag tag, Member member);
}
