import { useEffect, useState } from "react";
import type { Recipe } from "../types/recipe";

export const useTrendRecipe = () => {
  const [recipes, setRecipes] = useState<Recipe[]>([]);
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const data = getDummyData();
    setRecipes(data);
    setIsLoading(false);
  }, []);

  const getDummyData = (): Recipe[] => {
    return [
      {
        title: "초간단 김치찌개",
        content: "자취생도 실패 없는 김치찌개 황금 레시피!",
        thumbnail:
          "https://images.unsplash.com/photo-1626082927389-6cd097cdc6ec",
        author: "요리왕",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18",
      },
      {
        title: "초간단 김치찌개",
        content: "자취생도 실패 없는 김치찌개 황금 레시피!",
        thumbnail:
          "https://images.unsplash.com/photo-1626082927389-6cd097cdc6ec",
        author: "요리왕",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18",
      },
      {
        title: "초간단 김치찌개",
        content: "자취생도 실패 없는 김치찌개 황금 레시피!",
        thumbnail:
          "https://images.unsplash.com/photo-1626082927389-6cd097cdc6ec",
        author: "요리왕",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18",
      },
      {
        title: "초간단 김치찌개",
        content: "자취생도 실패 없는 김치찌개 황금 레시피!",
        thumbnail:
          "https://images.unsplash.com/photo-1626082927389-6cd097cdc6ec",
        author: "요리왕",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18",
      },
      {
        title: "초간단 김치찌개",
        content: "자취생도 실패 없는 김치찌개 황금 레시피!",
        thumbnail:
          "https://images.unsplash.com/photo-1626082927389-6cd097cdc6ec",
        author: "요리왕",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18",
      },
      {
        title: "초간단 김치찌개",
        content: "자취생도 실패 없는 김치찌개 황금 레시피!",
        thumbnail:
          "https://images.unsplash.com/photo-1626082927389-6cd097cdc6ec",
        author: "요리왕",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18",
      },
      {
        title: "초간단 김치찌개",
        content: "자취생도 실패 없는 김치찌개 황금 레시피!",
        thumbnail:
          "https://images.unsplash.com/photo-1626082927389-6cd097cdc6ec",
        author: "요리왕",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18",
      },
      {
        title: "초간단 김치찌개",
        content: "자취생도 실패 없는 김치찌개 황금 레시피!",
        thumbnail:
          "https://images.unsplash.com/photo-1626082927389-6cd097cdc6ec",
        author: "요리왕",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18",
      },
      {
        title: "초간단 김치찌개",
        content: "자취생도 실패 없는 김치찌개 황금 레시피!",
        thumbnail:
          "https://images.unsplash.com/photo-1626082927389-6cd097cdc6ec",
        author: "요리왕",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18",
      },
    ];
  };

  return {
    recipes,
    isLoading,
  };
};
