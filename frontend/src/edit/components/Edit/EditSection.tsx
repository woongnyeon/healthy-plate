import StarterKit from "@tiptap/starter-kit";
import Link from "@tiptap/extension-link";
import Placeholder from "@tiptap/extension-placeholder";
import { useEditor } from "@tiptap/react";

import { useRecipeEditorStore } from "../../store/EditStore";
import { EditToolBar } from "./EditToolBar";
import { TitleEditor } from "./TitleEditor";
import { TagEditor } from "./Tag/TagEditor";
import { IngredientEditor } from "./Ingredinet/IngredientEditor";
import { RecipeEditor } from "./RecipeEditor";
import { EditActionBar } from "./EditActionBar";

export const EditSection = () => {
  const title = useRecipeEditorStore((s) => s.title);
  const tags = useRecipeEditorStore((s) => s.tags);
  const contentHtml = useRecipeEditorStore((s) => s.contentHtml);

  const setTitle = useRecipeEditorStore((s) => s.setTitle);
  const addTag = useRecipeEditorStore((s) => s.addTag);
  const removeTag = useRecipeEditorStore((s) => s.removeTag);
  const setContentHtml = useRecipeEditorStore((s) => s.setContentHtml);

  const editor = useEditor({
    extensions: [
      StarterKit,
      Link.configure({ openOnClick: false }),
      Placeholder.configure({ placeholder: "레시피를 작성해 주세요…" }),
    ],
    content: contentHtml,
    editorProps: {
      attributes: {
        "data-placeholder": "레시피를 작성해 주세요…",
        class:
          "ProseMirror outline-none focus:outline-none " +
          "prose prose-sm max-w-none leading-7 text-gray-800 " +
          "min-h-[360px]",
      },
    },
    onUpdate: ({ editor }) => {
      setContentHtml(editor.getHTML());
    },
  });

  const saveDraft = () => {
    // store에서 값 꺼내서 저장하면 됨 (getState 사용도 가능)
    console.log("임시 저장");
  };

  const submit = () => {
    console.log("작성 완료");
  };

  if (!editor) return null;

  return (
    <section className="min-w-0">
      <div className="sticky top-0 z-20 bg-white pt-2">
        <div className="flex justify-center">
          <EditToolBar editor={editor} />
        </div>
        <div className="mt-4 border-t border-gray-100" />
      </div>

      <TitleEditor
        title={title}
        onChangeTitle={setTitle}
        allowNewLine={false}
        maxLength={60}
        onEnter={() => editor.commands.focus()}
      />

      <TagEditor tags={tags} onAdd={addTag} onRemove={removeTag} />

      <IngredientEditor />

      <div className="mt-10 w-full max-w-[720px]">
        <div className="max-h-[60vh] overflow-y-auto">
          <RecipeEditor editor={editor} />
        </div>
      </div>

      <EditActionBar
        onSaveDraft={saveDraft}
        onSubmit={submit}
        submitDisabled={title.trim().length === 0}
      />
    </section>
  );
};
