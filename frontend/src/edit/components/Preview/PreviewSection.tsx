import { useRecipeEditorStore } from "../../store/EditStore";
import { UNIT_LABELS } from "../../types/Ingredient";

export const PreviewSection = () => {
  const title = useRecipeEditorStore((s) => s.title);
  const tags = useRecipeEditorStore((s) => s.tags);
  const ingredients = useRecipeEditorStore((s) => s.ingredients);
  const totalKcal = useRecipeEditorStore((s) => s.totalKcal);
  const contentHtml = useRecipeEditorStore((s) => s.contentHtml);
  const settings = useRecipeEditorStore((s) => s.settings);
  
  // 마크다운 이미지 문법을 HTML img 태그로 변환
  // 예: ![](/blob:...) -> <img src="/blob:..." />
  const processedHtml = contentHtml
    ? contentHtml.replace(
        /!\[(.*?)\]\((.*?)\)/g,
        '<img src="$2" alt="$1" class="rounded-2xl my-4 w-full object-cover" />'
      )
    : "";

  return (
    <aside className="min-w-0 bg-white h-full overflow-y-auto">
      <div className="mx-auto max-w-[1000px] p-10">
        {/* Header: Tags & Title */}
        <div className="mb-10">
          <div className="flex flex-wrap items-center gap-2 mb-4">
            {tags.map((tag) => (
              <span
                key={tag}
                className="px-3 py-1.5 rounded-lg bg-[#FFF4E6] text-[#FF8A00] font-semibold text-sm"
              >
                {tag}
              </span>
            ))}
          </div>
          {title?
          <h1 className="text-4xl font-extrabold text-gray-900 leading-tight">
            {title}
          </h1>:
          <h1 className="text-4xl font-extrabold text-gray-200">
            제목을 입력해주세요.
          </h1>
          }
        </div>

        {/* 컨텐츠 영역 */}
        <div className="flex flex-col lg:flex-row gap-10">
          {/* Left: Main Content (Description & Images) */}
          <div className="flex-1 min-w-0">
            { processedHtml ? (
              <div
                className="prose prose-lg max-w-none text-gray-800 prose-img:rounded-2xl"
                dangerouslySetInnerHTML={{ __html: processedHtml }}
              />
            ) : (
                <div className="text-gray-400">내용을 입력해주세요.</div>
            )}
          </div>

          {/* 오른쪽 사이드 바 재료와 칼라로 표시 여부 */}
          <div className="w-full lg:w-[250px] flex flex-col gap-6 shrink-0">
            
            {/* 재료 카드 - 설정에 따라 표시 여부 결정 */}
            {settings.showIngredients && (
              <div className="rounded-[1.5rem] bg-[#F7F7F7] p-6">
                <div className="flex items-center justify-between mb-4">
                  <h2 className="text-base font-bold text-gray-900">재료</h2>
                  {settings.showKcal && (
                    <span className="text-xs font-medium text-gray-400">
                      총 {totalKcal} kcal
                    </span>
                  )}
                </div>

                <div className="flex flex-col gap-2.5">
                  {ingredients.length > 0 ? (
                    ingredients.map((ingredient) => (
                      <div
                        key={ingredient.id}
                        className="flex items-center justify-between bg-white rounded-xl px-4 py-2.5 shadow-[0_1px_2px_rgba(0,0,0,0.03)]"
                      >
                        <span className="text-gray-800 font-semibold text-sm">
                          {ingredient.name}
                        </span>
                        <div className="flex items-center gap-2">
                          <span className="text-gray-400 text-xs">{ingredient.amount}{UNIT_LABELS[ingredient.unit]}</span>
                          {settings.showKcal && (
                            <span className="bg-gray-100 text-gray-500 text-[10px] font-bold px-1.5 py-0.5 rounded-md min-w-[40px] text-center">
                              {ingredient.kcal}
                            </span>
                          )}
                        </div>
                      </div>
                    ))
                  ) : (
                    <div className="text-center py-6 text-gray-400 text-sm">
                      재료를 추가해주세요
                    </div>
                  )}
                </div>
              </div>
            )}

            {/* 칼로리 카드 - 설정에 따라 표시 여부 결정 */}
            {settings.showKcal && (
              <div className="rounded-[1.5rem] bg-[#F7F7F7] p-6">
                <h2 className="text-base font-bold text-gray-900 mb-6">영양성분</h2>

                <div className="flex justify-between items-end mb-6">
                  <span className="text-gray-600 font-medium text-sm">총 열량</span>
                  <span className="text-2xl font-black text-[#FF8A00]">
                    {totalKcal} <span className="text-sm font-bold text-[#FF8A00]">kcal</span>
                  </span>
                </div>

                <div className="flex flex-col gap-5">
                  {ingredients.map((ingredient) => {
                    const percentage = totalKcal > 0 ? (ingredient.kcal / totalKcal) * 100 : 0;
                    return (
                      <div key={ingredient.id}>
                        <div className="flex justify-between text-xs font-bold text-gray-800 mb-1.5">
                          <span>{ingredient.name}</span>
                          <span>{ingredient.kcal} Kcal</span>
                        </div>
                        <div className="h-1.5 w-full bg-gray-200 rounded-full overflow-hidden">
                          <div
                            className={`h-full rounded-full bg-gradient-to-r from-[#FF8A00] to-[#FF6B00]`}
                            style={{ width: `${percentage}%` }}
                          />
                        </div>
                      </div>
                    );
                  })}
                  {ingredients.length === 0 && (
                    <div className="text-center py-2 text-gray-400 text-sm">
                      영양 성분 데이터가 없습니다
                    </div>
                  )}
                </div>
              </div>
            )}

          </div>
        </div>
      </div>
    </aside>
  );
};
