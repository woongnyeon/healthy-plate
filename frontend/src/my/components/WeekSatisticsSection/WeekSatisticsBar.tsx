import {
  BarChart,
  CartesianGrid,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
  Bar,
} from "recharts";

interface WeekSatisticsBarProps {
  title: string;
  subtitle?: string;
  data: {
    label: string;
    value: number;
  }[];
}

export const WeekSatisticsBar = ({
  title,
  subtitle,
  data,
}: WeekSatisticsBarProps) => {
  return (
    <div className="w-full rounded-2xl border bg-white p-6 shadow-sm">
      <div className="mb-4 flex items-center justify-between">
        <h3 className="text-sm font-semibold text-gray-900">{title}</h3>
        <span className="text-sx text-gray-40-">{subtitle}</span>
      </div>

      <div className="h-[170px]">
        <ResponsiveContainer width="100%" height="100%">
          <BarChart data={data} barCategoryGap={28}>
            <CartesianGrid vertical={false} stroke="#F3F4F6" />
            <XAxis
              dataKey="label"
              tickLine={false}
              axisLine={false}
              tick={{ fontSize: 12, fill: "#9CA3AF" }}
            />
            <YAxis hide />
            <Tooltip
              cursor={{ fill: "rgba(0,0,0,0.03)" }}
              contentStyle={{
                borderRadius: "10px",
                border: "1px solid #E5E7EB",
                fontSize: "12px",
              }}
              formatter={(v) => [`${v}개`, "작성"]}
            />
            <Bar
              dataKey="value"
              radius={[10, 10, 10, 10]}
              fill="#FF8A00"
              maxBarSize={26}
            />
          </BarChart>
        </ResponsiveContainer>
      </div>
    </div>
  );
};
