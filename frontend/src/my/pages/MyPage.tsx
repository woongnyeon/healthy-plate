import { DraftSection } from "../components/DraftSection/DraftSection";
import { LikeRecipeSection } from "../components/LikeRecipeSection/LikeRecipeSection";

import { Profile } from "../components/Profile";
import { StatisticsSection } from "../components/SatisticsSection/SatisticsSection";
import { StatisticsCard } from "../components/SatisticsSection/StatisticsCard";
import { WeekSatisticsSection } from "../components/WeekSatisticsSection/WeekSatisticsSection";

export const MyPage = () => {
  return (
    <div className="flex flex-col w-full mt-16 px-4 md:px-8 lg:px-10 bg-bg ">
      <Profile />
      <div className="grid grid-cols-3 gap-6">
        <StatisticsCard
          title="총 작성 레시피"
          value={156}
          changeRate="+3"
          description="지난달 대비 증가"
        />

        <StatisticsCard
          title="총 조회수"
          value="45,230"
          changeRate="+12.5%"
          description="지난달 대비 증가"
        />

        <StatisticsCard
          title="평균 좋아요 수"
          value="21.8"
          description="레시피당 평균 반응률"
          changeRate="4.8%"
          isPositive={true}
        />
      </div>

      <div className="mt-4">
        <StatisticsSection />
      </div>

      <div className="mt-4">
        <WeekSatisticsSection />
      </div>

      <div className="mt-4">
        <DraftSection />
      </div>

      <div className="mt-4">
        <LikeRecipeSection />
      </div>
    </div>
  );
};
