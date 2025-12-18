import { DraftList } from "./DraftList";

export const DraftSection = () => {
  const drafts = [
    { id: "1", title: "차돌박이 된장찌개", updatedAt: "2025-12-17 14:30" },
    { id: "2", title: "웅웅찬 두루치기", updatedAt: "2025-12-17 14:30" },
    { id: "3", title: "김병년 숙주볶음", updatedAt: "2025-12-17 14:30" },
  ];
  return (
    <DraftList
      items={drafts}
      onEdit={(id) => console.log("edit", id)}
      onDelete={(id) => console.log("delete", id)}
    />
  );
};
