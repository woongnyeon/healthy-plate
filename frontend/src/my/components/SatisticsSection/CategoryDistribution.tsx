import { useMemo } from "react";
import { ResponsiveContainer, PieChart, Pie, Cell } from "recharts";

type DistributionItem = {
  name: string; // 한식, 양식...
  value: number; // 45 (퍼센트가 아니라 비율값/개수여도 OK)
};

interface CategoryDistributionProps {
  title?: string;
  data: DistributionItem[];
}

const COLORS = ["#FF8A00", "#F5B25B", "#F8D6A3", "#CBD5E1"];

export const CategoryDistribution = ({
  title = "태그별 분포",
  data,
}: CategoryDistributionProps) => {
  const total = useMemo(
    () => data.reduce((acc, cur) => acc + cur.value, 0),
    [data]
  );

  const top = useMemo(() => {
    if (!data.length) return null;
    return [...data].sort((a, b) => b.value - a.value)[0];
  }, [data]);

  return (
    <div className="w-full rounded-2xl border bg-white p-6 shadow-sm">
      <div className="mb-3">
        <h3 className="text-sm font-semibold text-gray-900">{title}</h3>
      </div>

      {/* 차트 영역 */}
      <div className="relative h-[180px]">
        <ResponsiveContainer width="100%" height="100%">
          <PieChart>
            <Pie
              data={data}
              dataKey="value"
              innerRadius={52}
              outerRadius={72}
              paddingAngle={2}
              stroke="transparent"
            >
              {data.map((_, idx) => (
                <Cell key={idx} fill={COLORS[idx % COLORS.length]} />
              ))}
            </Pie>
          </PieChart>
        </ResponsiveContainer>

        {/* 중앙 텍스트 */}
        <div className="pointer-events-none absolute inset-0 flex flex-col items-center justify-center">
          <p className="text-xs text-gray-500">최다 작성</p>
          <p className="mt-1 text-base font-semibold text-orange-500">
            {top?.name ?? "-"}
          </p>
        </div>
      </div>

      {/* 범례 */}
      <div className="mt-3 grid grid-cols-2 gap-x-6 gap-y-2 text-xs text-gray-600">
        {data.map((item, idx) => {
          const pct = total === 0 ? 0 : Math.round((item.value / total) * 100);
          return (
            <div key={item.name} className="flex items-center gap-2">
              <span
                className="h-2 w-2 rounded-full"
                style={{ backgroundColor: COLORS[idx % COLORS.length] }}
              />
              <span className="truncate">
                {item.name} ({pct}%)
              </span>
            </div>
          );
        })}
      </div>

      {/* (선택) 하단 설명이 필요하면 */}
      {/* <p className="mt-3 text-xs text-gray-500">
        최다 카테고리 비중: {topPercent}%
      </p> */}
    </div>
  );
};
