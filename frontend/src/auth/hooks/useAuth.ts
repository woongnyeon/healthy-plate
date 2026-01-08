import { useCallback } from "react";
import { useNavigate } from "react-router-dom";
import {
  useLoginMutation,
  useLogoutMutation,
  useSignupMutation,
  useOnBoardingMutation,
} from "./useAuthQuery";
import type { SignUpRequest } from "../types/Auth";
import { useQueryClient } from "@tanstack/react-query";
import { useAuthApi } from "./useAuthApi";
import { clearTokens } from "../../lib/tokenStorage";

export const useAuth = () => {
  const navigate = useNavigate();
  const loginMutation = useLoginMutation();
  const signupMutation = useSignupMutation();
  const onBoardingMutation = useOnBoardingMutation();
  const logoutMutation = useLogoutMutation();
  const queryClient = useQueryClient();
  const { userInfo } = useAuthApi();

  const isLoading = 
    loginMutation.isPending || 
    signupMutation.isPending || 
    onBoardingMutation.isPending || 
    logoutMutation.isPending;

  const handleLogin = useCallback(async () => {
    await loginMutation.mutateAsync();
    const userData = await queryClient.fetchQuery({
      queryKey: ["userInfo"],
      queryFn: () => userInfo(),
    });
    return userData;
  }, [loginMutation, queryClient, userInfo]);

  const handleOnBoarding = useCallback(async () => {
    await onBoardingMutation.mutateAsync();
    const userData = await queryClient.fetchQuery({
      queryKey: ["userInfo"],
      queryFn: () => userInfo(),
    });
    window.dispatchEvent(new Event("storage"));
    return userData;
  }, [onBoardingMutation, queryClient, userInfo]);

  const handleSignup = useCallback((data: SignUpRequest) => {
    signupMutation.mutate(data, {
      onSuccess: async () => {
        await queryClient.invalidateQueries({ queryKey: ["userInfo"] });
        window.dispatchEvent(new Event("storage"));
        navigate("/", { replace: true });
      },
    });
  }, [signupMutation, queryClient, navigate]);

  const handleLogout = useCallback(() => {
    logoutMutation.mutate(undefined, {
      onSuccess: () => {
        clearTokens();
        queryClient.clear();
        window.dispatchEvent(new Event("storage"));
        navigate("/login", { replace: true });
      },
    });
  }, [logoutMutation, queryClient, navigate]);

  const getUserInfo = useCallback(() => {
    return queryClient.getQueryData(["userInfo"]);
  }, [queryClient]);

  return {
    handleLogin,
    handleSignup,
    handleLogout,
    handleOnBoarding,
    getUserInfo,
    isLoading
  };
};
