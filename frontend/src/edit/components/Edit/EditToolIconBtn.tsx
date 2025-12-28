import React from "react";

interface EditToolIconBtnProps {
  onClick: () => void;
  children: React.ReactNode;
}

export const EditToolIconBtn = ({
  onClick,
  children,
}: EditToolIconBtnProps) => {
  return (
    <button
      type="button"
      onClick={onClick}
      className="
        grid h-7 w-7 place-items-center
        rounded-md text-gray-500
        transition-colors
        hover:bg-gray-100 hover:text-gray-800
      "
    >
      {children}
    </button>
  );
};
