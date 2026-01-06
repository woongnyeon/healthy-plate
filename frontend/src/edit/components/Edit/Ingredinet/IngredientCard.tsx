import type { IngredientCardProps } from "../../../types/Ingredient";
import { UNIT_LABELS } from "../../../types/Ingredient";


export const IngredientCard = ({
  ingredients,
  totalKcal,
  settings = { showIngredients: true, showKcal: true }, // 기본값
  onRemove,
  onToggle,
}: IngredientCardProps) => {
  return (
    <div className="rounded-2xl border border-gray-100 bg-white p-6 shadow-sm ring-1 ring-gray-100/50">
      <div className="flex items-center justify-between border-b border-gray-100 pb-4">
        <div className="flex flex-col gap-1">
          <h2 className="flex items-center gap-2 text-lg font-bold text-gray-800">
            🥗 재료 목록
            <span className="rounded-full bg-emerald-50 px-2 py-0.5 text-xs font-medium text-emerald-600">
              {ingredients.length}
            </span>
          </h2>
          <div className="flex gap-4">
             {/* 재료 표시 토글 */}
             <div className="flex items-center gap-2">
               <span className="text-xs font-semibold text-gray-500">재료 표시</span>
               <button 
                 type="button"
                 onClick={() => onToggle('showIngredients')}
                 className={`relative inline-flex h-5 w-9 items-center rounded-full transition-colors duration-200 focus:outline-none focus:ring-2 focus:ring-emerald-500 focus:ring-offset-2 ${
                   settings.showIngredients ? "bg-emerald-500" : "bg-gray-200"
                 }`}
               >
                 <span
                   className={`inline-block h-3.5 w-3.5 transform rounded-full bg-white transition-transform duration-200 ${
                     settings.showIngredients ? "translate-x-[18px]" : "translate-x-1"
                   }`}
                 />
               </button>
             </div>

             {/* 칼로리 표시 토글 */}
             <div className="flex items-center gap-2">
               <span className="text-xs font-semibold text-gray-500">칼로리 표시</span>
               <button 
                 type="button"
                 onClick={() => onToggle('showKcal')}
                 className={`relative inline-flex h-5 w-9 items-center rounded-full transition-colors duration-200 focus:outline-none focus:ring-2 focus:ring-emerald-500 focus:ring-offset-2 ${
                   settings.showKcal ? "bg-emerald-500" : "bg-gray-200"
                 }`}
               >
                 <span
                   className={`inline-block h-3.5 w-3.5 transform rounded-full bg-white transition-transform duration-200 ${
                     settings.showKcal ? "translate-x-[18px]" : "translate-x-1"
                   }`}
                 />
               </button>
             </div>
          </div>
        </div>

        <div className="text-right">
          <span className="text-xs text-gray-400">총 열량</span>
          <div className={`text-xl font-extrabold transition-opacity ${settings.showKcal ? "text-emerald-600" : "text-gray-300 blur-[2px]"}`}>
            {totalKcal.toLocaleString()} <span className="text-sm font-medium">kcal</span>
          </div>
        </div>
      </div>

      <div className="mt-5 flex flex-wrap gap-2.5">
        {ingredients.length === 0 ? (
          <div className="flex w-full flex-col items-center justify-center py-6 text-gray-400">
            <span className="text-sm">아직 추가된 재료가 없어요</span>
          </div>
        ) : (
          ingredients.map((i) => (
            <div
              key={i.id}
              className="group flex flex-col items-start gap-1 rounded-xl bg-gray-50 px-4 py-3 transition hover:bg-emerald-50/50 hover:ring-1 hover:ring-emerald-100"
            >
              <div className="flex items-center gap-2">
                <span className="font-bold text-gray-800">{i.name}</span>
                <button
                  type="button"
                  onClick={() => onRemove(Number(i.id))}
                  className="rounded-full bg-gray-200 p-0.5 text-gray-400 opacity-0 transition hover:bg-red-100 hover:text-red-500 group-hover:opacity-100"
                >
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 20 20"
                    fill="currentColor"
                    className="h-3 w-3"
                  >
                    <path d="M6.28 5.22a.75.75 0 00-1.06 1.06L8.94 10l-3.72 3.72a.75.75 0 101.06 1.06L10 11.06l3.72 3.72a.75.75 0 101.06-1.06L11.06 10l3.72-3.72a.75.75 0 00-1.06-1.06L10 8.94 6.28 5.22z" />
                  </svg>
                </button>
              </div>
              <div className="flex items-center gap-1.5 text-xs text-gray-500">
                <span>{i.amount}{UNIT_LABELS[i.unit]}</span>
                <span className="h-2 w-[1px] bg-gray-300" />
                <span className="font-medium text-emerald-600">{i.kcal} kcal</span>
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
};
