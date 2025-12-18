import { WeekSatisticsBar } from "./WeekSatisticsBar";

export const WeekSatisticsSection = () => {
  const weekData = [
    { label: "Mon", value: 2 },
    { label: "Tue", value: 4 },
    { label: "Wed", value: 3 },
    { label: "Thu", value: 6 },
    { label: "Fri", value: 5 },
    { label: "Sat", value: 8 },
    { label: "Sun", value: 1 },
  ];
  return <WeekSatisticsBar title="주간 레시피 작성 현황" data={weekData} />;
};
