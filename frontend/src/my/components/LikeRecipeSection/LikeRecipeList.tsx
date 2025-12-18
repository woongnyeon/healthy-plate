export interface LikeRecipeCardData {
  id: string;
  title: string;
  desc: string;
  thumbnail: string;
  authorName: string;
  authorAvatar: string;
  likeCount: number | string; // 1.2k 같은 문자열도 가능
  liked?: boolean; // 우측 상단 하트 배지 표시용
}

interface LikeRecipeSectionProps {
  title?: string;
  items: LikeRecipeCardData[];
  onClickAll?: () => void;
  onToggleLike?: (id: string) => void;
  onClickCard?: (id: string) => void;
}

export const LikeRecipeList = ({
  title = "내가 좋아요한 레시피",
  items,
  onClickAll,
  onToggleLike,
  onClickCard,
}: LikeRecipeSectionProps) => {
  return (
    <section className="w-full">
      {/* 헤더 */}
      <div className="mb-4 flex items-center justify-between">
        <div className="flex items-center gap-2">
          <span className="text-red-500">♡</span>
          <h2 className="text-base font-semibold text-gray-900">{title}</h2>
        </div>

        <button
          onClick={onClickAll}
          className="text-xs text-gray-500 hover:text-gray-700"
        >
          전체보기
        </button>
      </div>

      {/* 카드 그리드 */}
      <div className="grid grid-cols-1 gap-6 sm:grid-cols-2 lg:grid-cols-4">
        {items.map((item) => (
          <LikeRecipeCard
            key={item.id}
            item={item}
            onToggleLike={onToggleLike}
            onClick={() => onClickCard?.(item.id)}
          />
        ))}
      </div>
    </section>
  );
};

interface LikeRecipeCardProps {
  item: LikeRecipeCardData;
  onToggleLike?: (id: string) => void;
  onClick?: () => void;
}

const LikeRecipeCard = ({
  item,
  onToggleLike,
  onClick,
}: LikeRecipeCardProps) => {
  return (
    <article
      className="group overflow-hidden rounded-2xl border bg-white shadow-sm transition hover:shadow-md"
      onClick={onClick}
      role="button"
      tabIndex={0}
    >
      {/* 썸네일 */}
      <div className="relative aspect-[4/3] w-full overflow-hidden">
        <img
          src={item.thumbnail}
          alt={item.title}
          className="h-full w-full object-cover transition-transform duration-300 group-hover:scale-[1.02]"
        />

        {/* 우측 상단 하트 배지 */}
        <button
          className="absolute right-3 top-3 grid h-8 w-8 place-items-center rounded-full bg-white/90 shadow"
          onClick={(e) => {
            e.stopPropagation();
            onToggleLike?.(item.id);
          }}
          aria-label="like"
        >
          <span className="text-orange-500">♡</span>
        </button>
      </div>

      {/* 본문 */}
      <div className="p-4">
        <h3 className="line-clamp-1 text-sm font-semibold text-gray-900">
          {item.title}
        </h3>
        <p className="mt-2 line-clamp-2 text-xs leading-5 text-gray-500">
          {item.desc}
        </p>

        {/* 하단 메타 */}
        <div className="mt-4 flex items-center justify-between">
          <div className="flex items-center gap-2 min-w-0">
            <img
              src={item.authorAvatar}
              alt={item.authorName}
              className="h-6 w-6 rounded-full object-cover"
            />
            <span className="truncate text-xs text-gray-600">
              {item.authorName}
            </span>
          </div>

          <div className="flex items-center gap-1 text-xs text-gray-500">
            <span>♡</span>
            <span>{item.likeCount}</span>
          </div>
        </div>
      </div>
    </article>
  );
};
