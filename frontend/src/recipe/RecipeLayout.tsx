import { NavLink, Outlet } from "react-router-dom";
import { WriteFloatingButton } from "./components/WriteFloatingButton";

const NAV_ITEMS = [
  { label: "트렌드", path: "/" },
  { label: "셰프의 레시피", path: "/chef" },
  { label: "최신순", path: "/latest" },
  { label: "레시피북", path: "/book" },
];

export const RecipeLayout = () => {
  return (
    <>
      <div className="w-full mt-16 px-4 md:px-10 lg:px-12">
        <nav>
          <ul className="flex gap-4 md:gap-6 lg:gap-8 h-14 items-center">
            {NAV_ITEMS.map((item) => (
              <li key={item.path}>
                <NavLink
                  to={item.path}
                  className={({ isActive }) =>
                    [
                      "transition-colors",
                      "text-sm md:text-base lg:text-nav-items",
                      isActive
                        ? "text-main font-bold border-b-2 border-main pb-2"
                        : "text-tertiary",
                    ].join(" ")
                  }
                >
                  {item.label}
                </NavLink>
              </li>
            ))}
          </ul>
        </nav>

        <main className="mx-auto py-8">
          <Outlet />
        </main>

        <WriteFloatingButton />
      </div>
    </>
  );
};
