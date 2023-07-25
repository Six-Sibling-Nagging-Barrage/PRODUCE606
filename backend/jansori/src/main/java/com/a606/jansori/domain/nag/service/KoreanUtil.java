package com.a606.jansori.domain.nag.service;

public class KoreanUtil {
    // 초성 유니코드 시작 값과 종료 값
    private static final int KOREAN_SYLLABLES_START = 0xAC00;
    private static final int KOREAN_SYLLABLES_END = 0xD7A3;

    // 초성의 시작 값
    private static final int KOREAN_INITIAL_SOUND_START = 0x1100;

    // 초성으로 변환하는 메서드
    public static String convertToInitialSound(String input) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);

            // 한글인 경우만 변환
            if (isKorean(c)) {
                int code = (int) c;
                int initialSoundCode = KOREAN_INITIAL_SOUND_START + ((code - KOREAN_SYLLABLES_START) / 28) / 21;
                char initialSoundChar = (char) initialSoundCode;
                result.append(initialSoundChar);
            } else {
                // 한글이 아닌 경우 그대로 추가
                result.append(c);
            }
        }

        return result.toString();
    }

    // 주어진 문자가 한글인지 판별하는 메서드
    private static boolean isKorean(char c) {
        return c >= KOREAN_SYLLABLES_START && c <= KOREAN_SYLLABLES_END;
    }
}
