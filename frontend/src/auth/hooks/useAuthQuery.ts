import { useMutation, useQuery } from "@tanstack/react-query";
import { useAuthApi } from "./useAuthApi";
import type { SignUpRequest } from "../types/Auth";
import { getAccessToken } from "../../lib/tokenStorage";

export const useLoginMutation = () => {
  const { login } = useAuthApi();

  return useMutation({
    mutationFn: async () => {
      const result = await login();
      return result;
    },
    onSuccess: () => {
      console.log("로그인 성공");
    },
    onError: (error) => {
      console.log("로그인 실패", error);
    },
  });
};

export const useSignupMutation = () => {
  const { signUp } = useAuthApi();

  return useMutation({
    mutationFn: async (data: SignUpRequest) => {
      const result = await signUp(data);
      return result;
    },
    onSuccess: (data) => {
      console.log("회원가입 성공", data);
    },
    onError: (error) => {
      console.log("회원가입 실패", error);
    },
  });
};

export const useLogoutMutation = () => {
  const { logout } = useAuthApi();

  return useMutation({
    mutationFn: async () => {
      const result = await logout();
      return result;
    },
    onSuccess: () => {},
  });
};

export const useGetUserInfo = () => {
  const access_token = getAccessToken();
  const { userInfo } = useAuthApi();
  return useQuery({
    queryKey: ["userInfo"],
    enabled: !!access_token,
    queryFn: async () => {
      const result = await userInfo();
      return result;
    },
  });
};
