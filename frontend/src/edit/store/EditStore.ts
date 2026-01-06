import { create } from "zustand";
import { devtools } from "zustand/middleware";
import type { Ingredient } from "../types/Ingredient";

type EditorIngredient = Ingredient;

const LOCAL_STORAGE_KEY = "recipe-editor-draft";

export interface RecipeEditorState {
  // ===== 클라이언트 상태 정의 =====
  title: string;
  tags: string[];
  ingredients: EditorIngredient[];
  contentHtml: string;
  totalKcal: number;

  // ===== 액션 =====
  setTitle: (title: string) => void;

  addTag: (tag: string) => void;
  removeTag: (tag: string) => void;
  setTags: (tags: string[]) => void;

  addIngredient: (ingredient: EditorIngredient) => void;
  removeIngredient: (id: number) => void;
  setIngredients: (ingredients: EditorIngredient[]) => void;

  setContentHtml: (html: string) => void;

  // 임시 저장 (Local Storage)
  saveToLocalStorage: () => void;
  loadFromLocalStorage: () => boolean; // 성공 여부 반환

  // 표시 설정 관련 상태
  settings: {
    showIngredients: boolean;
    showKcal: boolean;
  }

  toggleSetting: (key: "showIngredients" | "showKcal") => void;

  // 편집 상태 초기화
  reset: () => void;

  // 서버 데이터 등 외부 데이터 주입
  hydrate: (payload: {
    title?: string;
    tags?: string[];
    ingredients?: EditorIngredient[];
    contentHtml?: string;
  }) => void;
}

const computeTotalKcal = (ingredients: EditorIngredient[]) =>
  ingredients.reduce((sum, i) => sum + (i.kcal ?? 0), 0);

const initialState = {
  title: "",
  tags: [] as string[],
  ingredients: [] as EditorIngredient[],
  contentHtml: "",
  totalKcal: 0,
  settings: {
    showIngredients: true,
    showKcal: true,
  },
};

export const useRecipeEditorStore = create<RecipeEditorState>()(
  devtools((set, get) => ({
    ...initialState,

    setTitle: (title) => set({ title }),

    addTag: (tag) =>
      set((state) => {
        const t = tag.trim();
        if (!t) return state;
        if (state.tags.includes(t)) return state;
        return { tags: [...state.tags, t] };
      }),

    removeTag: (tag) =>
      set((state) => ({
        tags: state.tags.filter((t) => t !== tag),
      })),

    setTags: (tags) =>
      set({
        tags: Array.from(new Set(tags.map((t) => t.trim()).filter(Boolean))),
      }),

    addIngredient: (ingredient) =>
      set((state) => {
        const next = [...state.ingredients, ingredient];
        return {
          ingredients: next,
          totalKcal: computeTotalKcal(next),
        };
      }),

    removeIngredient: (id) =>
      set((state) => {
        const next = state.ingredients.filter((i) => i.id !== id);
        return {
          ingredients: next,
          totalKcal: computeTotalKcal(next),
        };
      }),

    setIngredients: (ingredients) =>
      set({
        ingredients,
        totalKcal: computeTotalKcal(ingredients),
      }),

    setContentHtml: (contentHtml) => set({ contentHtml }),

    saveToLocalStorage: () => {
      const { title, tags, ingredients, contentHtml, settings } = get();
      const draft = { title, tags, ingredients, contentHtml, settings };
      localStorage.setItem(LOCAL_STORAGE_KEY, JSON.stringify(draft));
      alert("임시 저장되었습니다.");
    },

    loadFromLocalStorage: () => {
      const stored = localStorage.getItem(LOCAL_STORAGE_KEY);
      if (!stored) return false;

      try {
        const parsed = JSON.parse(stored);
        set((state) => ({
          ...state,
          title: parsed.title || "",
          tags: parsed.tags || [],
          ingredients: parsed.ingredients || [],
          contentHtml: parsed.contentHtml || "",
          totalKcal: computeTotalKcal(parsed.ingredients || []),
          settings: parsed.settings || state.settings,
        }));
        return true;
      } catch (e) {
        console.error("Failed to load draft", e);
        return false;
      }
    },

    // toggle로 보여줄 지 말지 결정
    toggleSetting: (key) => {
      set((state) => ({
        settings: {
          ...state.settings,
          [key]: !state.settings[key],
        }
      }))
    },

    reset: () => set({ ...initialState }),

    hydrate: (payload) =>
      set((state) => {
        const nextTitle = payload.title ?? state.title;
        const nextTags = payload.tags ?? state.tags;
        const nextIngredients = payload.ingredients ?? state.ingredients;
        const nextContentHtml = payload.contentHtml ?? state.contentHtml;

        return {
          title: nextTitle,
          tags: Array.from(
            new Set(nextTags.map((t) => t.trim()).filter(Boolean))
          ),
          ingredients: nextIngredients,
          contentHtml: nextContentHtml,
          totalKcal: computeTotalKcal(nextIngredients),
        };
      }),
  }))
);
