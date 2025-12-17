import { useResponsive } from "../../share/hooks/useResonsive";
// import { RecipeList } from "../components/RecipeList";
import { useTrendRecipe } from "../hooks/useTrendRecipe";

export const RecipeBookPage = () => {
  const { isLoading } = useTrendRecipe();
  const { gridCols } = useResponsive();
  if (isLoading) return <div>로딩중...</div>;

  return (
    <div
      className={`grid gap-4 ${gridCols === 4 ? "grid-cols-4" : "grid-cols-2"}`}
    >
      hi
    </div>
  );
};
