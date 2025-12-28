import { create } from "zustand";
import { devtools } from "zustand/middleware";
import type { Ingredient } from "../types/Ingredient";

type EditorIngredient = Ingredient; // 그대로 써도 되고, 필요하면 에디터용 타입 따로 만들어도 됨

interface RecipeEditorState {
  // ===== Editor(Client) State =====
  title: string;
  tags: string[];
  ingredients: EditorIngredient[];
  contentHtml: string;

  // ===== Derived =====
  totalKcal: number;

  // ===== Actions =====
  setTitle: (title: string) => void;

  addTag: (tag: string) => void;
  removeTag: (tag: string) => void;
  setTags: (tags: string[]) => void;

  addIngredient: (ingredient: EditorIngredient) => void;
  removeIngredient: (id: number) => void;
  setIngredients: (ingredients: EditorIngredient[]) => void;

  setContentHtml: (html: string) => void;

  // 편집 상태 초기화(페이지 나갈 때/새 글 작성 등)
  reset: () => void;

  // (선택) 서버에서 가져온 "게시글 수정 초기값"을 store에 주입할 때 사용
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
};

export const useRecipeEditorStore = create<RecipeEditorState>()(
  devtools((set) => ({
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
