export interface LoginResponse {
  access_token: string;
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
  profileImageUrl?: string | null;
  nickname: string;
  introduction?: string | null;
}

export interface SignUpResponse extends LoginResponse {}

export interface PresignedUrlRequest {
  contentType: string;
  fileSize: number;
}

export interface PresignedUrlResponse {
  presignedUrl: string;
  fileUrl: string;
}
