package com.a606.jansori.domain.nag.domain;

import com.a606.jansori.domain.nag.dto.NagOfNagBox;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class NagsOfNagBox {

  List<NagOfNagBox> nagOfNagBoxes;

  public NagsOfNagBox(List<Nag> nags, List<NagInteraction> nagInteractions) {
    makeNagOfNagBox(nags);
    matchNagUnlockWithLikeStatus(nagInteractions);
  }

  private void makeNagOfNagBox(List<Nag> nags) {
    nagOfNagBoxes = nags.stream().map(NagOfNagBox::from).collect(Collectors.toList());
  }

  private void matchNagUnlockWithLikeStatus(List<NagInteraction> nagInteractions) {
    nagInteractions.forEach(this::changeNagUnlockWithLikeStatus);
  }

  private void changeNagUnlockWithLikeStatus(NagInteraction nagInteraction) {
    nagOfNagBoxes.forEach(nagOfNagBox -> {
      if (nagOfNagBox.getNagId().equals(nagInteraction.getNag().getId())) {
        nagOfNagBox.changeStatus(nagInteraction);
      }
    });
  }
}
