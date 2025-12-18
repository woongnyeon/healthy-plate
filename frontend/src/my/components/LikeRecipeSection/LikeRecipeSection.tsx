import { LikeRecipeList } from "./LikeRecipeList";

export const LikeRecipeSection = () => {
  const items = [
    {
      id: "1",
      title: "초간단 10분 떡볶이",
      desc: "퇴근 후 맥주 한잔과 함께하기 딱 좋은 매콤달콤 떡볶이 레시피입니다.",
      thumbnail: "https://images.unsplash.com/photo-1604908176997-125f25cc500f",
      authorName: "길거리음식러",
      authorAvatar:
        "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?w=120",
      likeCount: 342,
    },
    {
      id: "2",
      title: "깊은 맛 김치찌개",
      desc: "할머니의 비법 그대로 진수성찬을 묻은 김치찌개, 밥 두 공기 순삭!",
      thumbnail: "https://images.unsplash.com/photo-1604908176997-125f25cc500f",
      authorName: "할매손맛",
      authorAvatar:
        "https://images.unsplash.com/photo-1527980965255-d3b416303d12?w=120",
      likeCount: 890,
    },
    {
      id: "3",
      title: "단짠단짠 소불고기",
      desc: "아이들이 제일 좋아하는 반찬 1위, 실패없는 황금 레시피 공개!",
      thumbnail: "https://images.unsplash.com/photo-1540189549336-e6e99c3679fe",
      authorName: "고기마스터",
      authorAvatar:
        "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?w=120",
      likeCount: "1.2k",
    },
    {
      id: "4",
      title: "건강한 야채 비빔밥",
      desc: "냉장고 털기 좋은 날, 건강하고 가볍게 즐기는 한 끼 식사.",
      thumbnail: "https://images.unsplash.com/photo-1546069901-ba9599a7e63c",
      authorName: "그린라이프",
      authorAvatar:
        "https://images.unsplash.com/photo-1544723795-3fb6469f5b39?w=120",
      likeCount: 56,
    },
    {
      id: "5",
      title: "홈메이드 연어 롤",
      desc: "집에서도 전문점처럼 즐기는 신선한 연어롤 만들기.",
      thumbnail: "https://images.unsplash.com/photo-1553621042-f6e147245754",
      authorName: "스시킹",
      authorAvatar:
        "https://images.unsplash.com/photo-1544005313-94ddf0286df2?w=120",
      likeCount: 421,
    },
  ];
  return (
    <LikeRecipeList
      items={items}
      onClickAll={() => console.log("전체보기")}
      onToggleLike={(id) => console.log("like toggle", id)}
      onClickCard={(id) => console.log("card", id)}
    />
  );
};
