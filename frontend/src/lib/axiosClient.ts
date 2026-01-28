import axios, { type AxiosInstance } from "axios";
import { clearTokens, updateAccessToken, getAccessToken } from "./tokenStorage";
import type { LoginResponse } from "../auth/types/Auth";

const axiosClient = axios.create({
  baseURL: "/api",
  withCredentials: true,
});

console.log(axiosClient.defaults.baseURL);

const authFreeEndpoints = [
  "/auth/reissue",
  "/auth/token",
  "/users/duplicate",
  "/oauth2",
  "/login",
  "/auth/onboarding",
];

const setupInterceptors = (client: AxiosInstance, skipAuth = false) => {
  client.interceptors.request.use((config) => {
  const isFree = authFreeEndpoints.some((endpoint) =>
    (config.url ?? "").includes(endpoint)
  );

  if (!skipAuth && !isFree) {
    const accessToken = getAccessToken();
    if (accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`;
    }
  }
  return config;
});


  client.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;
    const isOnboarding = (originalRequest.url ?? "").includes("/auth/onboarding");

    if (
      error.response?.status === 401 &&
      !originalRequest._retry &&
      !skipAuth &&
      !isOnboarding
    ) {
      originalRequest._retry = true;

      try {
        const refreshResponse = await axiosClient.post<LoginResponse>("/auth/token");
        // 인터셉터가 다시 전체 응답을 반환하므로 .data.access_token으로 접근합니다.
        const newAccessToken = refreshResponse.data.access_token;

        updateAccessToken(newAccessToken);
        originalRequest.headers.Authorization = `Bearer ${newAccessToken}`;
        return client(originalRequest);
      } catch {
        clearTokens();
        window.location.href = "/login";
      }
    }

    return Promise.reject(error);
  }
);

};

setupInterceptors(axiosClient);

export const apiRequest = {
  default: axiosClient,
};

export default axiosClient;
