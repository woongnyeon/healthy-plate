import { useNavigate } from "react-router-dom";
import { useDivdePath } from "../hooks/useDivdePath";

export const LoginPage = () => {
  const navigate = useNavigate();
  useDivdePath();
  return (
    <div className="min-h-screen flex items-center justify-center bg-white">
      <div className="w-full max-w-[380px] rounded-2xl bg-white shadow-lg px-8 py-10">
        {/* 로고 */}
        <div className="flex flex-col items-center mb-8">
          <div className="w-14 h-14 rounded-full bg-orange-100 flex items-center justify-center mb-4">
            🍽️
          </div>
          <h1 className="text-2xl font-bold">healthy-plate</h1>
          <p className="text-sm text-gray-500 mt-2 text-center">
            요리의 즐거움을 함께 나눠보세요.
            <br />
            로그인하고 나만의 레시피를 기록해보세요.
          </p>
        </div>

        {/* 소셜 로그인 버튼 */}
        <div className="flex flex-col gap-3">
          {/* Kakao */}
          <button
            className="flex items-center justify-center gap-2 rounded-full bg-[#FEE500] text-black py-3 font-medium hover:brightness-95 transition"
            onClick={() => {
              // TODO: 카카오 OAuth
              window.location.href =
                "http://localhost:8080/oauth2/authorization/kakao";
            }}
          >
            <span>💬</span>
            카카오로 시작하기
          </button>

          {/* Naver */}
          <button
            className="flex items-center justify-center gap-2 rounded-full bg-[#03C75A] text-white py-3 font-medium hover:brightness-95 transition"
            onClick={() => {
              // TODO: 네이버 OAuth
              window.location.href =
                "http://localhost:8080/oauth2/authorization/naver";
            }}
          >
            <span className="font-bold">N</span>
            네이버로 시작하기
          </button>

          {/* Google */}
          <button
            className="flex items-center justify-center gap-2 rounded-full border border-gray-300 py-3 font-medium hover:bg-gray-50 transition"
            onClick={() => {
              // TODO: 구글 OAuth
              window.location.href =
                "http://localhost:8080/oauth2/authorization/google";
            }}
          >
            <img src="/icons/google.svg" alt="google" className="w-5 h-5" />
            구글로 시작하기
          </button>
        </div>

        {/* 약관 */}
        <p className="mt-6 text-xs text-gray-400 text-center">
          로그인 시 이용약관 및 개인정보처리방침에 동의하게 됩니다.
        </p>
      </div>
    </div>
  );
};

export default LoginPage;
