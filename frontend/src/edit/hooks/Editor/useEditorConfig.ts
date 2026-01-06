import { useEditor } from "@tiptap/react";
import StarterKit from "@tiptap/starter-kit";
import Link from "@tiptap/extension-link";
import Placeholder from "@tiptap/extension-placeholder";
import Underline from "@tiptap/extension-underline";
import Image from "@tiptap/extension-image";
import Youtube from "@tiptap/extension-youtube";

interface UseEditorConfigProps {
  initialContent?: string;
  onUpdate: (html: string) => void;
  placeholder?: string;
}

/**
 * Tiptap 에디터 설정을 캡슐화한 훅
 * 에디터 확장 기능 및 설정을 관리합니다.
 */
export const useEditorConfig = ({
  initialContent = "",
  onUpdate,
  placeholder = "레시피를 작성해 주세요…",
}: UseEditorConfigProps) => {
  const editor = useEditor({
    extensions: [
      StarterKit,
      Link.configure({ openOnClick: false }),
      Placeholder.configure({ placeholder }),
      Underline,
      Image,
      Youtube.configure({ inline: false }),
    ],
    content: initialContent,
    editorProps: {
      attributes: {
        class:
          "ProseMirror outline-none focus:outline-none " +
          "prose prose-sm max-w-none leading-7 text-gray-800 " +
          "min-h-[360px]",
      },
    },
    onUpdate: ({ editor }) => {
      onUpdate(editor.getHTML());
    },
  });

  return editor;
};
