import { useEffect, useRef, useState } from "react";
import { useAuthApi } from "./useAuthApi";
import { getAccessToken } from "../../lib/tokenStorage";

export const useAuthLogin = () => {
  const [isChecking, setIsChecking] = useState(true);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const { login } = useAuthApi();

  const triedRef = useRef(false);

  useEffect(() => {
    if (triedRef.current) return;
    triedRef.current = true;
    
    const checkAuthStatus = async () => {
      try {
        const access_token = getAccessToken();

        if (!access_token) {
          setIsLoggedIn(false);
          return;
        }

        if (access_token) {
          try {
            const payload = JSON.parse(atob(access_token.split(".")[1]));
            const currentTime = Math.floor(Date.now() / 1000);

            if (payload.exp > currentTime) {
              setIsLoggedIn(true);
              return;
            } else {
              console.log("토큰 만료");
            }
          } catch (e) {
            console.log("토큰 파싱 실패, 갱신 시도");
          }
        }

        try {
          await login();
          setIsLoggedIn(true);
        } catch (e) {
          setIsLoggedIn(false);
        }
      } catch (e) {
        setIsLoggedIn(false);
      } finally {
        setIsChecking(false);
      }
    };
    checkAuthStatus();
  }, [login]);

  return { isChecking, isLoggedIn };
};
