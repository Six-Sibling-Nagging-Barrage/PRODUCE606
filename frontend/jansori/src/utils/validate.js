export const validateNickname = (value) => {
  if (/\s{2,}|^\s|\s$/.test(value)) {
    return '연속된 공백 또는 앞뒤 공백은 사용할 수 없어요.';
  }
  if (!/^[a-zA-Z0-9_\s가-힣]+$/.test(value)) {
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

export const validateEmail = (value) => {
  const regex = new RegExp(
    "([!#-'*+/-9=?A-Z^-~-]+(.[!#-'*+/-9=?A-Z^-~-]+)*|\"([]!#-[^-~ \t]|(\\[\t -~]))+\")@([!#-'*+/-9=?A-Z^-~-]+(.[!#-'*+/-9=?A-Z^-~-]+)*|[[\t -Z^-~]*])"
  );
  if (regex.test(value)) {
    return true;
  }
  return '올바른 이메일 형식을 입력해주세요.';
};

export const validatePassword = (value) => {
  const regex = /^(?=.*[a-z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,16}$/;
  if (regex.test(value)) {
    return true;
  }
  return '비밀번호는 8~16자의 길이로, 영문, 숫자, 특수문자를 포함하여 만들어주세요.';
};
