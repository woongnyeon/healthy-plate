export interface LoginResponse {
  accessToken: string;
  user: userInfo;
}

export interface userInfo {
  id: number;
  email: string;
  nickname: string;
  profileImageUrl: string;
  provider: string;
}

export interface SignUpRequest {
  profileImage?: File | null;
  nickname: string;
  introduction?: string | null;
}

export interface SignUpResponse extends LoginResponse {}
