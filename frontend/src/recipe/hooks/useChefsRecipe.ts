import type { Recipe } from "../types/recipe";
import { useEffect, useState } from "react";

export const useChefsRecipe = () => {
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
        title: "오감자",
        content: "나여 ㅋ 백종원",
        thumbnail:
          "https://images.unsplash.com/photo-1573126161855-f9633aa8a9f0?q=80&w=1374&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "백종원",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18"
      },
      {
        title: "오감자",
        content: "나여 ㅋ 백종원",
        thumbnail:
          "https://images.unsplash.com/photo-1573126161855-f9633aa8a9f0?q=80&w=1374&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "백종원",
        like_count: 31,
        comment_count: 12,category: "한식",
        created_at: "2025-12-18"
      },
      {
        title: "오감자",
        content: "나여 ㅋ 백종원",
        thumbnail:
          "https://images.unsplash.com/photo-1573126161855-f9633aa8a9f0?q=80&w=1374&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "백종원",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18"
      },
      {
        title: "오감자",
        content: "나여 ㅋ 백종원",
        thumbnail:
          "https://images.unsplash.com/photo-1573126161855-f9633aa8a9f0?q=80&w=1374&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "백종원",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18"
      },
      {
        title: "오감자",
        content: "나여 ㅋ 백종원",
        thumbnail:
          "https://images.unsplash.com/photo-1573126161855-f9633aa8a9f0?q=80&w=1374&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "백종원",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18"
      },
      {
        title: "오감자",
        content: "나여 ㅋ 백종원",
        thumbnail:
          "https://images.unsplash.com/photo-1573126161855-f9633aa8a9f0?q=80&w=1374&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "백종원",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18"
      },
      {
        title: "오감자",
        content: "나여 ㅋ 백종원",
        thumbnail:
          "https://images.unsplash.com/photo-1573126161855-f9633aa8a9f0?q=80&w=1374&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "백종원",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18"
      },
      {
        title: "오감자",
        content: "나여 ㅋ 백종원",
        thumbnail:
          "https://images.unsplash.com/photo-1573126161855-f9633aa8a9f0?q=80&w=1374&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "백종원",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18"
      },
      {
        title: "오감자",
        content: "나여 ㅋ 백종원",
        thumbnail:
          "https://images.unsplash.com/photo-1573126161855-f9633aa8a9f0?q=80&w=1374&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "백종원",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18"
      },
      {
        title: "오감자",
        content: "나여 ㅋ 백종원",
        thumbnail:
          "https://images.unsplash.com/photo-1573126161855-f9633aa8a9f0?q=80&w=1374&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "백종원",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18"
      },
      {
        title: "오감자",
        content: "나여 ㅋ 백종원",
        thumbnail:
          "https://images.unsplash.com/photo-1573126161855-f9633aa8a9f0?q=80&w=1374&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "백종원",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18"
      },
    ];
  };

  return {
    recipes,
    isLoading,
  };
};
