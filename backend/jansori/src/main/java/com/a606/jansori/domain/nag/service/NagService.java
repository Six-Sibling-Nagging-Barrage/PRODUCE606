package com.a606.jansori.domain.nag.service;

import com.a606.jansori.domain.member.domain.Member;
import com.a606.jansori.domain.member.exception.MemberNotFoundException;
import com.a606.jansori.domain.member.repository.MemberRepository;
import com.a606.jansori.domain.nag.domain.Nag;
import com.a606.jansori.domain.nag.domain.NagBox;
import com.a606.jansori.domain.nag.domain.NagLike;
import com.a606.jansori.domain.nag.domain.NagUnlock;
import com.a606.jansori.domain.nag.dto.GetMemberNagsOfReqDto;
import com.a606.jansori.domain.nag.dto.GetNagBoxStatisticsResDto;
import com.a606.jansori.domain.nag.dto.GetNagOfMainPageResDto;
import com.a606.jansori.domain.nag.dto.GetNagsOfOtherResDto;
import com.a606.jansori.domain.nag.dto.GetNagsOfResDto;
import com.a606.jansori.domain.nag.dto.GetNagsOfNagBoxResDto;
import com.a606.jansori.domain.nag.dto.GetNagsOfReqDto;
import com.a606.jansori.domain.nag.dto.NagDetailDto;
import com.a606.jansori.domain.nag.dto.NagDto;
import com.a606.jansori.domain.nag.dto.NagOfProfileDto;
import com.a606.jansori.domain.nag.dto.PostNagLikeResDto;
import com.a606.jansori.domain.nag.dto.PostNagReqDto;
import com.a606.jansori.domain.nag.dto.PostNagResDto;
import com.a606.jansori.domain.nag.exception.NagInvalidRequestException;
import com.a606.jansori.domain.nag.exception.NagNotFoundException;
import com.a606.jansori.domain.nag.exception.NagUnlockBusinessException;
import com.a606.jansori.domain.nag.repository.NagLikeRepository;
import com.a606.jansori.domain.nag.repository.NagRepository;
import com.a606.jansori.domain.nag.repository.NagUnlockRepository;
import com.a606.jansori.domain.nag.util.PreviewUtil;
import com.a606.jansori.domain.tag.domain.Tag;
import com.a606.jansori.domain.tag.exception.TagNotFoundException;
import com.a606.jansori.domain.tag.repository.TagRepository;
import com.a606.jansori.domain.todo.repository.TodoRepository;
import com.a606.jansori.global.auth.util.SecurityUtil;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
  private final NagLikeRepository nagLikeRepository;
  private final NagUnlockRepository nagUnlockRepository;
  private final TodoRepository todoRepository;
  private final NagRandomGenerator nagRandomGenerator;
  private final PreviewUtil previewUtil;
  private final SecurityUtil securityUtil;

  @Transactional
  public PostNagResDto createNag(PostNagReqDto postNagReqDto) {
    Tag tag = tagRepository.findById(postNagReqDto.getTagId())
        .orElseThrow(TagNotFoundException::new);
    Member member = securityUtil.getCurrentMemberByToken();
    String preview = previewUtil.convertNagToPreview(postNagReqDto.getContent());

    Nag nag = Nag.ofMemberWithNagContentAndPreview(member, tag, postNagReqDto.getContent(),
        preview);
    return PostNagResDto.builder().nagId(nagRepository.save(nag).getId()).build();
  }

  @Transactional
  public PostNagLikeResDto toggleNagLike(Long nagId) {
    Nag nag = nagRepository.findById(nagId).orElseThrow(NagNotFoundException::new);
    Member member = securityUtil.getCurrentMemberByToken();

    Optional<NagLike> nagLike = nagLikeRepository.findNagLikeByNagAndMember(nag, member);

    nagLike.ifPresentOrElse(like -> decreaseNagLike(nag, like),
        () -> increaseNagLike(nag, member));

    return PostNagLikeResDto.ofStatusAboutMemberLikeNag(nagLike.isEmpty());
  }

  @Transactional
  public NagDto unlockNagPreviewByMemberTicket(Long nagId) {
    Nag nag = nagRepository.findById(nagId).orElseThrow(NagNotFoundException::new);
    Member member = securityUtil.getCurrentMemberByToken();

    if (nagUnlockRepository.existsByNagAndMember(nag, member)) {
      throw new NagUnlockBusinessException();
    } else if (member.getTicket() <= 0) {
      throw new NagUnlockBusinessException();
    }

    member.consumeTicketToUnlockNag();
    nagUnlockRepository.save(NagUnlock.ofUnlockPreviewByNagAndMember(nag, member));
    return NagDto.from(nag);
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

    Long nextCursor = nags.hasNext() ? nags.getContent().get(size - 1).getId() : null;

    return GetNagsOfOtherResDto.ofOtherNagsList(nags.getContent()
        .stream()
        .map(nag -> NagOfProfileDto.ofNagAndTagAndNagUnlock(nag, nag.getTag()))
        .collect(Collectors.toList()), nags.hasNext(), nextCursor);
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
    List<NagUnlock> nagUnlocks = nagUnlockRepository.findNagUnlocksByNagIsInAndMember(nags, member);

    NagBox nagBox = new NagBox(nags, nagUnlocks);

    return GetNagsOfNagBoxResDto.fromNagsOfNagBox(nagBox.getNags());
  }

  @Transactional(readOnly = true)
  public GetNagBoxStatisticsResDto getNagBoxStatisticsResDto() {
    Long totalMemberCount = memberRepository.count();
    Long totalDoneTodoCount = todoRepository.countTodosByFinishedIsTrue();
    Long totalNagsCount = nagRepository.count();

    return GetNagBoxStatisticsResDto.of(totalMemberCount, totalDoneTodoCount, totalNagsCount);
  }

  private void decreaseNagLike(Nag nag, NagLike nagLike) {
    nagLikeRepository.delete(nagLike);
    nag.decreaseLikeCount();
  }

  private void increaseNagLike(Nag nag, Member member) {
    nagLikeRepository.save(NagLike.builder().nag(nag).member(member).build());
    nag.increaseLikeCount();
  }
}
