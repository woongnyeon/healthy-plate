interface StatisticsCardProps {
  title: string;
  value: string | number;
  description?: string;
  changeRate?: string;
  isPositive?: boolean;
}

export const StatisticsCard = ({
  title,
  value,
  description,
  changeRate,
  isPositive = true,
}: StatisticsCardProps) => {
  return (
    <div className="w-full rounded-2xl border bg-white p-6 shadow-sm">
      {/* 제목 */}
      <p className="text-sm text-gray-500">{title}</p>

      {/* 핵심 수치 */}
      <p className="mt-2 text-2xl font-semibold text-gray-900">{value}</p>

      {/* 하단 설명 */}
      {(changeRate || description) && (
        <div className="mt-3 flex items-center gap-2 text-sm">
          {changeRate && (
            <span
              className={`flex items-center gap-1 rounded-full px-2 py-0.5 text-xs font-medium
              ${
                isPositive
                  ? "bg-orange-100 text-orange-600"
                  : "bg-blue-100 text-blue-600"
              }`}
            >
              ▲ {changeRate}
            </span>
          )}

          {description && <span className="text-gray-500">{description}</span>}
        </div>
      )}
    </div>
  );
};
