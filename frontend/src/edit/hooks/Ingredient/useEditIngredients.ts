import { useRecipeEditorStore } from "../../store/EditStore";
import { useShallow } from "zustand/react/shallow";
import { useEditQuery } from "../query/useEditQuery";
import { useSearchIngredient } from "../util/useSearchIngredient";
import { useIngredient } from "../Ingredient/useIngredient";

/**
 * 재료 편집 로직을 담당하는 훅
 * 재료 검색, 추가, 삭제 등의 모든 재료 관련 기능을 캡슐화합니다.
 */
export const useEditIngredients = () => {
  // Store에서 재료 상태 가져오기
  const { ingredients, totalKcal, settings, addIngredient, removeIngredient, toggleSetting } =
    useRecipeEditorStore(
      useShallow((state) => ({
        ingredients: state.ingredients,
        totalKcal: state.totalKcal,
        settings: state.settings,
        addIngredient: state.addIngredient,
        removeIngredient: state.removeIngredient,
        toggleSetting: state.toggleSetting,
      }))
    );

  // 재료 검색 쿼리
  const { ingredientQuery } = useEditQuery();
  const allIngredients = ingredientQuery.data;
  const searchValues = useSearchIngredient(allIngredients || []);

  // 재료 리스트 UI 로직 (키보드, 수동 추가)
  const ingredientListLogic = useIngredient({
    query: searchValues.query,
    isOpen: searchValues.isSearching,
    items: searchValues.results.map((r) => ({
      id: Number(r.id),
      name: r.name,
      baseAmount: r.amount ?? 0,
      baseKcal: r.kcal ?? 0,
    })),
    onSelect: (item) => {
      addIngredient({
        id: Date.now(),
        name: item.name,
        amount: item.baseAmount ?? 0,
        unit: "g",
        kcal: item.baseKcal ?? 0,
      });
      searchValues.setQuery("");
    },
    onClose: () => searchValues.setQuery(""),
    onManualAdd: ({ name, amount, unit, kcal }) => {
      addIngredient({
        id: Date.now(),
        name,
        amount,
        unit,
        kcal,
      });
    },
  });

  const handleToggleSetting = (key: keyof typeof settings) => {
    toggleSetting(key);
  };

  return {
    // 데이터
    ingredients,
    totalKcal,
    settings,
    
    // 검색 관련
    searchValues,
    
    // UI 로직
    ingredientListProps: ingredientListLogic,
    
    // 액션
    onRemove: removeIngredient,
    onToggle: handleToggleSetting,
  };
};
