import { useLayoutEffect, useRef } from "react";
import { useRecipeEditorStore } from "../../store/EditStore";

interface UseTitleProps {
  maxLength?: number;
  allowNewLine?: boolean;
  onEnter?: () => void;
}

export const useTitle = ({
  maxLength = 60,
  allowNewLine = true,
  onEnter,
}: UseTitleProps) => {
  const title = useRecipeEditorStore((state) => state.title);
  const setTitle = useRecipeEditorStore((state) => state.setTitle);
  const ref = useRef<HTMLTextAreaElement | null>(null);

  const autoResize = () => {
    const el = ref.current;
    if (!el) return;
    el.style.height = "auto";
    el.style.height = `${el.scrollHeight}px`;
  };

  useLayoutEffect(() => {
    autoResize();
  }, [title]);

  const handleChange = (e: React.ChangeEvent<HTMLTextAreaElement>) => {
    const v = e.target.value;
    if (maxLength && v.length > maxLength) {
      setTitle(v.slice(0, maxLength));
      return;
    }
    setTitle(v);
  };

  const handleKeyDown = (e: React.KeyboardEvent<HTMLTextAreaElement>) => {
    if (!allowNewLine && e.key === "Enter") {
      e.preventDefault();
      onEnter?.();
    }
  };

  return {
    title,
    ref,
    handleChange,
    handleKeyDown,
    currentLength: title.length,
  };
};
