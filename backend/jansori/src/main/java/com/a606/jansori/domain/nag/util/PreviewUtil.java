package com.a606.jansori.domain.nag.util;

import com.github.kimkevin.hangulparser.HangulParser;
import com.github.kimkevin.hangulparser.HangulParserException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.stereotype.Component;

@Component
public class PreviewUtil {

  // 초성 유니코드 시작 값과 종료 값
  private static final int KOREAN_SYLLABLES_START = 0xAC00;
  private static final int KOREAN_SYLLABLES_END = 0xD7A3;

  // 잔소리 한글, 영어 상관없이 초성으로 바꿔주는 메서드
  public String convertNagToPreview(String nag) {
    nag = convertVowelsToUnderscore(nag);
    return convertKoreanToInitialSound(nag);
  }

  // 한글인 경우 초성으로 변환하는 메서드
  private String convertKoreanToInitialSound(String nag) {
    StringBuilder result = new StringBuilder();

    for (int i = 0; i < nag.length(); i++) {
      char letter = nag.charAt(i);
      try {
        // 한글인 경우만 변환
        if (isKorean(letter)) {
          List<String> disassembles = HangulParser.disassemble(letter);
          result.append(disassembles.get(0));
        } else {
          // 한글이 아닌 경우 그대로 추가
          result.append(letter);
        }
      } catch (HangulParserException e) {
        throw new RuntimeException(e);
      }
    }
    return result.toString();
  }

  //주어진 문자가 한글인지 판별하는 메서드
  private boolean isKorean(char c) {
    return c >= KOREAN_SYLLABLES_START && c <= KOREAN_SYLLABLES_END;
  }

  //영어가 존재할때, 모은은 언더바로 변경해주는 메서드
  private String convertVowelsToUnderscore(String nag) {
    Pattern pattern = Pattern.compile("[b-df-hj-np-tv-zB-DF-HJ-NP-TV-Z]");
    Matcher matcher = pattern.matcher(nag);
    return matcher.replaceAll("_");
  }
}
