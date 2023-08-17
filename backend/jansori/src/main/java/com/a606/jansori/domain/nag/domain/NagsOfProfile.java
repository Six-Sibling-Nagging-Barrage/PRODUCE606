package com.a606.jansori.domain.nag.domain;

import com.a606.jansori.domain.nag.dto.NagOfProfile;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class NagsOfProfile {

  List<NagOfProfile> nagOfProfiles;

  public NagsOfProfile(List<Nag> nags, List<NagInteraction> nagInteractions) {
    makeNagOfProfile(nags);
    matchNagUnlockWithLikeStatus(nagInteractions);
  }

  private void makeNagOfProfile(List<Nag> nags) {
    nagOfProfiles = nags.stream()
        .map(NagOfProfile::from)
        .collect(Collectors.toList());
  }

  private void matchNagUnlockWithLikeStatus(List<NagInteraction> nagInteractions) {
    nagInteractions.forEach(this::changeNagUnlockWithLikeStatus);
  }

  private void changeNagUnlockWithLikeStatus(NagInteraction nagInteraction) {
    nagOfProfiles.forEach(nagOfProfile -> {
      if (nagOfProfile.getNagId().equals(nagInteraction.getNag().getId())) {
        nagOfProfile.changeStatus(nagInteraction);
      }
    });
  }
}
