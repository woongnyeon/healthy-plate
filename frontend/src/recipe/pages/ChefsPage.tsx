import { useResponsive } from "../../share/hooks/useResonsive";
import { RecipeList } from "../components/RecipeList";
// import { RecipeList } from "../components/RecipeList";
import { useChefsRecipe } from "../hooks/useChefsRecipe";

export const ChefsPage = () => {
  const { isLoading, recipes } = useChefsRecipe();
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
