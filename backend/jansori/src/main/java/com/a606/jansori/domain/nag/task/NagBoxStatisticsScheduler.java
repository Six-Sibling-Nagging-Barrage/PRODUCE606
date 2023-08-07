package com.a606.jansori.domain.nag.task;

import com.a606.jansori.domain.nag.dto.GetNagBoxStatisticsResDto;
import com.a606.jansori.domain.nag.service.NagService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class NagBoxStatisticsScheduler {

  private final Map<String, GetNagBoxStatisticsResDto> nagBoxStatisticsResDtoMap;
  private final NagService nagService;
  private final String KEY_VALUE = "statistics";

  public NagBoxStatisticsScheduler(NagService nagService) {
    nagBoxStatisticsResDtoMap = new HashMap<>();
    this.nagService = nagService;
    nagBoxStatisticsResDtoMap.put(KEY_VALUE, this.nagService.getNagBoxStatisticsResDto());
  }

  @Scheduled(cron = "0 0 6 * * *")
  public void updateNagBoxStatistics() {
    nagBoxStatisticsResDtoMap.put(KEY_VALUE, nagService.getNagBoxStatisticsResDto());
  }

  public GetNagBoxStatisticsResDto getNagBoxStatisticsResDto() {
    return nagBoxStatisticsResDtoMap.get(KEY_VALUE);
  }
}
