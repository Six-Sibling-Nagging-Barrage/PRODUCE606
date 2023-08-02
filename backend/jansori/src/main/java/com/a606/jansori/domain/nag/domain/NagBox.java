package com.a606.jansori.domain.nag.domain;

import com.a606.jansori.domain.nag.dto.NagOfNagBox;
import java.util.List;
import java.util.stream.Collectors;

public class NagBox {

  private List<NagOfNagBox> nags;

  public NagBox(List<Nag> nags, List<NagUnlock> nagUnlocks) {
    makeTopRanksNags(nags);
  }

  public List<NagOfNagBox> getNags() {
    return nags;
  }

  private void makeTopRanksNags(List<Nag> nags) {
    this.nags = nags.stream().map(NagOfNagBox::fromNag).collect(Collectors.toList());
  }
}
