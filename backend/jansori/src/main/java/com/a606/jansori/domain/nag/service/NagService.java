package com.a606.jansori.domain.nag.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.exception.MemberNotFoundException;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.nag.domain.NagInteraction;
import com.a606.jansori.domain.nag.domain.NagLike;
import com.a606.jansori.domain.nag.domain.NagUnlock;
import com.a606.jansori.domain.nag.domain.Nags;
import com.a606.jansori.domain.nag.dto.GetMemberNagsOfReqDto;
import com.a606.jansori.domain.nag.dto.GetNagBoxStatisticsResDto;
import com.a606.jansori.domain.nag.dto.GetNagOfMainPageResDto;
import com.a606.jansori.domain.nag.dto.GetNagsOfNagBoxResDto;
import com.a606.jansori.domain.nag.dto.GetNagsOfOtherResDto;
import com.a606.jansori.domain.nag.dto.GetNagsOfReqDto;
import com.a606.jansori.domain.nag.dto.GetNagsOfResDto;
import com.a606.jansori.domain.nag.dto.NagDetailDto;
import com.a606.jansori.domain.nag.dto.NagDto;
import com.a606.jansori.domain.nag.dto.NagOfNagBox;
import com.a606.jansori.domain.nag.dto.PostNagLikeResDto;
import com.a606.jansori.domain.nag.dto.PostNagReqDto;
import com.a606.jansori.domain.nag.dto.PostNagResDto;
import com.a606.jansori.domain.nag.dto.PutNagUnlockResDto;
import com.a606.jansori.domain.nag.event.NagPublishedTodoEvent;
import com.a606.jansori.domain.nag.exception.NagInvalidRequestException;
import com.a606.jansori.domain.nag.exception.NagLikeBusinessException;
import com.a606.jansori.domain.nag.exception.NagNotFoundException;
import com.a606.jansori.domain.nag.exception.NagUnlockBusinessException;
import com.a606.jansori.domain.nag.repository.NagInteractionRepository;
import com.a606.jansori.domain.nag.repository.NagRepository;
import com.a606.jansori.domain.nag.util.PreviewUtil;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.exception.TagNotFoundException;
import com.a606.jansori.domain.tag.repository.TagRepository;
import com.a606.jansori.domain.todo.repository.TodoRepository;
import com.a606.jansori.global.auth.util.SecurityUtil;
import com.a606.jansori.infra.redis.util.NagBoxStatisticsUtil;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class NagService {

  private final int NAG_BOX_COUNT = 5;
  private final NagRepository nagRepository;
  private final TagRepository tagRepository;
  private final MemberRepository memberRepository;
  private final NagInteractionRepository nagInteractionRepository;
  private final TodoRepository todoRepository;
  private final NagRandomGenerator nagRandomGenerator;
  private final PreviewUtil previewUtil;
  private final SecurityUtil securityUtil;
  private final ApplicationEventPublisher publisher;
  private final NagBoxStatisticsUtil nagBoxStatisticsUtil;

  @Transactional
  public PostNagResDto createNag(PostNagReqDto postNagReqDto) {
    Tag tag = null;

    if (postNagReqDto.getTagId() >= 0) {
      tag = tagRepository.findById(postNagReqDto.getTagId())
          .orElseThrow(TagNotFoundException::new);
    } else if (postNagReqDto.getTagId() == -1) {
      tag = Tag.createTag(postNagReqDto.getTagName());
      tagRepository.save(tag);
    }

    Member member = securityUtil.getCurrentMemberByToken();
    String preview = previewUtil.convertNagToPreview(postNagReqDto.getContent());

    member.issuedTicketByCreateNag();
    Nag nag = Nag.ofMemberWithNagContentAndPreview(member, tag, postNagReqDto.getContent(),
        preview);

    Nag savedNag = nagRepository.save(nag);
    nagBoxStatisticsUtil.increaseTotalNagCount();

    if (postNagReqDto.getTagId() >= 0) {
      publisher.publishEvent(new NagPublishedTodoEvent(savedNag));
    }

    return PostNagResDto.builder()
        .nagId(savedNag.getId())
        .ticketCount(member.getTicket())
        .build();
  }

  @Transactional
  public PostNagLikeResDto toggleNagLike(Long nagId) {
    Nag nag = nagRepository.findById(nagId).orElseThrow(NagNotFoundException::new);
    Member member = securityUtil.getCurrentMemberByToken();

    NagInteraction nagInteraction = nagInteractionRepository
        .findNagInteractionByNagAndMember(nag, member).orElseThrow(NagLikeBusinessException::new);

    nagInteraction.toggleNagLike(nag);

    return PostNagLikeResDto.from(nagInteraction.getNagLike());
  }

  @Transactional
  public PutNagUnlockResDto unlockNagPreviewByMemberTicket(Long nagId) {
    Nag nag = nagRepository.findById(nagId).orElseThrow(NagNotFoundException::new);
    Member member = securityUtil.getCurrentMemberByToken();

    if (nagInteractionRepository.existsByNagAndMember(nag, member)) {
      throw new NagUnlockBusinessException();
    } else if (member.getTicket() <= 0) {
      throw new NagUnlockBusinessException();
    }

    member.consumeTicketToUnlockNag();
    nagInteractionRepository.save(NagInteraction.ofUnlockPreviewByNagAndMember(nag, member));
    return PutNagUnlockResDto.ofNagUnlockWithTicketCount(nag, member.getTicket());
  }

  @Transactional(readOnly = true)
  public GetNagsOfResDto getAllNagsByMine(GetNagsOfReqDto getNagsOfReqDto) {

    Member member = securityUtil.getCurrentMemberByToken();

    Long cursor = getNagsOfReqDto.getCursor();
    Integer size = getNagsOfReqDto.getSize();

    Slice<Nag> nags = nagRepository.findByNagsByMemberAndPages(member, cursor,
        PageRequest.of(0, size));

    Long nextCursor = nags.hasNext() ? nags.getContent().get(size - 1).getId() : null;

    return GetNagsOfResDto.ofNagList(nags.getContent()
        .stream()
        .map(nag -> NagDetailDto.ofNagAndTag(nag, nag.getTag()))
        .collect(Collectors.toList()), nags.hasNext(), nextCursor);
  }

  @Transactional(readOnly = true)
  public GetNagsOfOtherResDto getMemberNagsByMemberId(GetMemberNagsOfReqDto getMemberNagsOfReqDto) {
    Member viewer = securityUtil.getCurrentMemberByToken();
    Member owner = memberRepository.findMemberById(getMemberNagsOfReqDto.getMemberId())
        .orElseThrow(MemberNotFoundException::new);

    if (Objects.equals(viewer.getId(), owner.getId())) {
      throw new NagInvalidRequestException();
    }

    Long cursor = getMemberNagsOfReqDto.getCursor();
    Integer size = getMemberNagsOfReqDto.getSize();

    Slice<Nag> nags = nagRepository
        .findByNagsWithLockStatusByMemberAndPages(viewer, owner, cursor, PageRequest.of(0, size));
    List<NagUnlock> nagUnlocks = nagUnlockRepository
        .findNagUnlocksByNagIsInAndMember(nags.getContent(), viewer);
    List<NagLike> nagLikes = nagLikeRepository
        .findNagLikesByNagIsInAndMember(nags.getContent(), viewer);

    Nags nagsOfMemberProfile = new Nags(nags.getContent(), nagUnlocks, nagLikes);

    Long nextCursor = nags.hasNext() ? nags.getContent().get(size - 1).getId() : null;

    return GetNagsOfOtherResDto
        .ofOtherNagsList(nagsOfMemberProfile.getNags(), nags.hasNext(), nextCursor);
  }

  @Transactional(readOnly = true)
  public GetNagOfMainPageResDto getRandomNagsOfMainPage() {
    return GetNagOfMainPageResDto.builder()
        .nags(nagRandomGenerator.getRandomNagsOfMainPage()
            .stream()
            .map(NagDto::from)
            .collect(Collectors.toList()))
        .build();
  }

  @Transactional(readOnly = true)
  public GetNagsOfNagBoxResDto getNagsOfNagBox() {
    Member member = securityUtil.getCurrentMemberByToken();

    List<Nag> nags = nagRepository.findByNagsOfNagBox(PageRequest.of(0, NAG_BOX_COUNT));
    List<NagInteraction> nagInteractions = nagInteractionRepository
        .findNagInteractionsByNagIsInAndMember(nags, member);

    return GetNagsOfNagBoxResDto.fromNagInteraction(nagInteractions.stream()
        .map(NagOfNagBox::from)
        .collect(Collectors.toList()));
  }

  @Transactional(readOnly = true)
  public GetNagBoxStatisticsResDto getNagBoxStatisticsResDto() {
    if (nagBoxStatisticsUtil.hasNagBoxStatistics()) {
      return nagBoxStatisticsUtil.getNagBoxStatisticsResDto();
    }

    Long totalMemberCount = memberRepository.count();
    Long totalDoneTodoCount = todoRepository.countTodosByFinishedIsTrue();
    Long totalNagsCount = nagRepository.count();

    return nagBoxStatisticsUtil.cachingNagBoxStatistics(
        GetNagBoxStatisticsResDto.of(totalMemberCount,
            totalDoneTodoCount,
            totalNagsCount));
  }
}
