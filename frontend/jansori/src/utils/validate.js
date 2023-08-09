export const validateNickname = (value) => {
  if (/\s{2,}|^\s|\s$/.test(value)) {
    return '연속된 공백 또는 앞뒤 공백은 사용할 수 없어요.';
  }
  if (!/^[a-zA-Z0-9_\sㄱ-ㅎㅏ-ㅣ가-힣]+$/.test(value)) {
    return '한글, 영문, 숫자, _, 공백만 사용할 수 있어요.';
  }
  return true;
};

export const validateBio = (value) => {
  if (/\s{2,}/.test(value)) {
    return '연속된 공백을 사용할 수 없어요.';
  }
  return true;
};
