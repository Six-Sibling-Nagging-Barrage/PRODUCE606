package com.a606.jansori.domain.todo.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.domain.TodoTag;
import com.a606.jansori.domain.tag.exception.TagNotFoundException;
import com.a606.jansori.domain.tag.repository.TagRepository;
import com.a606.jansori.domain.tag.repository.TodoTagRepository;
import com.a606.jansori.domain.todo.domain.Todo;
import com.a606.jansori.domain.todo.dto.PostTodoReqDto;
import com.a606.jansori.domain.todo.dto.PostTodoResDto;
import com.a606.jansori.domain.todo.dto.TagDto;
import com.a606.jansori.domain.todo.repository.TodoRepository;
import com.a606.jansori.global.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    private final TagRepository tagRepository;

    private final MemberRepository memberRepository;

    @Transactional
    public PostTodoResDto postTodo(PostTodoReqDto postTodoReqDto, Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundException("800", "사용자를 찾을 수 없습니다."));

        Todo todo = Todo.ofMemberAndRequestDto(member, postTodoReqDto);

        postTodoReqDto.getTags().stream()
                .forEach(tagDto -> todo.getTodoTags().add(new TodoTag(getTagIfExistElseSave(tagDto))));

        return new PostTodoResDto(todoRepository.save(todo).getId());
    }

    private Tag getTagIfExistElseSave(TagDto tagDto) {

        if (tagDto.getTagId() == -1) {

            return tagRepository.save(Tag.from(tagDto));
        }

        return tagRepository.findById(tagDto.getTagId())
                .orElseThrow(() -> new TagNotFoundException());
    }


}
