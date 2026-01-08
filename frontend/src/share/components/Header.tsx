import { Link } from "react-router-dom";
import { useGetUserInfo } from "../../auth/hooks/useAuthQuery";
import { useAuth } from "../../auth/hooks/useAuth";
import { useProfileImage } from "../../auth/hooks/useProfileImage";
import { getAccessToken } from "../../lib/tokenStorage";

export const Header = () => {
  const accessToken = getAccessToken();
  const { data: userInfo } = useGetUserInfo(!!accessToken);
  const { handleLogout } = useAuth();
  const { fileRef, previewUrl, pick, onChange: onFileChange, upload, isUploading, uploadError, uploadedUrl } = useProfileImage();
  const handleImageUpload = async () => {
    const url = await upload();
    if (url) {
      alert(`이미지 업로드 성공!\nURL: ${url}`);
    } else if (uploadError) {
      alert(`업로드 실패: ${uploadError}`);
    }
  }
  const isActive = !!userInfo;

  return (
    <header className="fixed top-0 left-0 w-full z-50 bg-white">
      <div className="h-14 flex items-center justify-between px-4 md:px-10 lg:px-[90px]">
        <img src="/assets/logo.png" alt="logo" className="h-6" />

        <div className="flex items-center gap-4">
          <input
            type="file"
            ref={fileRef}
            className="hidden"
            onChange={onFileChange}
            accept="image/*"
          />
          {isActive ? (
            <div className="flex items-center gap-4">
              <button
                onClick={previewUrl ? handleImageUpload : pick}
                disabled={isUploading}
                className={`text-sm font-medium px-3 py-1 rounded-md transition-colors disabled:opacity-50 ${
                  uploadedUrl ? "bg-green-100 text-green-700" : "bg-gray-100 hover:bg-gray-200"
                }`}
              >
                {isUploading ? "통신 중..." : uploadedUrl ? "업로드 완료 ✨" : previewUrl ? "이미지 업로드" : "이미지 테스트"}
              </button>
              
              <Link
                to="/my"
                className="text-sm md:text-base text-primary font-medium"
              >
                마이페이지
              </Link>
              <Link
                to="/"
                className="text-sm md:text-base text-primary font-medium"
                onClick={handleLogout}
              >
                로그아웃
              </Link>
            </div>
          ) : (
            <Link
              to="/login"
              className="text-sm md:text-base text-primary font-medium"
            >
              로그인
            </Link>
          )}
        </div>
      </div>
    </header>
  );
};
