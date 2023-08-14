package com.a606.jansori.domain.nag.domain;

import com.a606.jansori.domain.nag.dto.NagOfProfile;
import java.util.List;
import java.util.stream.Collectors;

public class Nags {

  List<NagOfProfile> nags;

  public Nags(List<Nag> nags, List<NagUnlock> nagUnlocks, List<NagLike> nagLikes) {
    makeMemberProfileNags(nags);
    matchUnLockStatus(nagUnlocks);
    makeIsLikedStatus(nagLikes);
  }

  public List<NagOfProfile> getNags() {
    return nags;
  }

  private void makeMemberProfileNags(List<Nag> nags) {
    this.nags = nags.stream().map(NagOfProfile::ofOtherNagsInformation).collect(Collectors.toList());
  }

  private void matchUnLockStatus(List<NagUnlock> nagUnlocks) {
    nagUnlocks.forEach(nagUnlock -> {
      changeLockStatusByNagId(nagUnlock.getNag().getId());
    });
  }

  private void makeIsLikedStatus(List<NagLike> nagLikes) {
    nagLikes.forEach(nagLike -> {
      changeIsLikedStatusByNagId(nagLike.getNag().getId());
    });
  }

  private void changeIsLikedStatusByNagId(Long nagId) {
    this.nags.forEach(nagOfProfile -> {
      if (nagOfProfile.getNagId().equals(nagId)) {
        nagOfProfile.changeIsLikedStatus();
      }
    });
  }

  private void changeLockStatusByNagId(Long nagId) {
    this.nags.forEach(nagOfProfile -> {
      if (nagOfProfile.getNagId().equals(nagId)) {
        nagOfProfile.changeUnlocked();
      }
    });
  }
}
