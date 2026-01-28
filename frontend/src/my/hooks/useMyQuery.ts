import { useMutation } from "@tanstack/react-query";
import type { PresignedUrlRequest } from "../../auth/types/Auth";
import { useMyApi } from "./useMyApi";

export const usePreSignedUrlMutation = () => {
  const { preSignedUrl } = useMyApi();
  return useMutation({
    mutationFn: async (data: PresignedUrlRequest) => {
      const result = await preSignedUrl(data);
      return result;
    },
    onSuccess: (data) => {
      console.log("프로필 이미지 URL 생성 성공", data);
    },
    onError: (error) => {
      console.log("프로필 이미지 URL 생성 실패", error);
    },
  });
};
