export interface LoginResponse {
  accessToken: string;
  user: UserInfo;
}

export interface UserInfo {
  id: number;
  email: string;
  profile: Profile;
  provider: string;
}

export interface Profile {
  introduction: string;
  nickname: string;
  profileImageUrl: string;
}

export interface SignUpRequest {
  profileImage?: File | null;
  nickname: string;
  introduction?: string | null;
}

export interface SignUpResponse extends LoginResponse {}
