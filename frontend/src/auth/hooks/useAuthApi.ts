import { useApi } from "react-easy-api";
import {
  type UserInfo,
  type LoginResponse,
  type SignUpRequest,
  type SignUpResponse,
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

  const login = async (): Promise<LoginResponse> => {
    try {
      const response = await loginApi();

      if (!response) {
        throw new Error("로그인 응답이 잘못되었습니다.");
      }
      setTokens(response?.accessToken);

      return response;
    } catch (error) {
      console.log(error);
      throw error;
    }
  };

  const signUp = async (data: SignUpRequest): Promise<SignUpResponse> => {
    try {
      const response = await signUpApi(data);

      if (!response) {
        // ❗️ 정상 응답이 없으면 실패로 간주
        throw new Error("회원가입 응답이 비어 있습니다.");
      }

      // 필요하면 여기서 토큰 저장도 가능
      setTokens(response.accessToken);

      return response;
    } catch (error) {
      console.error("회원가입 실패:", error);
      throw error; // ❗️ 반드시 다시 던져야 상위에서 처리 가능
    }
  };

  const logout = async () => {
    try {
      const result = await logoutApi();
      clearTokens();
      return result;
    } catch (error) {
      console.log(error);
      throw error;
    }
  };

  const userInfo = async () => {
    try {
      const result = await userInfoApi();
      return result;
    } catch (error) {
      console.log(error);
      throw error;
    }
  };

  const checkNickname = async (nickname: string): Promise<boolean> => {
    const { data } = await axiosClient.get<boolean>(
      `/users/duplicate/${nickname}`
    );
    return data; // boolean
  };

  return {
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
  };
};
