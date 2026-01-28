import { useCallback, useMemo } from "react";
import { useApi } from "react-easy-api";
import {
  type UserInfo,
  type LoginResponse,
  type SignUpRequest,
  type SignUpResponse,
  type PresignedUrlRequest,
  type PresignedUrlResponse,
} from "../types/Auth";
import axiosClient from "../../lib/axiosClient";
import { clearTokens, setTokens } from "../../lib/tokenStorage";

export const useAuthApi = () => {
  const {
    execute: signUpApi,
    loading: isSignup,
    error: signUpError,
  } = useApi<SignUpResponse, SignUpRequest>({
    endpoint: "/auth/register",
    method: "PATCH",
    axiosInstance: axiosClient,
  });

  const {
    execute: preSignedUrlApi,
    loading: isPresignedUrl,
    error: presignedUrlError,
  } = useApi<PresignedUrlResponse, PresignedUrlRequest>({
    endpoint: "/auth/profile-image/presigned-url",
    method: "POST",
    axiosInstance: axiosClient,
  });

  const {
    execute: loginApi,
    loading: isLogin,
    error: loginError,
  } = useApi<LoginResponse, void>({
    endpoint: "/auth/token",
    method: "POST",
    axiosInstance: axiosClient,
  });

  const {
    execute: logoutApi,
    loading: isLogout,
    error: logoutError,
  } = useApi<string, void>({
    endpoint: "/auth/logout",
    method: "POST",
    axiosInstance: axiosClient,
  });

  const {
    execute: userInfoApi,
    loading: isGetUserInfo,
    error: getUserInfoError,
  } = useApi<UserInfo, void>({
    endpoint: "/users",
    method: "GET",
    axiosInstance: axiosClient,
  });

  const {
    execute: onBoardingApi,
    loading: isOnBoarding,
    error: onBoardingError,
  } = useApi<LoginResponse, void>({
    endpoint:"/auth/onboarding",
    method: "POST",
    axiosInstance: axiosClient,
  })

  const login = useCallback(async (): Promise<LoginResponse> => {
    try {
      const response = await loginApi();
      if (!response || !response.access_token) {
        throw new Error("로그인 응답이 잘못되었습니다.");
      }
      setTokens(response.access_token);
      return response;
    } catch (error) {
      console.log(error);
      throw error;
    }
  }, [loginApi]);

  const signUp = useCallback(async (data: SignUpRequest): Promise<SignUpResponse> => {
    try {
      const response = await signUpApi(data);
      
      // ✅ 서버 응답이 비어있어도 요청 자체가 성공(2xx)이면 진행합니다.
      // (PATCH /auth/register 는 프로필 업데이트만 하고 토큰을 안 줄 수 있음)
      if (response?.access_token) {
        setTokens(response.access_token);
      }
      
      return response as SignUpResponse;
    } catch (error) {
      console.error("회원가입 실패:", error);
      throw error;
    }
  }, [signUpApi]);

  const logout = useCallback(async () => {
    try {
      await logoutApi();
      clearTokens();
    } catch (error) {
      console.log(error);
      throw error;
    }
  }, [logoutApi]);

  const userInfo = useCallback(async () => {
    try {
      const result = await userInfoApi();
      return result;
    } catch (error) {
      console.log(error);
      throw error;
    }
  }, [userInfoApi]);

  const checkNickname = useCallback(async (nickname: string): Promise<boolean> => {
    const { data } = await axiosClient.get<boolean>(
      `/users/duplicate/${nickname}`
    );
    return data;
  }, []);

  const preSignedUrl = useCallback(async (data: PresignedUrlRequest): Promise<PresignedUrlResponse> => {
    try {
      const response = await preSignedUrlApi(data);
      return response || { presignedUrl: "", fileUrl: ""};
    } catch (error) {
      console.log(error);
      throw error;
    }
  }, [preSignedUrlApi]);

  const onBoarding = useCallback(async (): Promise<LoginResponse> => {
    try {
      const response = await onBoardingApi();
      console.log("[AuthApi] OnBoarding Response:", response);
      
      if (!response || !response.access_token) {
        console.error("[AuthApi] Access token missing in response:", response);
        throw new Error ("엑세스 토큰을 받을 수 없습니다.")
      }
      
      setTokens(response.access_token);
      return response;
    } catch (error) {
      console.error("[AuthApi] OnBoarding failed:", error);
      throw error;
    }
  }, [onBoardingApi]);

  return useMemo(() => ({
    signUp,
    isSignup,
    signUpError,

    login,
    isLogin,
    loginError,

    logout,
    isLogout,
    logoutError,

    checkNickname,

    userInfo,
    isGetUserInfo,
    getUserInfoError,

    preSignedUrl,
    isPresignedUrl,
    presignedUrlError,

    onBoarding,
    isOnBoarding,
    onBoardingError
  }), [
    signUp, isSignup, signUpError,
    login, isLogin, loginError,
    logout, isLogout, logoutError,
    checkNickname,
    userInfo, isGetUserInfo, getUserInfoError,
    preSignedUrl, isPresignedUrl, presignedUrlError,
    onBoarding, isOnBoarding, onBoardingError
  ]);
};
