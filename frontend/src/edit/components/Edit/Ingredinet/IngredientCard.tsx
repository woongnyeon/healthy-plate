import type { Ingredient } from "../../../types/Ingredient";

interface IngredientCardProps {
  ingredients: Ingredient[];
  totalKcal: number;
  onRemove: (id: number) => void;
}

export const IngredientCard = ({
  ingredients,
  totalKcal,
  onRemove,
}: IngredientCardProps) => {
  return (
    <div className="rounded-2xl bg-gray-50 p-5">
      <div className="flex items-center justify-between">
        <h2 className="text-sm font-extrabold text-gray-900">재료</h2>
        <span className="text-xs text-gray-400">총 {totalKcal} kcal</span>
      </div>

      <div className="mt-3 flex flex-wrap gap-2">
        {ingredients.map((i) => (
          <span
            key={i.id}
            className="inline-flex items-center gap-2 rounded-full bg-white px-3 py-1 text-xs shadow"
          >
            {i.name} {i.amount}
            <span className="text-gray-400 text-[10px]">{i.kcal}kcal</span>
            <button
              onClick={() => onRemove(Number(i.id))}
              className="text-gray-300 hover:text-gray-500"
            >
              ×
            </button>
          </span>
        ))}
      </div>
    </div>
  );
};
