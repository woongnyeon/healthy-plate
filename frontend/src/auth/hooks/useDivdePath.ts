import { useEffect, useRef } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { useAuth } from "./useAuth";
import { clearTokens } from "../../lib/tokenStorage";

export const useDivdePath = () => {
  const [params] = useSearchParams();
  const navigate = useNavigate();
  const { handleOnBoarding, handleLogin } = useAuth();
  const isFirstLoginParam = params.get("isFirstLogin"); 
  const isRunning = useRef(false);

  useEffect(() => {
    if (!isFirstLoginParam || isRunning.current) return;

    const run = async () => {
      isRunning.current = true;
      // ✅ 새로운 로그인/온보딩 프로세스 시작 시 기존 토큰 초기화 (MalformedJwtException 방지)
      clearTokens();
      
      try {
        if (isFirstLoginParam === "true") {
          const me = await handleOnBoarding();
          console.log("[useDivdePath] handleOnBoarding result:", me);

          if (!me?.profile?.nickname) {
            console.log("[useDivdePath] Nickname missing, navigating to /register");
            navigate("/register", { replace: true });
          } else {
            console.log("[useDivdePath] Nickname exists, navigating to /");
            navigate("/", { replace: true });
          }
        }

        if (isFirstLoginParam === "false") {
          await handleLogin();
          navigate("/", { replace: true });
        }
      } catch (error) {
        console.error("Path division failed:", error);
        isRunning.current = false;
      }
    };

    run();
  }, [isFirstLoginParam, handleOnBoarding, handleLogin, navigate]);
};