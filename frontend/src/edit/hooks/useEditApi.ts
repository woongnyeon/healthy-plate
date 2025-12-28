import type { Ingredient } from "../types/Ingredient";

export const useEditApi = () => {
  const DUMMY_INGREDIENTS: Ingredient[] = [
    { id: 1, name: "돼지고기", amount: 100, kcal: 242 },
    { id: 2, name: "제주 흑돼지", amount: 100, kcal: 270 },
    { id: 3, name: "묵은지", amount: 100, kcal: 35 },
    { id: 4, name: "대파", amount: 10, kcal: 3 },
    { id: 5, name: "마늘", amount: 10, kcal: 15 },
  ];

  const fetchAllIngredients = async (): Promise<Ingredient[]> => {
    await new Promise((r) => setTimeout(r, 200));
    return DUMMY_INGREDIENTS;
  };
  return {
    fetchAllIngredients,
  };
};
