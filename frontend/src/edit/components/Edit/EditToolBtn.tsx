import type React from "react";

interface EditToolBtnProps {
  active?: boolean;
  onClick: () => void;
  children: React.ReactNode;
}

export const EditToolBtn = ({
  active = false,
  onClick,
  children,
}: EditToolBtnProps) => {
  return (
    <button
      type="button"
      onClick={onClick}
      className={`
        "px-2 py-1 text-sm font-semibold transition-colors",
        ${active ? "text-gray-900" : "text-gray-500"},
        "hover:text-gray-900"
      `}
    >
      {children}
    </button>
  );
};
