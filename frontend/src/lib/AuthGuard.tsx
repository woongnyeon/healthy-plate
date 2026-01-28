import { useLocation, Navigate } from "react-router-dom";
import { useAuthLogin } from "../auth/hooks/useAuthLogin";
import { useGetUserInfo } from "../auth/hooks/useAuthQuery";
import { getAccessToken } from "../lib/tokenStorage";

/**
 * 전역 인증 가드 컴포넌트
 * 로그인 상태와 프로필(닉네임) 유무에 따라 사용자의 접근을 제어합니다.
 */
export const AuthGuard = ({ children }: { children: React.ReactNode }) => {
  const location = useLocation();
  const { pathname, search } = location;

  // 현재 로그인 상태와 토큰 확인
  const { isChecking: isAuthChecking, isLoggedIn } = useAuthLogin();
  const token = getAccessToken();
  
  // 로그인 상태이고 토큰이 있을 때만 유저 정보를 가져옴
  const { data: userInfo, isLoading: isUserLoading } = useGetUserInfo(isLoggedIn && !!token);
  
  const params = new URLSearchParams(search);
  const isFirstLogin = params.get("isFirstLogin") === "true";

  // 처음 로그인(isFirstLogin)할 때 잘못된 토큰이 남아있으면 제거 (백엔드 에러 방지)
  if (pathname.startsWith("/login") && isFirstLogin) {
    if (token && (token === "undefined" || token === "null")) {
      localStorage.removeItem("access_token");
    }
  }

  // 인증 확인 중에는 로딩 표시
  if (isAuthChecking) return <div>인증 확인중...</div>;

  // 1️⃣ 소셜 로그인 후 돌아오는 경로는 가드 제외 (OAuth 처리용)
  if (pathname.startsWith("/login") && isFirstLogin) {
    return <>{children}</>;
  }

  // 2️⃣ 회원가입(/register) 페이지 접근 제어
  if (pathname.startsWith("/register")) {
    // 로그인조차 안 했으면 로그인 페이지로
    if (!isLoggedIn || !token) return <Navigate to="/login" replace />;
    
    // 유저 정보 로딩 중이면 대기
    if (isUserLoading) return <div>유저 정보 확인 중...</div>;
    
    // 이미 프로필(닉네임)이 있는 가입자라면 메인으로 쫓아냄
    if (userInfo?.profile) {
      return <Navigate to="/" replace />;
    }
    return <>{children}</>;
  }

  // 3️⃣ 전역 프로필 가드: 로그인됐는데 프로필이 없다면 무조건 가입 페이지로 강제 이동
  if (isLoggedIn && !!token && !isUserLoading && !userInfo?.profile) {
    return <Navigate to="/register" replace />;
  }

  // 4️⃣ 보호 라우트 (/my, /edit 등) 접근 시 로그인 체크
  if (pathname.startsWith("/my") || pathname.startsWith("/edit")) {
    if (!isLoggedIn || !token) {
      return <Navigate to={{ pathname: "/login", search }} replace />;
    }
  }

  // 5️⃣ 이미 로그인된 사용자가 일반 로그인 페이지 접근 시 메인으로 이동
  if (pathname.startsWith("/login") && isLoggedIn && token && !isFirstLogin) {
    return <Navigate to="/" replace />;
  }

  return <>{children}</>;
};
