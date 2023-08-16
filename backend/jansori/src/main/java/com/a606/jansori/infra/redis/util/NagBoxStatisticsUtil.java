package com.a606.jansori.infra.redis.util;

import com.a606.jansori.domain.nag.dto.GetNagBoxStatisticsResDto;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NagBoxStatisticsUtil {

  private final RedisTemplate<String, Object> redisTemplate;
  private final int TTL = 14;
  private final String KEY = "NAG_BOX_STATISTICS";
  private final String TOTAL_MEMBER_COUNT = "MEMBER_COUNT";
  private final String TOTAL_DONE_TODO_COUNT = "TODO_COUNT";
  private final String TOTAL_NAGS_COUNT = "NAG_COUNT";

  public GetNagBoxStatisticsResDto cachingNagBoxStatistics(
      GetNagBoxStatisticsResDto getNagBoxStatisticsResDto) {
    HashOperations<String, String, Long> nagBoxStatistics = redisTemplate.opsForHash();
    Map<String, Long> nagBoxStatisticsMap = new HashMap<>();

    nagBoxStatisticsMap.put(TOTAL_MEMBER_COUNT,
        getNagBoxStatisticsResDto.getTotalMemberCount());
    nagBoxStatisticsMap.put(TOTAL_DONE_TODO_COUNT,
        getNagBoxStatisticsResDto.getTotalDoneTodoCount());
    nagBoxStatisticsMap.put(TOTAL_NAGS_COUNT,
        getNagBoxStatisticsResDto.getTotalNagsCount());

    nagBoxStatistics.putAll(KEY, nagBoxStatisticsMap);
    
    //TTL 설정
    redisTemplate.expire(KEY, Duration.ofDays(TTL));
    
    return getNagBoxStatisticsResDto();
  }

  public boolean hasNagBoxStatistics() {
    return redisTemplate.opsForHash().hasKey(KEY, TOTAL_NAGS_COUNT);
  }

  public GetNagBoxStatisticsResDto getNagBoxStatisticsResDto() {
    return GetNagBoxStatisticsResDto.of(
        (Long) redisTemplate.opsForHash().get(KEY, TOTAL_MEMBER_COUNT),
        (Long) redisTemplate.opsForHash().get(KEY, TOTAL_DONE_TODO_COUNT),
        (Long) redisTemplate.opsForHash().get(KEY, TOTAL_NAGS_COUNT)
    );
  }
}
