import { useEffect, useState, useCallback } from "react";
import { getAccessToken } from "../../lib/tokenStorage";

/**
 * 전역적인 로그인 상태를 관리하는 훅입니다.
 * 토큰의 존재 여부와 만료 시간을 체크하여 실시간으로 isLoggedIn 상태를 반환합니다.
 */
export const useAuthLogin = () => {
  const [isChecking, setIsChecking] = useState(true);
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  // 로컬 스토리지에서 토큰을 읽어 로그인 여부를 판단하는 함수
  const checkAuth = useCallback(() => {
    try {
      const token = getAccessToken();
      
      // 토큰이 없으면 로그인 안 됨
      if (!token) {
        setIsLoggedIn(false);
        setIsChecking(false);
        return;
      }

      // JWT 토큰 페이로드 파싱하여 만료 시간 체크
      const payload = JSON.parse(atob(token.split(".")[1]));
      const currentTime = Math.floor(Date.now() / 1000);

      // 만료되지 않았으면 로그인 성공
      setIsLoggedIn(payload.exp > currentTime);
    } catch {
      setIsLoggedIn(false);
    } finally {
      setIsChecking(false);
    }
  }, []);

  useEffect(() => {
    // 앱 시작 시 첫 체크
    checkAuth();

    // ✅ 다른 로직이나 다른 탭에서의 로그아웃/로그인을 감지하기 위해 storage 이벤트 등록
    window.addEventListener("storage", checkAuth);
    return () => window.removeEventListener("storage", checkAuth);
  }, [checkAuth]);

  return { isChecking, isLoggedIn };
};
