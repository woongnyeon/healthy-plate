import { useEffect, useState } from "react";
import type { Recipe } from "../types/recipe";

export const useLatestRecipe = () => {
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
        title: "도토리 국수 같노",
        content: "모수 갔다옴 ㅋ",
        thumbnail:
          "https://images.unsplash.com/photo-1585032226651-759b368d7246?q=80&w=1292&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "안성재명",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18"
      },
      {
        title: "도토리 국수 같노",
        content: "모수 갔다옴 ㅋ",
        thumbnail:
          "https://images.unsplash.com/photo-1585032226651-759b368d7246?q=80&w=1292&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "안성재명",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18"
      },
      {
        title: "도토리 국수 같노",
        content: "모수 갔다옴 ㅋ",
        thumbnail:
          "https://images.unsplash.com/photo-1585032226651-759b368d7246?q=80&w=1292&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "안성재명",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18"
      },
      {
        title: "도토리 국수 같노",
        content: "모수 갔다옴 ㅋ",
        thumbnail:
          "https://images.unsplash.com/photo-1585032226651-759b368d7246?q=80&w=1292&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "안성재명",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18"
      },
      {
        title: "도토리 국수 같노",
        content: "모수 갔다옴 ㅋ",
        thumbnail:
          "https://images.unsplash.com/photo-1585032226651-759b368d7246?q=80&w=1292&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "안성재명",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18"
      },
      {
        title: "도토리 국수 같노",
        content: "모수 갔다옴 ㅋ",
        thumbnail:
          "https://images.unsplash.com/photo-1585032226651-759b368d7246?q=80&w=1292&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "안성재명",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18"
      },
      {
        title: "도토리 국수 같노",
        content: "모수 갔다옴 ㅋ",
        thumbnail:
          "https://images.unsplash.com/photo-1585032226651-759b368d7246?q=80&w=1292&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "안성재명",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18"
      },
      {
        title: "도토리 국수 같노",
        content: "모수 갔다옴 ㅋ",
        thumbnail:
          "https://images.unsplash.com/photo-1585032226651-759b368d7246?q=80&w=1292&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "안성재명",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18"
      },
      {
        title: "도토리 국수 같노",
        content: "모수 갔다옴 ㅋ",
        thumbnail:
          "https://images.unsplash.com/photo-1585032226651-759b368d7246?q=80&w=1292&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "안성재명",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18"
      },
      {
        title: "도토리 국수 같노",
        content: "모수 갔다옴 ㅋ",
        thumbnail:
          "https://images.unsplash.com/photo-1585032226651-759b368d7246?q=80&w=1292&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "안성재명",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18"
      },
      {
        title: "도토리 국수 같노",
        content: "모수 갔다옴 ㅋ",
        thumbnail:
          "https://images.unsplash.com/photo-1585032226651-759b368d7246?q=80&w=1292&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "안성재명",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18"
      },
      {
        title: "도토리 국수 같노",
        content: "모수 갔다옴 ㅋ",
        thumbnail:
          "https://images.unsplash.com/photo-1585032226651-759b368d7246?q=80&w=1292&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "안성재명",
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
