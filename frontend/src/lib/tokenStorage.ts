/**
 * 토큰 저장 관리 유틸리티
 * rememberMe 옵션에 따라 localStorage 또는 sessionStorage 사용
 */

const TOKEN_KEYS = {
  ACCESS_TOKEN: "accessToken",
} as const;

/**
 * 토큰을 저장합니다
 */
export const setTokens = (accessToken: string) => {
  const storage = localStorage;

  storage.setItem(TOKEN_KEYS.ACCESS_TOKEN, accessToken);
};

/**
 * 액세스 토큰을 가져옵니다
 */
export const getAccessToken = (): string | null => {
  // localStorage와 sessionStorage 둘 다 확인
  return (
    localStorage.getItem(TOKEN_KEYS.ACCESS_TOKEN) ||
    sessionStorage.getItem(TOKEN_KEYS.ACCESS_TOKEN)
  );
};

/**
 * 액세스 토큰을 업데이트합니다 (갱신 시 사용)
 */
export const updateAccessToken = (accessToken: string) => {
  const storage = localStorage;

  storage.setItem(TOKEN_KEYS.ACCESS_TOKEN, accessToken);
};

/**
 * 모든 토큰을 제거합니다
 */
export const clearTokens = () => {
  // 양쪽 스토리지에서 모두 제거
  localStorage.removeItem(TOKEN_KEYS.ACCESS_TOKEN);
};

/**
 * 토큰이 존재하는지 확인합니다
 */
export const hasTokens = (): boolean => {
  return !!getAccessToken();
};
