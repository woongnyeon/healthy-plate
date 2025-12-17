import type { Recipe } from "../types/recipe";
import { RecipeCard } from "./RecipeCard";

export interface RecipeListProps {
  recipes: Recipe[];
}

export const RecipeList = ({ recipes }: RecipeListProps) => {
  return (
    <>
      {recipes.map((recipe) => (
        <RecipeCard key={recipe.title} recipe={recipe} />
      ))}
    </>
  );
};
