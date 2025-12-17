import type { Recipe } from "../types/recipe";

interface RecipeCardProps {
  recipe: Recipe;
}

export const RecipeCard = ({ recipe }: RecipeCardProps) => {
  return (
    <article className="w-full max-w-sm rounded-md overflow-hidden bg-card shadow-sm flex flex-col">
      <div className="w-full h-1/3 overflow-hidden">
        <img
          src={recipe.thumbnail}
          alt={recipe.title}
          className="w-full h-full object-cover"
        ></img>
      </div>

      <div className="p-4 space-y-2">
        <h2 className="text-lg font-bold text-main text-card-title">
          {recipe.title}
        </h2>
        <p className="text-sm text-primary text-card-content line-clamp-2">
          {recipe.content}
        </p>
      </div>
      <div className="px-4 pb-4 mt-auto space-y-2">
        <div className="flex justify-between items-center text-card-meta text-secondary pt-2">
          <span>{recipe.comment_count}개의 댓글</span>
        </div>

        <div className="flex pt-4 justify-between items-center text-primary text-meta">
          <span className="text-card-meta text-meta">{recipe.author}</span>
          <div className="flex items-center gap-1">
            <svg className="w-4 h-4 fill-current" viewBox="0 0 24 24">
              <path d="M12 21s-6.716-4.43-9.428-7.143C.859 12.143.5 10.762.5 9.5.5 6.462 2.962 4 6 4c1.657 0 3.156.746 4.125 1.938C11.844 4.746 13.343 4 15 4c3.038 0 5.5 2.462 5.5 5.5 0 1.262-.359 2.643-2.072 4.357C18.716 16.57 12 21 12 21z" />
            </svg>
            <span className="text-card-meta text-meta">
              {recipe.like_count}
            </span>
          </div>
        </div>
      </div>
    </article>
  );
};
