/* eslint-disable react-hooks/exhaustive-deps */
import { useEffect, useRef } from "react";
import { useRecipeEditorStore } from "../store/EditStore";
import { useShallow } from "zustand/react/shallow";
import type { RecipeEditorState } from "../store/EditStore";

// 도메인별 훅
import { useTitle } from "./Content/useTitle";
import { useEditTags } from "./Tag/useEditTags";
import { useEditIngredients } from "./Ingredient/useEditIngredients";
import { useEditorConfig } from "./Editor/useEditorConfig";

/**
 * 레시피 편집 페이지의 Facade 훅
 * 
 * 각 도메인별 훅을 조합하여 페이지 레벨의 상태와 액션을 제공합니다.
 * - useTitle: 제목 편집
 * - useEditTags: 태그 편집
 * - useEditIngredients: 재료 편집 (검색, 추가, 삭제)
 * - useEditorConfig: Tiptap 에디터 설정
 */
export const useEdit = () => {
  // 1. Store에서 필요한 상태와 액션 가져오기
  const {
    contentHtml,
    setContentHtml,
    saveToLocalStorage,
    loadFromLocalStorage,
  } = useRecipeEditorStore(
    useShallow((state: RecipeEditorState) => ({
      contentHtml: state.contentHtml,
      setContentHtml: state.setContentHtml,
      saveToLocalStorage: state.saveToLocalStorage,
      loadFromLocalStorage: state.loadFromLocalStorage,
    }))
  );

  const isHydratingRef = useRef(false);
  const hasCheckedDraftRef = useRef(false);

  // 2. 임시 저장 로직 (페이지 로드 시 1회)
  useEffect(() => {
    if (hasCheckedDraftRef.current) return;
    hasCheckedDraftRef.current = true;

    const hasData = localStorage.getItem("recipe-editor-draft");
    if (hasData) {
      if (window.confirm("임시 저장된 글이 있습니다. 불러오시겠습니까?")) {
        const success = loadFromLocalStorage();
        if (success) {
          isHydratingRef.current = true;
        }
      }
    }
  }, [loadFromLocalStorage]);

  // 3. Tiptap 에디터 설정
  const editor = useEditorConfig({
    initialContent: contentHtml,
    onUpdate: setContentHtml,
  });

  // 4. 콘텐츠 HTML -> 에디터 동기화 (Hydration 시에만 1회성)
  useEffect(() => {
    if (editor && isHydratingRef.current && contentHtml) {
      editor.commands.setContent(contentHtml);
      isHydratingRef.current = false;
    }
  }, [contentHtml, editor]);

  // 5. 도메인별 훅 사용
  const titleProps = useTitle({
    maxLength: 60,
    allowNewLine: false,
  });

  const tagProps = useEditTags();

  const ingredientData = useEditIngredients();

  // 6. 페이지 액션
  const handleSaveDraft = () => {
    saveToLocalStorage();
  };

  const handleSubmit = () => {
    console.log("Submit recipe");
    // TODO: 서버 전송 로직
  };

  const submitDisabled = !titleProps.title.trim();

  // 7. Props 조합하여 반환
  return {
    editor,
    
    titleProps,
    
    tagProps,
    
    ingredientProps: {
      ingredients: ingredientData.ingredients,
      totalKcal: ingredientData.totalKcal,
      settings: ingredientData.settings,
      onRemove: ingredientData.onRemove,
      searchValues: ingredientData.searchValues,
      ingredientListProps: ingredientData.ingredientListProps,
      onToggle: ingredientData.onToggle,
    },

    actions: {
      onSaveDraft: handleSaveDraft,
      onSubmit: handleSubmit,
      submitDisabled,
    },
  };
};
