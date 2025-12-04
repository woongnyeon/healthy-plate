import axios, { type AxiosInstance } from "axios";
import {
  clearTokens,
  updateAccessToken,
  getAccessToken,
  getRefreshToken,
} from "./tokenStorage";

const axiosClent = axios.create({
  baseURL: "",
});

const authFreeEndpoints = [""];

const setupInterceptors = (
  client: AxiosInstance,
  serverName: string,
  skipAuth = false
) => {
  client.interceptors.request.use((config) => {
    if (
      !skipAuth &&
      !authFreeEndpoints.some((endpoint) => config.url?.includes(endpoint))
    ) {
      const accessToken = getAccessToken();

      if (accessToken) {
        config.headers.Authorization = `Beare ${accessToken}`;
      }
    }
    return config;
  });

  client.interceptors.response.use(
    (response) => {
      return response;
    },
    async (error) => {
      const originalRequest = error.config;

      if (
        error.response?.status === 401 &&
        !originalRequest._retry &&
        !skipAuth
      ) {
        originalRequest._retry = true;

        try {
          const refreshToken = getRefreshToken();

          if (refreshToken) {
            const refreshResponse = await axiosClent.post("", { refreshToken });

            const newAccessToken = refreshResponse.data.accessToken;

            updateAccessToken(newAccessToken);

            originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;

            return client(originalRequest);
          }
        } catch (refreshError) {
          console.error("토큰 갱신 실패", refreshError);
          clearTokens();

          if (typeof window !== "undefined") {
            window.location.href = "";
          }
        }
      }
      return Promise.reject(error);
    }
  );
};

setupInterceptors(axiosClent, "Spring");

export const apiRequest = {
  default: axiosClent,
};

export default axiosClent;
