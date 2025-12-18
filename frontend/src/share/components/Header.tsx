import { Link } from "react-router-dom";
import { useUserInfo } from "../../auth/hooks/useUserInfo";

export const Header = () => {
  const { handleLogin, handleLogout } = useUserInfo();
  const isActive = localStorage.getItem("user") ? true : false;
  return (
    <header className="fixed top-0 left-0 w-full z-50 bg-white">
      {/* 내부 컨텐츠 영역 */}
      <div className="h-14 flex items-center justify-between px-4 md:px-10 lg:px-[90px]">
        {/* 좌측 로고 */}
        <img src="/assets/logo.png" alt="logo" className="h-6" />

        {/* 우측 메뉴 */}
        <div className="flex items-center gap-4">
          {isActive ? (
            <div className="flex flex gap-2">
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
              to="/"
              className="text-sm md:text-base text-primary font-medium"
              onClick={handleLogin}
            >
              로그인
            </Link>
          )}
        </div>
      </div>
    </header>
  );
};
