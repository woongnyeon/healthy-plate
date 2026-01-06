import type { TagEditorProps } from "../../../types/Tag";
import { HashTagChip } from "./HashTagChip";
import { TagInput } from "./TagInput";


export const TagEditor = ({ tags, onAdd, onRemove }: TagEditorProps) => {
  return (
    <div className="mt-10 flex flex-wrap items-center gap-2">
      {tags.map((tag) => (
        <HashTagChip key={tag} label={tag} onRemove={() => onRemove(tag)} />
      ))}

      <TagInput
        onSubmit={onAdd}
        onBackspace={() => {
          if (tags.length > 0) {
            onRemove(tags[tags.length - 1]);
          }
        }}
      />
    </div>
  );
};
