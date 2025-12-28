import { Editor } from "@tiptap/react";
import { EditToolBtn } from "./EditToolBtn";
import { EditToolIconBtn } from "./EditToolIconBtn";
import { Divider } from "./Divider";

interface EditToolBarProps {
  editor: Editor;
};

export const EditToolBar = ({ editor }: EditToolBarProps) =>  {
  if (!editor) return null;

  return (
    <div className="flex justify-center">
      <div
        className="
          flex items-center gap-1
          rounded-xl border border-gray-100 bg-white
          px-3 py-2
          shadow-[0_1px_10px_rgba(0,0,0,0.03)]
        "
      >
        {/* Heading */}
        <EditToolBtn
          active={editor.isActive("heading", { level: 1 })}
          onClick={() =>
            editor.chain().focus().toggleHeading({ level: 1 }).run()
          }
        >
          H1
        </EditToolBtn>
        <EditToolBtn
          active={editor.isActive("heading", { level: 2 })}
          onClick={() =>
            editor.chain().focus().toggleHeading({ level: 2 }).run()
          }
        >
          H2
        </EditToolBtn>
        <EditToolBtn
          active={editor.isActive("heading", { level: 3 })}
          onClick={() =>
            editor.chain().focus().toggleHeading({ level: 3 }).run()
          }
        >
          H3
        </EditToolBtn>

        <Divider />

        {/* Text style */}
        <EditToolBtn
          active={editor.isActive("bold")}
          onClick={() => editor.chain().focus().toggleBold().run()}
        >
          B
        </EditToolBtn>
        <EditToolBtn
          active={editor.isActive("italic")}
          onClick={() => editor.chain().focus().toggleItalic().run()}
        >
          I
        </EditToolBtn>

        <Divider />

        {/* List */}
        <EditToolBtn
          active={editor.isActive("bulletList")}
          onClick={() => editor.chain().focus().toggleBulletList().run()}
        >
          •
        </EditToolBtn>
        <EditToolBtn
          active={editor.isActive("orderedList")}
          onClick={() => editor.chain().focus().toggleOrderedList().run()}
        >
          1.
        </EditToolBtn>

        <Divider />

        {/* Quote */}
        <EditToolBtn
          active={editor.isActive("blockquote")}
          onClick={() => editor.chain().focus().toggleBlockquote().run()}
        >
          ❝
        </EditToolBtn>

        <Divider />

        {/* Link */}
        <EditToolIconBtn
          onClick={() => {
            const prev = editor.getAttributes("link").href as
              | string
              | undefined;
            const url = window.prompt("링크 URL을 입력하세요", prev || "");
            if (url === null) return;
            if (url.trim() === "") {
              editor.chain().focus().unsetLink().run();
              return;
            }
            editor.chain().focus().setLink({ href: url.trim() }).run();
          }}
        >
          🔗
        </EditToolIconBtn>
      </div>
    </div>
  );
}
