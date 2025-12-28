import { useEffect, useMemo, useState } from "react";
import type { Ingredient } from "../types/Ingredient";

export const useSearchIngredient = (ingredients: Ingredient[]) => {
  const [query, setQuery] = useState("");
  const [debouncedQuery, setDebouncedQuery] = useState("");

  useEffect(() => {
    const timer = setTimeout(() => {
      setDebouncedQuery(query);
    }, 300);

    return () => clearInterval(timer);
  }, [query]);

  const results = useMemo(() => {
    const q = debouncedQuery.trim().toLowerCase();
    if (!q) return [];

    return ingredients.filter((ingredient) =>
      ingredient.name.toLowerCase().includes(q)
    );
  }, [ingredients, debouncedQuery]);

  return {
    query,
    setQuery,
    results,
    isSearching: debouncedQuery.trim().length > 0,
  };
};
