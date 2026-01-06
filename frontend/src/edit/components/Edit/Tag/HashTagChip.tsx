import type { HashTagChipProps } from "../../../types/Tag";


export const HashTagChip = ({ label, onRemove }: HashTagChipProps) => {
  return (
    <span className="inline-flex items-center gap-1 rounded-full bg-orange-50 px-3 py-1 text-xs font-semibold text-orange-600">
      #{label}
      {onRemove && (
        <button
          onClick={onRemove}
          className="ml-1 text-orange-400 hover:text-orange-600"
        >
          ×
        </button>
      )}
    </span>
  );
};
