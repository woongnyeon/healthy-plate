export const MAX_DESC = 100;

export const validateNickname = (nickname: string) => {
  const trimmed = nickname.trim();
  const ok = trimmed.length >= 2 && trimmed.length <= 12;

  return {
    ok,
    message: ok ? "" : "닉네임은 2~12자로 입력해주세요.",
    value: trimmed,
  };
};

export const validateDescription = (desc: string) => {
  const trimmed = desc.trim();
  const ok = desc.length <= MAX_DESC;

  return {
    ok,
    message: ok ? "" : `${MAX_DESC}자 이내로 작성해주세요.`,
    value: trimmed,
  };
};
