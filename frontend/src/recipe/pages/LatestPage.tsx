import { useResponsive } from "../../share/hooks/useResonsive";
import { useLatestRecipe } from "../hooks/useLatestRecipe";
import { RecipeList } from "../components/RecipeList";

export const LatestPage = () => {
  const { isLoading, recipes } = useLatestRecipe();
  const { gridCols } = useResponsive();
  if (isLoading) return <div>로딩중...</div>;

  return (
    <div
      className={`grid gap-4 ${gridCols === 3 ? "grid-cols-4" : "grid-cols-2"}`}
    >
      <RecipeList recipes={recipes} />
    </div>
  );
};
