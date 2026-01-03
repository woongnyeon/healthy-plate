import { Editor } from "@tiptap/react"
import { useEffect, useRef, useState, type ChangeEvent } from "react"

export const useToolbar = (editor: Editor) => {
    const fileInputRef = useRef<HTMLInputElement>(null);
    const popupRef = useRef<HTMLDivElement>(null);
    const [isLinkPopupOpen, setIsLinkPopupOpen] = useState(false);
    const [linkUrl, setLinkUrl] = useState("");

    useEffect(() => {
        if (editor && isLinkPopupOpen) {
            const currentLink = editor.getAttributes("link").href;
            if (currentLink) {
                setLinkUrl(currentLink || "");
            }
        } 
    }, [editor, isLinkPopupOpen]);


    const toggleLinkPopup = () => {
        if (isLinkPopupOpen) {
            setIsLinkPopupOpen(false);
        } else {
            const prev = editor.getAttributes("link").href;
            setLinkUrl(prev || "");
            setIsLinkPopupOpen(true);
        }
    }

    const applyLink = () => {
        if (linkUrl === null) return;
        if (linkUrl.trim() === "") {
            editor.chain().focus().extendMarkRange("link").unsetLink().run();
        } else {
            editor.chain().focus().extendMarkRange("link").setLink({ href: linkUrl.trim() }).run();
        }
        setIsLinkPopupOpen(false);
    };

    const handleKeyDown = (e: React.KeyboardEvent) => {
        if (e.key === 'Enter') {
            applyLink();
        }
    }

    const addImage = () => {
        fileInputRef.current?.click();
    };

    const handleImageUpload = (e: ChangeEvent<HTMLInputElement>) => {
        const file = e.target.files?.[0];
        if (!file) return;

        // TODO: 실제 서비스에서는 여기서 서버로 이미지를 업로드하고 반환된 URL을 사용해야 합니다.
        const url = URL.createObjectURL(file);
        
        // Velog 스타일: 이미지를 바로 렌더링하지 않고 마크다운 텍스트로 삽입
        editor.chain().focus().insertContent(`![](${url})`).run();

        e.target.value = "";
    };

    return {
        fileInputRef,
        setIsLinkPopupOpen,
        toggleLinkPopup,
        applyLink,
        handleKeyDown,
        addImage,
        handleImageUpload,
        isLinkPopupOpen,
        linkUrl,
        setLinkUrl,
        popupRef,
    }
}