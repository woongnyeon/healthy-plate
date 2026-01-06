import { EditorContent, Editor } from "@tiptap/react";


export const RecipeEditor = ({ editor }: { editor: Editor }) => {
  return (
    <div className="mt-10 w-full max-w-[720px] bg-white px-6 py-6">
      <EditorContent editor={editor} />
    </div>
  );
};
