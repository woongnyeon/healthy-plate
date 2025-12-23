import { useNavigate } from "react-router-dom";
import {
  useLoginMutation,
  useLogoutMutation,
  useSignupMutation,
} from "./useAuthQuery";
import type { SignUpRequest } from "../types/Auth";
import { useQueryClient } from "@tanstack/react-query";

export const useAuth = () => {
  const navigate = useNavigate();
  const loginMutation = useLoginMutation();
  const signupMutation = useSignupMutation();
  const logoutMutation = useLogoutMutation();
  const queryClient = useQueryClient();

  const handleLogin = () => {
    loginMutation.mutate(undefined, {
      onSuccess: () => {
        queryClient.invalidateQueries({ queryKey: ["userInfo"] });
        navigate("/");
      },
    });
  };

  const handeSignup = (data: SignUpRequest) => {
    signupMutation.mutate(data, {
      onSuccess: () => {
        queryClient.setQueryData(["userInfo"], null);
        queryClient.removeQueries({ queryKey: ["userInfo"] });
        navigate("/");
      },
    });
  };

  const handleLogout = () => {
    logoutMutation.mutate(undefined, {
      onSuccess: () => {
        queryClient.clear();
        navigate("/");
      },
    });
  };

  return {
    handleLogin,
    handeSignup,
    handleLogout,
  };
};
