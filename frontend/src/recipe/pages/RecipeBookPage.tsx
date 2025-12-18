import { useResponsive } from "../../share/hooks/useResonsive";
import { RecipeList } from "../components/RecipeList";
import { SearchIntro } from "../components/SearchIntro";
import { SearchBar } from "../components/SearchBar";
import { useRecipeBook } from "../hooks/useRecipeBook";
import { useRecipeBookDummy } from "../hooks/useRecipeBookDummy";

export const RecipeBookPage = () => {
  const { recipes, isLoading } = useRecipeBookDummy();
  const { query, setQuery, filterRecipes, isSearching } =
    useRecipeBook(recipes);
  const { gridCols } = useResponsive();
  const isSticky = query.trim().length > 0;

  if (isLoading) return <div>...로딩중</div>;

  return (
    <div>
      {/* 검색 영역 */}

      <SearchIntro onTagClick={setQuery} />

      <div
        className={`
    transition-all
    ${isSticky ? "sticky top-14 z-10 bg-bg" : ""}
  `}
      >
        <div className="w-full py-2">
          <SearchBar value={query} onChange={setQuery} />
        </div>
      </div>

      {/* 결과 영역 */}
      {isSearching && filterRecipes && (
        <div className="mt-6">
          <div className="flex items-center mb-4 gap-2">
            <span className="text-primary font-bold text-card-title">
              추천 레시피
            </span>
            <span className="text-main text-card-title">
              {filterRecipes.length}
            </span>
          </div>

          <div
            className={`grid gap-4 ${
              gridCols === 3 ? "grid-cols-4" : "grid-cols-2"
            }`}
          >
            <RecipeList recipes={filterRecipes} />
          </div>
        </div>
      )}
    </div>
  );
};
