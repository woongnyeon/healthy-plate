import { useEffect, useState } from "react";
import type { Recipe } from "../types/recipe";

export const useRecipeBookDummy = () => {
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
        created_at: "2025-12-18",
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
        title: "오감자",
        content: "나여 ㅋ 백종원",
        thumbnail:
          "https://images.unsplash.com/photo-1573126161855-f9633aa8a9f0?q=80&w=1374&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "백종원",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18",
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
        title: "오감자",
        content: "나여 ㅋ 백종원",
        thumbnail:
          "https://images.unsplash.com/photo-1573126161855-f9633aa8a9f0?q=80&w=1374&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "백종원",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18",
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
        title: "오감자",
        content: "나여 ㅋ 백종원",
        thumbnail:
          "https://images.unsplash.com/photo-1573126161855-f9633aa8a9f0?q=80&w=1374&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "백종원",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18",
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
        title: "오감자",
        content: "나여 ㅋ 백종원",
        thumbnail:
          "https://images.unsplash.com/photo-1573126161855-f9633aa8a9f0?q=80&w=1374&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "백종원",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18",
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
        title: "오감자",
        content: "나여 ㅋ 백종원",
        thumbnail:
          "https://images.unsplash.com/photo-1573126161855-f9633aa8a9f0?q=80&w=1374&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "백종원",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18",
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
        title: "오감자",
        content: "나여 ㅋ 백종원",
        thumbnail:
          "https://images.unsplash.com/photo-1573126161855-f9633aa8a9f0?q=80&w=1374&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "백종원",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18",
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
        title: "오감자",
        content: "나여 ㅋ 백종원",
        thumbnail:
          "https://images.unsplash.com/photo-1573126161855-f9633aa8a9f0?q=80&w=1374&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "백종원",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18",
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
        title: "오감자",
        content: "나여 ㅋ 백종원",
        thumbnail:
          "https://images.unsplash.com/photo-1573126161855-f9633aa8a9f0?q=80&w=1374&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "백종원",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18",
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
        title: "오감자",
        content: "나여 ㅋ 백종원",
        thumbnail:
          "https://images.unsplash.com/photo-1573126161855-f9633aa8a9f0?q=80&w=1374&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "백종원",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18",
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
        title: "오감자",
        content: "나여 ㅋ 백종원",
        thumbnail:
          "https://images.unsplash.com/photo-1573126161855-f9633aa8a9f0?q=80&w=1374&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
        author: "백종원",
        like_count: 31,
        comment_count: 12,
        category: "한식",
        created_at: "2025-12-18",
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
    isLoading,
    recipes,
  };
};
