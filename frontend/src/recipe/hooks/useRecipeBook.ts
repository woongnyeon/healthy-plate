import { useEffect, useMemo, useState } from "react";
import type { Recipe } from "../types/recipe";

export const useRecipeBook = (recipes: Recipe[]) => {
  const [query, setQuery] = useState("");
  const [debouncedQuery, setDebouncedQuery] = useState("");

  useEffect(() => {
    const timer = setTimeout(() => {
      setDebouncedQuery(query);
    }, 300);

    return () => clearTimeout(timer);
  }, [query]);

  const filterRecipes = useMemo(() => {
    if (!debouncedQuery.trim()) return null;

    const lowerQuery = debouncedQuery.toLowerCase();

    return recipes.filter((recipe) =>
      [recipe.title, recipe.author, recipe.category].some((field) =>
        field.toLowerCase().includes(lowerQuery)
      )
    );
  }, [recipes, debouncedQuery]);

  return {
    query,
    setQuery,
    filterRecipes,
    isSearching: !!debouncedQuery.trim(),
  };
};
