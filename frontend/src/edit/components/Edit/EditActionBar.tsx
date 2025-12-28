interface EditActionBarProps {
  onSaveDraft: () => void;
  onSubmit: () => void;
  submitDisabled?: boolean;
}

export const EditActionBar = ({
  onSaveDraft,
  onSubmit,
  submitDisabled = false,
}: EditActionBarProps) => {
  return (
    <div className="sticky bottom-0 z-30">
      {/* 배경 + 상단 경계선(떠있는 느낌) */}
      <div className="bg-white/90 backdrop-blur border-t border-gray-100">
        <div className="mx-auto max-w-[720px] px-2 py-4 flex justify-end gap-3">
          <button
            className="h-10 rounded-lg bg-gray-100 px-4 text-sm font-medium text-gray-700 hover:bg-gray-200"
            onClick={onSaveDraft}
          >
            임시 저장
          </button>

          <button
            onClick={onSubmit}
            disabled={submitDisabled}
            className={[
              "h-10 rounded-lg px-4 text-sm font-semibold text-white transition-colors",
              submitDisabled
                ? "bg-orange-200 cursor-not-allowed"
                : "bg-orange-500 hover:bg-orange-600",
            ].join(" ")}
          >
            작성 완료
          </button>
        </div>
      </div>
    </div>
  );
};
