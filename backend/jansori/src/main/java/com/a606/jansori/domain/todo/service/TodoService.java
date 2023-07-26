package com.a606.jansori.domain.todo.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.exception.MemberNotFoundException;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.domain.TodoTag;
import com.a606.jansori.domain.tag.exception.TagNotFoundException;
import com.a606.jansori.domain.tag.repository.TagRepository;
import com.a606.jansori.domain.todo.domain.Todo;
import com.a606.jansori.domain.todo.dto.*;
import com.a606.jansori.domain.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    private final TagRepository tagRepository;

    private final MemberRepository memberRepository;

    private final Clock clock;

    @Transactional
    public PostTodoResDto postTodo(PostTodoReqDto postTodoReqDto, Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException());

        Todo todo = postTodoReqDto.getTodoWith(member);

        postTodoReqDto.getTags().stream()
                .forEach(tagDto -> {
                    TodoTag todoTag = new TodoTag(getTagIfExistElseSave(tagDto));

                    todoTag.setTodo(todo);
                });

        return new PostTodoResDto(todoRepository.save(todo).getId());
    }

    private Tag getTagIfExistElseSave(TagDto tagDto) {

        if (tagDto.getTagId() == -1) {

            return tagRepository.save(Tag.from(tagDto));
        }

        return tagRepository.findById(tagDto.getTagId())
                .orElseThrow(() -> new TagNotFoundException());
    }


    @Transactional(readOnly = true)
    public GetTodoListResDto getMyTodayTodo(Long memberId) {

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException());

        LocalDate current = LocalDate.now(clock);
        LocalDateTime today = current.atStartOfDay();
        LocalDateTime tomorrow = current.plusDays(1).atStartOfDay();

        List<TodoDto> todos = todoRepository.findAllByMemberAndCreatedAtBetween(member, today, tomorrow).stream()
                .map(todo -> TodoDto.from(todo))
                .collect(Collectors.toList());

        return GetTodoListResDto.builder()
                .todos(todos)
                .build();
    }
}
