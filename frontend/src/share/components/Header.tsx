import { Link } from "react-router-dom";

export const Header = () => {
  return (
    <header className="fixed top-0 left-0 w-full z-50 bg-bg">
      {/* 내부 컨텐츠 영역 */}
      <div className="h-14 flex items-center justify-between px-4 md:px-10 lg:px-[90px]">
        {/* 좌측 로고 */}
        <img src="/assets/logo.png" alt="logo" className="h-6" />

        {/* 우측 메뉴 */}
        <div className="flex items-center gap-4">
          <Link
            to="/login"
            className="text-sm md:text-base text-primary font-medium"
          >
            로그인
          </Link>
        </div>
      </div>
    </header>
  );
};
