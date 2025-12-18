import { StatisticsGraph } from "./StatisticsGraph";
import { CategoryDistribution } from "./CategoryDistribution";

export const StatisticsSection = () => {
  const graphData = [
    { label: "Week 1", views: 1200, likes: 400 },
    { label: "Week 2", views: 2400, likes: 700 },
    { label: "Week 3", views: 2000, likes: 650 },
    { label: "Week 4", views: 3600, likes: 1200 },
  ];

  const categoryData = [
    { name: "한식", value: 45 },
    { name: "양식", value: 25 },
    { name: "디저트", value: 15 },
    { name: "기타", value: 15 },
  ];

  return (
    <div className="grid grid-cols-1 gap-6 lg:grid-cols-12">
      {/* 좌: 그래프 (넓게) */}
      <div className="lg:col-span-8">
        <StatisticsGraph title="조회수 및 좋아요 추이" data={graphData} />
      </div>

      {/* 우: 카테고리 분포 */}
      <div className="lg:col-span-4">
        <CategoryDistribution data={categoryData} />
      </div>
    </div>
  );
};
