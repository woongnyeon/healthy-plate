// 재료 단위 타입
export type IngredientUnit = 
  | "g"        // 그램
  | "kg"       // 킬로그램
  | "ml"       // 밀리리터
  | "L"        // 리터
  | "ea"       // 개
  | "tbsp"     // 큰술
  | "tsp"      // 작은술
  | "cup";     // 컵

// 단위 표시명 매핑
export const UNIT_LABELS: Record<IngredientUnit, string> = {
  g: "g",
  kg: "kg",
  ml: "ml",
  L: "L",
  ea: "개",
  tbsp: "큰술",
  tsp: "작은술",
  cup: "컵",
};

export interface Ingredient {
  id: number;
  name: string;
  amount: number;
  unit: IngredientUnit;  // 단위 추가
  kcal: number;
}
