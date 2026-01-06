import type { TagInputProps } from "../../../types/Tag";


export const TagInput = ({ onSubmit, onBackspace }: TagInputProps) => {
  return (
    <input
      type="text"
      placeholder="태그 입력 (Enter)"
      className="min-w-[140px] bg-transparent text-sm outline-none placeholder:text-gray-400"
      onKeyDown={(e) => {
        // 1. IME 입력 중 중복 이벤트 방지
        if (e.nativeEvent.isComposing) return;

        if (e.key === "Enter") {
          const value = e.currentTarget.value.trim();
          if (!value) return;
          onSubmit(value);
          e.currentTarget.value = "";
        }

        // 2. 백스페이스로 태그 삭제 (입력값이 없을 때만)
        if (e.key === "Backspace" && e.currentTarget.value === "") {
          onBackspace?.();
        }
      }}
    />
  );
};
