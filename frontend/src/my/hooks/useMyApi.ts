import { useApi } from "react-easy-api";
import type {
  PresignedUrlRequest,
  PresignedUrlResponse,
} from "../../auth/types/Auth";
import axiosClient from "../../lib/axiosClient";
import { useCallback } from "react";

export const useMyApi = () => {
  const {
    execute: preSignedUrlApi,
    loading: isPresignedUrl,
    error: presignedUrlError,
  } = useApi<PresignedUrlResponse, PresignedUrlRequest>({
    endpoint: "/users/profile-image/presigned-url",
    method: "POST",
    axiosInstance: axiosClient,
  });

  const preSignedUrl = useCallback(
    async (data: PresignedUrlRequest): Promise<PresignedUrlResponse> => {
      try {
        const response = await preSignedUrlApi(data);
        return response || { presignedUrl: "", fileUrl: "" };
      } catch (error) {
        console.log(error);
        throw error;
      }
    },
    [preSignedUrlApi],
  );

  return {
    preSignedUrl,
    isPresignedUrl,
    presignedUrlError,
  };
};
