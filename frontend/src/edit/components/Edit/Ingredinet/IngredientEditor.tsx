import { useSearchIngredient } from "../../../hooks/useSearchIngredient";
import { useEditQuery } from "../../../hooks/useEditQuery";
import { useRecipeEditorStore } from "../../../store/EditStore";

import { IngredientCard } from "./IngredientCard";
import { IngredientList } from "./IngredientList";

export const IngredientEditor = () => {
  const ingredients = useRecipeEditorStore((s) => s.ingredients);
  const totalKcal = useRecipeEditorStore((s) => s.totalKcal);
  const addIngredient = useRecipeEditorStore((s) => s.addIngredient);
  const removeIngredient = useRecipeEditorStore((s) => s.removeIngredient);
  const { ingredientQuery } = useEditQuery();
  const { data: allIngredients = [] } = ingredientQuery();

  const { query, setQuery, results, isSearching } =
    useSearchIngredient(allIngredients);

  const isListOpen = isSearching;

  return (
    <div className="mt-12 w-full max-w-[520px]">
      <IngredientCard
        ingredients={ingredients}
        totalKcal={totalKcal}
        onRemove={removeIngredient}
      />

      <div className="mt-3">
        <div className="flex items-center gap-2 rounded-full bg-white px-4 py-3 shadow-sm ring-1 ring-gray-100">
          <span className="text-gray-400">검색</span>
          <input
            value={query}
            onChange={(e) => setQuery(e.target.value)}
            placeholder="재료 검색"
            className="w-full text-sm outline-none"
          />
        </div>

        <IngredientList
          query={query}
          isOpen={isListOpen}
          items={results.map((r) => ({
            id: Number(r.id),
            name: r.name,
            baseAmount: r.amount ?? 0,
            baseKcal: r.kcal ?? 0,
          }))}
          onSelect={(item) => {
            addIngredient({
              id: Date.now(),
              name: item.name,
              amount: item.baseAmount ?? 0,
              kcal: item.baseKcal ?? 0,
            });
            setQuery("");
          }}
          onClose={() => setQuery("")}
          onManualAdd={({ name, amount, kcal }) => {
            addIngredient({
              id: Date.now(),
              name,
              amount,
              kcal,
            });
          }}
        />
      </div>
    </div>
  );
};
