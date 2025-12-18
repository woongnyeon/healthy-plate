export interface DraftItem {
  id: string;
  title: string;
  updatedAt: string; // "2025-12-17 14:30"
}

interface DraftListProps {
  items: DraftItem[];
  onEdit: (id: string) => void;
  onDelete: (id: string) => void;
}

export const DraftList = ({ items, onEdit, onDelete }: DraftListProps) => {
  return (
    <div className="w-full overflow-hidden rounded-2xl border bg-white shadow-sm">
      {items.map((item, idx) => (
        <div
          key={item.id}
          className={`flex items-center justify-between px-6 py-5 ${
            idx !== 0 ? "border-t" : ""
          }`}
        >
          {/* 왼쪽 텍스트 */}
          <div className="min-w-0">
            <p className="truncate text-sm font-semibold text-gray-900">
              {item.title}
            </p>
            <p className="mt-1 text-xs text-gray-400">
              마지막 수정 : {item.updatedAt}
            </p>
          </div>

          {/* 오른쪽 액션 */}
          <div className="ml-6 flex shrink-0 items-center gap-3">
            <button
              onClick={() => onEdit(item.id)}
              className="inline-flex items-center gap-2 rounded-full border px-4 py-2 text-xs font-medium text-gray-700 hover:bg-gray-50"
            >
              ✏️ 편집
            </button>

            <button
              onClick={() => onDelete(item.id)}
              className="inline-flex items-center gap-2 rounded-full border border-red-200 px-4 py-2 text-xs font-medium text-red-600 hover:bg-red-50"
            >
              🗑️ 삭제
            </button>
          </div>
        </div>
      ))}

      {items.length === 0 && (
        <div className="px-6 py-10 text-center text-sm text-gray-400">
          임시 저장된 글이 없습니다.
        </div>
      )}
    </div>
  );
};
