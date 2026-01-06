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

export interface IngredientSearchItem {
  id: number;
  name: string;
  baseAmount?: number;
  baseKcal?: number;
}


export interface IngredientListProps {
  query: string;
  isOpen: boolean;
    
  // 상태
  activeIndex: number;
  sliced: IngredientSearchItem[];
  wrapperRef: React.RefObject<HTMLDivElement | null>;
  name: string;
  amount: string;
  unit: IngredientUnit;
  kcal: string;
  setName: (v: string) => void;
  setAmount: (v: string) => void;
  setUnit: (v: IngredientUnit) => void;
  setKcal: (v: string) => void;
  handleManualAdd: () => void;
  
  // 콜백
  onSelect: (item: IngredientSearchItem) => void;
}

export interface UseIngredientReturn {
  activeIndex: number;
  wrapperRef: React.RefObject<HTMLDivElement | null>;
  sliced: IngredientSearchItem[];
  name: string;
  amount: string;
  unit: IngredientUnit;
  kcal: string;
  setName: (v: string) => void;
  setAmount: (v: string) => void;
  setUnit: (v: IngredientUnit) => void;
  setKcal: (v: string) => void;
  handleManualAdd: () => void;
  onSelect: (item: IngredientSearchItem) => void;
  handleKeyDown: (e: React.KeyboardEvent) => void;
}

export interface IngredientEditorProps {
  ingredients: Ingredient[];
  totalKcal: number;
  settings: {
    showIngredients: boolean;
    showKcal: boolean;
  };
  onRemove: (id: number) => void;

  // 검색 입력 상태
  searchValues: {
    query: string;
    setQuery: (q: string) => void;
    isSearching: boolean;
  };

  // 재료 검색 리스트 props
  ingredientListProps: UseIngredientReturn;

  // 설정 토글 함수
  onToggle: (key: "showIngredients" | "showKcal") => void;
}

export interface IngredientCardProps {
  ingredients: Ingredient[];
  totalKcal: number;
  // 설정 (보이기 여부)
  settings?: {
    showIngredients: boolean;
    showKcal: boolean;
  };
  onRemove: (id: number) => void;
  onToggle: (key: "showIngredients" | "showKcal") => void;
}